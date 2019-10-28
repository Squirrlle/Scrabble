import java.io.*;
import java.net.Socket;

class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;
    int squre;

    ServerClientThread(Socket inSocket,int counter){
        serverClient = inSocket;
        clientNo=counter;
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