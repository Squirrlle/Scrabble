import java.io.*;
import java.net.Socket;

class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;
    Board b;
    Server s;
    Tiles t;
    boolean firstStart = true;

    ServerClientThread(Socket inSocket, int counter, Board b, Server s, Tiles t){
        serverClient = inSocket;
        clientNo=counter;
        this.b = b;
        this.s = s;
        this.t = t;
    }
    public void run(){
        try{
            String clientMessage="", serverMessage="";
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            outStream.writeUTF(System.getProperty("os.name") + ", " + System.getProperty("os.version"));
            outStream.flush();
            while(!clientMessage.equalsIgnoreCase("quit")){
                clientMessage = inStream.readUTF();
                System.out.println("From Client-" +clientNo+ ": "+clientMessage);
                if(clientMessage.equalsIgnoreCase("Ready") && !firstStart){
                    s.readyUp();
                    serverMessage = "From Server to Client-" + clientNo + ": " + "OK";
                }
                if(s.startGame()){
                    if(firstStart) {
                        serverMessage = "From Server to Client-" + clientNo + ": " + "Game Starting";
                        Player p = new Player();
                        p.makeHand(s.getCounter(), t);
                        firstStart = false;
                    }
                    else {
                        serverMessage = "From Server to Client-" + clientNo + ": " + clientMessage;
                    }
                }
                else {
                    serverMessage = "From Server to Client-" + clientNo + ": " + clientMessage;
                }
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            System.out.println("Connection to server closed.");
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