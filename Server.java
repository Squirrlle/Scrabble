import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private int port;
    private int counter = 0;
    private int ready = 0;
    private Board b = new Board();
    private Tiles t = new Tiles();
    private ArrayList<Player> plr = new ArrayList<>();

    private Server(int port){
        this.port = port;
    }

    private Server(){
        this(7000);
    }

    private void start(){
        try{
            ServerSocket server=new ServerSocket(port);
            System.out.println("Server Started ....");
            boolean isRunning = true;
            while(isRunning){
                counter++;
                //server accept the client connection request
                Socket serverClient = server.accept();
                System.out.println(" >> " + "Client No:" + counter + " started!");
                //send  the request to a separate thread
                ServerClientThread sct = new ServerClientThread(serverClient,counter, this, t);
                sct.start();
            }
        }
	catch(Exception e){
            System.err.println(e);
        }
    }


    void readyUp(){
        ready++;
    }

    boolean startGame(){
        return ready == (counter - 1);
    }

    int getCounter() {
        return counter;
    }

    String getBoardState(){
        return b.display();
    }

    Board getB() {
        return b;
    }

    void addPlayer(Player p){
        plr.add(p);
    }

    String getWinner(){
        String win = "";
        int winner = 0;
        for(Player s : plr){
            if(s.getPts() >= winner)
                win = s.getName();
        }
        return win + " is the Winner";
    }

    String getPoints(){
        StringBuilder points = new StringBuilder();
        for(Player s : plr){
            points.append(s.getName()).append("Has ").append(s.getPts()).append(" Points");
        }
        return points.toString();
    }


    public static void main(String[] args) {

        Server svr = new Server();
        svr.start();

    }

}
