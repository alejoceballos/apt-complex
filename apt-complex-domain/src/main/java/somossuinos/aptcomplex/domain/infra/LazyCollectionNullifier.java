package somossuinos.aptcomplex.domain.infra;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.collection.internal.AbstractPersistentCollection;
import org.jadira.usertype.dateandtime.joda.PersistentDateMidnight;
import somossuinos.aptcomplex.domain.exception.AptComplexInfraException;

/**
 * @author ceballos
 * @since 2015-07-22
 */
public class LazyCollectionNullifier {

    private Set<Collection> verified = new HashSet<>();

    @SuppressWarnings("unchecked")
    public void execute(final Object obj) {
        verified.clear();

        if (obj instanceof Collection) {
            executeMany((Collection<Object>) obj);

        } else {
            executeSingle(obj);
        }
    }

    @SuppressWarnings("unchecked")
    private void executeSingle(final Object obj) {
        Class cls = obj.getClass();

        for (final Field field : cls.getDeclaredFields()) {
            try {
                if (Collection.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    final Object value = field.get(obj);

                    if (value != null) {
                        if (AbstractPersistentCollection.class.isAssignableFrom(value.getClass()) &&
                                !((AbstractPersistentCollection) value).wasInitialized()) {
                            field.set(obj, null);

                        } else {
                            executeMany((Collection) value);
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                throw new AptComplexInfraException(e);
            }
        }
    }

    public void executeMany(final Collection<Object> objs) {
        if (!verified.contains(objs)) {
            verified.add(objs);

            for (final Object obj : objs) {
                executeSingle(obj);
            }
        }
    }
}
