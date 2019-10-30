import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try{
            Socket socket = new Socket("127.0.0.1",7000);
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