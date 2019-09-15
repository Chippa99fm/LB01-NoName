import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileRunner {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String SPACE_SEPARATOR = " ";

    public DataFile ReadFile(String fileName) {
        BufferedReader reader = null;
        DataFile data = new DataFile();

        try {
            log.info("Begin reading file - {}",fileName);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            String line;

            //TODO убрать эти четыре цикла нафег
            if ((line = reader.readLine()) != null) {
                log.info("T: {{}}", line);
                for (String secondLine : line.split(SPACE_SEPARATOR)) {
                    data.addNonTerminate(secondLine);
                }
            }

            if ((line = reader.readLine()) != null) {
                log.info("N: {{}}", line);
                for (String secondLine : line.split(SPACE_SEPARATOR)) {
                    data.addTerminate(secondLine);
                }
            }

            readRules(reader, data);

            if ((line = reader.readLine()) != null) {
                log.info("Axiom - {}", line);
                data.addLine(line);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
           return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
        }
        log.info("Reading file competed - {}",fileName);
        return data;
    }

    private void readRules(BufferedReader reader, DataFile data) throws IOException {
        String line;
        if ((line = reader.readLine()) != null) {
            log.info("p: {}", line);
            for (String rule : line.split(SPACE_SEPARATOR)) {
               String [] massRule = rule.split("=");
              String [] massRule2 = massRule[1].split("\\|");
              List<String> list = new ArrayList<>();

                for (int i = 0; i < massRule2.length; i++)
                    list.add(massRule2[i]);

                data.addRules(massRule[0], list);
            }
        }
    }


}
