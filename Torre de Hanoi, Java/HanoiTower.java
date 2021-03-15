import java.util.Scanner;
import  java.lang.Character;
class HanoiTower {
    
    public static void main(String[] args) {
        int size;
        Scanner sc = new Scanner(System.in);
        System.out.println("*********************Tower of Hanoi*********************");

        do{
            System.out.println("Number of Rods:");

            size = sc.nextInt();
            if(size<3) System.out.println("To small (between 3 and 10)");
            if(size>10) System.out.println("To large (between 3 and 10)");
        }while(size<3 || size >10);

        Game game = new Game(size);

        game.optimal();

        char abort;
        int choice;
        while(true){
            game.numberMoviments();
            game.showPuzzle();

            System.out.println("Abort?([Y] for yes, any key to continue)");
            abort = sc.next().charAt(0);
            if('y'==Character.toLowerCase(abort)) break;
            
            while(true){
                System.out.println("Choose Possible Movement:\n1:A->B\t2:A->C\t3:B->A\t4:B->C\t5:C->A\t6:C->B");
                choice = sc.nextInt();
                if(choice<1 || choice>6)
                    System.out.println("Invalid option");

                else break;   
            }

            game.play(choice);
            if(game.win()){
                System.out.println("Congratulations!!! Done in " + game.getMovements() + " steps.");
                if(game.getMovements()==((int)Math.pow(2,size)-1))
                    System.out.println("You did it in the minimal number of steps that was "+ ((int)Math.pow(2,size)-1) +".");
                break;
            }
        }
    }

}