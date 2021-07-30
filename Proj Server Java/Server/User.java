public class User{
    private String _name;
    private String _password;

    private int played[] = new int[11];
    private float average[] = new float[11];

    User(String name, String password){
        _name = name;
        _password = password;
        
        for(int i = 0; i < played.length; i++) {
            played[i] = 0;
        }
    }

    public void addResult(int size, int score){
        played[size]++;
        average[size] = (average[size]*(played[size]-1) + score)/played[size];
    } 

    public String stats(){
        String aux = "";
        for(int i=3; i<10; i++){
            if(played[i]!=0){
                aux += "For " + i + " rods you have in average " + average[i] + " moves and you played " + played[i] + " times.\n";
            }       
        }
        return aux;
    }
    public Boolean correctPassword(String pass){
        return pass.compareTo(_password)==0;
    }

    public String getName(){
        return _name;
    }
}