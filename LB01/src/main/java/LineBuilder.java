import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineBuilder {
    private List<String> startLine;
    private String TERMINATE_REGEX;
    private Map<String, List<String>> listRules;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public LineBuilder(DataFile data) {
        startLine = data.getLine();
        listRules = data.getRules();
        fillTerminateRegex(data.getTerminate());
    }

    public void createdLine() {
        log.info("Started line building, start line - {}", startLine.toString());
        Pattern pattern = Pattern.compile(TERMINATE_REGEX);
        Matcher matcher = pattern.matcher(startLine.get(0));
        while (matcher.find()) {
            List<String> tmpStartLine = new ArrayList<>(startLine);

            replaceByRules(listRules, tmpStartLine);
            matcher = pattern.matcher(startLine.toString());

            log.info(tmpStartLine.toString());
        }
        log.info("Line building completed, end line - {}", startLine);
    }

    private void replaceByRules(Map<String, List<String>> map, List<String> tmpStartLine) {
        for (int i=0; i<tmpStartLine.size();i++) {
            String line = tmpStartLine.get(i);
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (line.matches(entry.getKey())) {
                    line = line.replace(entry.getKey(),
                            entry.getValue().get(randomValue(entry.getValue())));

                    tmpStartLine.remove(i);
                    tmpStartLine.addAll(i,Arrays.asList(line.split(",")));
                    startLine = tmpStartLine;
                    return;
                }
            }
        }
    }

    private int randomValue(List<String> collection) {
        return (int) (Math.random() * collection.size());
    }

    private void fillTerminateRegex(List<String> terminate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String t : terminate) {
            stringBuilder.append(t);
        }
        stringBuilder.append("]");
        TERMINATE_REGEX = stringBuilder.toString();
    }
}
