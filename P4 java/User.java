import java.util.*;

public class User{
    private ArrayList<Publication> publications = new ArrayList<Publication>();

    private String _firstName;
    private String _lastName;
    private String _email;
    private String _password;
    private String _afiliation;

    User(String firstName,String lastName,String email,String password, String afiliation){
        _firstName = firstName;
        _lastName = lastName;
        _email = email;
        _password = password;
        _afiliation = afiliation;
    }

    public Boolean correctPassword(String pass){
        return pass.compareTo(_password)==0;
    }

    public void newPub(Publication pub){
        publications.add(pub);
    }

    public String showPub(){
        return "";
    }
    public Boolean removePub(int n){
        if(publications.size()>0){
            publications.remove(n);
            return true;
        }
        return false;
    }
    public String publications(int t){
        if(publications.size()>0){
            if(t==1)
                Collections.sort(publications, Publication.yearComparator);
            else Collections.sort(publications, Publication.citationsComparator);

            String aux = " ";
            int count = 0;
            for(Publication publication : publications){
                aux+=count + "->" + publication.toStringP(t);
                count ++;
            }
            return aux;
        }
        else return "Sem Publicações";
    }

    public int getH(){
        int H = 0;
        for(Publication publication : publications){
            if(publication.getCitation()>=publications.size()){
                H++;
            }
        }
        return H;
    }

    public int getI10(){
        int i10 = 0;
        for(Publication publication : publications){
            if(publication.getCitation()>=10){
                i10++;
            }
        }
        return i10/10;
    }
    public int getTotalCitation(){
        int total = 0;
        for(Publication publication : publications){
            total += publication.getCitation();
        }
        return total;
    }
    
    @Override
    public String toString(){
        return _lastName + ", " + _firstName;
    }   
}