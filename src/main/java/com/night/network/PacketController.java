package com.night.network;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/packets")
public class PacketController {

    private static final String DB_URL = "jdbc:sqlite:packets.db";

    @PostMapping("/start")
    public String startSniffing() {
        new Thread(packetSniffer::startSniffing).start();
        return "Packet sniffing started!";
    }

    @PostMapping("/stop")
    public String stopSniffing() {
        packetSniffer.stopSniffing();
        return "Packet sniffing stopped!";
    }

    @GetMapping("/all")
    public List<PacketData> getCapturedPackets() {
        return packetSniffer.getCapturedPackets();
    }

    @GetMapping
    public List<PacketData> getFilteredPackets(
            @RequestParam(required = false) String protocol,
            @RequestParam(required = false) String sourceIP,
            @RequestParam(required = false) String destIP,
            @RequestParam(required = false) Integer port) {

        List<PacketData> packets = packetSniffer.getCapturedPackets();

        if (protocol != null) {
            packets = packets.stream()
                    .filter(p -> p.getProtocol().equalsIgnoreCase(protocol))
                    .collect(Collectors.toList());
        }

        if (sourceIP != null) {
            packets = packets.stream()
                    .filter(p -> p.getSourceIP().equals(sourceIP))
                    .collect(Collectors.toList());
        }

        if (destIP != null) {
            packets = packets.stream()
                    .filter(p -> p.getDestIP().equals(destIP))
                    .collect(Collectors.toList());
        }

        if (port != null) {
            packets = packets.stream()
                    .filter(p -> p.getSourcePort() == port || p.getDestPort() == port)
                    .collect(Collectors.toList());
        }

        return packets;
    }
}
