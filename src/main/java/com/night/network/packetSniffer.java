package com.night.network;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.Packet;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

public class packetSniffer {
    private static final int SNAPLEN = 65536;
    private static final int TIMEOUT = 1000;
    private static final List<PacketData> packetList = new ArrayList<>();
    private static boolean isSniffing = true;  // Flag to control packet sniffing

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

            handle.loop(-1, packet -> {
                PacketData parsedPacket = parsePacket(packet);
                synchronized (packetList) {
                    packetList.add(parsedPacket);
                }
                System.out.println(parsedPacket);
            });

            handle.close();
        } catch (PcapNativeException e) {
            System.err.println("Error opening the device: " + e.getMessage());
        } catch (NotOpenException e) {
            System.err.println("Error: The PcapHandle is not open: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Packet sniffing was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();  // Restore interrupted status
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }


    public static void stopSniffing() {
        isSniffing = false;  // Stop sniffing
        System.out.println("Packet sniffing stopped.");
    }

    private static PacketData parsePacket(PcapPacket packet) {
        String timeStamp = Instant.now().toString();
        String sourceIP = "Unknown";
        String destIP = "Unknown";
        String protocol = "Unknown";
        int length = packet.length();
        int sourcePort = -1;
        int destPort = -1;

        try {
            Packet ethernetPacket = packet.getPacket();

            if (ethernetPacket instanceof org.pcap4j.packet.EthernetPacket ethPacket) {
                Packet payload = ethPacket.getPayload();

                if (payload instanceof IpPacket ipPacket) {
                    sourceIP = ipPacket.getHeader().getSrcAddr().getHostAddress();
                    destIP = ipPacket.getHeader().getDstAddr().getHostAddress();

                    if (ipPacket.getPayload() instanceof TcpPacket tcpPacket) {
                        protocol = "TCP";
                        sourcePort = tcpPacket.getHeader().getSrcPort().valueAsInt();
                        destPort = tcpPacket.getHeader().getDstPort().valueAsInt();
                    } else if (ipPacket.getPayload() instanceof UdpPacket udpPacket) {
                        protocol = "UDP";
                        sourcePort = udpPacket.getHeader().getSrcPort().valueAsInt();
                        destPort = udpPacket.getHeader().getDstPort().valueAsInt();
                    } else {
                        protocol = "Other IP"; // Other IP-based protocols (e.g., ICMP)
                    }
                } else if (payload instanceof org.pcap4j.packet.ArpPacket) {
                    protocol = "ARP";
                } else {
                    protocol = "Non-IP"; // If it's Ethernet but not IP or ARP
                }
            } else {
                protocol = "Non-Ethernet"; // Some other low-level packet type
            }
        } catch (Exception e) {
            System.err.println("Error parsing packet: " + e.getMessage());
        }

        DatabaseManager.insertPacket(sourceIP, destIP, protocol, length);
        PacketStats.updateStats(protocol);

        return new PacketData(timeStamp, sourceIP, destIP, protocol, length, sourcePort, destPort);
    }

    public static List<PacketData> getCapturedPackets() {
        return new ArrayList<>(packetList);
    }
}
