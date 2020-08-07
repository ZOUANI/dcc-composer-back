package ma.zs.dynamiquecall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.zs.dynamiquecall.bean.RequestCollection;
import ma.zs.dynamiquecall.dto.RequestDto;
import ma.zs.dynamiquecall.service.RequestCollectionService;
import ma.zs.dynamiquecall.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request-collection")
@CrossOrigin(origins = "*")
public class RequestCollectionController {

    @Autowired
    public RequestCollectionService requestCollectionService;

    @PostMapping("/")
    public RequestCollection create(@RequestBody RequestCollection requestCollection){
        return requestCollectionService.create(requestCollection);
    }

    @GetMapping("/")
    public List<RequestCollection> findAll(){
        return requestCollectionService.findAll();
    }

    @PostMapping("/launch")
    public String launch(@RequestBody RequestDto requestDto) throws JsonProcessingException {

        return requestCollectionService.execute(requestDto.getCollectionName(),requestDto.getPathVariable(),requestDto.getBodyParams());
//        System.out.println(JsonUtil.extractfronJsonBasic(requestDto.getBodyParams(), "text"));
    }

}
