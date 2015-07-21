package somossuinos.aptcomplex.domain.apartment;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import somossuinos.aptcomplex.domain.person.Person;

/**
 * @author ceballos
 * @since 2015-07-17
 */
@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @Column(nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 4)
    private String number;

    public String getNumber() {
        return number;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "residence",
            joinColumns = @JoinColumn(name = "apartment_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false)
    )
    private Set<Person> residents = new HashSet<>(0);

    public Set<Person> getResidents() {
        return residents;
    }

    @Version
    @Column(nullable = false)
    private Long version;

    protected Apartment() {
    }

    public Apartment(@NotNull final String number) {
        this.number = number;
    }

}
