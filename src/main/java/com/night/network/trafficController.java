package com.night.network;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class trafficController {
    // API controller for sending data to the frontend
    @GetMapping("/status")
    public Map<String, String> getStatus(){
        Map<String, String> response = new HashMap<>();
        response.put("status","Network Traffic Analyser is running");
        return response;
    }
}
