package somossuinos.aptcomplex.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * When passing JSON around, it's good to use a standard text representation of
 * the date, rather than the full details of a Joda DateTime object. Therefore,
 * this will serialize the value to the ISO-8601 standard:
 *
 * <pre>yyyy-MM-dd'T'HH:mm:ss.SSSZ</pre>
 *
 * This can then be parsed by a JavaScript library such as moment.js.
 * <p>
 *     <b>Reference:</b> http://stackoverflow.com/questions/29636831/serializing-joda-datetime-with-jackson-and-spring
 * </p>
 */
public class JsonJodaDateTimeSerializer extends JsonSerializer<DateTime> {

    private static final DateTimeFormatter FORMATTER = ISODateTimeFormat.dateTime();

    @Override
    public void serialize(final DateTime value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {

        gen.writeString(FORMATTER.print(value));
    }

}