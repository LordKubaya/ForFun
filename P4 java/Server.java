import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.io.*;

public class Server extends UnicastRemoteObject implements Interface{

    private static final int PORT = 5268;

    private Hashtable<String, User> users = new Hashtable<String, User>();  
    private Hashtable<String, Publication> publications = new Hashtable<String, Publication>();

    public Server() throws RemoteException {}

    public String login(String email, String password){ 
        if(!users.containsKey(email)){
            return "User não registado";
        }
        else{
            if(users.get(email).correctPassword(password)){
                return "logged";
            }
            else return "wrong password";
        }
     }
    
     public String registerNewAutor(String firstName,String lastName, String email, String password, String afiliation){
        if(users.containsKey(email)) return "User já regitado";
        users.put(email, new User(firstName, lastName, email, password, afiliation));
        return "User " + email + " registado com sucesso";
    }

    public String addPublication(String email, String authorList, String title, int publicationYear, String magazine, int citation, int volume, int number, String pages, String DOI){
        if(publications.containsKey(DOI)){ return "Publicação já existente";}
        Publication aux = new Publication(authorList, title, publicationYear, magazine, citation, volume, number, pages, DOI);
        publications.put(DOI,aux);
        users.get(email).newPub(aux);
        return "Publicação adicionado com sucesso";
    }
    public String showPublications(String email, int t){
        return users.get(email).publications(t);
    }

    public String removePublication(String email, int n){
        User aux = users.get(email);
        if(aux.removePub(n)) return "Publicação removida com sucesso!";
        return "Publicação não existente!";
    }
    public String getUserName(String email){
        return users.get(email).toString();
    }

    public String findPublications(String email){
        ArrayList<Publication> pub = new ArrayList<Publication>(publications.values());
        if(publications.size()>0){
            String name = getUserName(email);
            Collections.sort(pub, Publication.citationsComparator);

            String aux = " ";
            int count = 0;
            for(Publication publication : pub){

                if(publication.getAuthorList().contains(name)){
                    aux+=count + "->" + publication.toStringP(0);
                    count ++;
                }
            }
            return aux;
        }

        else return "Sem Publicações";
    }
    
    public String addPublicationsList(String email,String pubs){
        ArrayList<Publication> pub = new ArrayList<Publication>(publications.values());
        ArrayList<Publication> pubIN = new ArrayList<Publication>();
        String[] parts = pubs.split(";");
        if(publications.size()>0 & pubs != ""){
            String name = getUserName(email);
            Collections.sort(pub, Publication.citationsComparator);
            
            for(Publication publication : pub){

                if(publication.getAuthorList().contains(name)){
                    pubIN.add(publication);
                }
            }
            User u = users.get(email);
        for(String aux : parts){
            u.newPub(pub.get(Integer.parseInt(aux)));
        }

        return "foram adiciadas " + parts.length + " publicações";
        }
        return "Não foram adicinadas publicações";
    }

    public String showPerformace(String email){
        User u = users.get(email);
        return "Total Citations=" + u.getTotalCitation() + "\nH="+u.getH() + "\ni10="+u.getI10();
    }

    static class Close extends Thread {
        Registry registry = null;
        Server server;
        Close(Registry _registry, Server _server) {
            registry = _registry;
            server = _server;
        }

        public void run() {
           System.out.println("Bye.");
           try {
            FileOutputStream fileOut =
            new FileOutputStream("server.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(server);
            out.close();
            fileOut.close();

            registry.unbind("server");
            UnicastRemoteObject.unexportObject(registry, true);
            } catch (Exception e) {
            }
           System.exit(0);
        }
     }
    public static void main(String args[])
    {   
        System.out.println("Server ON");
        try
        {   Registry registry = null;
            
            Server obj = null;
            try {
                FileInputStream fileIn = new FileInputStream("server.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                obj = (Server) in.readObject();
                in.close();
                fileIn.close();
            } catch (Exception e) {
                obj = new Server();
            }
            Runtime.getRuntime().addShutdownHook(new Close(registry,obj));
            registry = LocateRegistry.createRegistry(PORT);
			
			// Binding the Object
			registry.bind("server", obj);
        }
        catch (Exception e)
        {
            System.out.println("HelloImpl err: " + e.getMessage());
            e.printStackTrace();
        }
    }
}          
