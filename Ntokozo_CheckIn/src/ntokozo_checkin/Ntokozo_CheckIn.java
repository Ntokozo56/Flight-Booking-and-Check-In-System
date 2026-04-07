
package ntokozo_checkin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class Ntokozo_CheckIn extends Application {
    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        
        String data = requestManifest();
        textArea.setText(data);

        StackPane root = new StackPane(textArea);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Check-In System");
        primaryStage.show();
    }

    private String requestManifest() {
        try (Socket s = new Socket("localhost", 1742);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {
            
            out.writeUTF("CheckIn");
            return in.readUTF();
        } catch (IOException e) {
            return "Error connecting to server.";
        }
    }

    public static void main(String[] args) { launch(args); }
}