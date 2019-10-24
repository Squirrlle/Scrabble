import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            String message = (String) is.readObject();
            System.out.println("Message Received: " + message);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            if(message.equalsIgnoreCase("exit"))
                os.writeObject("GOODBYE");
            else
                os.writeObject("Hi Client");
            is.close();
            os.close();
            socket.close();
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }

}
