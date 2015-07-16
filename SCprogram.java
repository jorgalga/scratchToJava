//Scratch parsed in Java 
package cucumber.features;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.Calendar;
public class SCprogram {}
class Thread_1 extends Thread {
	public void run(){
		//Move forward instruction
		Globals.listSCObjects.get(0).scratchX = Globals.listSCObjects.get(0).scratchX + Math.round(Math.sin(Math.toRadians(Globals.listSCObjects.get(0).direction)))*(1 + 1) ;
		Globals.listSCObjects.get(0).scratchY = Globals.listSCObjects.get(0).scratchY + Math.round(Math.cos(Math.toRadians(Globals.listSCObjects.get(0).direction)))*(1 + 1)  ;
		//Turn right instruction
		Globals.listSCObjects.get(0).direction = Globals.listSCObjects.get(0).direction + (2 - 2);
		if(Globals.listSCObjects.get(0).direction >= 180){Globals.listSCObjects.get(0).direction = -180 + (Globals.listSCObjects.get(0).direction-180);}
		//Turn left instruction
		Globals.listSCObjects.get(0).direction = Globals.listSCObjects.get(0).direction - (2 * 2);
		if(Globals.listSCObjects.get(0).direction >= -180){Globals.listSCObjects.get(0).direction = 180 - (Math.abs(Globals.listSCObjects.get(0).direction+180));}
		//Heading left instruction
		Globals.listSCObjects.get(0).direction = (3 / 2);
		//Goto XY instruction
		Globals.listSCObjects.get(0).scratchX =0;
		Globals.listSCObjects.get(0).scratchY =0;
		//glideSecs:toX:y:elapsed:from: instruction
		try {
			Thread.sleep((1*1000));
		} catch (InterruptedException e) {e.printStackTrace();}
		Globals.listSCObjects.get(0).scratchX =0;
		Globals.listSCObjects.get(0).scratchY =0;
		//changeXposBy instruction
		Globals.listSCObjects.get(0).scratchX =Globals.listSCObjects.get(0).scratchX + 10;
		//set posx instruction
		Globals.listSCObjects.get(0).scratchX =0;
		//changeYposBy instruction
		Globals.listSCObjects.get(0).scratchY =Globals.listSCObjects.get(0).scratchY + 10;
		//set posy instruction
		Globals.listSCObjects.get(0).scratchY =0;
		//bounce Off Edge instruction
		//Edge of the Left
		if(Globals.listSCObjects.get(0).scratchX - Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).width/2.0  <= (Globals.wScreen/2.0)*-1){
			Globals.listSCObjects.get(0).direction = Globals.listSCObjects.get(0).direction * -1;
			Globals.listSCObjects.get(0).scratchX = -1*(Globals.wScreen/2.0) + Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).width/2.0;
		}
		//Edge of the Right
		if(Globals.listSCObjects.get(0).scratchX + Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).width/2.0  >= (Globals.wScreen/2.0)){
			Globals.listSCObjects.get(0).direction = Globals.listSCObjects.get(0).direction * -1;
Globals.listSCObjects.get(0).scratchX = Globals.wScreen/2.0 - Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).width/2.0;
		}
		//Edge of the Top
		if(Globals.listSCObjects.get(0).scratchY - Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).height/2.0  <= (Globals.hScreen/2.0)*-1){
			if(Globals.listSCObjects.get(0).direction < 0) { Globals.listSCObjects.get(0).direction = - 180 + Math.abs(Globals.listSCObjects.get(0).direction); }
			if(Globals.listSCObjects.get(0).direction >=0) { Globals.listSCObjects.get(0).direction = 180 - Globals.listSCObjects.get(0).direction; }
			Globals.listSCObjects.get(0).scratchY = -1*(Globals.hScreen/2.0) + Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).height/2.0;
		}
		//Edge of the Bottom
		if(Globals.listSCObjects.get(0).scratchY + Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).height/2.0 >= (Globals.hScreen/2.0) ){
			if(Globals.listSCObjects.get(0).direction < 0) { Globals.listSCObjects.get(0).direction = - 180 + Math.abs(Globals.listSCObjects.get(0).direction);}
			if(Globals.listSCObjects.get(0).direction >= 0){ Globals.listSCObjects.get(0).direction = 180 - Globals.listSCObjects.get(0).direction; }
			Globals.listSCObjects.get(0).scratchY = (Globals.hScreen/2.0) - Globals.listSCObjects.get(0).costumes.get(Globals.listSCObjects.get(0).currentCostume).height/2.0;
		}
		//Set rotation Style instruction
		Globals.listSCObjects.get(0).rotationStyle ="left-right";
		Globals.cucumberKey = false;
	}
}
class Thread_2 extends Thread {
	public void run(){
		//Play sound with name:meow
		//Play sound and wait instruction
		try {
			Thread.sleep(Globals.getDurationByName("meow"));
		} catch (InterruptedException e) {e.printStackTrace();}
		//Play drum number:1 for 0.25 pulses 
		//Play note:60 for 0.5 pulses 
		//Fixed instrument to:1 
		Globals.volume = Globals.volume-10;
		if(Globals.volume > Globals.Vumbral){Globals.initiater.TriggerMessage("loudness");}
		Globals.volume = 100;
		Globals.cucumberKey = false;
	}
}
class Thread_3 extends Thread {
	public void run(){
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)2015; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)6; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)16; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)5; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)10; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)47; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test4")){ Globals.listSCVariables.get(i).value = (double)56; }
		}
		Globals.cucumberKey = false;
	}
}
class Thread_4 extends Thread {
	public void run(){
		System.out.println("I say: Hello! and I go to sleep");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("I say: Hello!");
		System.out.println("I think: Hmm... and I go to sleep");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("I think: Hmm... ");
		Globals.listSCObjects.get(0).setVisible();
		Globals.listSCObjects.get(0).setNoVisible();
		Globals.listSCObjects.get(0).setCostumebyID("costume2");
		Globals.listSCObjects.get(0).nextCostume();
		for(int i=0; i < Globals.listBackgrounds.size(); i++){
			if(Globals.listBackgrounds.get(i).equals("backdrop1")){
				Globals.currentCostumeIndex = i;
			}
		}
		Globals.initiater.TriggerMessage("startScene_backdrop1");
		Globals.listSCObjects.get(0).size = Globals.listSCObjects.get(0).size-10;
		Globals.listSCObjects.get(0).size = 100;
		Globals.cucumberKey = false;
	}
}
class Thread_5 extends Thread {
	public void run(){
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("test")){ Globals.listSCVariables.get(i).value = (double)Globals.volume; }
		}
		//Set Variable to a value
		for(int i=0;i< Globals.listSCVariables.size();i++){
		if(Globals.listSCVariables.get(i).name.equals("tempo")){ Globals.listSCVariables.get(i).value = (double)Globals.tempo; }
		}
		//Hide Variable by name
		for(int i=0;i< Globals.listSCVariables.size();i++){
			if(Globals.listSCVariables.get(i).name.equals("tempo")){Globals.listSCVariables.get(i).visible = false; }
		}
		//Hide Variable by name
		for(int i=0;i< Globals.listSCVariables.size();i++){
			if(Globals.listSCVariables.get(i).name.equals("tempo")){Globals.listSCVariables.get(i).visible = true; }
		}
		Globals.cucumberKey = false;
	}
}
class Thread_6 extends Thread {
	public void run(){
		//wait:elapsed:from: stopping thread for n seconds
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
		//Do-Repeat instruction
		for(int i=0; i < 10; i++){
			i--;//i never increments 
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
class Globals {
	public static boolean  appLaunched = false;
	public static int steps ;
	public static int currentCostumeIndex = 0;
	public static int wScreen = 480;
	public static int hScreen = 360;
	public static int volume = 100;
	public static int tempo = 60;
	public static boolean infloop = true ;
	public static boolean cucumberKey = true;
	public static boolean loop = true;
	public static long total_timeApp = 0;
	public static long timeApp = System.currentTimeMillis();
	public static Initiater initiater = new Initiater();
	public static Responder responder = new Responder();
	public static Thread_1 scThread_1 = new Thread_1();
	public static Thread_2 scThread_2 = new Thread_2();
	public static Thread_3 scThread_3 = new Thread_3();
	public static Thread_4 scThread_4 = new Thread_4();
	public static Thread_5 scThread_5 = new Thread_5();
	public static Thread_6 scThread_6 = new Thread_6();
	public static App appT = new App();
	public static ArrayList<SCVariable> listSCVariables = new ArrayList<SCVariable>();
	public static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();
	public static ArrayList<Costume> listBackgrounds = new ArrayList <Costume>();
	public static ArrayList<Thread> listScripts = new ArrayList <Thread>();
	public static ArrayList<SCSound> listSCSounds = new ArrayList <SCSound>();
	public static double getSCValueByName(String id){
		double res=-999999;
		for(int i=0;i< Globals.listSCVariables.size();i++){
			if(Globals.listSCVariables.get(i).name.equals(id)){res = Globals.listSCVariables.get(i).value;}
		}
		return res;
	}
	public static int getDurationByName(String id){
		int res =0;
		for(int i=0;i<Globals.listSCSounds.size();i++){
			if(Globals.listSCSounds.get(i).soundName.equals(id)){res = Globals.listSCSounds.get(i).duration; }
		}
		return res;
	}
}
class MKeyListener extends KeyAdapter {
	@Override public void keyPressed(KeyEvent event) {
		char ch = event.getKeyChar();
		if (ch == 'q') { //Quit the mainLoop
			Globals.loop = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			Globals.scThread_1.start();
			Globals.cucumberKey = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			Globals.scThread_4.start();
		}
		if (event.getKeyChar() == 't') {
			Globals.scThread_5.start();
		}
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
		Globals.listSCObjects.add(new SCObject("Sprite1",0,90.47999999999999,30.159999999999997,0,"leftRight",false,1,false,1));
		Globals.cucumberKey = false;
		Globals.appLaunched = true;
		Globals.initiater.addListener(Globals.responder);
	}
}
interface messageListener { void receptorEvent(String msg);}
class Initiater {
	private ArrayList<messageListener> listeners = new ArrayList<messageListener>(); 
	public void addListener(messageListener toAdd) {
		listeners.add(toAdd);
	}
	public void TriggerMessage(String msg) {
		for (messageListener hl : listeners)
			hl.receptorEvent(msg);
	}
}
class Responder implements messageListener {
	@Override
	public void receptorEvent(String msg) {
		if(msg.equals("message1")){Globals.scThread_3.start();}
		if(msg.equals("backdrop1")){Globals.scThread_6.start();}
	}
}
