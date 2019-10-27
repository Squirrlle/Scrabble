import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;
    Board b;
    Tiles t;
    Player p;

    ServerClientThread(Socket inSocket, int counter, Tiles t) {
        serverClient = inSocket;
        clientNo = counter;
        this.b = b;
        this.t = t;
    }

    public void run() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(serverClient.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(serverClient.getOutputStream());
            String clientMessage;
            String serverMessage = "";
            outStream.writeObject("");
            boolean playing = true;

            //HELLO implementation :tm:
            outStream.writeObject("HELLO, " + System.getProperty("os.name") +"\nEnter your name: ");
            outStream.flush();
            clientMessage = inStream.readUTF();
            Player p = new Player(clientMessage);
            inStream.close();
            outStream.close();

            //Playing the game
            while (playing) {
                clientMessage = inStream.readUTF();
                if(clientMessage.equalsIgnoreCase("QUIT")){
                    playing = false;
                    serverMessage = "GOODBYE";
                }
                outStream.writeObject(serverMessage);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            serverClient.close();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }

}