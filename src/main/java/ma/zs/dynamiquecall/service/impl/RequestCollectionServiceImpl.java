package ma.zs.dynamiquecall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import ma.zs.dynamiquecall.bean.BodyParam;
import ma.zs.dynamiquecall.bean.Request;
import ma.zs.dynamiquecall.bean.RequestCollection;
import ma.zs.dynamiquecall.dao.BodyParamDao;
import ma.zs.dynamiquecall.dao.RequestCollectionDao;
import ma.zs.dynamiquecall.dao.RequestDao;
import ma.zs.dynamiquecall.service.RequestCollectionService;
import ma.zs.dynamiquecall.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestCollectionServiceImpl implements RequestCollectionService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private RequestCollectionDao requestCollectionDao;
    @Autowired
    private BodyParamDao bodyParamDao;
    @Autowired
    private RequestDao requestDao;
    @Override
    public RequestCollection create(RequestCollection requestCollection) {
        RequestCollection createdRequestCollection = requestCollectionDao.save(requestCollection);
        for (Request request : requestCollection.getRequests()) {
            request.setRequestCollection(createdRequestCollection);
            Request createdRequest = requestDao.save(request);
            for (BodyParam bodyParam : request.getBodyParams()) {
                bodyParam.setRequest(createdRequest);
                bodyParamDao.save(bodyParam);
            }
        }
        return createdRequestCollection;
    }

    @Override
    public List<RequestCollection> findAll() {
        return requestCollectionDao.findAll();
    }

    @Override
    public String execute(String collectionName , String pathVariable, String bodyParams) throws JsonProcessingException {
        RequestCollection collection = requestCollectionDao.findByName(collectionName);
        System.out.println(collection);
        List<Request> requests = collection.getRequests();
        for (int i = 0; i < requests.size(); i++) {
            if (i == 0) {
                Map<String, String> map = new HashMap<>();
                for (BodyParam bodyParam : requests.get(i).getBodyParams()) {
                    String key = bodyParam.getName();
                    String json = bodyParams;
                    String location = bodyParam.getName();
                    String val = JsonUtil.extractfronJsonNested(json, location);
                    map.put(key, val);
                }
                requests.get(i).setResponse(executeRequest(requests.get(i).getUrl(), requests.get(i).getHttpMethod(), pathVariable, map));
            } else {
                Map<String, String> parametersMap = new HashMap<>();
                for (BodyParam bodyParam : requests.get(i).getBodyParams()) {
                    String key = bodyParam.getNextName();
                    String jsoon = requests.get(bodyParam.getResponseSource() - 1).getResponse();
                    String location = bodyParam.getName();
                    String val = JsonUtil.extractfronJsonNested(jsoon, location);
                    parametersMap.put(key, val);
                }
                if (requests.get(i).getPathVariableName() != null) {
                    String jsooon = requests.get(requests.get(i).getPathVariableResponseSource() - 1).getResponse();
                    String loc = requests.get(i).getPathVariableName();
                    requests.get(i).setPathVariableValue(JsonUtil.extractfronJsonNested(jsooon, loc));

                }
                requests.get(i).setResponse(executeRequest(requests.get(i).getUrl(), requests.get(i).getHttpMethod(), requests.get(i).getPathVariableValue(), parametersMap));

            }


        }

        return requests.get(requests.size() - 1).getResponse();
    }

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
}
