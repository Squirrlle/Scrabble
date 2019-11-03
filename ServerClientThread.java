import java.io.*;
import java.net.Socket;

class ServerClientThread extends Thread {
    private Socket serverClient;
    private Server s;
    private Tiles t;
    private Player p;
    private int pNum;
    private String userName;
    private boolean firstStart = true;
    private boolean isMoving = false;
    private DataInputStream inStream;
    private DataOutputStream outStream;

    ServerClientThread(Socket inSocket, int counter, Server s, Tiles t){
        serverClient = inSocket;
        this.s = s;
        this.t = t;
        pNum = counter;
        userName = Integer.toString(pNum);
    }

    public void run(){
        try{
            p = new Player(userName);
            s.addPlayer(p);
            String clientMessage = "";
            String serverMessage;
            inStream = new DataInputStream(serverClient.getInputStream());
            outStream = new DataOutputStream(serverClient.getOutputStream());
            outStream.writeUTF("HELLO " + System.getProperty("os.name") + ", "
                    + System.getProperty("os.version") + ", " + System.getProperty("java.runtime.version") +
                    ", " + System.getProperty("os.name"));
            outStream.flush();
            while(!clientMessage.equalsIgnoreCase("quit")){
                //Read Data
                clientMessage = receiveData();

                if(clientMessage.equalsIgnoreCase("Ready") && firstStart){
                    s.readyUp();
                    serverMessage = "From Server to Player-" + userName + ": " + "OK";
                }

                else if(clientMessage.equalsIgnoreCase("USERSET")){
                    serverMessage = "OK: " + userset();
                }

                else if (isMoving){
                    moving(clientMessage);
                    serverMessage = "OK";
                }

                else if(clientMessage.contains("HELLO")){
                    serverMessage = "OK";
                }

                else if(clientMessage.equalsIgnoreCase("QUIT")){
                    serverMessage = "OK: GOODBYE";
                }

                else if(s.startGame()){
                    if(firstStart) {
                        serverMessage = "From Server to Player-" + userName + ": " + "Game Starting";
                        p.makeHand(s.getCounter(), t);
                        firstStart = false;
                    }
                    else if(clientMessage.equalsIgnoreCase("PASS") && s.isTurn(pNum)){
                        serverMessage = "From Server to Player-" + userName + ": " + "Passing Turn";
                        s.nextTurn();
                    }
                    else if(clientMessage.equalsIgnoreCase("BOARDPUSH")){
                        serverMessage = s.getBoardState();
                    }
                    else if(clientMessage.equalsIgnoreCase("TILES")){
                        serverMessage = "OK: " + p.getHand();
                    }
                    else if(clientMessage.equalsIgnoreCase("PLACE")){
                        if(s.isTurn(pNum)) {
                            serverMessage = "OK: What is your move (row, column, letter): ";
                            isMoving = true;
                        }
                        else{
                            serverMessage = "NOK: It is not your turn";
                        }
                    }
                    else if(clientMessage.equalsIgnoreCase("SCORE")){
                        serverMessage = s.getPoints();
                    }
                    else if(clientMessage.equalsIgnoreCase("WINNER")){
                        serverMessage = "OK: " + s.getWinner();
                    }
                    else{
                        serverMessage = "From Server to Player-" + userName + ": NOK";
                    }

                }

                else {
                    serverMessage = "From Server to Player-" + userName + ": NOK";
                }

                //Sends Data
                sendData(serverMessage);
            }
            //Shut Everything Down
            System.out.println("Connection to server closed.");
            inStream.close();
            outStream.close();
            serverClient.close();
        }
        catch(Exception ex){
            System.err.println(ex);
        }
        finally{
            System.out.println("Player -" + userName + " exit!! ");
        }
    }

    private void moving(String st){
        st = st.replaceAll(" ", "");
        st = st.replaceAll("\\(", "");
        int x;
        x = Character.getNumericValue(st.charAt(st.indexOf(',') - 1));
        int y = Character.getNumericValue(st.charAt(st.indexOf(',') + 1));
        char c = st.charAt(st.lastIndexOf(',') + 1);
        p.pMove(s.getB(), c, x, y);
        isMoving = false;
    }

    private void sendData(String s){
        try {
            outStream.writeUTF(s);
            outStream.flush();
        }
        catch (Exception ex){
            System.err.println(ex);
        }
    }

    private String receiveData(){
        try {
            String out = inStream.readUTF();
            System.out.println("From Player-" + userName + ": " + out);
            return out;
        }
        catch(Exception ex){
            System.err.println(ex);
        }
        return "NULL";
    }

    private String userset(){
        String sM = "Enter a New User Name: ";
        sendData(sM);
        userName = receiveData();
        p.setName(userName);
        return "Ok your name is now " + userName;
    }

}