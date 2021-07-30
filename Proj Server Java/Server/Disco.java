public class Disco{
    private int _tam;
    private int _general_size;

    public Disco(int tam,int general_size){
        _tam = tam;
        _general_size = general_size;
    }

    public int getTam(){return _tam;}
    @Override
    public String toString(){
        if(_tam==0){
            return " ".repeat(_general_size-_tam)+"|"+" ".repeat(_general_size-_tam);
        }
        return " ".repeat(_general_size-_tam)+"*".repeat(1+2*_tam)+" ".repeat(_general_size-_tam); 
    }
}