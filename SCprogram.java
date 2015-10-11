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
		//Goto XY instruction
		Globals.listSCObjects.get(0).scratchX =0;
		Globals.listSCObjects.get(0).scratchY =0;
		//Set rotation Style instruction
		Globals.listSCObjects.get(0).rotationStyle ="don't rotate";
		//Do-forever instruction
		for(int i=0; i < Globals.steps; i++){
			if(Globals.infloop == true){i--;}//i does not increment
			else{System.out.println("[Thread1] - Step:"+i);}
			//Move forward instruction
			Globals.listSCObjects.get(0).scratchX = Globals.listSCObjects.get(0).scratchX + Math.round(Math.sin(Math.toRadians(Globals.listSCObjects.get(0).direction)))*(1 + Math.random()*3) ;
			Globals.listSCObjects.get(0).scratchY = Globals.listSCObjects.get(0).scratchY + Math.round(Math.cos(Math.toRadians(Globals.listSCObjects.get(0).direction)))*(1 + Math.random()*3)  ;
			//Turn right instruction
			Globals.listSCObjects.get(0).direction = Globals.listSCObjects.get(0).direction + 15;
			if(Globals.listSCObjects.get(0).direction >= 180){Globals.listSCObjects.get(0).direction = -180 + (Globals.listSCObjects.get(0).direction-180);}
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
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
class Thread_2 extends Thread {
	public void run(){
		Globals.listSCObjects.get(0).setCostumebyID("starter crab");
		//Do-forever instruction
		for(int i=0; i < Globals.steps; i++){
			if(Globals.infloop == true){i--;}//i does not increment
			else{System.out.println("[Thread2] - Step:"+i);}
			Globals.listSCObjects.get(0).nextCostume();
			//wait:elapsed:from: stopping thread for n seconds
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
class Thread_3 extends Thread {
	public void run(){
		//Do-forever instruction
		for(int i=0; i < Globals.steps; i++){
			if(Globals.infloop == true){i--;}//i does not increment
			else{System.out.println("[Thread3] - Step:"+i);}
			//Play sound and wait instruction
			try {
				Thread.sleep(Globals.getDurationByName("human beatbox1"));
			} catch (InterruptedException e) {e.printStackTrace();}
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
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
	public static boolean cucumberKey_msg = true;
	public static boolean loop = true;
	public static long total_timeApp = 0;
	public static long timeApp = System.currentTimeMillis();
	public static Thread_1 scThread_1;
	public static Thread_2 scThread_2;
	public static Thread_3 scThread_3;
	public static App appT = new App();
	public static ArrayList<SCVariable> listSCVariables = new ArrayList<SCVariable>();
	public static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();
	public static ArrayList<Costume> listBackgrounds = new ArrayList <Costume>();
	public static ArrayList<Thread> listScripts = new ArrayList <Thread>();
	public static ArrayList<SCSound> listSCSounds = new ArrayList <SCSound>();
	public static ArrayList<SCMessage> listSCMessage_sent = new ArrayList <SCMessage>();
	public static ArrayList<SCMessage> listSCMessage_received = new ArrayList <SCMessage>();
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
			Globals.scThread_1= new Thread_1();
			Globals.scThread_1.start();
			Globals.scThread_2= new Thread_2();
			Globals.scThread_2.start();
			Globals.scThread_3= new Thread_3();
			Globals.scThread_3.start();
			Globals.cucumberKey = false;
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
		Globals.listSCObjects.add(new SCObject("Crab",0,14.61084719307128,7.11707852528936,-153.47114057941724,"none",false,1,true,1));
		Globals.listSCObjects.get(0).costumes.add(new Costume("starter crab",1,"61dd4003375099d6aaf36336bd1b1ec9.svg",1,240,106,348,235));
		Globals.listSCObjects.get(0).costumes.add(new Costume("crab legs",2,"2e24ee5d950c8f711bcb746201cb1972.svg",1,240,105,348,237));
		Globals.listSCObjects.get(0).costumes.add(new Costume("cheerful crab",3,"08bc5cea610a0ca84a06d7900303ea77.svg",1,242,109,348,235));
		Globals.listSCObjects.get(0).costumes.add(new Costume("elated crab",4,"703b9ec95f20563f0aa9476f990580f4.svg",1,240,106,348,235));
		Globals.listSCObjects.get(0).costumes.add(new Costume("upset crab",5,"13561e82a3e18064cd6fa9bf59c780ac.svg",1,240,105,348,235));
		Globals.listSCObjects.get(0).costumes.add(new Costume("mischevious crab",6,"954734e5739b46c0b6ad334ba28b5187.svg",1,240,105,348,312));
		Globals.listSCObjects.get(0).costumes.add(new Costume("dazed crab",7,"dc727879610170c6e7d1bad44fe55812.svg",1,240,105,348,236));
		try{
		Globals.listSCSounds.add(new SCSound("human beatbox1",0,"1d4a8995a91efffac51d943016fd6604.wav",103680,22050,"","/Users/jorgalga/Programacion_Ecplipse/Java/SCparser1/scratch/"));
		}catch(Exception e){}
		Globals.listBackgrounds.add(new Costume("backdrop1",8,"510da64cf172d53750dffd23fbf73563.png",1,240,180,480,360));
		Globals.listBackgrounds.add(new Costume("underwater3",9,"be203c37c3ea07b39a5519d1a55214ff.gif",1,240,180,480,360));
		Globals.listBackgrounds.add(new Costume("backdrop2",10,"f3912a1e36ffa0f64d22349466ba95dc.svg",1,243,182,481,360));
		Globals.listSCObjects.get(0).threads.add(Globals.scThread_1);
		Globals.listSCObjects.get(0).threads.add(Globals.scThread_2);
		Globals.listSCObjects.get(0).threads.add(Globals.scThread_3);
		Globals.cucumberKey = false;
		Globals.appLaunched = true;
	}
}
