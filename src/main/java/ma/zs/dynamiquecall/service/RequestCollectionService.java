package ma.zs.dynamiquecall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ma.zs.dynamiquecall.bean.RequestCollection;

import java.util.List;

public interface RequestCollectionService {
    public RequestCollection create(RequestCollection requestCollection);
    public List<RequestCollection> findAll();
    public String execute(String collectionName ,String pathVariable , String BodyParams) throws JsonProcessingException;
}
