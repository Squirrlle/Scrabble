import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ServerSocket svr;
    private int port;
    private static final int DEFAULT_PORT = 7000;


    public Server(int port) throws IllegalArgumentException {

        if(port < 1024) {
            throw new IllegalArgumentException("Port must be greater than or equal to 1024");
        } else {
            this.port = port;

        }
    }

    public Server(){
        this(DEFAULT_PORT);
    }

    public void start(){
        svr = null;
        try{
            svr = new ServerSocket(port);
        }
        catch (IOException io){
            System.err.println(io);
        }
    }


    public static void main(String args[]) throws IOException, ClassNotFoundException{
    }

}
