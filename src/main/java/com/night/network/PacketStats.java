package com.night.network;

import java.util.concurrent.atomic.AtomicInteger;

public class PacketStats {
    private static final AtomicInteger totalPackets = new AtomicInteger(0);
    private static final AtomicInteger tcpPackets = new AtomicInteger(0);
    private static final AtomicInteger udpPackets = new AtomicInteger(0);
    private static final AtomicInteger arpPackets = new AtomicInteger(0);
    private static final AtomicInteger otherPackets = new AtomicInteger(0);

    public static synchronized void updateStats(String protocol) {
        totalPackets.incrementAndGet();

        switch (protocol) {
            case "TCP":
                tcpPackets.incrementAndGet();
                break;
            case "UDP":
                udpPackets.incrementAndGet();
                break;
            case "ARP":
                arpPackets.incrementAndGet();
                break;
            default:
                otherPackets.incrementAndGet();
                break;
        }
    }

    // ✅ Add getter methods
    public static int getTotalPackets() { return totalPackets.get(); }
    public static int getTcpPackets() { return tcpPackets.get(); }
    public static int getUdpPackets() { return udpPackets.get(); }
    public static int getArpPackets() { return arpPackets.get(); }
    public static int getOtherPackets() { return otherPackets.get(); }
}
