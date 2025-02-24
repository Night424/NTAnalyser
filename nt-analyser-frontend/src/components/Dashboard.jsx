import React, { useState, useEffect } from "react";
import axios from "axios";

const Dashboard = () => {
  const [trafficData, setTrafficData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await axios.get("http://localhost:8080/api/packets/all");
        setTrafficData(response.data);
      } catch (error) {
        setError("Error fetching traffic data.");
        console.error("Error fetching traffic data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
    const interval = setInterval(fetchData, 3000); // Refresh every 3s
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="bg-gray-900 min-h-screen text-white p-6">
      <h1 className="text-3xl font-bold mb-4">Network Traffic Dashboard</h1>

      {/* Loading state */}
      {loading && <div className="text-center text-lg text-gray-300">Loading...</div>}

      {/* Error state */}
      {error && <div className="text-center text-lg text-red-500">{error}</div>}

      {/* Data Table */}
      {!loading && !error && (
        <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
          <div className="overflow-x-auto">
            <table className="min-w-full table-auto text-sm text-gray-300">
              <thead>
                <tr className="text-left">
                  <th className="px-4 py-2">Timestamp</th>
                  <th className="px-4 py-2">Source IP</th>
                  <th className="px-4 py-2">Destination IP</th>
                  <th className="px-4 py-2">Protocol</th>
                  <th className="px-4 py-2">Length</th>
                  <th className="px-4 py-2">Source Port</th>
                  <th className="px-4 py-2">Destination Port</th>
                </tr>
              </thead>
              <tbody>
                {trafficData.length > 0 ? (
                  trafficData.map((packet, index) => (
                    <tr key={index} className="hover:bg-gray-700">
                      <td className="px-4 py-2">{new Date(packet.timestamp).toLocaleString()}</td>
                      <td className="px-4 py-2">{packet.sourceIP}</td>
                      <td className="px-4 py-2">{packet.destIP}</td>
                      <td className="px-4 py-2">{packet.protocol}</td>
                      <td className="px-4 py-2">{packet.length}</td>
                      <td className="px-4 py-2">{packet.sourcePort}</td>
                      <td className="px-4 py-2">{packet.destPort}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="px-4 py-2 text-center">No data available.</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
