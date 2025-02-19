package com.night.network;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class PacketStatsController {

    @GetMapping
    public Map<String, Integer> getPacketStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Total Packets", PacketStats.getTotalPackets());
        stats.put("TCP Packets", PacketStats.getTcpPackets());
        stats.put("UDP Packets", PacketStats.getUdpPackets());
        stats.put("ARP Packets", PacketStats.getArpPackets());
        stats.put("Other Packets", PacketStats.getOtherPackets());
        return stats;
    }
}

