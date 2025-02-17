package com.night.network;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packets")
public class PacketController {

    @PostMapping("/start")
    public String startSniffing() {
        new Thread(packetSniffer::startSniffing).start();
        return "Packet sniffing started!";
    }

    @GetMapping("/all")
    public List<PacketData> getCapturedPackets() {
        return packetSniffer.getCapturedPackets();
    }
}

