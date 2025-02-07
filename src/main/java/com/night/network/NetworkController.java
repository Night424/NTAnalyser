package com.night.network;

import org.pcap4j.core.PcapNetworkInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/network")
public class NetworkController {
    private final NetworkInterfaceService networkInterfaceService;

    public NetworkController(NetworkInterfaceService networkInterfaceService) {
        this.networkInterfaceService = networkInterfaceService;
    }

    @GetMapping("/interfaces")
    public List<NetworkInterfaceDTO> getInterfaces() {
        List<PcapNetworkInterface> interfaces = networkInterfaceService.getNetworkInterfaces();
        return interfaces.stream()
                .map(NetworkInterfaceDTO::fromPcapInterface)
                .collect(Collectors.toList());
    }
}
