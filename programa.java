//Scratch parsed in Java 
import java.io.*;
import org.json.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Thread_1 extends Thread {
	//whenGreenFlag
	//gotoX:y:
	//setRotationStyle
	//doForever
}
class Thread_2 extends Thread {
	//whenGreenFlag
	//lookLike:
	//doForever
}
class Thread_3 extends Thread {
	//whenGreenFlag
	//doForever
}
class Globals {
	public static boolean loop = true;
	public static long total_timeApp = 0;
	public static long timeApp = System.currentTimeMillis();
	public static SCObject scObject_1 = new SCObject();
	public static Thread_1 scThread_1 = new Thread_1();
	public static Thread_2 scThread_2 = new Thread_2();
	public static Thread_3 scThread_3 = new Thread_3();
}
class MKeyListener extends KeyAdapter {
	@Override public void keyPressed(KeyEvent event) {
		char ch = event.getKeyChar();
		if (ch == 'q') { //Quit the mainLoop
			Globals.loop = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			Globals.scThread_1.start();
			Globals.scThread_2.start();
			Globals.scThread_3.start();
		}
	}
}
public class programa {
	public static void main(String[] args) {
		// Key Listener declaration
		JTextField textField = new JTextField();
		textField.addKeyListener(new MKeyListener());
		JFrame jframe = new JFrame();
		jframe.add(textField);
		jframe.setSize(400, 400);
		jframe.setVisible(true);
		//Main Loop of the app
		while(Globals.loop){
			if(System.currentTimeMillis() - Globals.timeApp >= 1000 ){
				System.out.println(Globals.total_timeApp);
				Globals.total_timeApp++;
				Globals.timeApp = System.currentTimeMillis();
			}
		}
	}
}
