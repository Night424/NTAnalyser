package com.night.network;

import org.pcap4j.core.Pcaps;
import org.pcap4j.core.PcapNetworkInterface;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NetworkInterfaceService {
    public List<PcapNetworkInterface> getNetworkInterfaces() {
        try {
            return Pcaps.findAllDevs();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list if no devices are found
        }
    }
}
