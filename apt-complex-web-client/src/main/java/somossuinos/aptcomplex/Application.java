package somossuinos.aptcomplex;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.fasterxml.jackson.datatype.joda.ser.JacksonJodaFormat;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import somossuinos.aptcomplex.domain.infra.DataFactory;

@SpringBootApplication
public class Application {

    public static void main(final String ... args) {
        final ApplicationContext ctx = SpringApplication.run(Application.class, args);

        if (ArrayUtils.isNotEmpty(args)) {
            if ("populate".equalsIgnoreCase(args[0])) {
                try {
                    ctx.getBean(DataFactory.class).populate();
                    System.exit(0);

                } catch (final Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

}
