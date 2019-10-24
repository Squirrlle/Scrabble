import java.util.ArrayList;
import java.util.Random;

public class Tiles {
    private ArrayList<Character> bag = new ArrayList<Character>(100);
    private Random r = new Random();


    Tiles(){
        newBag();
    }

    private void newBag(){
        for(int i = 0; i < 9; i++)
            bag.add('a');
        for(int i = 0; i < 2; i++)
            bag.add('b');
        for(int i = 0; i < 2; i++)
            bag.add('c');
        for(int i = 0; i < 4; i++)
            bag.add('d');
        for(int i = 0; i < 12; i++)
            bag.add('e');
        for(int i = 0; i < 2; i++)
            bag.add('f');
        for(int i = 0; i < 3; i++)
            bag.add('g');
        for(int i = 0; i < 2; i++)
            bag.add('h');
        for(int i = 0; i < 9; i++)
            bag.add( 'i');
        bag.add( 'j');
        bag.add('k');
        for(int i = 0; i < 4; i++)
            bag.add('l');
        for(int i = 0; i < 2; i++)
            bag.add('m');
        for(int i = 0; i < 6; i++)
            bag.add('n');
        for(int i = 0; i < 8; i++)
            bag.add('o');
        for(int i = 0; i < 2; i++)
            bag.add('p');
        bag.add( 'q');
        for(int i = 0; i < 6; i++)
            bag.add('r');
        for(int i = 0; i < 4; i++)
            bag.add('s');
        for(int i = 0; i < 6; i++)
            bag.add('t');
        for(int i = 0; i < 4; i++)
            bag.add('u');
        for(int i = 0; i < 2; i++)
            bag.add('v');
        for(int i = 0; i < 2; i++)
            bag.add('w');
        bag.add('x');
        for(int i = 0; i < 2; i++)
            bag.add('y');
        bag.add('z');
        bag.add('x');
        bag.add('e');
    }

    public ArrayList<Character> makePile(int n){
        ArrayList<Character> pile = new ArrayList<>();
        int l = 100/n;
        for(int i = 0; i < l; i++){
            int ran =  r.nextInt(bag.size());
            pile.add(bag.get(ran));
            bag.remove(ran);
        }
        return pile;
    }

}
