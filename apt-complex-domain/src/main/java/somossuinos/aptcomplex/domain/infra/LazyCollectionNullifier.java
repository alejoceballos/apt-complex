package somossuinos.aptcomplex.domain.infra;

import java.lang.reflect.Field;
import java.util.Collection;
import org.hibernate.collection.internal.AbstractPersistentCollection;
import somossuinos.aptcomplex.domain.exception.AptComplexInfraException;

/**
 * @author ceballos
 * @since 2015-07-22
 */
public class LazyCollectionNullifier {

    @SuppressWarnings("unchecked")
    public static void execute(final Object obj) {
        if (obj instanceof Collection) {
            executeMany((Collection<Object>) obj);
        } else {
            executeSingle(obj);
        }
    }

    private static void executeSingle(final Object obj) {
        Class cls = obj.getClass();

        for (final Field field : cls.getDeclaredFields()) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    final Object value = field.get(obj);

                    if (AbstractPersistentCollection.class.isAssignableFrom(value.getClass()) &&
                            !((AbstractPersistentCollection) value).wasInitialized()) {
                        field.set(obj, null);
                    }

                } catch (IllegalAccessException e) {
                    throw new AptComplexInfraException(e);
                }
            }
        }
    }

    public static void executeMany(final Collection<Object> objs) {
        for (final Object obj : objs) {
            executeSingle(obj);
        }
    }
}
