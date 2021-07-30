import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HanoiServer{
	
	public static void main(String args[]) throws IOException
	{
		ArrayList<User> users = new ArrayList<User>();
		// Register service on port 1234
		users.add(new User("Ines","1234"));
		users.add(new User("Admin","Admin"));
		


		ServerSocket s = new ServerSocket(1236);

		while (true)
		{
			// Wait and accept a connection
            System.out.println("Nobody wants to talk :-( Waiting for a client :-(");
			Socket s1 = s.accept();
            System.out.println("Nice, I have a client to talk :-)");
			// Build DataStreams (input and output) to send and receive messages to/from the client
			OutputStream out = s1.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);

			InputStream in = s1.getInputStream();
			DataInputStream dataIn = new DataInputStream(in);

			// use the DataInputStream to read a String sent by the client
            dataOut.writeUTF("Connected");
            dataOut.flush();
			User login = null;
            while(true){
                
                String user = dataIn.readUTF();
				
				for(User aux : users){
					System.out.println(aux.getName());
					if(aux.getName().compareTo(user)==0){
						login = aux;
						break;
					}
				}
				
                if(login.getName().compareTo(user)==0) {
                    dataOut.writeUTF("Password");
                    dataOut.flush();
                    break;
                }
                dataOut.writeUTF("Wrong");
                dataOut.flush();
            }

            while (true){
            	String password = dataIn.readUTF();
                if(login.correctPassword(password)) {
                    dataOut.writeUTF("accepted");
                    dataOut.flush();
                    break;
                }
                dataOut.writeUTF("Wrong");
                dataOut.flush();

            }
            System.out.println("HandShake done!!! Lets start!!!");	
			
			while(true){
				int numberOfRods;
				do{
					System.out.println("Waiting for the number of rods!!!");
					numberOfRods = Integer.parseInt(dataIn.readUTF());
					if(numberOfRods>2 && numberOfRods<11){
						dataOut.writeUTF("Rods Accepted");
						dataOut.flush();
						break;
					}
					dataOut.writeUTF("Rods Rejected");
					
				}while(true);

			
				String originPin;
				do{
					System.out.println("Lets Wait for the pin infomation...");
					originPin = dataIn.readUTF();
					if(originPin.compareTo("A")==0 || originPin.compareTo("B")==0 || originPin.compareTo("C")==0){
						dataOut.writeUTF("Pin Accepted");
						dataOut.flush();
						break;
					}
					dataOut.writeUTF("Pin Rejected");
				}while(true);

				String targetPin;
				do{
					//System.out.println("Lets Wait for the pin infomation...");
					targetPin = dataIn.readUTF();
					if(originPin.compareTo(targetPin)!=0 && (targetPin.compareTo("A")==0 || targetPin.compareTo("B")==0 || targetPin.compareTo("C")==0)){
						dataOut.writeUTF("Pin Accepted");
						dataOut.flush();
						break;
					}
					dataOut.writeUTF("Pin Rejected");
				}while(true);


			
				if(originPin.compareTo("A")==0){
					if(targetPin.compareTo("B")==0){
						System.out.println("I received PINDEF:0:1");
					}
					else{
						System.out.println("I received PINDEF:0:2");
					}

				}
				else if(originPin.compareTo("B")==0){
					if(targetPin.compareTo("A")==0){
						System.out.println("I received PINDEF:1:0");
					}
					else{
						System.out.println("I received PINDEF:1:2");
					}

				}
				else{
					if(targetPin.compareTo("A")==0){
						System.out.println("I received PINDEF:2:0");
					}
					else{
						System.out.println("I received PINDEF:2:1");
					}
		
				}
				Game game = new Game(numberOfRods,originPin,targetPin);
				int choice;
				String msg = "\n";
				while(true){
					dataOut.writeUTF(msg + game.showPuzzle()); 
					msg = "\n";
					//System.out.println(game.showPuzzle());
					dataOut.flush();
					

					System.out.println("Waiting for an instruction!!");
					choice = Integer.parseInt(dataIn.readUTF());
					switch (choice) {
						case 1:{
							System.out.println("Received and instructuin to play:MOV:0:1");
							break;
						}
						case 2:{
							System.out.println("Received and instructuin to play:MOV:0:2");
							break;
						}	
						case 3:{
							System.out.println("Received and instructuin to play:MOV:1:0");
							break;
						}
						case 4:{
							System.out.println("Received and instructuin to play:MOV:1:2");
							break;
						}
						case 5:{
							System.out.println("Received and instructuin to play:MOV:2:0");
							break;
						}

						case 6:{
							System.out.println("Received and instructuin to play:MOV:2:1");
							break;
						}

						default:{
							//System.out.println("Received and instructuin to play:GAME:STOP"));
							break;
						}
						
					}
					
					if(game.play(choice) == 1){
						msg = "Impossible Play\n";
					}

					if(game.win()){
						System.out.println("Nice!!! The Client Win!!! ");
						dataOut.writeUTF("WIN");
						dataOut.flush();
						if(game.getMovements()==((int)Math.pow(2,numberOfRods)-1)){
							dataOut.writeUTF(game.showPuzzle() + "You did it in the minimal number of steps that was "+ ((int)Math.pow(2,numberOfRods)-1) +".");
							dataOut.flush();
							login.addResult(numberOfRods,game.getMovements());
						}
						else{
							dataOut.writeUTF("You did it in " +game.getMovements() +" steps.");
							login.addResult(numberOfRods,game.getMovements());
						}
						break;
					}

					if(choice<1 || choice>6){
						dataOut.writeUTF("EXIT");
						dataOut.flush();

						dataOut.writeUTF("I will quit this game!!!\n" + game.showPuzzle());
						dataOut.flush();
						break;
					}
				}

				System.out.println("Ready to play!!!!!");
				System.out.println("Waiting for one instruction...");
				String option = dataIn.readUTF();

				while(true){
					
					if (option.compareTo("2")==0){
						dataOut.writeUTF(login.stats());
					}
					else break;

					option = dataIn.readUTF();
				}

				if(option.compareTo("Q")==0){
					System.out.println("Received and instructuin to play:GAME:STOP");
					break;
				}
			}
			dataOut.close();
			dataIn.close();
			s1.close();
		}
	}
}