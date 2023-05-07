package com.gaerine.triple.googleAPI;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class GoogleAPITests {

    @Test
    public void mpaTest(){
        String url ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";
        String key ="key";

        WebClient client = WebClient.builder().
                baseUrl(url)
                .build();

        log.info("test={}",client.get().uri(data -> data
                .queryParam("input", "Museum of Contemporary Art Australia")
                .queryParam("inputtype", "textquery")
                .queryParam("fields", "formatted_address,name,rating,opening_hours,geometry")
                .queryParam("key",key).build()
        ).retrieve().bodyToMono(Map.class).block());

/* 구글 API 예제 코드
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=Museum%20of%20Contemporary%20Art%20Australia&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Copening_hours%2Cgeometry&key=YOUR_API_KEY")
                .method("GET", body)
                .build();
        Response response = client.newCall(request).execute();*/
    }
}
