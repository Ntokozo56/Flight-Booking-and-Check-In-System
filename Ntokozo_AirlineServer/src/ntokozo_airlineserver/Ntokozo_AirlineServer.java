package ntokozo_airlineserver;


import java.io.*;
import java.net.*;

public class Ntokozo_AirlineServer {
    private static String[][] passengerOnSeat = new String[2][4];

    public static void main(String[] args) {
        System.out.println("Server started on port 1742...");
        try (ServerSocket serverSocket = new ServerSocket(1742)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                    String clientType = in.readUTF();

                    if (clientType.equals("Reservations")) {
                        String seatName = in.readUTF(); // Format example: "0,1"
                        String passengerName = in.readUTF();
                        
                        String[] coords = seatName.split(",");
                        int row = Integer.parseInt(coords[0]);
                        int col = Integer.parseInt(coords[1]);
                        passengerOnSeat[row][col] = passengerName;
                        
                        printManifest();
                    } else if (clientType.equals("CheckIn")) {
                        out.writeUTF(getManifestString());
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void printManifest() {
        System.out.println(getManifestString());
    }

    private static String getManifestString() {
        StringBuilder sb = new StringBuilder("--- Flight Manifest ---\n");
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 4; c++) {
                sb.append("[").append(r).append(",").append(c).append("] ")
                  .append(passengerOnSeat[r][c] == null ? "Empty" : passengerOnSeat[r][c])
                  .append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
