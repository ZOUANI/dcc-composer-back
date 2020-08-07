package ma.zs.dynamiquecall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.objects.XString;
import ma.zs.dynamiquecall.bean.BodyParam;
import ma.zs.dynamiquecall.bean.Request;
import ma.zs.dynamiquecall.bean.RequestCollection;
import ma.zs.dynamiquecall.dao.BodyParamDao;
import ma.zs.dynamiquecall.dao.RequestCollectionDao;
import ma.zs.dynamiquecall.dao.RequestDao;
import ma.zs.dynamiquecall.dto.RequestDto;
import ma.zs.dynamiquecall.service.RequestCollectionService;
import ma.zs.dynamiquecall.util.JsonUtil;
import ma.zs.dynamiquecall.util.ListLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.*;

@RestController
public class CallController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RequestDao requestDao;
    @Autowired
    BodyParamDao bodyParamDao;
    @Autowired
    RequestCollectionDao requestCollectionDao;

//        private String executeRequest(Request request){
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        if (request.getHttpMethod().equalsIgnoreCase("get")){
//            return restTemplate.getForObject(request.getLink()+request.getLinkSuffix(),String.class);
//        }
//        else if (request.getHttpMethod().equalsIgnoreCase("post")){
//            HttpEntity<String> entity = new HttpEntity<String>(request.getBodyParams(),headers);
//
//            ResponseEntity<String> response = restTemplate
//                    .exchange(request.getLink()+request.getLinkSuffix(), HttpMethod.POST, entity, String.class);
//            return  response.getBody();
//
//        }
//        else {
//            return null;
//        }
//    }

    public String executeRequest(String url, String methode, String pathValue, Map<String, String> params) {
        if (methode.equalsIgnoreCase("post")) {
            ResponseEntity<String> response = restTemplate.postForEntity(url + pathValue, params, String.class);
            return response.getBody();
        } else if (methode.equalsIgnoreCase("get")) {
            ResponseEntity<String> response = restTemplate.getForEntity(url + pathValue, String.class, params);
            return response.getBody();
        }
        return null;
    }

    String origin = "{\n" +
            "  \"firstName\": \"ismail\",\n" +
            "  \"lastName\": \"boulaanait\",\n" +
            "\t\"adress\" : {\"city\" : \"agadir\" , \"countru\" : \"maroc\"\t},\n" +
            "  \"roles\":[\n" +
            "\t\t{\n" +
            "\t\t\t\"id\":\"2\",\n" +
            "\t\t\t\"name\" : \"user\",\n" +
            "\t\t\t\"status\" : {\"blocked\":\"false\",\"duration\":\"100\"}\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\":\"5\",\n" +
            "\t\t\t\"name\" : \"admin\",\n" +
            "\t\t\t\"status\" : {\"blocked\":\"true\",\"duration\":\"300\"}\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\":\"7\",\n" +
            "\t\t\t\"name\" : \"chef\",\n" +
            "\t\t\t\"status\" : {\"blocked\":\"true\",\"duration\":\"1000\"}\n" +
            "\t\t}\n" +
            "\t] \n" +
            "}";

    @GetMapping("/call")
    public void launch()  throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
//        String location = "roles(id,status.duration)";
//        System.out.println(JsonUtil.extractFromList(origin, location));

        String list = "[\n" +
                "\t{\n" +
                "\t\t\"word\" : \"not\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"word\" : \"was\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"word\" : \"is\"\n" +
                "\t}\n" +
                "]";

//        Map<String,Object> map = new HashMap<>();
//        map.put("",list);
////        map.put("commendeItems",list);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("text","salam cv labas");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8001/bag", request, String.class);
        System.out.println(response.getBody());

    }


}

