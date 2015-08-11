package somossuinos.aptcomplex.infra;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
public class JsonJodaDateTimeDeserializer extends JsonDeserializer<DateTime> {

    public static final String PATTERN_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String PATTERN_GMT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final DateTimeFormatter FORMATTER_Z = DateTimeFormat.forPattern(PATTERN_Z);
    private static final DateTimeFormatter FORMATTER_GMT = DateTimeFormat.forPattern(PATTERN_GMT);

    @Override
    public DateTime deserialize(final JsonParser jp, final DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        final String jsonDate = node.asText();
        DateTime result;

        try {
            result = FORMATTER_Z.parseDateTime(jsonDate);

        } catch (final IllegalArgumentException e) {
            result = FORMATTER_GMT.parseDateTime(jsonDate);
        }

        return result;
    }

}