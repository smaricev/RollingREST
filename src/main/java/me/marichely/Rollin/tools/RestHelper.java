package me.marichely.Rollin.tools;

import me.marichely.Rollin.entity.Equipment;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestHelper<T>{
    private final Class<T> typeParam;


    public RestHelper(Class<T> typeParam) {
        this.typeParam = typeParam;
    }

    private HttpHeaders httpHeaders = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<T> HttpGET(String baseurl,HttpHeaders httpHeaders){
        HttpEntity<T> httpEntity = new HttpEntity<T>(null,httpHeaders);
        return restTemplate.exchange(baseurl, HttpMethod.GET,httpEntity,typeParam);

    }
}
