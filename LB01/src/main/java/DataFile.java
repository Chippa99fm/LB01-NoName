import org.slf4j.*;

import java.util.*;


public class DataFile {
    private final List<String> terminate = new ArrayList<>();
    private final List<String> nonTerminate = new ArrayList<>();
    private final Map<String, List<String>> rules = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String line;

    public void addRules(String sybol, List<String> newSybols) {
        try {
            rules.put(sybol, newSybols);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    public void addTerminate(String terminate) {
        if (terminate != null)
            this.terminate.add(terminate);
        else log.warn("String is null");
    }

    public void addNonTerminate(String nonTerminate) {
        if (nonTerminate != null)
            this.nonTerminate.add(nonTerminate);
        else log.warn("String is null");
    }

    public void setLine(String line) {
        if (line != null)
            this.line = line;
        else log.warn("String is null");
    }

    public String getLine() {
        return line;
    }

    public List<String> getNonTerminate() {
        return nonTerminate;
    }

    public Map<String, List<String>> getRules() {
        return rules;
    }

    public List<String> getTerminate() {
        return terminate;
    }
}
