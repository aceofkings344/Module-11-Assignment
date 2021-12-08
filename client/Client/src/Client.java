import java.io.*;
import java.net.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;

public class Client extends Application{
	
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	
	public void start(Stage s) {
		
		BorderPane p = new BorderPane();
		p.setPadding(new Insets(10,10,10,10));
		p.setStyle("-fx-border-color: green");
		p.setLeft(new Label("Enter a number: "));
		
		TextField tf = new TextField();
		tf.setAlignment(Pos.BOTTOM_RIGHT);
		p.setCenter(tf);
		
		BorderPane m = new BorderPane();
		TextArea ta = new TextArea();
		m.setCenter(new ScrollPane(ta));
	    m.setTop(p);
	    
	    Scene sc = new Scene(m, 700, 700);
	    s.setTitle("Client"); 
	    s.setScene(sc); 
	    s.show(); 
	    
	    tf.setOnAction(e -> {
	    	try {
	    		int num = Integer.parseInt(tf.getText().trim());
	    		
	    		toServer.writeInt(num);
	    		toServer.flush();
	    		
	    		boolean prime = fromServer.readBoolean();
	    		
	    		if(!prime){
	    			ta.appendText(num + " is prime.\n");
	    		}
	    		else {
	    			ta.appendText(num + " is not prime.\n");
	    		}
	    	}
	    	catch(Exception f) {
	    		System.err.println(f);
	    	}
	    });
	    
	    try {
	    	Socket so = new Socket("localhost", 8000);
	    	fromServer = new DataInputStream(so.getInputStream());
	    	toServer = new DataOutputStream(so.getOutputStream());
	    }
	    catch(Exception g) {
	    	ta.appendText(g.toString() + '\n');
	    }
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		launch(args);
	}

}
