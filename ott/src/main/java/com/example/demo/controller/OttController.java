package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

import com.example.demo.entity.OTTData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class OttController {
	private final RestTemplate restTemplate;


    OttController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/ott/data")
    public CompletableFuture<List<Object>> getOTTData() {
        CompletableFuture<List<Object>> netflixDataFuture = CompletableFuture.supplyAsync(() -> {
            OTTData[] netflixDataArray = restTemplate.getForObject("http://localhost:8080/netflix", OTTData[].class);
            List<Object> netflixData = new ArrayList<>();
            netflixData.add("Netflix");
            netflixData.addAll(Arrays.asList(netflixDataArray));
            return netflixData;
        });

        CompletableFuture<List<Object>> hotstarDataFuture = CompletableFuture.supplyAsync(() -> {
            OTTData[] hotstarDataArray = restTemplate.getForObject("http://localhost:8081/hotstar", OTTData[].class);
            List<Object> hotstarData = new ArrayList<>();
            hotstarData.add("Hotstar");
            hotstarData.addAll(Arrays.asList(hotstarDataArray));
            return hotstarData;
        });

        CompletableFuture<List<Object>> amazonDataFuture = CompletableFuture.supplyAsync(() -> {
            OTTData[] amazonDataArray = restTemplate.getForObject("http://localhost:8082/amazon", OTTData[].class);
            List<Object> amazonData = new ArrayList<>();
            amazonData.add("Amazon");
            amazonData.addAll(Arrays.asList(amazonDataArray));
            return amazonData;
        });

        return CompletableFuture.allOf(netflixDataFuture, hotstarDataFuture, amazonDataFuture)
                .thenApply(v -> {
                    List<Object> aggregatedData = new ArrayList<>();
                    aggregatedData.addAll(netflixDataFuture.join());
                    aggregatedData.addAll(hotstarDataFuture.join());
                    aggregatedData.addAll(amazonDataFuture.join());
                    return aggregatedData;
                });
    }
    
//    @GetMapping("/ott/data")
//    public CompletableFuture<List<OTTData>> getOTTData() {
//        CompletableFuture<List<OTTData>> netflixDataFuture = CompletableFuture.supplyAsync(() -> {
//            OTTData[] netflixDataArray = restTemplate.getForObject("http://localhost:8080/netflix", OTTData[].class);
//            return Arrays.asList(netflixDataArray);
//        });
//
//        CompletableFuture<List<OTTData>> hotstarDataFuture = CompletableFuture.supplyAsync(() -> {
//            OTTData[] hotstarDataArray = restTemplate.getForObject("http://localhost:8081/hotstar", OTTData[].class);
//            return Arrays.asList(hotstarDataArray);
//        });
//
//        CompletableFuture<List<OTTData>> amazonDataFuture = CompletableFuture.supplyAsync(() -> {
//            OTTData[] amazonDataArray = restTemplate.getForObject("http://localhost:8082/amazon", OTTData[].class);
//            return Arrays.asList(amazonDataArray);
//        });
//
//        return CompletableFuture.allOf(netflixDataFuture, hotstarDataFuture, amazonDataFuture)
//                .thenApply(v -> {
//                    List<OTTData> aggregatedData = new ArrayList<>();
//                    aggregatedData.addAll(netflixDataFuture.join());
//                    aggregatedData.addAll(hotstarDataFuture.join());
//                    aggregatedData.addAll(amazonDataFuture.join());
//                    return aggregatedData;
//                });
//    }


    
	 @GetMapping("/send")
	    public ResponseEntity<String> sendRequest() {
	        String receiverUrl = "http://localhost:8080/netflix"; // URL of the receiver application
	        ResponseEntity<List> response = restTemplate.getForEntity(receiverUrl, List.class);
	        return ResponseEntity.ok("Response from receiver: " + response.getBody());
	    }
       
    }

    

