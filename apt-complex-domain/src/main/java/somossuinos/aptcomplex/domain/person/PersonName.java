package somossuinos.aptcomplex.domain.person;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

/**
 * @since 2015-07-17
 */
@Embeddable
public class PersonName {

    @Column(length = 50)
    private String firstName;

    @Column(length = 255)
    private String middleName;

    @Column(length = 50)
    private String lastName;

    protected PersonName() {
    }

    public PersonName(final String firstName, final String middleName, final String lastName) {
        this.firstName = StringUtils.isBlank(firstName) ? null : firstName.trim();
        this.middleName = StringUtils.isBlank(middleName) ? null : middleName.trim();
        this.lastName = StringUtils.isBlank(lastName) ? null : lastName.trim();
    }

    public PersonName(final String name) {
        final String[] slices = name.split(" ");
        final List<String> names = new ArrayList<>();

        for (final String nm : slices) {
            if (StringUtils.isNotBlank(nm)) {
                names.add(nm.trim());
            }
        }

        if (names.size() > 0) {
            firstName = names.get(0);

            if (names.size() > 1) {
                lastName = names.get(names.size() - 1);
                names.remove(names.size() - 1);
            }

            names.remove(0);
        }

        if (names.size() > 0) {
            middleName = StringUtils.join(names.toArray(), ' ');
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        final String n1 = StringUtils.isBlank(firstName) ? "" : firstName.trim();
        final String n2 = StringUtils.isBlank(middleName) ? n1 : (n1 + " " + middleName.trim()).trim();
        return StringUtils.isBlank(lastName) ? n2 : (n2 + " " + lastName.trim()).trim();
    }
}
