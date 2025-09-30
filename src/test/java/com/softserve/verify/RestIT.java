package com.softserve.verify;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softserve.dto.ContactDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
public class RestIT {

    @Value("${server.port}")
    private String port;

    @DisplayName("Reading all contacts by the api")
    @Test
    public void checkContacts() throws Exception {
        List<ContactDto> expected = Arrays.asList(
                new ContactDto(1, "Ivan", "Ivanov", "ivan@gmail.com", "+380671234567"),
                new ContactDto(2, "Petro", "Petrov", "petro@gmail.com", "+380631234567")
        );
        //
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ContactDto>>(){}.getType();
        //
        OkHttpClient client = new OkHttpClient();
        //RequestBody formBody;
        Request request;
        Response response;
        String resultJson;
        //
        request = new Request
                .Builder()
                .url("http://localhost:" + port + "/api/contacts")
                .get()
                .addHeader("accept","*/*")
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        log.info("resultJson: " + resultJson);
        //
        List<ContactDto> actual = gson.fromJson(resultJson, listType);
        Assertions.assertEquals(expected, actual, "checkRemoveContacts() Error, actual.size() = " + actual.size());
    }
}
