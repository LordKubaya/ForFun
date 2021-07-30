import java.rmi.*;

public interface Interface extends java.rmi.Remote {
        public String login(String email, String password) throws RemoteException;
        public String registerNewAutor(String firstName,String lastName, String email, String password, String afiliation) throws RemoteException;
        public String showPublications(String email, int t) throws RemoteException;
        public String addPublication(String email, String authorList, String title, int publicationYear, String magazine,int citations, int volume, int number, String pages, String DOI) throws RemoteException;
        public String removePublication(String email, int n) throws RemoteException;
        public String findPublications(String email) throws RemoteException;
        public String addPublicationsList(String email,String pubs) throws RemoteException;
        public String showPerformace(String email) throws RemoteException;
        public String getUserName(String email) throws RemoteException;
}
