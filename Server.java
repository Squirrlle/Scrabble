import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int port;

    Server(int port){
        this.port = port;
    }

    Server(){
        this(3033);
    }

    public void start(){
        try{
            ServerSocket server=new ServerSocket(port);
            int counter = 0;
            System.out.println("Server Started ....");
            while(true){
                counter++;
                Socket serverClient=server.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public static void main(String[] args) {

        Server svr = new Server();
        svr.start();

    }

}
