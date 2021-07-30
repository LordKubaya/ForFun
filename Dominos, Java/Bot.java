import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.Port;
public class Bot extends Player{

    public Bot(int name){
        super("bot_" + name);
    }

    @Override
    public Domino play(Domino upper, Domino lower){ //null if passes
        Random rand = new Random();
        int aux;
        int choice;
        Domino playDom;

        if(this.getMao().size() == 0) return null;
        
        System.out.println("Player " + this.getName() + " played");
        
        if( upper == null && lower==null){ //when the table doesn't has any domino
            aux = rand.nextInt(7);
            playDom = super.getMao().get(aux);
            super.getMao().remove(aux);
            return playDom;            
        }
        
        ArrayList<Domino> possiblePlays = new ArrayList<Domino>();

        for(Domino domino : this.getMao()){
            if(lower.isFree(domino,1)!=-1) possiblePlays.add(domino);
            else if(upper.isFree(domino,0)!=-1) possiblePlays.add(domino);
        }
        
        if(possiblePlays.size()>0){
            choice = rand.nextInt(possiblePlays.size());

            aux = lower.isFree(possiblePlays.get(choice),1);
            if(aux != -1){
                
                lower.bottomDomino(possiblePlays.get(choice));
                playDom = possiblePlays.get(choice);
                playDom.topDomino(upper);
                this.getMao().remove(playDom);
                
                if(aux == 1 ){
                    playDom.turn(); //turn the domino
                }

                return playDom;
                }
            else{
                aux = upper.isFree(possiblePlays.get(choice),0);
                if(aux != -1){
                    upper.topDomino(possiblePlays.get(choice));
                    playDom = possiblePlays.get(choice);
                    playDom.bottomDomino(upper);
                    this.getMao().remove(playDom);
                    
                    if(aux == 1){
                        playDom.turn();
                    }
                    return playDom;
                }
            }
        //hand play 
       }
       return null;
    }
}

