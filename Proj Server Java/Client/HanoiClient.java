import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HanoiClient{
	
	public static void main(String args[]) throws IOException 
	{
        Scanner sc = new Scanner(System.in);
		// Open your connection to a server, at port 1234
        System.out.println("Connect to Server Hanói? [Y/N]");
        if(sc.nextLine().compareTo("Y") == 0){
        
            Socket socket = new Socket("localhost", 1236);

            // Build DataStreams (input and output) to send and receive messages to/from the server
            InputStream in = socket.getInputStream();
            DataInputStream dataIn = new DataInputStream(in);

            OutputStream out = socket.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);

            String serverResponse = dataIn.readUTF();

            String username;
            do{
                
                System.out.println("Insira Login:");
                username = sc.nextLine();
                dataOut.writeUTF(username);
                dataOut.flush();
                serverResponse = dataIn.readUTF();
                
                if(serverResponse.compareTo("Wrong") == 0){
                    System.out.println("Utilizador invalido");
                }

            }while(serverResponse.compareTo("Password")!=0);

            String password;
            do{
                
                System.out.println("Insira Password: ");
                password = sc.nextLine();
                dataOut.writeUTF(password);
                dataOut.flush();
                serverResponse = dataIn.readUTF();
                if(serverResponse.compareTo("Wrong") == 0){
                    System.out.println("Password invalida");
                }
            }while(serverResponse.compareTo("accepted")!=0);
            
            System.out.println("Nice " + username + ", the server accepted you :-))))");
            
            while(true){
                String msg;
                do{
                    System.out.println("Insert Number of Rods: ");
                    msg = sc.nextLine();
                    dataOut.writeUTF(msg);
                    dataOut.flush();

                    serverResponse = dataIn.readUTF();
                    if(serverResponse.compareTo("Rods Rejected") == 0){
                        System.out.println("Invalid Number of Rods");
                    }
                }while(serverResponse.compareTo("Rods Accepted")!=0);

                do{
                    System.out.println("Insert Origin Pin: ");
                    msg = sc.nextLine();
                    dataOut.writeUTF(msg);
                    dataOut.flush();

                    serverResponse = dataIn.readUTF();
                    if(serverResponse.compareTo("Pin Rejected") == 0){
                        System.out.println("Invalid Origin Pin");
                    }
                }while(serverResponse.compareTo("Pin Accepted")!=0);

                do{
                    System.out.println("Insert Target Pin: ");
                    msg = sc.nextLine();
                    dataOut.writeUTF(msg);
                    dataOut.flush();

                    serverResponse = dataIn.readUTF();
                    if(serverResponse.compareTo("Pin Rejected") == 0){
                        System.out.println("Invalid Target Pin");
                    }
                }while(serverResponse.compareTo("Pin Accepted")!=0);

                serverResponse = dataIn.readUTF();
                String choice;
                while(serverResponse.compareTo("WIN")!=0 && serverResponse.compareTo("EXIT")!=0){
                    
                    System.out.println(serverResponse);
                    System.out.println("Choose Possible Movement:\n1:A->B\t2:A->C\t3:B->A\t4:B->C\t5:C->A\t6:C->B\tOther:Exit");
                    choice = sc.nextLine();
                    dataOut.writeUTF(choice);
                    dataOut.flush();

                    serverResponse = dataIn.readUTF();
                }

                if(serverResponse.compareTo("EXIT")==0){
                    System.out.println(dataIn.readUTF());
                }
                else{
                    System.out.println(dataIn.readUTF());
                }

               
                System.out.println("----------Select an Option----------\n1-Play again :-)\n2-See statistics :-)\nQ-Stop :-(");
                String option = sc.nextLine();

                while(true){
                    dataOut.writeUTF(option);

                    if(option.compareTo("2")==0){
                        System.out.println(dataIn.readUTF());
                    }

                    else break;

                    System.out.println("----------Select an Option----------\n1-Play again :-)\n2-See statistics :-)\nQ-Stop :-(");
                    option = sc.nextLine();
                }

                if(option.compareTo("Q")==0){
                    break;
                }
            }
            System.out.println("GG");
            dataOut.close();
            dataIn.close();
            socket.close();
        }
        else System.out.println("Bye Bye");
	}
}