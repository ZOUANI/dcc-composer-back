package ma.zs.dynamiquecall.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static String extractfronJsonBasic(String json, String location) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        Object obj = map.get(location);
        if (obj.getClass().equals(String.class)) {
            return obj.toString();
        } else {
            String jsonInString = objectMapper.writeValueAsString(obj);
            return jsonInString;
        }

    }

    public static String extractfronJsonNested(String json, String location) throws JsonProcessingException {
        String[] locs = location.split("\\.");
        String tmp = json;
        for (String s : locs) {
            String res = extractfronJsonBasic(tmp, s);
            tmp = res;
        }
        return tmp;
    }

    public static ListLocation filterListLocation(String location){

        String place = "";
        String params = "";
        char[] chars = location.toCharArray();

        int i=0;
        while (chars[i] != '('){
            place += chars[i];
            i++;
        }
        i++;
        while (chars[i] != ')'){
            params += chars[i];
            i++;
        }
        return new ListLocation(place,params);
    }

    public static String extractFromList(String json , String location) throws JsonProcessingException {
        ListLocation listLocation = JsonUtil.filterListLocation(location);
        String listJson = JsonUtil.extractfronJsonNested(json,listLocation.getLocation());
        String res = "[";
        ObjectMapper mapper = new ObjectMapper();
        List<Object> objs = mapper.readValue(listJson, new TypeReference<List<Object>>() {});
        for (int i = 0 ; i < objs.size() ; i++) {
            String temp = mapper.writeValueAsString(objs.get(i));
            String[] paramsToExtract = listLocation.getParams().split("\\,");
            res += "{";
            for (int j = 0 ; j < paramsToExtract.length ; j++) {
                String key = paramsToExtract[j];
                if (paramsToExtract[j].contains(".")) {
                    key = paramsToExtract[j].split("\\.")[paramsToExtract.length-1];
                }
                res += "\""+key+"\" : "+"\""+JsonUtil.extractfronJsonNested(temp,paramsToExtract[j])+"\"";
                if (j != paramsToExtract.length - 1){
                    res +=",";
                }
            }
            res+="}";

            if (i != objs.size()-1){
                res +=",";
            }
        }
        res+= "]";
        return res;
    }

}
