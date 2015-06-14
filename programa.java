//Scratch parsed in Java 
package cucumber.features;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
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
	public static boolean cucumberKey = true;
	public static boolean loop = true;
	public static long total_timeApp = 0;
	public static long timeApp = System.currentTimeMillis();
	public static SCObject scObject_1 = new SCObject();
	public static Thread_1 scThread_1 = new Thread_1();
	public static Thread_2 scThread_2 = new Thread_2();
	public static Thread_3 scThread_3 = new Thread_3();
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
			Globals.scThread_1.start();
			Globals.scThread_2.start();
			Globals.scThread_3.start();
			Globals.cucumberKey = false;
		}
	}
}
class App extends Thread{ {
	public void run (){
		// Key Listener declaration
		JTextField textField = new JTextField();
		textField.addKeyListener(new MKeyListener());
		JFrame jframe = new JFrame();
		jframe.add(textField);
		jframe.setSize(400, 400);
		jframe.setVisible(true);
		//Filling the ArrayListwith the SCobjects
		Globals.listSCObjects.add(new SCObject("Crab",0,14.61084719307128,7.11707852528936,-153.47114057941724,"none",false,1,true));
		Globals.cucumberKey = false;
	}
}
