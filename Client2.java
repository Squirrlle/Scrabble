import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client2{
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.print("Enter an ip address: ");
        String ip = s.nextLine();
        if(ip.isEmpty() || ip.equalsIgnoreCase("localhost")){
            ip = "127.0.0.1";
        }
        System.out.print("Enter a port number: ");
        int port = s.nextInt();
        if(port == 0){
            port = 7000;
        }
        try{
            Socket socket = new Socket(ip,port);
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String clientMessage="";
            String serverMessage;
            while(!clientMessage.equalsIgnoreCase("quit")){
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);
                System.out.print("Input :");
                clientMessage = br.readLine();
                if(clientMessage.equalsIgnoreCase("hello")){
                    clientMessage = "HELLO " + System.getProperty("os.name") + ", "
                            + System.getProperty("os.version") + ", " + System.getProperty("java.runtime.version") +
                            ", " + System.getProperty("os.name");
                }
                outStream.writeUTF(clientMessage);
                outStream.flush();
            }
            serverMessage = inStream.readUTF();
            System.out.println(serverMessage);
            outStream.close();
            outStream.close();
            socket.close();
        }catch(Exception e){
            System.err.println(e);
        }
    }
}