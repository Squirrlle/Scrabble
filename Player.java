import java.util.ArrayList;

public class Player {

    private String name;
    private int pts;
    private ArrayList<Character> hand;

    Player(String n){
        name = n;
        pts = 0;
    }


    Player(){
        this("Player");
    }

    public void rename(String n){
        name = n;
    }

    public void makeHand(int nPlayers, Tiles t){
        hand = t.makePile(nPlayers);
    }

    public ArrayList<Character> getHand() {
        return hand;
    }

    public void pMove(Board b, char c, int x, int y){
        if(hand.contains(c)) {
            if (b.addItem(c, x, y)) {
                hand.remove(hand.indexOf(c));
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

    public String getName() {
        return name;
    }

    public int getPts() {
        return pts;
    }
}
