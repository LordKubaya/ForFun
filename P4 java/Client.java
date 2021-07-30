import java.rmi.RMISecurityManager;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client{
    public static void main(String arg[])
    {
        int timeOut = 0;
        int port = 5268;
        boolean connected = false;
        String firstName = null;
        String lastName = null;
        String password = null;
        String email = null;
        String afiliation = null;
        String userName = null;

        String response = null;
        
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("=================Scholar System=================");
            System.out.println("1-Register new author");
            System.out.println("2-Login");
            System.out.println("3-Exit");
            System.out.println("Option?");

            switch(Integer.parseInt(scanner.nextLine())){
                case 1:
                    System.out.println("Enter the First Name : ");
                    firstName = scanner.nextLine();
                    System.out.println("Enter the First Name : ");
                    lastName = scanner.nextLine();
                    System.out.println("Enter the email : ");
                    email= scanner.nextLine();
                    System.out.println("Enter the password : ");
                    password = scanner.nextLine();
                    System.out.println("Enter the afiliation : ");
                    afiliation = scanner.nextLine();
                    timeOut = 0;
                    do{
                        try
                        {
            
                            Interface obj = (Interface) Naming.lookup( "rmi://localhost:" +port+"/server");
                            connected = true;
                            System.out.println(obj.registerNewAutor(firstName, lastName, email, password, afiliation));
                        }
                        catch (Exception e)
                        {   
                            timeOut++;
                            System.out.println("Server OFF. Lets try again in 3 seconds("+ timeOut+" Attemps!!!)");
                            try{
                                TimeUnit.SECONDS.sleep(3);
                            }catch (Exception x){}
                        }
                    }while(timeOut<10 && !connected);
                    connected = false;
                        break;

                case 2:
                    System.out.println("Enter the username : ");
                    email = scanner.nextLine();
                    
                    System.out.println("Enter the password : ");
                    password = scanner.nextLine();
                    timeOut = 0;
                    do{
                        try
                        {
            
                            Interface obj = (Interface) Naming.lookup( "rmi://localhost:" +port+"/server");
                            connected = true;
                            response = obj.login(email, password);
                            if(response.compareTo("logged")==0){
                                userName = obj.getUserName(email);
                            }
                            else{
                                connected = false;
                                break;
                            }
                        }
                        catch (Exception e)
                        {   
                            timeOut++;
                            System.out.println("Server OFF. Lets try again in 3 seconds("+ timeOut+" Attemps!!!)");
                            try{
                                TimeUnit.SECONDS.sleep(3);
                            }catch (Exception x){}
                        }
                    }while(timeOut<=10 && !connected);
                    if(connected == false) break;

                    connected = false;
                    
                    if(timeOut > 10){
                        System.out.println("Server connection not possible closing...");
                        return;
                    }
                    
                    while (true){
                        timeOut = 0;
                        System.out.println("============== " + userName + " =================");
                        System.out.println("1-List Publications by year (Newest first)");
                        System.out.println("2-List Publication by citations (Most cited first)");
                        System.out.println("3-Add Publication");
                        System.out.println("4-Look for author publications in database");
                        System.out.println("5-Remove Publication");
                        System.out.println("6-Show author Statistics");
                        System.out.println("7-Logout");
                        System.out.println("=================================================");
                        int option = Integer.parseInt(scanner.nextLine());
                        if(option == 7) break;
                        String authorList = null;
                        String title = null;
                        int publicationYear = 0;
                        String magazine = null;
                        int volume = 0;
                        int number = 0;
                        int citations = 0;
                        String pages = null;
                        String DOI = null;
                        if(option == 3) {
                                System.out.println("Lista de autores: (Por exemplo: 'Teixeira, César; Pinto, Mauro; ...'");
                                authorList = scanner.nextLine();
                                System.out.println("Titulo da publicação: ");
                                title = scanner.nextLine();
                                System.out.println("Ano de publicação: ");
                                publicationYear = Integer.parseInt(scanner.nextLine());
                                System.out.println("Nome da Revista: ");
                                magazine = scanner.nextLine();
                                System.out.println("Numero de citações: ");
                                citations = Integer.parseInt(scanner.nextLine());
                                System.out.println("Volume: ");
                                volume = Integer.parseInt(scanner.nextLine());
                                System.out.println("Numero: ");
                                number = Integer.parseInt(scanner.nextLine());
                                System.out.println("Paginas: (Página de Início – Página de fim)");
                                pages = scanner.nextLine();
                                System.out.println("DOI: (Digital Object Identifier)");
                                DOI = scanner.nextLine();
                        }
                        
                        do{                      
                            try
                            {
                
                                Interface obj = (Interface) Naming.lookup( "rmi://localhost:" +port+"/server");
                                connected = true;
                                switch (option) {
                                    case 1: 
                                        System.out.println("-----------------> Publications <----------------");
                                        System.out.println(obj.showPublications(email,1));
                                        System.out.println("-------------------------------------------------");
                                    break;
                                    case 2: 
                                        System.out.println("-----------------> Publications <----------------");
                                        System.out.println(obj.showPublications(email,2));
                                        System.out.println("-------------------------------------------------");
                                    break;
                                    case 3: 
                                        System.out.println(obj.addPublication(email, authorList, title, publicationYear, magazine,citations, volume, number, pages, DOI));
                                    break;
                                    case 4:
                                        System.out.println("-----------------> Sugested Publications <----------------");
                                        System.out.println(obj.findPublications(email));
                                        System.out.println("----------------------------------------------------------");
                                        System.out.println("Witch ones would you like to insert (insert witch ones separeted by ;");
                                        String pubs = scanner.nextLine();
                                        System.out.println(obj.addPublicationsList(email,pubs));
                                        System.out.println("----------------------------------------------------------");

                                    break; 
                                    case 5:
                                        String aux = obj.showPublications(email,2);
                                        System.out.println("-----------------> Publications <----------------");
                                        System.out.println(aux);
                                        System.out.println("-------------------------------------------------");
                                        if(aux.compareTo("Sem Publicações")!=0){
                                            System.out.println("Witch to remove?");
                                            System.out.println(obj.removePublication(email,Integer.parseInt(scanner.nextLine())));
                                        }
                                    break;
                                    case 6: 
                                        System.out.println("-----------------> Author Statistics <----------------");
                                        System.out.println(obj.showPerformace(email));
                                        System.out.println("------------------------------------------------------");
                                    break;
        
                                }
                            }
                            catch (Exception e)
                            {   
                                timeOut++;
                                System.out.println("Server OFF. Lets try again in 3 seconds("+ timeOut+" Attemps!!!)");
                                try{
                                    TimeUnit.SECONDS.sleep(3);
                                }catch (Exception x){}
                            }
                        }while(timeOut<10 && !connected);
                        connected = false;
                        if(timeOut > 10){
                            System.out.println("Server connection not possible closing...");
                            return;
                        }
                    }
                    break;
                case 3: System.out.println("Closing");
                    return;
            }
        
        }
    }
}