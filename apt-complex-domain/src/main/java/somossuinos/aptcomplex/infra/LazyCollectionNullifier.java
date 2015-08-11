package somossuinos.aptcomplex.infra;

import org.hibernate.collection.internal.AbstractPersistentCollection;
import somossuinos.aptcomplex.domain.exception.AptComplexInfraException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ceballos
 * @since 2015-07-22
 */
public class LazyCollectionNullifier {

    private Set<Object> verified = new HashSet<>();

    @SuppressWarnings("unchecked")
    public void execute(final Object obj) {
        verified.clear();

        if (obj instanceof Collection) {
            executeMany((Collection<Object>) obj);

        } else if (obj instanceof Map) {
            executeMany((Map<Object, Object>) obj);

        } else {
            executeSingle(obj);
        }
    }

    private void processFields(final Class cls, final Object obj) {
        for (final Field field : cls.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object value = field.get(obj);

                if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                    if (value != null) {
                        if (AbstractPersistentCollection.class.isAssignableFrom(value.getClass()) &&
                                !((AbstractPersistentCollection) value).wasInitialized()) {
                            field.set(obj, null);

                        } else if (Collection.class.isAssignableFrom(field.getType())) {
                            executeMany((Collection) value);

                        } else if (Map.class.isAssignableFrom(field.getType())) {
                            executeMany((Map) value);
                        }
                    }

                } else {
                    executeSingle(value);
                }

            } catch (IllegalAccessException e) {
                throw new AptComplexInfraException(e);
            }
        }
    }
    @SuppressWarnings("unchecked")
    private void executeSingle(final Object obj) {
        if (obj != null) {
            if (!verified.contains(obj)) {
                verified.add(obj);
                Class cls = obj.getClass();

                processFields(cls, obj);

                while (cls.getSuperclass() != null && !cls.getSuperclass().equals(Object.class)) {
                    cls = cls.getSuperclass();
                    Object superObj = cls.cast(obj);
                    processFields(cls, superObj);
                }
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

    public void executeMany(final Map<Object, Object> objs) {
        if (!verified.contains(objs)) {
            verified.add(objs);

            for (final Object obj : objs.values()) {
                executeSingle(obj);
            }
        }
    }
}
