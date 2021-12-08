import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Server extends Application{
	
	private static ServerSocket sS= null;
	
	public void start(Stage s) {
		
		TextArea ta = new TextArea();
		
		Scene sc = new Scene(new ScrollPane(ta), 700, 700);
		s.setTitle("Server");
		s.setScene(sc);
		s.show();
		
		s.setOnCloseRequest(e -> {
			e.consume();
			try {
				sS.close();
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		new Thread( () -> {
			try {
				 sS = new ServerSocket(8000);
				
				Platform.runLater(() ->
						ta.appendText("Server started at " + new Date() + '\n'));
				
				Socket so = sS.accept();
				
				DataInputStream inputFromClient = new DataInputStream(so.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(so.getOutputStream());
				
				while(true) {
					
					int num = inputFromClient.readInt();
					
					boolean prime = false;
					for(int i = 2; i <= num / 2; i++) {
						if(num % i == 0) {
							prime = true;
							break;
						}
					}
					
					outputToClient.writeBoolean(prime);
					
					Platform.runLater(() -> {
						ta.appendText("Number recieved from client to check if prime is: " + num + '\n');
					});
				}
			}
			catch(Exception f) {
				f.printStackTrace();
			}
		}).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
