import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    public String[][] board;
    public int spaces;

    Board(int x, int y){
        board = mkBoard(x, y);
        spaces = x * y;
    }

    Board(){
        this(15,15);
    }

    public String[][] mkBoard(int x, int y){
        spaces = x * y;
        board = new String[x][y];
        for(int i = 0; i < y; i++){
            for(int z = 0; z < x; z++){
                board[z][i] = "(-, 0)";
            }
        }
        return board;
    }

    public boolean addItem(String c, int x, int y){
        if(board[x][y].equals("(-, 0)")) {
            --spaces;
            board[x][y] = "(" + c + ", 0)";
            return true;
        }
        else {
            System.out.println("This space is taken, pick a different space");
            return false;
        }
    }

    public String display(){
        String table = "";
        for(int i = 0; i < board.length; i++){
            for(int z = 0; z < board[i].length; z++){
                table += board[z][i] + " ";
            }
            table += '\n';
        }

        return display();
    }

}