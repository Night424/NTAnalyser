package com.night.network;

import org.pcap4j.core.*;
import java.util.List;

public class packetSniffer {

    // Method to start sniffing packets on a given network interface
    public void startSniffing(PcapNetworkInterface nif) throws PcapNativeException, NotOpenException {
        // Set up the capture parameters
        int snapLen = 65536; // Maximum length of packet to capture (for now, capture full packets)
        PcapHandle handle = nif.openLive(snapLen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);  // Promiscuous mode captures all packets

        // Start capturing packets
        try {
            handle.loop(-1, new PacketListener() {
                @Override
                public void gotPacket(PcapPacket packet) {
                    // Process the packet here
                    System.out.println("Captured packet: " + packet);
                    // You can extract specific details from the packet here, like IP, TCP, etc.
                }
            });
        } catch (InterruptedException e) {
            System.out.println("Packet sniffing interrupted.");
            Thread.currentThread().interrupt(); // Restore the interrupt status
        } finally {
            // Closing the capture handle when done
            // handle.close(); // Uncomment this if you plan to stop the capture at some point
        }
    }

    // Main method to run packet sniffing on the first network interface
    public static void main(String[] args) {
        try {
            // Find all available network devices
            List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
            if (devices == null || devices.isEmpty()) {
                System.out.println("No network devices found.");
                return;
            }

            // Print all available devices
            for (PcapNetworkInterface device : devices) {
                System.out.println("Device: " + device.getName() + " - " + device.getDescription());
            }

            // Choose the first available device (you can modify this if you want to select specific device)
            PcapNetworkInterface nif = devices.get(3); // Using the first device as an example

            // Start sniffing on this network interface
            packetSniffer sniffer = new packetSniffer();
            sniffer.startSniffing(nif);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
