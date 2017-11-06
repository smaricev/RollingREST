package me.marichely.Rollin;

import me.marichely.Rollin.entity.Equipment;
import me.marichely.Rollin.tools.RestHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class EquipmentTest {

    private static final String BASE_URL = "http://localhost:8080/equipment";

    @Test
    @Ignore

    public void getAllEquipmentTest() {
        RestHelper<Equipment[]> restHelper = new RestHelper<Equipment[]>(Equipment[].class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("UserApiKey", "1");
        // TEST CORRECT API KEY
        ResponseEntity<Equipment[]> responseEntity = restHelper.HttpGET(BASE_URL,httpHeaders);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        // TEST WRONG API KEY
        httpHeaders.set("UserApiKey", "2");
        try {
            responseEntity = restHelper.HttpGET(BASE_URL,httpHeaders);
        }catch (HttpStatusCodeException e){
            assertEquals(e.getStatusCode(),HttpStatus.NOT_FOUND);
        }
        //TEST NO API KEY
        try {
            responseEntity = restHelper.HttpGET(BASE_URL,null);
        }catch (HttpStatusCodeException e){
            assertEquals(e.getStatusCode(),HttpStatus.BAD_REQUEST);
        }
    }
}
