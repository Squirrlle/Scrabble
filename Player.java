import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class Player {

    private String name;
    private int pts;
    private ArrayList<Character> hand;

    Player(String n) {
        name = n;
        pts = 0;
    }

    Player(){
        this("PLAYER");
    }

    void makeHand(int nPlayers, Tiles t){
        hand = t.makePile(nPlayers);
    }

    String getHand() {
        return hand.toString();
    }

    void pMove(Board b, char c, int x, int y){
        if(hand.contains(c)) {
            if (b.addItem("(" + c + ",0)", x, y)) {
                hand.remove(c);
                switch (Character.toLowerCase(c)) {
                    case 'q':
                    case 'z':
                        pts += 10;
                        break;
                    case 'j':
                    case 'x':
                        pts += 8;
                        break;
                    case 'k':
                        pts += 5;
                        break;
                    case 'f':
                    case 'h':
                    case 'v':
                    case 'w':
                    case 'y':
                        pts += 4;
                        break;
                    case 'p':
                    case 'b':
                    case 'c':
                    case 'm':
                        pts += 3;
                        break;
                    case 'd':
                    case 'g':
                        pts += 2;
                        break;
                    default:
                        pts++;
                }
            }
            else{
                System.out.println("Board Full");
            }
        }
        else{
            System.out.println("You don't have that tile");
        }
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getPts() {
        return pts;
    }


}
