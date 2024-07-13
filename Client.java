import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("Waiting for connection...");
            Socket clientSocket = new Socket("localhost", 4567);
            System.out.println("Connected to server...");

            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter text: ");
                String inputLine = scanner.nextLine();
                if (inputLine.equalsIgnoreCase("quit")) {
                    break;
                }
                pw.println(inputLine);
                String response = br.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println("Server: " + response);
            }

            br.close();
            pw.close();
            clientSocket.close();
            scanner.close();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host exception: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
