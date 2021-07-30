import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Game{

    private String _playerName;

    private ArrayList<Player> _players = new ArrayList<Player>();

    private ArrayList<Domino> _dominos = new ArrayList<Domino>();

    private int maxPoints = 0;

    private Domino topDomino = null;

    private Domino bottomDomino = null;
 
    public Game(String playerName){
        _playerName = playerName;

        _players.add(new Player(_playerName));
        for(int i = 1; i<4; i++) _players.add(new Bot(i));

        for(int i = 0; i <= 6; i++){
            for(int j = i; j<=6; j++){
                _dominos.add(new Domino(i, j));
            }
        }
            
    }

    public void start(){
        Random rand = new Random();
        int startPlayingOrder = rand.nextInt(4);
        int startDistributionOrder = rand.nextInt(4);
    
        ArrayList<Player> playingOrderList;
        ArrayList<Player> DistributionOrderList;
        int match = 0;
        System.out.println("----- New Match ------");
        while(maxPoints < 50){
            playingOrderList = getOrder(startPlayingOrder + match);
            DistributionOrderList = getOrder(startDistributionOrder + match);

            System.out.println(playingOrder("Playing Order", playingOrderList));
            System.out.println(playingOrder("Distributions Order", DistributionOrderList));

            System.out.println(receivingOrder(DistributionOrderList));
            distributeGame(DistributionOrderList);

            System.out.println("—— Match starting ——\n");
            int x = 0;
            int numberPasses = 0;

            while(numberPasses < 4){
                if(x%4==0) System.out.println("\n------ Round " + (x/4+1) +" ------\n");
                Domino played;
                played = playingOrderList.get(x%4).play(topDomino, bottomDomino);
                if(topDomino == null){
                    topDomino = played;
                    bottomDomino = topDomino;
                }
                else 
                    if(played == null) numberPasses++;
                    else{
                        if(topDomino.isThatDomino(played)){
                            topDomino = played;
                        }
                        else bottomDomino = played;
                        numberPasses = 0;
                    }
                x++;
            }
           
            maxPoints = countPoints(playingOrderList);
            match++;
            resetTable();
        }
        System.out.println("Game is Over.");
        getWinner();
        System.out.println("\n---- Players Points: ----");
        System.out.println(getPoints(_players));
        System.out.println("---------------------------------------");

    }

    private void distributeGame(ArrayList<Player> players){

        @SuppressWarnings("unchecked") //Always the same List, thats why doesn't need to be checked
        ArrayList<Domino> aux = (ArrayList<Domino>) _dominos.clone();

        Random rand = new Random();
        int nonRecived = 28;
        for(Player player : players){
            for(int i = 0; i<7; i++){
                int randomNumber = rand.nextInt(nonRecived);
                player.giveHand(aux.get(randomNumber));
                aux.remove(randomNumber);
                nonRecived--;
            }
        }
    }


    private ArrayList<Player> getOrder(int order){
        ArrayList<Player> aux = new ArrayList<Player>();
        for(int i = 0; i<4; i++){
            aux.add(_players.get((order+i)%4));
        }
        return aux;
    }

    private String playingOrder(String type, ArrayList<Player> aux){
        return "--- " + type +" ----\n" +
            "Player " + aux.get(0).getName() +"\n" +
            "Player " + aux.get(1).getName() +"\n" +
            "Player " + aux.get(2).getName() +"\n" +
            "Player " + aux.get(3).getName() +"\n" +
            "——————————\n";
    }

    private String receivingOrder(ArrayList<Player> aux){
        return aux.get(3).getName() + " Shuffling the pieces ...\n\n" +
        "Player " + aux.get(0).getName() + " receiving pieces ...\n" +
        "Player " + aux.get(1).getName() + " receiving pieces ...\n" +
        "Player " + aux.get(2).getName() + " receiving pieces ...\n" +
        "Player " + aux.get(3).getName() + " receiving pieces ...\n";
    }

    private String getPoints(ArrayList<Player> aux){
        return aux.get(0).getName()+ " : " + aux.get(0).getPoints() + " points\n" 
            + aux.get(1).getName()+ " : " + aux.get(1).getPoints() + " points\n" 
            + aux.get(2).getName()+ " : " + aux.get(2).getPoints() + " points\n"
            + aux.get(3).getName()+ " : " + aux.get(3).getPoints() + " points\n";
    }

    private int countPoints(ArrayList<Player> aux){
        ArrayList<Integer> points = new ArrayList<Integer>();
        ArrayList<String> winners = new ArrayList<String>();
        int min = 99999; //to know the min its was to compare teh first one to a bigger impossible number
        int hpoints = 0;
        for(Player player : aux){
            hpoints = player.handPoints();
            if(hpoints < min){ min = hpoints;}
            points.add(hpoints);
        }
        for(int i = 0; i<4; i ++){
            if(points.get(i) == min)
                winners.add(aux.get(i).getName());
        }

        System.out.println("\nMatch is Over.");
        if(winners.size()>1){
            System.out.print("Draw ");
            for(String player : winners){
               System.out.print(player + " ");
            } 
            System.out.println("with " + min);
        }
        else System.out.println("Winner of the match: "+ winners.get(0) + " with "+ min + " points");
        System.out.println("\n---- Players Match Points: ----");
        System.out.println(aux.get(0).getName()+ " : " + points.get(0) + " points\n" 
        + aux.get(1).getName()+ " : " + points.get(1) + " points\n" 
        + aux.get(2).getName()+ " : " + points.get(2) + " points\n"
        + aux.get(3).getName()+ " : " + points.get(3) + " points\n");
        System.out.println("---------------------------------------\n");

        System.out.println("---- Players Accumulated Points ----");
        System.out.println(getPoints(aux));
        System.out.println("---------------------------------------\n");
        int max = 0;
        for(Player player : aux){
            if(player.getPoints()>max) max=player.getPoints();
        }

        return max;
    }

    private void getWinner(){
        int min = 99999;
        ArrayList<Player> winPlayer = new ArrayList<Player>();
        for(Player player : _players) {
           if(player.getPoints()<min) min = player.getPoints();
        }
        for(Player player : _players){
            if(player.getPoints() == min) winPlayer.add(player);
        }

        if(winPlayer.size() == 1) System.out.println("Winner of the match: " + winPlayer.get(0).getName()+" with "+winPlayer.get(0).getPoints() + " points");
        else{
            System.out.print("Draw between: ");
            for(Player player : winPlayer){
                System.out.print(player.getName() + " ");
            }
            System.out.println("with "+winPlayer.get(0)+ " points");
           }
    }

    private void resetTable(){
        for(Domino domino : _dominos){
            domino.reset();
        }
        topDomino = null;
        bottomDomino = null;
        for(Player player : _players){
            player.resethand();
        }
    }

}