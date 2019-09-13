import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LineBuilder {
    private String startLine;
    private String TERMINATE_REGEX;
    private Map<String,List<String>> listRules;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public LineBuilder(DataFile data) {
        startLine = data.getLine();
        listRules= data.getRules();
       fillTerminateRegex(data.getTerminate());
    }

    public void createdLine(){
        log.info("Started line building, start line - {}", startLine);
        Pattern pattern = Pattern.compile(TERMINATE_REGEX);
        Matcher matcher = pattern.matcher(startLine);

        while (matcher.find()){
            log.info(startLine);
            replaceByRules(listRules);
            matcher = pattern.matcher(startLine);
        }
        log.info("Line building completed, end line - {}", startLine);
    }

    private void replaceByRules(Map<String, List<String>> map) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
           startLine=  startLine.replace(entry.getKey(),
                    entry.getValue().get(randomValue(entry.getValue())));
        }
    }

    private int randomValue(List<String> collection){
        return (int) (Math.random() * collection.size());
    }

    private void fillTerminateRegex(List<String> terminate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String t:terminate) {
            stringBuilder.append(t);
        }
        stringBuilder.append("]");
        TERMINATE_REGEX = stringBuilder.toString();
    }
}
