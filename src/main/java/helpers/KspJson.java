package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class KspJson {
    public static String convertToString(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    public static JsonObject convertToObj(String str){
        Gson gson = new Gson();
        return gson.fromJson(str, JsonObject.class);
    }
}
