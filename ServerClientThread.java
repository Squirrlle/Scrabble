import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

class ServerClientThread extends Thread {
    private Socket serverClient;
    private int clientNo;
    private Board b;
    private Server s;
    private Tiles t;
    private Player p;
    private boolean firstStart = true;
    private boolean isMoving = false;
    private DataInputStream inStream;
    private DataOutputStream outStream;

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
            inStream = new DataInputStream(serverClient.getInputStream());
            outStream = new DataOutputStream(serverClient.getOutputStream());
            outStream.writeUTF(System.getProperty("os.name") + ", " + System.getProperty("os.version"));
            outStream.flush();
            while(!clientMessage.equalsIgnoreCase("quit")){
                clientMessage = recieveData();
                if(clientMessage.equalsIgnoreCase("Ready") && !firstStart){
                    s.readyUp();
                    serverMessage = "From Server to Client-" + clientNo + ": " + "OK";
                }
                if (isMoving){
                    moving(clientMessage);
                }
                else if(s.startGame()){
                    if(firstStart) {
                        serverMessage = "From Server to Client-" + clientNo + ": " + "Game Starting";
                        p = new Player();
                        p.makeHand(s.getCounter(), t);
                        firstStart = false;
                    }
                    else if(clientMessage.equalsIgnoreCase("BOARDPUSH")){
                        serverMessage = b.display();
                    }
                    else if(clientMessage.equalsIgnoreCase("TILES")){
                        serverMessage = p.getHand();
                    }
                    else if(clientMessage.equalsIgnoreCase("PLACE")){
                        serverMessage = "What is your move (row, column, letter): ";
                        isMoving = true;
                    }
                    else {
                        serverMessage = "From Server to Client-" + clientNo + ": " + clientMessage;
                    }
                }
                else {
                    serverMessage = "From Server to Client-" + clientNo + ": " + clientMessage;
                }
                sendData(serverMessage);
            }
            System.out.println("Connection to server closed.");
            inStream.close();
            outStream.close();
            serverClient.close();
        }
        catch(Exception ex){
            System.err.println(ex);
        }
        finally{
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }

    public void moving(String s){
        s.replaceAll(" ", "");
        int x =  Character.getNumericValue(s.charAt(0));
        int y = Character.getNumericValue(s.charAt(s.indexOf(',') + 1));
        char c = s.charAt(s.lastIndexOf(',') + 1);
        p.pMove(b, c, x, y);
        isMoving = false;
    }

    public void sendData(String s){
        try {
            outStream.writeUTF(s);
            outStream.flush();
        }
        catch (Exception ex){
            System.err.println(ex);
        }
    }

    public String recieveData(){
        try {
            String s = inStream.readUTF();
            System.out.println("From Client-" +clientNo+ ": " + s);
            return s;
        }
        catch(Exception ex){
            System.err.println(ex);
        }
        return "NULL";
    }

}