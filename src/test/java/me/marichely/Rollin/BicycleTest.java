package me.marichely.Rollin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.sun.jndi.toolkit.url.Uri;
import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.Equipment;
import me.marichely.Rollin.tools.RestHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BicycleTest {

    private static final String BASE_URL = "http://localhost:8080/bicycle";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    @Ignore
    public void getAllBicycles() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("UserApiKey", "1");
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<Bicycle[]> responseEntity = restTemplate.exchange(BASE_URL + "/getall", HttpMethod.GET, httpEntity, Bicycle[].class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Ignore

    public void updateBicycle() {
        int statusCode = 0;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("bicycleid", "1");
        params.set("status", "0");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL + "/update", params, String.class);
        params.set("status", "3");
        assertEquals(200, responseEntity.getStatusCodeValue());
        try {
            ResponseEntity<String> responseEntity1 = restTemplate.postForEntity(BASE_URL + "/update", params, String.class);
        } catch (HttpStatusCodeException e) {
            statusCode = e.getStatusCode().value();
        }
        assertEquals(403, statusCode);
    }

    @Test
    @Ignore

    public void getAllBicyclesWithCategory() {
        RestHelper<Bicycle[]> restHelper = new RestHelper<Bicycle[]>(Bicycle[].class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("UserApiKey","1");
        ResponseEntity<Bicycle[]>responseEntity = restHelper.HttpGET(BASE_URL + "/cat1",httpHeaders);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }


}
