# Network Traffic Analysis Tool

## Overview

The **Network Traffic Analysis Tool** is a Java-based packet sniffer that captures network traffic and displays it in a real-time React dashboard. It allows users to monitor network activity, including IP addresses, protocols, packet lengths, and timestamps.

## Features

- Captures live network packets using **jNetPcap**.
- Displays real-time traffic data in a **React.js** dashboard.
- Provides details such as **source/destination IP, ports, protocol, length, and timestamps**.
- Refreshes automatically every **3 seconds**.
- Uses **Spring Boot** for the backend and **Axios** for API communication.

---

## Technologies Used

### Backend (Java - Spring Boot)
- **Spring Boot** - REST API development
- **jNetPcap** - Packet sniffing
- **Jackson** - JSON serialization
- **CorsConfiguration** - Handles CORS issues
- **Maven** - Dependency management

### Frontend (React.js)
- **React.js** - UI framework
- **Axios** - API requests
- **TailwindCSS** - Styling
- **Recharts** - Graphical representation of network data (Future enhancement)

---

## Installation & Setup

### Backend Setup (Spring Boot)

1. Clone the repository:
   git clone https://github.com/yourusername/network-traffic-analyzer.git
   cd network-traffic-analyzer

2. Install dependencies using Maven:
   mvn clean install

3. Run the Spring Boot backend:
   mvn spring-boot:run
   
4. Start packet capturing:
   curl -X POST http://localhost:8080/api/packets/start

5. Verify API endpoints:
   curl http://localhost:8080/api/packets/all

### Backend Setup (Spring Boot)

1. Navigate to the frontend directory:
   cd frontend

2. Install Dependencies
   npm install

3. Start the dev server
   npm run dev

4. Open the browser and go to:
   http://localhost:5173
