import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerPlrClientIO implements Runnable {
    private boolean closedConnection;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private Server server;
    private Socket clientSocket;

    ServerPlrClientIO(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
        closedConnection = false;
    }

    @Override
    public void run() {
        try {
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromClient = new ObjectInputStream(clientSocket.getInputStream());
            while (!closedConnection) {
                receiveData();
            }
        }
        catch (IOException e){
            System.err.print(e);
        }
    }

    /**
     * takes in data
     */
    public void receiveData(){

    }

    /**
     * sends out data
     */
    public void sendData(){

    }

    public void setDataToSendToClient(){
    }
}
