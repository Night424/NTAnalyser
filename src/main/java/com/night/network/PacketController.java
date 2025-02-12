package com.night.network;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/packets")
public class PacketController {

    @PostMapping("/start")  // Start sniffing when this endpoint is hit
    public String startSniffing() {
        new Thread(() -> {
            packetSniffer.startSniffing(); // Start sniffing in a new thread
        }).start();
        return "Packet sniffing started!";
    }

    @GetMapping("/all")
    public List<String> getCapturedPackets() {
        return packetSniffer.getCapturedPackets(); // Use the method to get captured packets
    }
}
