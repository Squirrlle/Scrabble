import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int port;
    int counter = 0;
    int ready = 0;
    Board b = new Board(15,15);
    Tiles t = new Tiles();

    Server(int port){
        this.port = port;
    }

    Server(){
        this(3033);
    }

    public void start(){
        try{
            ServerSocket server=new ServerSocket(port);
            System.out.println("Server Started ....");
            while(true){
                counter++;
                //server accept the client connection request
                Socket serverClient=server.accept();
                System.out.println(" >> " + "Client No:" + counter + " started!");
                //send  the request to a separate thread
                ServerClientThread sct = new ServerClientThread(serverClient,counter, b, this, t);
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public void readyUp(){
        ready++;
    }

    public boolean startGame(){
        return ready == (counter - 1);
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {

        Server svr = new Server();
        svr.start();

    }

}
