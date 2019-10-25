public class Board {

    public char[][] board;
    public int spaces;

    Board(int x, int y){
        board = mkBoard(x, y);
        spaces = x * y;
    }

    private char[][] mkBoard(int x, int y){
        spaces = x * y;
        board = new char[x][y];
        for(int i = 0; i < y; i++){
            for(int z = 0; z < x; z++){
                board[z][i] = '-';
            }
        }
        return board;
    }

    public boolean addItem(char c, int x, int y){
        if(board[x][y] == '-') {
            --spaces;
            board[x][y] = c;
            return true;
        }
        else {
            System.out.println("This space is taken, pick a different space");
            return false;
        }
    }

    public void display(){
        for(int i = 0; i < board.length; i++){
            for(int z = 0; z < board[i].length; z++){
                System.out.print(board[z][i] + " ");
            }
            System.out.print('\n');
        }
    }

    public boolean notFull(){
        for (char[] c : board) {
            for (char aC : c) {
                if (aC == '-')
                    return true;
            }
        }
        return false;
    }

}