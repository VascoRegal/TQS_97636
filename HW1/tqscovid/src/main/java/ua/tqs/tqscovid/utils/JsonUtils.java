package ua.tqs.tqscovid.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonUtils {
    static public JSONObject responseToJson(String response) throws ParseException {
        return (JSONObject) new JSONParser().parse(response);
    }

    static public JSONArray responseToJsonArray(String response) throws ParseException {
        return (JSONArray) new JSONParser().parse(response);
    }

}
