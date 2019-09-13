import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartReader {
    private static final Logger log = LoggerFactory.getLogger("StartReader");


    public static void main(String[] args) {
        try {
            log.info("Program start");
            if(args[0].isEmpty()) log.warn("Missing file path in command line arguments");

            FileRunner run = new FileRunner();
            LineBuilder builder = new LineBuilder(
                    run.ReadFile(args[0]));

            builder.createdLine();

            log.info("Program end");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
