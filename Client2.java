import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2{
    public static void main(String[] args) throws Exception {
        try{
            Socket socket = new Socket("127.0.0.1",3033);
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String clientMessage="", serverMessage="";
            while(!clientMessage.equalsIgnoreCase("quit")){
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);
                System.out.print("Say Something :");
                clientMessage = br.readLine();
                if(clientMessage.equalsIgnoreCase("hello")){
                    clientMessage = System.getProperty("os.name") + ", " + System.getProperty("os.version");
                }
                outStream.writeUTF(clientMessage);
                outStream.flush();
            }
            outStream.close();
            outStream.close();
            socket.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}