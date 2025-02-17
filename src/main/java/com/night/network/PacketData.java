package com.night.network;

public class PacketData {
    private String sourceIP;
    private String destIP;
    private String protocol;
    private int length;

    public PacketData(String sourceIP, String destIP, String protocol, int length) {
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.protocol = protocol;
        this.length = length;
    }

    public String getSourceIP() { return sourceIP; }
    public String getDestIP() { return destIP; }
    public String getProtocol() { return protocol; }
    public int getLength() { return length; }

    @Override
    public String toString() {
        return "PacketData{" +
                "sourceIP='" + sourceIP + '\'' +
                ", destIP='" + destIP + '\'' +
                ", protocol='" + protocol + '\'' +
                ", length=" + length +
                '}';
    }
}
