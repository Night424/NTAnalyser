package com.night.network;

public class PacketData {
    private String timeStamp;
    private String sourceIP;
    private String destIP;
    private String protocol;
    private int length;
    private int sourcePort;
    private int destPort;

    public PacketData(String timeStamp, String sourceIP, String destIP, String protocol, int length, int sourcePort, int destPort) {
        this.timeStamp = timeStamp;
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.protocol = protocol;
        this.length = length;
        this.sourcePort = sourcePort;
        this.destPort = destPort;
    }

    public String getTimeStamp() { return timeStamp; }
    public String getSourceIP() { return sourceIP; }
    public String getDestIP() { return destIP; }
    public String getProtocol() { return protocol; }
    public int getLength() { return length; }
    public int getSourcePort() { return sourcePort; }
    public int getDestPort() { return destPort; }

    @Override
    public String toString() {
        return "PacketData{" +
                "timeStamp='" + timeStamp + '\'' +
                ", sourceIP='" + sourceIP + '\'' +
                ", destIP='" + destIP + '\'' +
                ", protocol='" + protocol + '\'' +
                ", length=" + length +
                ", sourcePort=" + sourcePort +
                ", destPort=" + destPort +
                '}';
    }
}
