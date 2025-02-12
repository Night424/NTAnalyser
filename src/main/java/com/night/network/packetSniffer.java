package com.night.network;

import org.pcap4j.core.*;
import java.util.List;
import java.util.ArrayList;

public class packetSniffer {
    private static final int SNAPLEN = 65536;
    private static final int TIMEOUT = 1000;
    private static final List<String> packetList = new ArrayList<>();

    // This method will start sniffing and capturing packets
    public static void startSniffing() {
        try {
            List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
            if (devices.isEmpty()) {
                System.out.println("No network interfaces found.");
                return;
            }

            PcapNetworkInterface device = devices.get(3); // Change index as needed
            System.out.println("Starting packet sniffing on: " + device.getDescription());

            PcapHandle handle = device.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS, TIMEOUT);
            System.out.println("Listening for packets...");

            handle.loop(-1, new PacketListener() {
                @Override
                public void gotPacket(PcapPacket packet) {
                    String packetData = packet.toString();
                    synchronized (packetList) {
                        packetList.add(packetData); // Thread-safe list update
                    }
                    System.out.println("Packet Captured:\n" + packetData);
                }
            });

            handle.close();
        } catch (PcapNativeException e) {
            System.err.println("Error accessing network interface: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Packet sniffer interrupted.");
            Thread.currentThread().interrupt();
        } catch (NotOpenException e) {
            System.err.println("Error: PcapHandle is not open.");
        }
    }

    // This method will return a copy of the list of captured packets
    public static List<String> getCapturedPackets() {
        synchronized (packetList) {
            return new ArrayList<>(packetList); // Return a copy of the list to avoid external modification
        }
    }
}
