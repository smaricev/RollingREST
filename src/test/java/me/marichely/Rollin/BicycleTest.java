package me.marichely.Rollin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.sun.jndi.toolkit.url.Uri;
import me.marichely.Rollin.entity.Bicycle;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;


public class BicycleTest {

    private static final String BASE_URL = "http://localhost:8080/bicycle";
    private RestTemplate restTemplate = new RestTemplate();
    @Test
    @Ignore
    public void getAllBicycles(){
        ResponseEntity<Bicycle[]> responseEntity= restTemplate.getForEntity(BASE_URL+"/getall",Bicycle[].class);
        Bicycle[] bicycles1 = responseEntity.getBody();
        responseEntity.getStatusCode();
        assertEquals(200,responseEntity.getStatusCodeValue());
    }
    @Test
    public void updateBicycle(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("bicycleid", "1");
        params.set("status", "0");
        ResponseEntity<String> responseEntity= restTemplate.postForEntity(BASE_URL+"/update",params,String.class);
        System.out.print("wow");
    }


}
