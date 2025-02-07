package com.night.network;

import org.pcap4j.core.PcapNetworkInterface;

public class NetworkInterfaceDTO {
    private String name;
    private String description;

    public NetworkInterfaceDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static NetworkInterfaceDTO fromPcapInterface(PcapNetworkInterface device) {
        return new NetworkInterfaceDTO(device.getName(), device.getDescription());
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}
