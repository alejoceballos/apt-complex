package somossuinos.aptcomplex.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * @author ceballos
 * @since 2015-08-04
 */
@Embeddable
public class ReferenceMonth {

    @NotNull
    @Column(name = "year", nullable = false)
    private Short year;

    public Short getYear() {
        return year;
    }

    @NotNull
    @Column(name = "month", nullable = false)
    private Short month;

    public Short getMonth() {
        return month;
    }

    protected ReferenceMonth() {
    }

    public ReferenceMonth(final Short year, final Short month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public String toString() {
        return "ReferenceMonth{" +
                "year=" + year +
                ", month=" + month +
                '}';
    }
}
