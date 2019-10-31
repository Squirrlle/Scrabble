class Board {

    private String[][] board;

    Board(int x, int y){
        board = mkBoard(x, y);
    }

    Board(){
        this(9,9);
    }

    private String[][] mkBoard(int x, int y){
        board = new String[x][y];
        for(int i = 0; i < y; i++){
            for(int z = 0; z < x; z++){
                board[z][i] = "(0,0)";
            }
        }
        return board;
    }

    boolean addItem(String c, int x, int y){
        if(board[x][y].equals("(0,0)")) {
            board[x][y] = c;
            return true;
        }
        else {
            System.out.println("This space is taken, pick a different space");
            return false;
        }
    }

    String display(){
        StringBuilder table = new StringBuilder();
        for(int i = 0; i < board.length; i++){
            for(int z = 0; z < board[i].length; z++){
                table.append(board[z][i]);
            }
            table.append('\n');
        }

        return table.toString();
    }

}