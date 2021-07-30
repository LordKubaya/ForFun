//import Domino;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player{
    private String _name;

    private ArrayList<Domino> _mao = new ArrayList<Domino>();

    private int points = 0;

    private Scanner sc = new Scanner(System.in);
        
    public Player(String name) {
        _name = name;
    }
    public String getName(){
        return _name;
    }
    public int getPoints(){
        return points;
    }
    public void giveHand(Domino domino){
        _mao.add(domino);
    }

    public ArrayList<Domino> getMao(){
        return _mao;
    }

    public Domino play(Domino topDomino, Domino bottomDomino){
        //Random rand = new Random();
        int aux,i;
        Domino playDom;

        if(_mao.size()>0){
            

            System.out.println("\n********************************\n"); // a space between last choice
            System.out.println("Your Turn:\n");
            printTable(topDomino);
            
            System.out.println("---- " + this.getName() + " hand ----\n");
            
            for(i = 0; i < _mao.size(); i++){
                System.out.println(i+"):\n\n" + _mao.get(i).toString() + "\n");
                }
                
            System.out.println(i + "):\n Pass\n");
            //while(true){
            int choice = -1;
            while(choice < 0 || choice > _mao.size()){
                choice = sc.nextInt();
            }
            if(choice == _mao.size()){ return null;}
            if(topDomino==null && bottomDomino==null){ //when the table doesn't has any domino
                if(choice < _mao.size()){
                    playDom = _mao.get(choice);
                    _mao.remove(choice);
                    return playDom;
                } 
                else return null;
            }

            else if(_mao.size() != 0){ //return 0 if not turned, 1 if it is, -1 if not compatible
                aux = bottomDomino.isFree(_mao.get(choice),1);
                if(aux != -1){
                    
                    bottomDomino.bottomDomino(_mao.get(choice));
                    playDom = _mao.get(choice);
                    playDom.topDomino(topDomino);
                    _mao.remove(choice);
                    
                    if(aux == 1 ){
                        playDom.turn(); //turn the domino
                    }

                    return playDom;
                    }
                else{
                    aux = topDomino.isFree(_mao.get(choice),0);
                    if(aux != -1){
                        topDomino.topDomino(_mao.get(choice));
                        playDom = _mao.get(choice);
                        playDom.bottomDomino(topDomino);
                        _mao.remove(choice);
                        
                        if(aux == 1){
                            playDom.turn();
                        }
                        return playDom;
                    }
                }
            }
        }
        return null;
    }

    private void printTable(Domino domino){
        if(domino != null){
            System.out.println(domino + "\n");
            printTable(domino.nextDomino());
        }
    }

    public int handPoints(){
        int total = 0;
        for(Domino domino : _mao)
            total+=domino.worth();
        points += total;
        return total;
    }
    
    public void resethand(){
        _mao = new ArrayList<Domino>();
    }
}