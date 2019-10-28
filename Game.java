import java.util.Scanner;
import java.util.ArrayList;

public class Game {

    public static void main(String args[]){

        System.out.print("Enter the width & height of the board (w, h): ");
        Scanner s = new Scanner(System.in);
        String dim = s.nextLine();
        dim = dim.replaceAll(" ", "");
        int w =  Character.getNumericValue(dim.charAt(0));
        int h = Character.getNumericValue(dim.charAt(dim.indexOf(',') + 1));
        Board b = new Board(w, h);
        Tiles t = new Tiles();

        System.out.print("Enter number of players: ");
        int p = s.nextInt();
        Player[] plr = new Player[p];
        for (int i = 0; i < plr.length; i++){
            s = new Scanner(System.in);
            System.out.print("Enter a player name for player " + i + ": ");
            String name = s.nextLine();
            plr[i] = new Player(name);
            plr[i].makeHand(p, t);
        }

        System.out.println("Begin the game.\n");

        int turn = 0;
        while (turn < (h*w)){
            for (int i = 0; i < plr.length; i++){
                turn++;
                System.out.println("Turn " + turn);
                System.out.println("Here is the board\n");
                b.display();
                s = new Scanner(System.in);
                System.out.println(plr[i].getName() + "'s move");
                System.out.println("Here is your hand\n");
                System.out.println(plr[i].getHand().toString());
                System.out.print("What is your move (row, column, letter)");
                String mv = s.nextLine();
                mv = mv.replaceAll(" ", "");
                int x =  Character.getNumericValue(mv.charAt(0));
                int y = Character.getNumericValue(mv.charAt(mv.indexOf(',') + 1));
                char c = mv.charAt(mv.lastIndexOf(',') + 1);
                plr[i].pMove(b, c, x, y);
                b.display();
                System.out.println(plr[i].getName() + " has " + plr[i].getPts() + " points\n\n");
                if(turn >= (h*w))
                    break;
            }
        }

    }

}
