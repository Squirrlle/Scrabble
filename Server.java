import javax.tools.ToolProvider;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private int counter = 0;
    private int ready = 0;
    private Board b = new Board();
    private Tiles t = new Tiles();
    private boolean isRunning = true;

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
            while(isRunning){
                counter++;
                //server accept the client connection request
                Socket serverClient=server.accept();
                System.out.println(" >> " + "Client No:" + counter + " started!");
                //send  the request to a separate thread
                ServerClientThread sct = new ServerClientThread(serverClient,counter, b, this, t);
                sct.start();
            }
        }catch(Exception e){
            System.err.println(e);
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
