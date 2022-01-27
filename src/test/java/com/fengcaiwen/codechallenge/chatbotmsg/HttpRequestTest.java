package com.fengcaiwen.codechallenge.chatbotmsg;

import com.fengcaiwen.codechallenge.chatbotmsg.vo.Response;
import jdk.jfr.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;


//TODO more tests need to be done!
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Welcome");
    }

    @Test
    public void getShouldReturnSOK() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/data", String.class);
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void getResponseContentIsExpected() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/data", String.class);
        JSONObject json = new JSONObject(response.getBody());
        assertThat(json.getString("code")).isEqualTo("OK");
        assertThat(json.getString("message")).isEqualTo("success");
    }


}
