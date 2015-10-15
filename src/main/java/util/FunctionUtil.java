package util;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FunctionUtil {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern _pattern;
    private Matcher _matcher;

    public FunctionUtil(){

        _pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validate(final String hex) {

        _matcher = _pattern.matcher(hex);
        return _matcher.matches();

    }

    //TODO implement id assignment
    public static Long generateId() {

        return new Long(0);
    }

    public static JsonObject multiMapToJson(MultiMap map){
        List<Map.Entry<String,String>> mapEntries = map.entries();
        JsonObject json = new JsonObject();
        for(Map.Entry<String,String> entry : mapEntries){
            String key = entry.getKey();
            String value = entry.getValue();

            json.put(key, value);
        }
        return null;
    }


}
