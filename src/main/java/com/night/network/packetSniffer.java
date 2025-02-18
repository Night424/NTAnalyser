package com.night.network;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.Packet;

import java.util.List;
import java.util.ArrayList;

public class packetSniffer {
    private static final int SNAPLEN = 65536;
    private static final int TIMEOUT = 1000;
    private static final List<PacketData> packetList = new ArrayList<>();

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
        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static PacketData parsePacket(PcapPacket packet) {
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
                        protocol = "Other IP";
                    }
                } else if (payload instanceof org.pcap4j.packet.ArpPacket) {
                    protocol = "ARP";
                } else {
                    protocol = "Unknown Payload";
                }
            } else {
                protocol = "Non-Ethernet Packet";
            }
        } catch (Exception e) {
            System.err.println("Error parsing packet: " + e.getMessage());
        }

        return new PacketData(sourceIP, destIP, protocol, length, sourcePort, destPort);
    }

    public static List<PacketData> getCapturedPackets() {
        return new ArrayList<>(packetList);
    }
}
