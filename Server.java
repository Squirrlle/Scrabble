import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static final int DEFAULT_PORT = 7000;
    private int port;
    private boolean closeConnection;
    private ArrayList<ServerPlrClientIO> serverSideClientIOList;

    public Server(int port) throws IllegalArgumentException {

        if(port < 1024) {
            throw new IllegalArgumentException("Port must be greater than or equal to 1024");
        } else {
            this.closeConnection = false;

            this.port = port;

            this.serverSideClientIOList = new ArrayList<>();
        }
    }

    public Server(){
        this(DEFAULT_PORT);
    }


    public void start() {
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(this.port);

            while(!this.closeConnection) {

                Socket client = serverSocket.accept();
                ServerPlrClientIO newClient = new ServerPlrClientIO(this, client);
                serverSideClientIOList.add(newClient);

                Thread thread = new Thread(newClient);
                thread.start();
            }

        } catch(IOException ioe) {
            this.closeConnection = true;
            System.err.println("Line 128: " + ioe.getMessage());
        }

        try {
            serverSocket.close();
            this.closeConnection = true;
        } catch (IOException ioe) {
            System.err.println("Line 135: " + ioe.getMessage());
            this.closeConnection = true;
        }
    }


    /**
     * brodcasts what is to be sent to the client
     */
    public synchronized void broadcast() {
        for(int i = 0; i < serverSideClientIOList.size(); i++) {

        }
    }

    /**
     * removes an element from the ArrayList
     * @param serverSideClientToRemove
     */
    public synchronized void remove(ServerPlrClientIO serverSideClientToRemove) {
        this.serverSideClientIOList.remove(serverSideClientToRemove);
    }


    /**
     * Creates a ClypeServer with a port given by args and calls start()
     * @param args int
     */
    public static void main(String[] args) {
        if(args.length == 1) {

            try {
                int port = Integer.parseInt(args[0]);

                try {

                } catch(IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                }

            } catch(NumberFormatException nfe) {
                System.err.println("Port must be a number");
            }

        } else if(args.length == 0) {
            Server server = new Server();
            System.out.println("Server started on port " + Server.DEFAULT_PORT);
            server.start();
        }


    }

}