import java.io.*;
import java.net.Socket;

class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;
    Board b;
    Server s;
    Tiles t;
    Player p;

    ServerClientThread(Socket inSocket,int counter, Board b, Server s, Tiles t){
        serverClient = inSocket;
        clientNo=counter;
        this.b = b;
        this.s = s;
        this.t = t;
        Player p = new Player();
    }
    public void run(){
        try{
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientMessage="", serverMessage="";
            outStream.writeUTF(System.getProperty("os.name") + ", " + System.getProperty("os.version"));
            outStream.flush();
            while(!clientMessage.equalsIgnoreCase("quit")){
                clientMessage = inStream.readUTF();
                if(clientMessage.equalsIgnoreCase("Ready")){
                    s.readyUp();
                }
                System.out.println("From Client-" +clientNo+ ": "+clientMessage);
                serverMessage="From Server to Client-" + clientNo + ": " + clientMessage;
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            serverClient.close();
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }
}