
package ntokozo_flightreservations;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

public class Ntokozo_FlightReservations {
    private JFrame airplane = new JFrame("Reservations Airplane");
    private JFrame passenger = new JFrame("Reservations Passenger");
    private JTextField nameField = new JTextField();
    private String selectedSeat;

    public Ntokozo_FlightReservations() {
        airplane.setLayout(null);
        airplane.setSize(300, 500);

        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 4; c++) {
                String seatCoord = r + "," + c;
                Button btn = new Button("Seat " + seatCoord);
                btn.setBounds(20 + (r * 110), 20 + (c * 50), 100, 40);
                
                btn.addActionListener(e -> {
                    selectedSeat = seatCoord;
                    airplane.setVisible(false);
                    btn.setVisible(false);
                    passenger.setVisible(true);
                });
                airplane.add(btn);
            }
        }

        passenger.setLayout(null);
        passenger.setSize(300, 200);
        nameField.setBounds(50, 50, 200, 30);
        nameField.addActionListener(e -> {
            Communicator.sendToServer("Reservations", selectedSeat, nameField.getText());
            nameField.setText("");
            passenger.setVisible(false);
            airplane.setVisible(true);
        });
        passenger.add(nameField);

        airplane.setVisible(true);
    }

    public static void main(String[] args) { new Ntokozo_FlightReservations(); }
}

class Communicator {
    public static void sendToServer(String type, String seat, String name) {
        try (Socket s = new Socket("localhost", 1742);
             DataOutputStream out = new DataOutputStream(s.getOutputStream())) {
            out.writeUTF(type);
            out.writeUTF(seat);
            out.writeUTF(name);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
