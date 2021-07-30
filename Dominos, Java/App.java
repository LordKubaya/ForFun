import java.util.Scanner;

public class App {
    public static void main(String[] args){
        System.out.println("Welcome to Domino SI Game.\nPlease insert your player name:");
        Scanner sc = new Scanner(System.in);
        String playerName = sc.nextLine();

        Game game = new Game(playerName);
        game.start();
    }
}
