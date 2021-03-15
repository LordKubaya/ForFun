import java.util.ArrayList;

import java.lang.Math;
public class Game{
    private int _size;
    private int _movements;

    private ArrayList<Disco> _pile1 = new ArrayList<Disco>(); //(3 discos + | ) _pile1[0] 
    private int numOfDiscsA = 0;
    
    private ArrayList<Disco> _pile2 = new ArrayList<Disco>();
    private int numOfDiscsB = 0;

    private ArrayList<Disco> _pile3 = new ArrayList<Disco>();
    private int numOfDiscsC = 0;

    Game(int size){
        _size = size;
        _movements = 0;

        for(int i=0; i<_size+1; i++){
            _pile1.add(new Disco(_size-i,_size));
            _pile2.add(new Disco(0,_size));
            _pile3.add(new Disco(0,_size));
        }

        numOfDiscsA = _size;
    }

    public void optimal(){
        System.out.println("->The optimal number of movements for " + _size + " rods is " + ((int)Math.pow(2,_size)-1) + "<-" );
    }

    public void numberMoviments(){
        System.out.println("Number of movements="+_movements+"\n");
    }
    public int getMovements(){return _movements;}

    public void showPuzzle(){
        for(int i=_size;i>=0;i--){
            System.out.println(" "+_pile1.get(i)+" "+_pile2.get(i)+" "+_pile3.get(i));
        }
        System.out.println("#".repeat((2*_size+1)*3+4)+"\n"+" ".repeat(_size+1)+"A"+" ".repeat(_size*2+1)+ "B"+" ".repeat(_size*2+1)+"C");
    }

    public void play(int choice){
        switch(choice){
            case 1: //A -> B 
                if(numOfDiscsA==0){
                    System.out.println("Impossible move, A is empty");
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsB>0 && _pile1.get(numOfDiscsA-1).getTam() < _pile2.get(numOfDiscsB-1).getTam() || numOfDiscsA!=0 && numOfDiscsB == 0){
                        _pile2.set(numOfDiscsB,_pile1.get(numOfDiscsA-1));
                        _pile1.set(numOfDiscsA-1,new Disco(0,_size));
                        numOfDiscsA--;
                        numOfDiscsB++;
                        _movements++;
                    }
                    else System.out.println("Impossible move, Disc on A is bigher than the Disc on B");
                }
                break;

            case 2:
                if(numOfDiscsA==0){
                    System.out.println("Impossible move, A is empty");
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsC>0 && _pile1.get(numOfDiscsA-1).getTam() < _pile3.get(numOfDiscsC-1).getTam() || numOfDiscsA!=0 && numOfDiscsC ==0){
                        _pile3.set(numOfDiscsC,_pile1.get(numOfDiscsA-1));
                        _pile1.set(numOfDiscsA-1,new Disco(0,_size));
                        numOfDiscsA--;
                        numOfDiscsC++;
                        _movements++;
                    }
                    else System.out.println("Impossible move, Disc on A is bigher than the Disc on C");
                }
                break;

            case 3:
                if(numOfDiscsB==0){
                    System.out.println("Impossible move, B is empty");
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsB>0 && _pile2.get(numOfDiscsB-1).getTam() < _pile1.get(numOfDiscsA-1).getTam() || numOfDiscsB!=0 && numOfDiscsA == 0){
                        _pile1.set(numOfDiscsA,_pile2.get(numOfDiscsB-1));
                        _pile2.set(numOfDiscsB-1,new Disco(0,_size));
                        numOfDiscsB--;
                        numOfDiscsA++;
                        _movements++;
                    }
                    else System.out.println("Impossible move, Disc on B is bigher than the Disc on A");
                }
                break;
            case 4:
                if(numOfDiscsB==0){
                    System.out.println("Impossible move, B is empty");
                }
                else{
                    if(numOfDiscsB>0 && numOfDiscsC>0 && _pile2.get(numOfDiscsB-1).getTam() < _pile3.get(numOfDiscsC-1).getTam() || numOfDiscsB!=0 && numOfDiscsC == 0){
                        _pile3.set(numOfDiscsC,_pile2.get(numOfDiscsB-1));
                        _pile2.set(numOfDiscsB-1,new Disco(0,_size));
                        numOfDiscsB--;
                        numOfDiscsC++;
                        _movements++;
                    }
                    else System.out.println("Impossible move, Disc on B is bigher than the Disc on C");
                }
                break;
            
            case 5:
                if(numOfDiscsC==0){
                    System.out.println("Impossible move, C is empty");
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsC>0 && _pile3.get(numOfDiscsC-1).getTam() < _pile1.get(numOfDiscsA-1).getTam() || numOfDiscsC!=0 && numOfDiscsA == 0){
                        _pile1.set(numOfDiscsA,_pile3.get(numOfDiscsC-1));
                        _pile3.set(numOfDiscsC-1,new Disco(0,_size));
                        numOfDiscsC--;
                        numOfDiscsA++;
                        _movements++;
                    }
                    else System.out.println("Impossible move, Disc on A is bigher than the Disc on C");
                }
                break;
                
            case 6:
                if(numOfDiscsC==0){
                    System.out.println("Impossible move, C is empty");
                }
                else{
                    if(numOfDiscsB>0 && numOfDiscsC>0 && _pile3.get(numOfDiscsC-1).getTam() < _pile2.get(numOfDiscsB-1).getTam() || numOfDiscsC!=0 && numOfDiscsB == 0){
                        _pile2.set(numOfDiscsB,_pile3.get(numOfDiscsC-1));
                        _pile3.set(numOfDiscsC-1,new Disco(0,_size));
                        numOfDiscsC--;
                        numOfDiscsB++;
                        _movements++;
                    }
                    else System.out.println("Impossible move, Disc on A is bigher than the Disc on C");
                }
                break;

        }

    }

    public boolean win(){
        if(numOfDiscsC == _size) return true;
        return false;
    }
    
}