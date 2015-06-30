//Scratch parsed in Java 
package cucumber.features;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
public class SCprogram {}
class Thread_1 extends Thread {
	public void run(){
		Globals.listSCObjects.get(0).setCostumebyID("costume2");
		Globals.listSCObjects.get(0).nextCostume();
	}
}
class Thread_2 extends Thread {
	public void run(){
	}
}
class Globals {
	public static int steps ;
	public static int wScreen = 480;
	public static int hScreen = 360;
	public static boolean infloop = true ;
	public static boolean cucumberKey = true;
	public static boolean loop = true;
	public static long total_timeApp = 0;
	public static long timeApp = System.currentTimeMillis();
	public static Thread_1 scThread_1 = new Thread_1();
	public static Thread_2 scThread_2 = new Thread_2();
	public static App appT = new App();
	public static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();
}
class MKeyListener extends KeyAdapter {
	@Override public void keyPressed(KeyEvent event) {
		char ch = event.getKeyChar();
		if (ch == 'q') { //Quit the mainLoop
			Globals.loop = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			Globals.cucumberKey = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			Globals.scThread_1.start();
;		}
	}
}
class App extends Thread{
	public void run (){
		// Key Listener declaration
		JTextField textField = new JTextField();
		textField.addKeyListener(new MKeyListener());
		JFrame jframe = new JFrame();
		jframe.add(textField);
		jframe.setSize(400, 400);
		jframe.setVisible(true);
		//Filling the ArrayListwith the SCobjects
		Globals.listSCObjects.add(new SCObject("Sprite1",0,120,0,90,"normal",false,1,true,1));
		Globals.listSCObjects.get(0).costumes.add(new Costume("costume1",1,"09dc888b0b7df19f70d81588ae73420e.svg",1,47,55,348,235));
		Globals.listSCObjects.get(0).costumes.add(new Costume("costume2",2,"3696356a03a8d938318876a593572843.svg",1,47,55,348,237));
		Globals.cucumberKey = false;
		Globals.initiater.addListener(Globals.responder);
	}
}
