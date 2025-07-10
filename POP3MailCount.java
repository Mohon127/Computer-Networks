import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class POP3MailCount {

    private static DataOutputStream out;
    public static BufferedReader in;

    public static void main(String [] args) throws Exception {
        String host = "pop.gmail.com";
        int port = 995;
        String user = "s2110976127@ru.ac.bd";
        String pass = "user_password";

        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Server: " + in.readLine());

    
        send("USER " + user);
        System.out.println("Server: " + in.readLine());

        send("PASS " + pass);
        System.out.println("Server: " + in.readLine());

        send("STAT");
        String response = in.readLine();
        System.out.println("Server: " + response);
        String[] parts = response.split(" ");
            if (parts.length > 1) {
                int emailCount = Integer.parseInt(parts[1]); // The second value is the count of emails
                System.out.println("You have " + emailCount + " emails in your inbox.");
            }

        send("QUIT");
        System.out.println("Server: " + in.readLine());


        socket.close();



    }

    public static void send(String s) throws Exception {
        out.writeBytes(s + "\r\n");
        out.flush();
        Thread.sleep(1000);
        System.out.println("Client: " + s);
    }
    
}
