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

    private String _targetPin; 
    
    Game(int size, String originPin, String targetPin){
        _size = size;
        _movements = 0;
        _targetPin = targetPin;

        if(originPin.compareTo("A")==0){
            for(int i=0; i<_size+1; i++){
                _pile1.add(new Disco(_size-i,_size));
                _pile2.add(new Disco(0,_size));
                _pile3.add(new Disco(0,_size));
            }
            numOfDiscsA = _size;
        }
        else if(originPin.compareTo("B")==0){
            for(int i=0; i<_size+1; i++){
                _pile2.add(new Disco(_size-i,_size));
                _pile1.add(new Disco(0,_size));
                _pile3.add(new Disco(0,_size));
            }
            numOfDiscsB = _size;
        }
        else{
            for(int i=0; i<_size+1; i++){
                _pile3.add(new Disco(_size-i,_size));
                _pile2.add(new Disco(0,_size));
                _pile1.add(new Disco(0,_size));
            }
            numOfDiscsC = _size;
        }

        
    }

    public void optimal(){
        System.out.println("->The optimal number of movements for " + _size + " rods is " + ((int)Math.pow(2,_size)-1) + "<-" );
    }

    public void numberMoviments(){
        System.out.println("Number of movements="+_movements+"\n");
    }
    public int getMovements(){return _movements;}

    public String showPuzzle(){
        String aux = "";
        for(int i=_size;i>=0;i--){
            aux+=" "+_pile1.get(i)+" "+_pile2.get(i)+" "+_pile3.get(i)+ "\n";
        }

        if(_targetPin.compareTo("C")==0){
            return aux + "#".repeat((2*_size+1)*3+4)+"\n"+" ".repeat(_size+1)+"A"+" ".repeat(_size*2+1)+ "B"+" ".repeat(_size*2+1)+"C*";
        }
        else if(_targetPin.compareTo("B")==0){
            return aux + "#".repeat((2*_size+1)*3+4)+"\n"+" ".repeat(_size+1)+"A"+" ".repeat(_size*2+1)+ "B*"+" ".repeat(_size*2+1)+"C";
        }
        else{
            return aux + "#".repeat((2*_size+1)*3+4)+"\n"+" ".repeat(_size+1)+"A*"+" ".repeat(_size*2+1)+ "B"+" ".repeat(_size*2+1)+"C";
        }
    }

    public int play(int choice){
        switch(choice){
            case 1: //A -> B 
                if(numOfDiscsA==0){
                    return 1;
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsB>0 && _pile1.get(numOfDiscsA-1).getTam() < _pile2.get(numOfDiscsB-1).getTam() || numOfDiscsA!=0 && numOfDiscsB == 0){
                        _pile2.set(numOfDiscsB,_pile1.get(numOfDiscsA-1));
                        _pile1.set(numOfDiscsA-1,new Disco(0,_size));
                        numOfDiscsA--;
                        numOfDiscsB++;
                        _movements++;
                        return 0;
                    }
                    else return 1;
                }
                
            case 2:
                if(numOfDiscsA==0){
                    return 1;
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsC>0 && _pile1.get(numOfDiscsA-1).getTam() < _pile3.get(numOfDiscsC-1).getTam() || numOfDiscsA!=0 && numOfDiscsC ==0){
                        _pile3.set(numOfDiscsC,_pile1.get(numOfDiscsA-1));
                        _pile1.set(numOfDiscsA-1,new Disco(0,_size));
                        numOfDiscsA--;
                        numOfDiscsC++;
                        _movements++;
                        return 0;
                    }
                    else return 1;
                }
               

            case 3:
                if(numOfDiscsB==0){
                    return 1;
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsB>0 && _pile2.get(numOfDiscsB-1).getTam() < _pile1.get(numOfDiscsA-1).getTam() || numOfDiscsB!=0 && numOfDiscsA == 0){
                        _pile1.set(numOfDiscsA,_pile2.get(numOfDiscsB-1));
                        _pile2.set(numOfDiscsB-1,new Disco(0,_size));
                        numOfDiscsB--;
                        numOfDiscsA++;
                        _movements++;
                        return 0;
                    }
                    else return 1;
                }
             
            case 4:
                if(numOfDiscsB==0){
                    return 1;
                }
                else{
                    if(numOfDiscsB>0 && numOfDiscsC>0 && _pile2.get(numOfDiscsB-1).getTam() < _pile3.get(numOfDiscsC-1).getTam() || numOfDiscsB!=0 && numOfDiscsC == 0){
                        _pile3.set(numOfDiscsC,_pile2.get(numOfDiscsB-1));
                        _pile2.set(numOfDiscsB-1,new Disco(0,_size));
                        numOfDiscsB--;
                        numOfDiscsC++;
                        _movements++;
                        return 0;
                    }
                    else return 1;
                }
            
            case 5:
                if(numOfDiscsC==0){
                    return 1;
                }
                else{
                    if(numOfDiscsA>0 && numOfDiscsC>0 && _pile3.get(numOfDiscsC-1).getTam() < _pile1.get(numOfDiscsA-1).getTam() || numOfDiscsC!=0 && numOfDiscsA == 0){
                        _pile1.set(numOfDiscsA,_pile3.get(numOfDiscsC-1));
                        _pile3.set(numOfDiscsC-1,new Disco(0,_size));
                        numOfDiscsC--;
                        numOfDiscsA++;
                        _movements++;
                        return 0;
                    }
                    else return 1;
                }
                
            case 6:
                if(numOfDiscsC==0){
                   return 1;
                }
                else{
                    if(numOfDiscsB>0 && numOfDiscsC>0 && _pile3.get(numOfDiscsC-1).getTam() < _pile2.get(numOfDiscsB-1).getTam() || numOfDiscsC!=0 && numOfDiscsB == 0){
                        _pile2.set(numOfDiscsB,_pile3.get(numOfDiscsC-1));
                        _pile3.set(numOfDiscsC-1,new Disco(0,_size));
                        numOfDiscsC--;
                        numOfDiscsB++;
                        _movements++;
                        return 0;
                    }
                    else return 1;
                }
        
            }
            return -1;
        }

    public boolean win(){

        if(_targetPin.compareTo("C")==0 && numOfDiscsC == _size) return true;
        else if(_targetPin.compareTo("A")==0 && numOfDiscsA == _size) return true;
        else if(_targetPin.compareTo("B")==0 && numOfDiscsB == _size) return true;
        return false;
    }
    
}