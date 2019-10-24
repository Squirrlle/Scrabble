import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        Scanner s = new Scanner(System.in);
        boolean open = true;
        ObjectOutputStream os;
        ObjectInputStream is;
        while(open){
            String msg;
            socket = new Socket(host.getHostName(), 9876);
            os = new ObjectOutputStream(socket.getOutputStream());
            System.out.print("Message: ");
            msg = s.nextLine();
            if(msg.equalsIgnoreCase("exit")){
                os.writeObject("exit");
                open = false;
            }
            else os.writeObject(msg);
            //read the server response message
            is = new ObjectInputStream(socket.getInputStream());
            String message = (String) is.readObject();
            System.out.println("Response: " + message);
            //close resources
            is.close();
            os.close();
            Thread.sleep(100);
        }
        System.out.println("Connection Closed");
    }

}
