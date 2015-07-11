import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.StringTokenizer;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.net.URL;
public class SCparser1 {
	
	public static void main(String[] args) {
        parseFile();
	}
	public static void initMsgSnippet(){
		
		
		Globals.MessageEvents_snippet += "interface messageListener { void receptorEvent(String msg);}\n";
		Globals.MessageEvents_snippet += "class Initiater {\n";
		Globals.MessageEvents_snippet += "\tprivate ArrayList<messageListener> listeners = new ArrayList<messageListener>(); \n";
		Globals.MessageEvents_snippet += "\tpublic void addListener(messageListener toAdd) {\n";
		Globals.MessageEvents_snippet += "\t\tlisteners.add(toAdd);\n";
		Globals.MessageEvents_snippet += "\t}\n";
		Globals.MessageEvents_snippet += "\tpublic void TriggerMessage(String msg) {\n";
		Globals.MessageEvents_snippet += "\t\tfor (messageListener hl : listeners)\n";
		Globals.MessageEvents_snippet += "\t\t\thl.receptorEvent(msg);\n";
		Globals.MessageEvents_snippet += "\t}\n";
		Globals.MessageEvents_snippet += "}\n";
		Globals.MessageEvents_snippet += "class Responder implements messageListener {\n";
		Globals.MessageEvents_snippet += "\t@Override\n\tpublic void receptorEvent(String msg) {\n";
		
		
		
	}
    
	public static String evalExpression(Object dataux){
		
		/*
		if(dataux.get(0).equals("randomFrom:to:")){
			res = "Double steps = "+dataux.get(1)+" + Math.random()*"+dataux.get(2)+";\n";
		}else if(dataux.get(0).equals("+:")){
			res = "Double steps = "+dataux.get(1)+" + "+dataux.get(2)+";\n";
		}else if(dataux.get(0).equals("-")){
			res = "Double steps = "+dataux.get(1)+" - "+dataux.get(2)+";\n";
		}else if(dataux.get(0).equals("/")){
			res = "Double steps = "+dataux.get(1)+" * "+dataux.get(2)+";\n";
		}else if(dataux.get(0).equals("*")){
			res = "Double steps = "+dataux.get(1)+" / "+dataux.get(2)+";\n";
		}
		
		return res;
		*/
		
		System.out.println(dataux.getClass());
		if(dataux instanceof String || dataux instanceof Integer  || dataux instanceof Double || dataux instanceof Long){
			System.out.println("el: "+dataux.toString());
			return dataux.toString();
		}
		else
		{
			System.out.println(dataux);
			JSONArray dataux2 = (JSONArray) dataux;
			System.out.println("el: "+dataux2.get(0));
			if(dataux2.get(0).equals("randomFrom:to:")){
				return  "("+ evalExpression(dataux2.get(1)) +" + Math.random()*"+ evalExpression(dataux2.get(2))+")"; 
			}else if(dataux2.get(0).equals("+")){
				return  "("+ evalExpression(dataux2.get(1)) +" + "+ evalExpression(dataux2.get(2))+")"; 
			}else if(dataux2.get(0).equals("-")){
				return  "("+ evalExpression(dataux2.get(1)) +" - "+ evalExpression(dataux2.get(2))+")"; 
			}else if(dataux2.get(0).equals("*")){
				return  "("+ evalExpression(dataux2.get(1)) +" * "+ evalExpression(dataux2.get(2))+")"; 
			}else if(dataux2.get(0).equals("/")){
				return  "("+ evalExpression(dataux2.get(1)) +" / "+ evalExpression(dataux2.get(2))+")"; 
			}
		}
		return"";
	}
	
    public static String evalInstruct(JSONArray ins, int numthread, int numObject) {
    	String s="";
        //Events Snippets
    	if(ins.get(0).equals("whenKeyPressed")){
    		if(ins.get(1).equals("space")){
    			Globals.KeyPress_snippet += "\t\tif (event.getKeyCode() == KeyEvent.VK_ENTER) {\n" ;
    			Globals.KeyPress_snippet += "\t\t\tGlobals.scThread_"+Globals.total_numthreads+".start();\n;";
    			Globals.KeyPress_snippet += "\t\t}\n"; 
    		}
    		else{
    			Globals.KeyPress_snippet += "\t\tif (event.getKeyChar() == \""+ins.get(1)+"\") {\n" ;
    			Globals.KeyPress_snippet += "\t\t\tGlobals.scThread_"+Globals.total_numthreads+".start();\n";
    			Globals.KeyPress_snippet += "\t\t}\n"; 
    		}
    	}
        if(ins.get(0).equals("whenGreenFlag")){
            Globals.Listener_snippet+= "\t\t\tGlobals.scThread_"+Globals.total_numthreads+".start();\n" ;
        }
        if(ins.get(0).equals("whenIReceive")){
        	if(Globals.msgSending == false){initMsgSnippet();Globals.msgSending = true;}
        	
        	Globals.MessageEvents_snippet+= "\t\tif(msg.equals(\""+ins.get(1)+"\")){Globals.scThread_"+numthread+".start();}\n";
        	
        }
        if(ins.get(0).equals("broadcast:")){
        	if(Globals.msgSending == false){initMsgSnippet();Globals.msgSending = true;}
        	if(Globals.openControl){
        		s+="\t\t\tGlobals.initiater.TriggerMessage(\""+ins.get(1)+"\");\n";
        	}
        	else{
        		s+="\t\tGlobals.initiater.TriggerMessage(\""+ins.get(1)+"\");\n";
        	}
        }
        //Movement Snippets
        if(ins.get(0).equals("gotoX:y:")){
        	if(Globals.openControl){
        		s += "\t\t\t//Goto XY instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX ="+ins.get(1)+";\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY ="+ins.get(2)+";\n";
        	}
        	else{
        		s += "\t\t//Goto XY instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX ="+ins.get(1)+";\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY ="+ins.get(2)+";\n";
        	}
        }
        if(ins.get(0).equals("forward:")){
        	
        	//String steps = "Double steps = Double.parseDouble("+ evalExpression(ins.get(1))  +");\n";
        	
        	String steps = evalExpression(ins.get(1));
        	System.out.println(steps);
        	//String steps = "Double steps = Double.parseDouble((String)ins.get(1));\n";
        	/*if(ins.get(1) instanceof String || ins.get(1) instanceof Double || ins.get(1) instanceof Integer){
        		//System.out.println("Es cadena");
        		steps = "Double steps = Double.parseDouble((String)ins.get(1));\n";
        	}
        	else{
        		//There is an operator expresion
        		//System.out.println("Es JASONArray");
        		JSONArray dataux = (JSONArray) ins.get(1);
        		
        		steps += evalExpression(dataux);
        	}*/
        	if(Globals.openControl){
        		s += "\t\t\t//Move forward instruction\n";
        		s += "\t\t\tDouble steps ="+steps+";\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX = Globals.listSCObjects.get("+(numObject-1)+").scratchX + Math.round(Math.sin(Math.toRadians(Globals.listSCObjects.get("+(numObject-1)+").direction)))*steps ;\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY = Globals.listSCObjects.get("+(numObject-1)+").scratchY + Math.round(Math.cos(Math.toRadians(Globals.listSCObjects.get("+(numObject-1)+").direction)))*steps ;\n";
        	}
        	else{
        		s += "\t\t//Move forward instruction\n";
        		s += "\t\t"+steps;
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX = Globals.listSCObjects.get("+(numObject-1)+").scratchX + Math.round(Math.sin(Math.toRadians(Globals.listSCObjects.get("+(numObject-1)+").direction)))*steps ;\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY = Globals.listSCObjects.get("+(numObject-1)+").scratchY + Math.round(Math.cos(Math.toRadians(Globals.listSCObjects.get("+(numObject-1)+").direction)))*steps ;\n";
            
        	}
        }
        if(ins.get(0).equals("turnRight:")){
        	if(Globals.openControl){
        		s += "\t\t\t//Turn right instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction + 15;\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >= 180){Globals.listSCObjects.get("+(numObject-1)+").direction = -180 + (Globals.listSCObjects.get("+(numObject-1)+").direction-180);}\n";
        	}
        	else{
        		s += "\t\t//Turn right instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction + 15;\n";
        		s += "\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >= 180){Globals.listSCObjects.get("+(numObject-1)+").direction = -180 + (Globals.listSCObjects.get("+(numObject-1)+").direction-180);}\n";
        	}
        }
        if(ins.get(0).equals("turnLeft:")){
        	if(Globals.openControl){
        		s += "\t\t\t//Turn left instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction - 15;\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >= -180){Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - (Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction+180));}\n";
        	}
        	else{
        		s += "\t\t//Turn left instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction - 15;\n";
        		s += "\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >= -180){Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - (Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction+180));}\n";
        	
        	}
        }
        if(ins.get(0).equals("heading:")){
        	if(Globals.openControl){
        		s += "\t\t\t//Heading left instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = "+ins.get(1)+";\n"; 
        	}
        	else{
        		s += "\t\t//Heading instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = "+ins.get(1)+";\n";
        	}
        }
        if(ins.get(0).equals("changeXposBy:")) {
        	if(Globals.openControl){
        		s += "\t\t\t//changeXposBy left instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX ="+ins.get(1)+";\n";
        	}
        	else{
        		s += "\t\t//changeXposBy instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX ="+ins.get(1)+";\n";
        	}
        }
        if(ins.get(0).equals("changeYposBy:")) {
        	if(Globals.openControl){
        		s += "\t\t\t//changeYposBy left instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY ="+ins.get(1)+";\n";
        	}
        	else{
        		s += "\t\t//changeYposBy instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY ="+ins.get(1)+";\n";
        	}
        }
        
        if(ins.get(0).equals("bounceOffEdge")){
        	if(Globals.openControl){
        		s += "\t\t\t//bounce Off Edge instruction\n";
        		s += "\t\t\t//Edge of the Left\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchX - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0  <= (Globals.wScreen/2.0)*-1){\n";
        		s += "\t\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction * -1;\n";
        		s += "\t\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX = -1*(Globals.wScreen/2.0) + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0;\n";
        		s += "\t\t\t}\n";
        		s += "\t\t\t//Edge of the Right\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchX + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0  >= (Globals.wScreen/2.0)){\n";
        		s += "\t\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction * -1;\n";
        		s += "\t\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX = Globals.wScreen/2.0 - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0;\n";
        		s += "\t\t\t}\n";
        		s += "\t\t\t//Edge of the Top\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchY - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0  <= (Globals.hScreen/2.0)*-1){\n";
        		s += "\t\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction < 0) { Globals.listSCObjects.get("+(numObject-1)+").direction = - 180 + Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction); }\n";
        		s += "\t\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >=0) { Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - Globals.listSCObjects.get("+(numObject-1)+").direction; }\n";
        		s += "\t\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY = -1*(Globals.hScreen/2.0) + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0;\n";
        		s += "\t\t\t}\n";
        		s += "\t\t\t//Edge of the Bottom\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchY + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0 >= (Globals.hScreen/2.0) ){\n";
        		s += "\t\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction < 0) { Globals.listSCObjects.get("+(numObject-1)+").direction = - 180 + Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction);}\n";
        		s += "\t\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >= 0){ Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - Globals.listSCObjects.get("+(numObject-1)+").direction; }\n";
        		s += "\t\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY = (Globals.hScreen/2.0) - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0;\n";
        		s += "\t\t\t}\n";
        	}
        	else{
        		s += "\t//bounce Off Edge instruction\n";
        		s += "\t\t//Edge of the Left\n";
        		s += "\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchX - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0  <= (Globals.wScreen/2.0)*-1){\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction * -1;\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX = -1*(Globals.wScreen/2.0) + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0;\n";
        		s += "\t\t}\n";
        		s += "\t\t//Edge of the Right\n";
        		s += "\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchX + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0  >= (Globals.wScreen/2.0)){\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction * -1;\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchX = Globals.wScreen/2.0 - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0;\n";
        		s += "\t\t}\n";
        		s += "\t\t//Edge of the Top\n";
        		s += "\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchY - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0  <= (Globals.hScreen/2.0)*-1){\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction < 0) { Globals.listSCObjects.get("+(numObject-1)+").direction = - 180 + Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction); }\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >=0) { Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - Globals.listSCObjects.get("+(numObject-1)+").direction; }\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY = -1*(Globals.hScreen/2.0) + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0;\n";
        		s += "\t\t}\n";
        		s += "\t\t//Edge of the Bottom\n";
        		s += "\t\tif(Globals.listSCObjects.get("+(numObject-1)+").scratchY + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0 >= (Globals.hScreen/2.0) ){\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction < 0) { Globals.listSCObjects.get("+(numObject-1)+").direction = - 180 + Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction);}\n";
        		s += "\t\t\tif(Globals.listSCObjects.get("+(numObject-1)+").direction >= 0){ Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - Globals.listSCObjects.get("+(numObject-1)+").direction; }\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").scratchY = (Globals.hScreen/2.0) - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0;\n";
        		s += "\t\t}\n";
        	}
        }
        
        //Appereance snippets
        if(ins.get(0).equals("lookLike:")){
        	if(Globals.openControl){
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").setCostumebyID(\""+(ins.get(1))+"\");\n";
        	}
        	else{
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").setCostumebyID(\""+(ins.get(1))+"\");\n";
        	}
        }
        if(ins.get(0).equals("nextCostume")){
        	if(Globals.openControl){
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").nextCostume();\n";
        	}
        	else{
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").nextCostume();\n";
        	}
        }
        if(ins.get(0).equals("say:duration:elapsed:from:")){
        	int auxv = Integer.parseInt(ins.get(2).toString());
        	if(Globals.openControl){
        		s += "\t\t\tSystem.out.println(\"I say: \""+ins.get(1)+" and I go to sleep\");\n";
        		s += "\t\t\ttry {\n";
        		s += "\t\t\t\tThread.sleep("+(auxv*1000)+");\n";
        		s += "\t\t\t} catch (InterruptedException e) {e.printStackTrace();}\n";
        	
        	}
        	else{
        		s += "\t\tSystem.out.println(\"I say: "+ins.get(1)+" and I go to sleep\");\n";
        		s += "\t\ttry {\n";
        		s += "\t\t\tThread.sleep("+(auxv*1000)+");\n";
        		s += "\t\t} catch (InterruptedException e) {e.printStackTrace();}\n";
        	}
        }
        
        if(ins.get(0).equals("say:")){
        	if(Globals.openControl){
        		s += "\t\t\tSystem.out.println(\"I say: \""+ins.get(1)+"\");\n";
        	}
        	else{
        		s += "\t\tSystem.out.println(\"I say: "+ins.get(1)+"\");\n";
        	}
        }
        if(ins.get(0).equals("think:duration:elapsed:from:")){
        	int auxv = Integer.parseInt(ins.get(2).toString());
        	if(Globals.openControl){
        		s += "\t\t\tSystem.out.println(\"I think: \""+ins.get(1)+" and I go to sleep\");\n";
        		s += "\t\t\ttry {\n";
        		s += "\t\t\t\tThread.sleep("+(auxv*1000)+");\n";
        		s += "\t\t\t} catch (InterruptedException e) {e.printStackTrace();}\n";
        	
        	}
        	else{
        		s += "\t\tSystem.out.println(\"I think: "+ins.get(1)+" and I go to sleep\");\n";
        		s += "\t\ttry {\n";
        		s += "\t\t\tThread.sleep("+(auxv*1000)+");\n";
        		s += "\t\t} catch (InterruptedException e) {e.printStackTrace();}\n";
        	}
        }
        
        if(ins.get(0).equals("think:")){
        	if(Globals.openControl){
        		s += "\t\t\tSystem.out.println(\"I think: \""+ins.get(1)+"\");\n";
        	}
        	else{
        		s += "\t\tSystem.out.println(\"I think: "+ins.get(1)+"\");\n";
        	}
        }
        if(ins.get(0).equals("show")){
        	if(Globals.openControl){
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").setVisible();\n";
        	}
        	else{
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").setVisible();\n";
        	}
        }
        if(ins.get(0).equals("hide")){
        	if(Globals.openControl){
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").setVisible();\n";
        	}
        	else{
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").setVisible();\n";
        	}
        }
        if(ins.get(0).equals("startScene")){
        	if(Globals.openControl){
        		
        	}
        	else{
        		
        	}
        }
        
        
        //Set Value Snippets
        if(ins.get(0).equals("setRotationStyle")){
        	if(Globals.openControl){
        		s += "\t\t\t//Set rotation Style instruction\n";
        		s += "\t\t\tGlobals.listSCObjects.get("+(numObject-1)+").rotationStyle =\""+ins.get(1)+"\";\n";
        	}
        	else{
        		s += "\t\t//Set rotation Style instruction\n";
        		s += "\t\tGlobals.listSCObjects.get("+(numObject-1)+").rotationStyle =\""+ins.get(1)+"\";\n";
        	}
        }
        //Control instructions Snipets
        if(ins.get(0).equals("doForever")){
        	Globals.openControl = true;
        	Globals.clevel++;
        	s= "\t\t//Do-forever instruction\n";
        	s+= "\t\tfor(int i=0; i < Globals.steps; i++){\n";
        	s+= "\t\t\tif(Globals.infloop == true){i--;}//i does not increment\n";
        	s+= "\t\t\telse{System.out.println(\"[Thread"+numthread+"] - Step:\"+i);}\n";
        }
        
        return s;
    }
    public static void parseFile() {
        
    	String workingDir = System.getProperty("user.dir");
  	    //System.out.println("Current working directory : " + workingDir);
    	
        //Aux variables
        final String filePath = workingDir+ "/scratch/project.json";
        FileReader reader = null;
        
        //Writing The header lines
        String imports = "//Scratch parsed in Java \n";
        imports += "package cucumber.features;\n"
        		+ "import java.awt.event.ActionEvent;\n"
        		+ "import java.awt.event.ActionListener;\n"
        		+ "import java.awt.event.KeyAdapter;\n"
        		+ "import java.awt.event.KeyEvent;\n"
        		+ "import java.util.ArrayList;\n"
        		+ "import javax.swing.JFrame;\n"
        		+ "import javax.swing.JTextField;\n"
        		+ "public class SCprogram {}\n";
        File file;
        FileWriter fw; 
        BufferedWriter bw;
        try{
            file = new File("SCprogram.java");
            if(!file.exists()){
                file.createNewFile();
            }
            else{
            	file.delete();
            	file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(imports);  //Writing the heather
            bw.close();           
        }catch (IOException e){}
        try {
            reader = new FileReader(filePath);
            final JSONParser parser = new JSONParser();
            final JSONObject json = (JSONObject) parser.parse(reader);
            final String createDate = (String) json.get("objName");
            System.out.println("Object Name: " + createDate);
            final JSONArray jsonChildren = (JSONArray) json.get("children");
            final Iterator it = jsonChildren.iterator();
            
            final JSONArray jsonBG = (JSONArray) json.get("costumes");
            final Iterator it2 = jsonBG.iterator();
            
            //Foreach Object in the Json File
            while (it.hasNext()) {
            	JSONObject jsonChild = (JSONObject) it.next();
            	if((String) jsonChild.get("objName")!=null){
            	
            	Globals.i_object++;
                System.out.println("[NEW CHILD]");
                //Globals.SCObjets_snippet += "\tpublic static SCObject scObject_"+Globals.i_object+" = new SCObject();\n";
                
                String objName = (String) jsonChild.get("objName");
                Object cci = jsonChild.get("currentCostumeIndex");
                Object sx = jsonChild.get("scratchX");
                Object sy = jsonChild.get("scratchY");
                Object d = jsonChild.get("direction");
                String rs = (String) jsonChild.get("rotationStyle");
                Object isd = jsonChild.get("isDraggable");
                Object iil = jsonChild.get("indexInLibrary");
                Object vis = jsonChild.get("visible"); 
                
                System.out.println("objName: " + objName);
                
                Globals.SCObjets_AddListSnippet += "\t\tGlobals.listSCObjects.add(new SCObject(\""+objName+"\","+cci+","+sx+","+sy+","+d+",\""+rs+"\","+isd+","+iil+","+vis+","+Globals.i_object+"));\n";
                
                JSONArray jsonScripts = (JSONArray) jsonChild.get("scripts");
                
                boolean looping=true;
                
                
                System.out.println(jsonScripts.size());
                // Foreach Script
                for(int i = 0 ; i < jsonScripts.size() ; i++){  
                    //System.out.println("--[NEW Thread of the child]");
                    Globals.total_numthreads++;
                    Globals.SCThreads_snippet += "\tpublic static Thread_"+Globals.total_numthreads+" scThread_"+Globals.total_numthreads+" = new Thread_"+Globals.total_numthreads+"();\n";
                    try{
                        file = new File("SCprogram.java");
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
                        bw.write("class Thread_"+Globals.total_numthreads+" extends Thread {\n");  //Writing the header
                        bw.write("\tpublic void run(){\n");
                        bw.close();           
                    }catch (IOException e){}
                    
                    //We get the array i of instruction of the current Child
                    JSONArray jsonInstructions = (JSONArray) jsonScripts.get(i) ;
                    jsonInstructions = (JSONArray) jsonInstructions.get(2) ;
                    JSONArray ins;
                    for(int j=0 ; j< jsonInstructions.size() ; j++ ){
                        ins =  (JSONArray) jsonInstructions.get(j);
                        //System.out.println("----"+ins.get(0));
                        
                        try{
                            file = new File("SCprogram.java");
                            fw = new FileWriter(file.getAbsoluteFile(), true);
                            bw = new BufferedWriter(fw);
                            bw.write(evalInstruct(ins,Globals.total_numthreads,Globals.i_object));
                            if(Globals.openControl){
                            	
                            	//We get The array j of instructions 
                            	JSONArray loopins = (JSONArray) ins.get(1);
                            	for(int k = 0; k < loopins.size(); k++){
                            		JSONArray jsonIns2 = (JSONArray) loopins.get(k);
                            		System.out.println(jsonIns2.get(0));
                            		bw.write(evalInstruct(jsonIns2,Globals.total_numthreads,Globals.i_object));                            		
                            	}
                            	//Closing Loop snippets
                            	Globals.openControl =  false;
                            	Globals.clevel--;
                            	bw.write ("\t\t\ttry {\n");
                            	bw.write ("\t\t\t\tThread.sleep(1000/"+Globals.fps+");\n");
                            	bw.write ("\t\t\t} catch (InterruptedException e) {e.printStackTrace();}\n");
                            	bw.write ("\t\t}\n");
                            	bw.write ("\t\tGlobals.cucumberKey = false;\n");
                            	
                            }
                            bw.close(); 
                        }catch (IOException e){}
                    }//End for
                    try{
                        file = new File("SCprogram.java");
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
                        bw.write("\t}\n");
                        bw.write("}\n");  
                        bw.close(); 
                    }catch (IOException e){}
                }//End For
                JSONArray jsonCostumes = (JSONArray) jsonChild.get("costumes");
                JSONObject jsonCos;
                String caux;
                for(int i=0; i< jsonCostumes.size(); i++){
                	caux="";
                	jsonCos = (JSONObject) jsonCostumes.get(i);
                   	caux = "new Costume(\"";
                   	caux+= (String) jsonCos.get("costumeName")+"\",";
                   	caux+= jsonCos.get("baseLayerID")+",";
                   	caux+= "\""+(String) jsonCos.get("baseLayerMD5")+"\",";
                   	caux+= jsonCos.get("bitmapResolution")+",";
                   	caux+= jsonCos.get("rotationCenterX")+",";
                   	caux+= jsonCos.get("rotationCenterY")+",";
                   	
                   	
                   	//Opening .svg file for getting the width and the height;
                   	String formatFile = "";
                	StringTokenizer st2 = new StringTokenizer((String) jsonCos.get("baseLayerMD5"), ".");
                	while (st2.hasMoreElements()) { formatFile = (String) st2.nextElement(); }
                	//System.out.println(formatFile);
                	if(formatFile.equals("svg")){
                		File fXmlFile = new File( workingDir + "/scratch/"+jsonCos.get("baseLayerID") + ".svg");
                    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    	Document doc = dBuilder.parse(fXmlFile);
                    	
                    	NodeList nList = doc.getElementsByTagName("svg");
                    	Node nNode = nList.item(0);
                    	Element eElement = (Element) nNode;
                    	System.out.println("Width : " + eElement.getAttribute("width"));
                    	
                    	caux+= eElement.getAttribute("width").replace("px", "")+",";
                       	caux+= eElement.getAttribute("height").replace("px", "")+")";
                	}
                	else {
                		final BufferedImage bi = ImageIO.read(new File(workingDir + "/scratch/"+jsonCos.get("baseLayerID") + "."+formatFile));
                		caux+= bi.getWidth()+",";
                       	caux+= bi.getHeight()+")";
                	}
                   	System.out.println(caux);
                	Globals.SCObjets_AddListSnippet += "\t\tGlobals.listSCObjects.get("+(Globals.i_object-1)+").costumes.add("+caux+");\n";
                }
                
            	}//En comparision for parsin only SCObjects
            }//End Object iterator
            //Reading the Backgrounds
            while(it2.hasNext()){
            	String caux = " ";
             	String formatFile = "";
            	JSONObject jsonChild = (JSONObject) it2.next();
            	            	
            	StringTokenizer st2 = new StringTokenizer((String) jsonChild.get("baseLayerMD5"),".");
            	while (st2.hasMoreElements()) { formatFile = (String) st2.nextElement(); }
            	
            	caux = "new Costume(\"";
               	caux+= (String) jsonChild.get("costumeName")+"\",";
               	caux+= jsonChild.get("baseLayerID")+",";
               	caux+= "\""+(String) jsonChild.get("baseLayerMD5")+"\",";
               	caux+= jsonChild.get("bitmapResolution")+",";
               	caux+= jsonChild.get("rotationCenterX")+",";
               	caux+= jsonChild.get("rotationCenterY")+",";
               	
               	if(formatFile.equals("svg")){
            		File fXmlFile = new File( workingDir + "/scratch/"+jsonChild.get("baseLayerID") + ".svg");
                	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                	Document doc = dBuilder.parse(fXmlFile);
                	
                	NodeList nList = doc.getElementsByTagName("svg");
                	Node nNode = nList.item(0);
                	Element eElement = (Element) nNode;
                	System.out.println("Width : " + eElement.getAttribute("width"));
                	
                	caux+= eElement.getAttribute("width").replace("px", "")+",";
                   	caux+= eElement.getAttribute("height").replace("px", "")+")";
            	}
               	
            	else {
            		try {
            			System.out.println(workingDir + "/scratch/"+jsonChild.get("baseLayerID") + "."+formatFile);
            			Image picture = ImageIO.read(new File(workingDir + "/scratch/"+jsonChild.get("baseLayerID") + "."+formatFile));
            	    } catch (IOException e) {
            	    	e.printStackTrace();
            	    }
            		
            		final BufferedImage bi = ImageIO.read(new File(workingDir + "/scratch/"+jsonChild.get("baseLayerID") + "."+formatFile));
            		caux+= bi.getWidth()+",";
                   	caux+= bi.getHeight()+")";
            	}
            	
               	Globals.SCObjets_AddListSnippet += "\t\tGlobals.listBackgrounds.add("+caux+");\n";
            }
           
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Writing The Globals variables Class snippet
        try{
            file = new File("SCprogram.java");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write("class Globals {\n"); 
            bw.write("\tpublic static boolean  appLaunched = false;\n");
            bw.write("\tpublic static int steps ;\n");
            bw.write("\tpublic static int wScreen = 480;\n");
            bw.write("\tpublic static int hScreen = 360;\n");
            bw.write("\tpublic static boolean infloop = true ;\n");
            bw.write("\tpublic static boolean cucumberKey = true;\n");
            bw.write("\tpublic static boolean loop = true;\n");
            bw.write("\tpublic static long total_timeApp = 0;\n");
            bw.write("\tpublic static long timeApp = System.currentTimeMillis();\n");
            if(Globals.msgSending){
            	bw.write("\tpublic static Initiater initiater = new Initiater();\n");
            	bw.write("\tpublic static Responder responder = new Responder();\n");
            }
            //bw.write(Globals.SCObjets_snippet);
            bw.write(Globals.SCThreads_snippet);
            bw.write("\tpublic static App appT = new App();\n");
            bw.write("\tpublic static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();\n");
            bw.write("\tpublic static ArrayList<Costume> listBackgrounds = new ArrayList <Costume>();\n");
            bw.write("\tpublic static ArrayList<Thread> listScripts = new ArrayList <Thread>();");
            bw.write("}\n");  
            bw.close(); 
        }catch (IOException e){}
        
        //Writing the KeyListener Class snippet
        try{
        	file = new File("SCprogram.java");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
        	bw.write("class MKeyListener extends KeyAdapter {\n");
        	bw.write("\t@Override public void keyPressed(KeyEvent event) {\n");
        	bw.write("\t\tchar ch = event.getKeyChar();\n");
        	
        	bw.write("\t\tif (ch == 'q') { //Quit the mainLoop\n");
        	bw.write("\t\t\tGlobals.loop = false;\n");
        	bw.write("\t\t}\n");
        	
        	bw.write("\t\tif (event.getKeyCode() == KeyEvent.VK_ENTER) {\n");
        	//The starting of the Threads is now controlled by the cucumber Step Definitions
        	//By default the construction is done by cucumber
        	bw.write(Globals.Listener_snippet);
        	
        	bw.write("\t\t\tGlobals.cucumberKey = false;\n");
        	
        	
        	bw.write("\t\t}\n");
        	
        	//Adding KeypressEvents
        	bw.write(Globals.KeyPress_snippet);
        	
        	bw.write("\t}\n");
        	bw.write("}\n");
            bw.close(); 
        }catch (IOException e){}
        //Writing the App main snippet
        try{
        	file = new File("SCprogram.java");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            
            bw.write("class App extends Thread{\n");
            bw.write("\tpublic void run (){\n");
            //Writing Key Listener declaration
            bw.write("\t\t// Key Listener declaration\n");
            bw.write("\t\tJTextField textField = new JTextField();\n");
            bw.write("\t\ttextField.addKeyListener(new MKeyListener());\n");
            bw.write("\t\tJFrame jframe = new JFrame();\n");
            bw.write("\t\tjframe.add(textField);\n");
            bw.write("\t\tjframe.setSize(400, 400);\n");
            bw.write("\t\tjframe.setVisible(true);\n");
            //Filling the ArrayListwith the SCobjects
            bw.write("\t\t//Filling the ArrayListwith the SCobjects\n");
            bw.write(Globals.SCObjets_AddListSnippet);
            bw.write("\t\tGlobals.cucumberKey = false;\n");
            bw.write("\t\tGlobals.appLaunched = true;\n");
            if(Globals.msgSending){bw.write("\t\tGlobals.initiater.addListener(Globals.responder);\n");}
            bw.write("\t}\n");//Close run function
            bw.write("}\n");//Close App class
            
            if(Globals.msgSending){
            	Globals.MessageEvents_snippet += "\t}\n";
        		Globals.MessageEvents_snippet += "}\n";
        		bw.write(Globals.MessageEvents_snippet);
            }
            bw.close(); 
        }catch (IOException e){}
    }
}
class Globals{
	public static int fps = 30;
	
	public static int total_numthreads = 0;
	public static int i_object = 0;
	public static String MessageEvents_snippet = "";
	public static String SCObjets_snippet ="";
	public static String SCObjets_AddListSnippet ="";
	public static String SCThreads_snippet = "";
	public static String Listener_snippet = "";
	public static String KeyPress_snippet = "";
	public static int clevel = 0;
	public static boolean openControl = false;
	public static boolean msgSending = false;
}