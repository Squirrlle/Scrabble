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

    Server(int port){
        this.port = port;
    }

    Server(){
        this(7000);
    }

    public void start(){
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


    public void readyUp(){
        ready++;
    }

    public boolean startGame(){
        boolean going = ready == (counter - 1);
        return going;
    }

    public int getCounter() {
        return counter;
    }

    public String getBoardState(){
        return b.display();
    }

    public Board getB() {
        return b;
    }

    public void addPlayer(Player p){
        plr.add(p);
    }

    public String getWinner(){
        String win = "";
        int winner = Integer.MIN_VALUE;
        for(Player s : plr){
            if(s.getPts() >= winner)
                win = s.getName();
        }
        return win + " is the Winner";
    }

    public String getPoints(){
        String points = "";
        for(Player s : plr){
            points += s.getName() + "Has " + s.getPts() + " Points";
        }
        return points;
    }


    public static void main(String[] args) {

        Server svr = new Server();
        svr.start();

    }

}
