package com.night.network;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/packets/db")
public class DatabseController {

    private static final String DB_URL = "jdbc:sqlite:packets.db";

    @GetMapping
    public List<PacketData> getPackets() {
        List<PacketData> packets = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT sourceIP, destIP, protocol, length, sourcePort, destPort, timeStamp FROM packets")) {

            while (rs.next()) {
                String timeStamp = rs.getString("timeStamp");

                packets.add(new PacketData(
                        timeStamp,  // Include the timestamp here
                        rs.getString("sourceIP"),
                        rs.getString("destIP"),
                        rs.getString("protocol"),
                        rs.getInt("length"),
                        rs.getInt("sourcePort"),
                        rs.getInt("destPort")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packets;
    }
}
