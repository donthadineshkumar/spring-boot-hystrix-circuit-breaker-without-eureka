package com.cb;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class BookService {

    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "reliable",
    commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",
                             value="5000")
    })
    public String readingList() {
        URI uri = URI.create("http://localhost:8090/recommended");
        return restTemplate.getForObject(uri, String.class);
    }

    public String reliable() {
        return "Spring in Action(Manning)";
    }
}
