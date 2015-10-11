import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SCparser1 {
	
	public static void main(String[] args) {
		
		
        System.out.println(args[0]);
        
		
        parseFile(args[0]);
        
        deleteTempFiles();
	}
	public static String duplicateString(String s, int n){
		String res="";
		for(int i=0;i<n;i++){
			res += s;
		}
		return res;
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
    
	public static String evalExpression(Object dataux,  int numObject){
		
		System.out.println(dataux.getClass());
		if(dataux instanceof Integer  || dataux instanceof Double || dataux instanceof Long){
			System.out.println("el: "+dataux.toString());
			return dataux.toString();
		}
		else if(dataux instanceof String){
			return "\""+dataux.toString()+"\"";
		}else
		{
			
			//System.out.println(new String("test").length());
			JSONArray dataux2 = (JSONArray) dataux;
			System.out.println("el: "+dataux2.get(0));
			if(dataux2.get(0).equals("randomFrom:to:")){
				return  "("+ evalExpression(dataux2.get(1),numObject) +" + Math.random()*"+ evalExpression(dataux2.get(2),numObject)+")"; 
			}else if(dataux2.get(0).equals("+")){
				return  "("+ evalExpression(dataux2.get(1),numObject) +" + "+ evalExpression(dataux2.get(2),numObject)+")"; 
			}else if(dataux2.get(0).equals("-")){
				return  "("+ evalExpression(dataux2.get(1),numObject) +" - "+ evalExpression(dataux2.get(2),numObject)+")"; 
			}else if(dataux2.get(0).equals("*")){
				return  "("+ evalExpression(dataux2.get(1),numObject) +" * "+ evalExpression(dataux2.get(2),numObject)+")"; 
			}else if(dataux2.get(0).equals("/")){
				return  "("+ evalExpression(dataux2.get(1),numObject) +" / "+ evalExpression(dataux2.get(2),numObject)+")"; 
			}else if(dataux2.get(0).equals("%")){
				return "("+ evalExpression(dataux2.get(1),numObject) +" % "+ evalExpression(dataux2.get(2),numObject) +")";
			}else if(dataux2.get(0).equals("rounded")){
				return "Math.round("+evalExpression(dataux2.get(1),numObject)+")";
			}else if(dataux2.get(0).equals("stringLength:")){
				return "new String(\""+dataux2.get(1)+"\").length()";
			}else if(dataux2.get(0).equals("letter:of:")){
				return "Character.toString(new String(\""+dataux2.get(2)+"\").charAt("+dataux2.get(1)+"))";
			}else if(dataux2.get(0).equals("readVariable")){
				return "Globals.getSCValueByName(\""+dataux2.get(1)+"\")";
			}else if(dataux2.get(0).equals("costumeIndex")){
				return "Globals.listSCObjects.get("+(numObject)+").currentCostume";
			}else if(dataux2.get(0).equals("volume") || dataux2.get(0).equals("soundLevel")){
				return "Globals.volume";
			}else if(dataux2.get(0).equals("tempo")){
				return "Globals.tempo";
			}else if(dataux2.get(0).equals("touching:")){
				return "Globals.listSCObjects.get("+(numObject)+").isTouching(\""+dataux2.get(1)+"\")";
			}else if(dataux2.get(0).equals("concatenate:with:")){
				return ""+evalExpression(dataux2.get(1),numObject)+"+"+evalExpression(dataux2.get(2),numObject)+"";
			}else if(dataux2.get(0).equals("touchingColor:")){
				return "Globals.listSCObjects.get("+(numObject)+").isTouchingColor(\""+dataux2.get(1)+"\")";
			}else if(dataux2.get(0).equals("color:sees:")){
				return "false"; //Is not fully implemented (Graphical execution)
			}else if(dataux2.get(0).equals("distanceTo:")){
				return "100";   //Is not fully implemented (Graphical execution)
			}else if(dataux2.get(0).equals("answer")){
				return "\"answer\"";   //Is not fully implemented (Graphical execution)
			}else if(dataux2.get(0).equals("timestamp")){
				return "5675";
			}else if(dataux2.get(0).equals("keyPressed:")){
				return "false";
			}else if(dataux2.get(0).equals("getUserName")){
				return "0";
			}	
			else if(dataux2.get(0).equals("timeAndDate")){
			

				if(dataux2.get(1).equals("year")){
					return ""+Calendar.getInstance().get(Calendar.YEAR);
				}else if(dataux2.get(1).equals("month")){
					return ""+Calendar.getInstance().get(Calendar.MONTH);
				}else if(dataux2.get(1).equals("date")){
					return ""+Calendar.getInstance().get(Calendar.DATE);
				}else if(dataux2.get(1).equals("day of week")){
					return ""+Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
				}else if(dataux2.get(1).equals("hour")){
					return ""+Calendar.getInstance().get(Calendar.HOUR);
				}else if(dataux2.get(1).equals("minute")){
					return ""+Calendar.getInstance().get(Calendar.MINUTE);
				}else if(dataux2.get(1).equals("second")){
					return ""+Calendar.getInstance().get(Calendar.SECOND);
				}
			} else if(dataux2.get(0).equals("getAttribute:of:")){
				int oindex=-1;
				for(int i = 0; i<Globals.SCOnames.size(); i++){
					if(Globals.SCOnames.get(i).equals(dataux2.get(2))){
						oindex=i;
					}
				}
				if(oindex >=0){
					if(dataux2.get(1).equals("x position")){
						return "Globals.listSCObjects.get("+oindex+").scratchX";
					}else if(dataux2.get(1).equals("y position")){
						return "Globals.listSCObjects.get("+oindex+").scratchY";
					}else if(dataux2.get(1).equals("costume #")){
						return "Globals.listSCObjects.get("+oindex+").currentCostume";
					}else if(dataux2.get(1).equals("direction")){
						return "Globals.listSCObjects.get("+oindex+").direction";
					}else if(dataux2.get(1).equals("size")){
						return "Globals.listSCObjects.get("+oindex+").size";
					}else if(dataux2.get(1).equals("volume")){
						return "Globals.volume"; //At the moment the sound is global.
					}
				}
			}
		}
		return"";
	}
	
    public static String evalInstruct(JSONArray ins, int numthread, int numObject) {
    	String s="";
        //Events Snippets
    	if(ins.get(0).equals("whenKeyPressed")){
    		if(ins.get(1).equals("space")){
    			Globals.KeyPress_snippet += "\t\tif (event.getKeyCode() == KeyEvent.VK_SPACE) {\n" ;
    			Globals.KeyPress_snippet += "\t\t\tGlobals.scThread_"+numthread+"= new Thread_"+numthread+"();\n";
    			Globals.KeyPress_snippet += "\t\t\tGlobals.scThread_"+numthread+".start();\n";
    			Globals.KeyPress_snippet += "\t\t}\n"; 
    		}
    		else{
    			Globals.KeyPress_snippet += "\t\tif (event.getKeyChar() == '"+ins.get(1)+"') {\n" ;
    			Globals.KeyPress_snippet += "\t\t\tGlobals.scThread_"+numthread+"= new Thread_"+numthread+"();\n";
    			Globals.KeyPress_snippet += "\t\t\tGlobals.scThread_"+numthread+".start();\n";
    			Globals.KeyPress_snippet += "\t\t}\n"; 
    		}
    	}
        if(ins.get(0).equals("whenGreenFlag")){
        	Globals.Listener_snippet+= "\t\t\tGlobals.scThread_"+numthread+"= new Thread_"+numthread+"();\n";
            Globals.Listener_snippet+= "\t\t\tGlobals.scThread_"+numthread+".start();\n" ;
        }
        if(ins.get(0).equals("whenSceneStarts")){
        	if(Globals.msgSending == false){initMsgSnippet();Globals.msgSending = true;}
        	Globals.MessageEvents_snippet+= "\t\tif(msg.equals(\""+ins.get(1)+"\")){Globals.scThread_"+numthread+"= new Thread_"+numthread+"(); Globals.scThread_"+numthread+".start();}\n";
        }
        if(ins.get(0).equals("whenIReceive")){
        	if(Globals.msgSending == false){initMsgSnippet();Globals.msgSending = true;}
        	Globals.MessageEvents_snippet+= "\t\tif(msg.equals(\""+ins.get(1)+"\")){Globals.listSCMessage_received.add(new SCMessage(\""+ins.get(1)+"\","+(numObject-1)+"));Globals.scThread_"+numthread+"= new Thread_"+numthread+"();Globals.scThread_"+numthread+".start();}\n";
        }
        if(ins.get(0).equals("broadcast:")){
        	if(Globals.msgSending == false){initMsgSnippet();Globals.msgSending = true;}
        	s+=duplicateString("\t", Globals.clevel + 2)+"Globals.initiater.TriggerMessage(\""+ins.get(1)+"\");\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"Globals.listSCMessage_sent.add(new SCMessage(\""+ins.get(1)+"\","+(numObject-1)+"));\n";
        }
        
        if(ins.get(0).equals("whenSensorGreaterThan")){
        	if(ins.get(1).equals("loudness")){
        		Globals.Globals_snippet +=  "\tpublic static int Vumbral = "+ins.get(2)+";\n";
        		Globals.MessageEvents_snippet+= "\t\tif(msg.equals(\"loudness\")){Globals.scThread_"+numthread+"= new Thread_"+numthread+"();Globals.scThread_"+numthread+".start();}\n";
        	}
        }
        
        //Movement Snippets
        if(ins.get(0).equals("gotoX:y:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Goto XY instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchX ="+evalExpression(ins.get(1),numObject-1)+";\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY ="+evalExpression(ins.get(2),numObject-1)+";\n";
        }
        if(ins.get(0).equals("forward:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Move forward instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchX = Globals.listSCObjects.get("+(numObject-1)+").scratchX + Math.round(Math.sin(Math.toRadians(Globals.listSCObjects.get("+(numObject-1)+").direction)))*"+evalExpression(ins.get(1),numObject-1)+" ;\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY = Globals.listSCObjects.get("+(numObject-1)+").scratchY + Math.round(Math.cos(Math.toRadians(Globals.listSCObjects.get("+(numObject-1)+").direction)))*"+evalExpression(ins.get(1),numObject-1)+"  ;\n";
        }
        if(ins.get(0).equals("turnRight:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Turn right instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction + "+evalExpression(ins.get(1),numObject-1)+";\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").direction >= 180){Globals.listSCObjects.get("+(numObject-1)+").direction = -180 + (Globals.listSCObjects.get("+(numObject-1)+").direction-180);}\n";
        }
        if(ins.get(0).equals("turnLeft:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Turn left instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction - "+evalExpression(ins.get(1),numObject-1)+";\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").direction >= -180){Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - (Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction+180));}\n";
        }
        if(ins.get(0).equals("heading:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Heading left instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").direction = "+evalExpression(ins.get(1),numObject-1)+";\n";
        }
        
        if (ins.get(0).equals("pointTowards:")){
        	//Graphical method. Requieres mouse position inside the frame.
        }
        if (ins.get(0).equals("gotoSpriteOrMouse:")){
        	//Graphical method. Requieres mouse position inside the frame.
        }
        if (ins.get(0).equals("glideSecs:toX:y:elapsed:from:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//glideSecs:toX:y:elapsed:from: instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"\tThread.sleep(("+evalExpression(ins.get(1),numObject-1)+"*1000));\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchX ="+evalExpression(ins.get(2),numObject-1)+";\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY ="+evalExpression(ins.get(3),numObject-1)+";\n";
        	
        }
        if(ins.get(0).equals("changeXposBy:")) {
        	s += duplicateString("\t", Globals.clevel + 2)+"//changeXposBy instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchX =Globals.listSCObjects.get("+(numObject-1)+").scratchX + "+evalExpression(ins.get(1),numObject-1)+";\n";
        }
        if(ins.get(0).equals("changeYposBy:")) {
        	s += duplicateString("\t", Globals.clevel + 2)+"//changeYposBy instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY =Globals.listSCObjects.get("+(numObject-1)+").scratchY + "+evalExpression(ins.get(1),numObject-1)+";\n";
        }
        if(ins.get(0).equals("xpos:")) {
        	s += duplicateString("\t", Globals.clevel + 2)+"//set posx instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchX ="+evalExpression(ins.get(1),numObject-1)+";\n";
      
        }
        if(ins.get(0).equals("ypos:")) {
        	s += duplicateString("\t", Globals.clevel + 2)+"//set posy instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY ="+evalExpression(ins.get(1),numObject-1)+";\n";
       
        }
        if(ins.get(0).equals("bounceOffEdge")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//bounce Off Edge instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"//Edge of the Left\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").scratchX - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0  <= (Globals.wScreen/2.0)*-1){\n";
        	Globals.clevel++;
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction * -1;\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchX = -1*(Globals.wScreen/2.0) + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0;\n";
        	Globals.clevel--;
        	s += duplicateString("\t", Globals.clevel + 2)+"}\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"//Edge of the Right\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").scratchX + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0  >= (Globals.wScreen/2.0)){\n";
        	Globals.clevel++;
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").direction = Globals.listSCObjects.get("+(numObject-1)+").direction * -1;\n";
        	s += "Globals.listSCObjects.get("+(numObject-1)+").scratchX = Globals.wScreen/2.0 - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).width/2.0;\n";
        	Globals.clevel--;
        	s += duplicateString("\t", Globals.clevel + 2)+"}\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"//Edge of the Top\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").scratchY - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0  <= (Globals.hScreen/2.0)*-1){\n";
        	Globals.clevel++;
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").direction < 0) { Globals.listSCObjects.get("+(numObject-1)+").direction = - 180 + Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction); }\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").direction >=0) { Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - Globals.listSCObjects.get("+(numObject-1)+").direction; }\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY = -1*(Globals.hScreen/2.0) + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0;\n";
        	Globals.clevel--;
        	s += duplicateString("\t", Globals.clevel + 2)+"}\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"//Edge of the Bottom\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").scratchY + Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0 >= (Globals.hScreen/2.0) ){\n";
        	Globals.clevel++;
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").direction < 0) { Globals.listSCObjects.get("+(numObject-1)+").direction = - 180 + Math.abs(Globals.listSCObjects.get("+(numObject-1)+").direction);}\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCObjects.get("+(numObject-1)+").direction >= 0){ Globals.listSCObjects.get("+(numObject-1)+").direction = 180 - Globals.listSCObjects.get("+(numObject-1)+").direction; }\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").scratchY = (Globals.hScreen/2.0) - Globals.listSCObjects.get("+(numObject-1)+").costumes.get(Globals.listSCObjects.get("+(numObject-1)+").currentCostume).height/2.0;\n";
        	Globals.clevel--;
        	s += duplicateString("\t", Globals.clevel + 2)+"}\n";
        }
        if(ins.get(0).equals(" setRotationStyle")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").rotationStyle ="+evalExpression(ins.get(1),numObject-1)+";\n";
        }
       
        //Appereance snippets
        if(ins.get(0).equals("lookLike:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").setCostumebyID(\""+(ins.get(1))+"\");\n";
        }
        if(ins.get(0).equals("nextCostume")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").nextCostume();\n";
        }
        if(ins.get(0).equals("say:duration:elapsed:from:")){
        	int auxv = Integer.parseInt(evalExpression(ins.get(2),numObject-1));
        	s += duplicateString("\t", Globals.clevel + 2)+"System.out.println(\"I say: "+ins.get(1)+" and I go to sleep\");\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"\tThread.sleep("+(auxv*1000)+");\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        }
        if(ins.get(0).equals("say:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"System.out.println(\"I say: "+ins.get(1)+"\");\n";
        }
        if(ins.get(0).equals("think:duration:elapsed:from:")){
        	int auxv = Integer.parseInt(evalExpression(ins.get(2),numObject-1));
        	s += duplicateString("\t", Globals.clevel + 2)+"System.out.println(\"I think: "+ins.get(1)+" and I go to sleep\");\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"\tThread.sleep("+(auxv*1000)+");\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        }
        if(ins.get(0).equals("think:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"System.out.println(\"I think: "+ins.get(1)+" \");\n";
        }
        if(ins.get(0).equals("show")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").setVisible();\n";
        }
        if(ins.get(0).equals("hide")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").setNoVisible();\n";
        }
        if(ins.get(0).equals("startScene")){
        	s += duplicateString("\t", Globals.clevel + 2)+ "for(int i=0; i < Globals.listBackgrounds.size(); i++){\n";
        	s += duplicateString("\t", Globals.clevel + 3)+ "if(Globals.listBackgrounds.get(i).equals(\""+ins.get(1)+"\")){\n";
        	s += duplicateString("\t", Globals.clevel + 4)+ "Globals.currentCostumeIndex = i;\n";
        	s += duplicateString("\t", Globals.clevel + 3)+ "}\n";
        	s += duplicateString("\t", Globals.clevel + 2)+ "}\n";
        	s  +=duplicateString("\t", Globals.clevel + 2)+"Globals.initiater.TriggerMessage(\"startScene_"+ins.get(1)+"\");\n";
        }
        if(ins.get(0).equals("changeGraphicEffect:by:")){
        	//Graphical method
        }
        if(ins.get(0).equals("filterReset")){
        	//Graphical method
        }
        if(ins.get(0).equals("changeSizeBy:")){
        	s = duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").size = Globals.listSCObjects.get("+(numObject-1)+").size"+evalExpression(ins.get(1),numObject-1)+";\n";
        }
        if(ins.get(0).equals("setSizeTo:")){
        	s = duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").size = "+evalExpression(ins.get(1),numObject-1)+";\n";
        }
        if(ins.get(0).equals("comeToFront")){
        	//Graphical method
        }
        if(ins.get(0).equals("goBackByLayers:")){
        	//Graphical method
        }
        
        
        //Sound Snippets
        if(ins.get(0).equals("playSound:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Play sound with name:"+ins.get(1)+"\n";
        }
        if(ins.get(0).equals("doPlaySoundAndWait")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Play sound and wait instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	Globals.clevel++;
        	s += duplicateString("\t", Globals.clevel + 2)+"Thread.sleep(Globals.getDurationByName(\""+ins.get(1)+"\"));\n";
        	Globals.clevel--;
        	s += duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        }
        if(ins.get(0).equals("playDrum")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Play drum number:"+evalExpression(ins.get(1),numObject-1)+" for "+evalExpression(ins.get(2),numObject-1)+" pulses \n";
        	
        }
        if(ins.get(0).equals("noteOn:duration:elapsed:from:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Play note:"+evalExpression(ins.get(1),numObject-1)+" for "+evalExpression(ins.get(2),numObject-1)+" pulses \n";
        	
        }
        if(ins.get(0).equals("instrument:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Fixed instrument to:"+evalExpression(ins.get(1),numObject-1)+" \n";
        }
        if(ins.get(0).equals("changeVolumeBy:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.volume = Globals.volume"+evalExpression(ins.get(1),numObject-1)+";\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"if(Globals.volume > Globals.Vumbral){Globals.initiater.TriggerMessage(\"loudness\");}\n";
  
        }
        if(ins.get(0).equals("setVolumeTo:")){
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.volume = "+evalExpression(ins.get(1),numObject-1)+";\n";
        }
        //Data Variable Snippets
        if(ins.get(0).equals("setVar:to:")){
        	s +=duplicateString("\t", Globals.clevel + 2)+"//Set Variable to a value\n";
        	s +=duplicateString("\t", Globals.clevel + 2)+"for(int i=0;i< Globals.listSCVariables.size();i++){\n";
        	s +=duplicateString("\t", Globals.clevel + 2)+"if(Globals.listSCVariables.get(i).name.equals(\""+ ins.get(1) + "\")){ Globals.listSCVariables.get(i).value = (double)"+evalExpression(ins.get(2),numObject-1)+"; }\n";
        	s +=duplicateString("\t", Globals.clevel + 2)+"}\n";
        }
        if(ins.get(0).equals("changeVar:by:")){
        	
        	s+= duplicateString("\t",Globals.clevel+2)+"//Add value to a Variable by name\n";
        	s+= duplicateString("\t",Globals.clevel+2)+"for(int i=0;i< Globals.listSCVariables.size();i++){\n";
        	Globals.clevel++;
        	s+= duplicateString("\t",Globals.clevel+2)+"if(Globals.listSCVariables.get(i).name.equals(\""+ ins.get(1) + "\")){Globals.listSCVariables.get(i).value = Globals.listSCVariables.get(i).value + (double)"+ins.get(2)+"; }\n";
        	Globals.clevel--;
        	s+= duplicateString("\t",Globals.clevel+2)+"}\n";
        }
        if(ins.get(0).equals("hideVariable:")){
        	s+= duplicateString("\t",Globals.clevel+2)+"//Hide Variable by name\n";
        	s+= duplicateString("\t",Globals.clevel+2)+"for(int i=0;i< Globals.listSCVariables.size();i++){\n";
        	Globals.clevel++;
        	s+= duplicateString("\t",Globals.clevel+2)+"if(Globals.listSCVariables.get(i).name.equals(\""+ ins.get(1) + "\")){Globals.listSCVariables.get(i).visible = false; }\n";
        	Globals.clevel--;
        	s+= duplicateString("\t",Globals.clevel+2)+"}\n";
        	
        }
        if(ins.get(0).equals("showVariable:")){
        	s+= duplicateString("\t",Globals.clevel+2)+"//Hide Variable by name\n";
        	s+= duplicateString("\t",Globals.clevel+2)+"for(int i=0;i< Globals.listSCVariables.size();i++){\n";
        	Globals.clevel++;
        	s+= duplicateString("\t",Globals.clevel+2)+"if(Globals.listSCVariables.get(i).name.equals(\""+ ins.get(1) + "\")){Globals.listSCVariables.get(i).visible = true; }\n";
        	Globals.clevel--;
        	s+= duplicateString("\t",Globals.clevel+2)+"}\n";
        	
        }
        
        
        
        
        //Set Value Snippets
        if(ins.get(0).equals("setRotationStyle")){
        	s += duplicateString("\t", Globals.clevel + 2)+"//Set rotation Style instruction\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"Globals.listSCObjects.get("+(numObject-1)+").rotationStyle =\""+ins.get(1)+"\";\n";
        }
        //Control instructions Snipets
        if(ins.get(0).equals("doForever")){
        	/*
        	Globals.openControl = true;
        	Globals.clevel++;
        	s= "\t\t//Do-forever instruction\n";
        	s+= "\t\tfor(int i=0; i < Globals.steps; i++){\n";
        	s+= "\t\t\tif(Globals.infloop == true){i--;}//i does not increment\n";
        	s+= "\t\t\telse{System.out.println(\"[Thread"+numthread+"] - Step:\"+i);}\n";
        	*/
        	s+=duplicateString("\t", Globals.clevel + 2)+"//Do-forever instruction\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"for(int i=0; i < Globals.steps; i++){\n";
        	Globals.clevel++;
        	if(Globals.clevel <= 1){
        		s+=duplicateString("\t", Globals.clevel + 2)+"if(Globals.infloop == true){i--;}//i does not increment\n";
        		s+=duplicateString("\t", Globals.clevel + 2)+"else{System.out.println(\"[Thread"+numthread+"] - Step:\"+i);}\n";
        	}else{
        		s+=duplicateString("\t", Globals.clevel + 2)+"i--;//i never increments \n";
        	}
        		
        	JSONArray bloqIns = (JSONArray) ins.get(1);
        	for(int i=0; i < bloqIns.size() ; i++ ){
        		JSONArray iaux2 = (JSONArray) bloqIns.get(i) ;
        		s+= evalInstruct(iaux2,numthread, numObject);
        	}
        	
        	s+=duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s+=duplicateString("\t", Globals.clevel + 3)+"Thread.sleep(1000/"+Globals.fps+");\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        	Globals.clevel--;
        	s+=duplicateString("\t", Globals.clevel + 2)+"}\n";
        }
        if(ins.get(0).equals("doWaitUntil")){
        	String operator="";
        	JSONArray iaux = (JSONArray) ins.get(1);
        	if(iaux.get(0).equals("=")){
        		operator = "!=";
        	}else if(iaux.get(0).equals("<")){
        		operator = ">";
        	}else if(iaux.get(0).equals(">")){
        		operator = "<";
        	}
        	
        	s+=duplicateString("\t", Globals.clevel + 2)+"//Do-wait-until instruction\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"while("+evalExpression(iaux.get(1),numObject-1)+" "+operator+" "+evalExpression(iaux.get(2),numObject-1)+" ){\n";
        	Globals.clevel++;
        	s+=duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"Thread.sleep(1000/"+Globals.fps+");\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        	Globals.clevel--;
        	s+= duplicateString("\t", Globals.clevel + 2) +"}\n";
        }
        if(ins.get(0).equals("doRepeat")){
        	s+=duplicateString("\t", Globals.clevel + 2)+"//Do-Repeat instruction\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"for(int i=0; i < "+ins.get(1)+"; i++){\n";
        	Globals.clevel++;
        	
        	s+=duplicateString("\t", Globals.clevel + 2)+"i--;//i never increments \n";
        	System.out.println(ins.get(2));
        	if(ins.get(2) != null){	
        	JSONArray bloqIns = (JSONArray) ins.get(2);
	        	for(int i=0; i < bloqIns.size() ; i++ ){
	        		JSONArray iaux2 = (JSONArray) bloqIns.get(i) ;
	        		s+= evalInstruct(iaux2,numthread, numObject);
	        	}
        	}
        	s+=duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s+=duplicateString("\t", Globals.clevel + 3)+"Thread.sleep(1000/"+Globals.fps+");\n";
        	s+=duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        	Globals.clevel--;
        	s+=duplicateString("\t", Globals.clevel + 2)+"}\n";
        }
        if(ins.get(0).equals("doIf")){
        	System.out.println(ins.get(2));
        	
	        	String operator="";
	        	JSONArray iaux = (JSONArray) ins.get(1);
	        	if(iaux.get(0).equals("=")){
	        		operator = "==";
	        	}else if(iaux.get(0).equals("<")){
	        		operator = "<";
	        	}else if(iaux.get(0).equals(">")){
	        		operator = ">";
	        	}else {operator ="sensor";}
	        	System.out.println("Operator "+operator);
	        	
	        	s += duplicateString("\t", Globals.clevel + 2)+"//Do-if instruction\n";
	        	if(operator.equals("sensor")){
	        		s += duplicateString("\t", Globals.clevel + 2)+"if("+evalExpression(ins.get(1),numObject-1)+"){\n";
	        	}else{
	        		s += duplicateString("\t", Globals.clevel + 2)+"if("+evalExpression(iaux.get(1),numObject-1)+" "+operator+" "+evalExpression(iaux.get(2),numObject-1)+" ){\n" ;
		        		
	        	}
	        	if(ins.get(2) != null){	
		        	Globals.clevel++;
		        	
		        	JSONArray bloqIns = (JSONArray) ins.get(2);
		        	for(int i=0; i < bloqIns.size() ; i++ ){
		        		JSONArray iaux2 = (JSONArray) bloqIns.get(i) ;
		        		s+= evalInstruct(iaux2,numthread, numObject);
		        	}
		        	Globals.clevel--;
	        	}
	        	s+= duplicateString("\t", Globals.clevel + 2) +"}\n";
        	
        }
        
        if(ins.get(0).equals("doIfElse")){
        	
	        	String operator="";
	        	JSONArray iaux = (JSONArray) ins.get(1);
	        	if(iaux.get(0).equals("=")){
	        		operator = "==";
	        	}else if(iaux.get(0).equals("<")){
	        		operator = "<";
	        	}else if(iaux.get(0).equals(">")){
	        		operator = ">";
	        	}
	        	s += duplicateString("\t", Globals.clevel + 2)+"//Do-if-else instruction\n";
	        	s += duplicateString("\t", Globals.clevel + 2)+"if("+evalExpression(iaux.get(1),numObject)+" "+operator+" "+evalExpression(iaux.get(2),numObject)+" ){\n" ;
	        	JSONArray bloqIns;
	        	if(ins.get(2)!=null){
		        	Globals.clevel++;
		        	bloqIns = (JSONArray) ins.get(2);
		        	for(int i=0; i < bloqIns.size() ; i++ ){
		        		JSONArray iaux2 = (JSONArray) bloqIns.get(i) ;
		        		s+= evalInstruct(iaux2,numthread, numObject);
		        	}
		        	Globals.clevel--;
	        	}
	        	s+= duplicateString("\t", Globals.clevel + 2) +"}\n";
	        	s+= duplicateString("\t", Globals.clevel + 2) +"else{\n";
	        	if(ins.get(3)!=null){
		        	Globals.clevel++;
		        	bloqIns = (JSONArray) ins.get(3);
		        	for(int i=0; i < bloqIns.size() ; i++ ){
		        		JSONArray iaux2 = (JSONArray) bloqIns.get(i) ;
		        		s+= evalInstruct(iaux2,numthread, numObject);
		        	}
		        	Globals.clevel--;
	        	}
	        	s+= duplicateString("\t", Globals.clevel + 2) +"}\n";
        }
        if(ins.get(0).equals("wait:elapsed:from:")){
        	int auxv = Integer.parseInt(evalExpression(ins.get(1),numObject-1));
        	s += duplicateString("\t", Globals.clevel + 2)+"//wait:elapsed:from: stopping thread for n seconds\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"try {\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"\tThread.sleep("+(auxv*1000)+");\n";
        	s += duplicateString("\t", Globals.clevel + 2)+"} catch (InterruptedException e) {e.printStackTrace();}\n";
        }
        return s;
    }
    public static void deleteTempFiles(){
    	String workingDir = System.getProperty("user.dir");
    	File directory = new File(workingDir+"/scratch/");
    	//make sure directory exists
    	if(!directory.exists()){
    		System.out.println("No existe");
    	}
    	else{
    		 try{
          	   
                 delete(directory);
          	
             }catch(IOException e){
                 e.printStackTrace();
                 System.exit(0);
             }
    	}
    }
    public static void delete(File file)
        	throws IOException{
     
        	if(file.isDirectory()){
     
        		//directory is empty, then delete it
        		if(file.list().length==0){
        			
        		   file.delete();
        		   System.out.println("Directory is deleted : " 
                                                     + file.getAbsolutePath());
        			
        		}else{
        			
        		   //list all the directory contents
            	   String files[] = file.list();
         
            	   for (String temp : files) {
            	      //construct the file structure
            	      File fileDelete = new File(file, temp);
            		 
            	      //recursive delete
            	     delete(fileDelete);
            	   }
            		
            	   //check the directory again, if empty then delete it
            	   if(file.list().length==0){
               	     file.delete();
            	     System.out.println("Directory is deleted : " 
                                                      + file.getAbsolutePath());
            	   }
        		}
        		
        	}else{
        		//if file, then delete it
        		file.delete();
        		System.out.println("File is deleted : " + file.getAbsolutePath());
        	}
        }
    public static void parseFile(String scf) {
        
    	String workingDir = System.getProperty("user.dir");
    	System.out.println(workingDir);
  	    //System.out.println("Current working directory : " + workingDir);
    	
    	try {
			ZipFile zipFile = new ZipFile(workingDir+"/"+ scf);
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();

				String name = zipEntry.getName();
				long size = zipEntry.getSize();
				long compressedSize = zipEntry.getCompressedSize();
				System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", 
						name, size, compressedSize);
				
				File theDir = new File("scratch");
				  try{
				        theDir.mkdir();
				    } 
				    catch(SecurityException se){
				        //handle it
				    } 
				
				File file = new File("scratch/"+name);
				if (name.endsWith("/")) {
					file.mkdirs();
					continue;
				}

				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}

				InputStream is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();
			}
			zipFile.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
        //Aux variable
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
        		+ "import java.util.Calendar;\n"
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
            /*
            final JSONArray jsonSounds = (JSONArray) json.get("sounds");
            final Iterator it3 = jsonSounds.iterator();
            */
            final JSONArray jsonVar = (JSONArray) json.get("variables");
            Iterator it4=null;
            if (jsonVar != null){ it4 = jsonVar.iterator();}
            
            //Foreach Object in the Json File
            while (it.hasNext()) {
            	JSONObject jsonChild = (JSONObject) it.next();
            	if((String) jsonChild.get("objName")!=null){
            	
            	Globals.i_object++;
                System.out.println("[NEW CHILD]");
                //Globals.SCObjets_snippet += "\tpublic static SCObject scObject_"+Globals.i_object+" = new SCObject();\n";
                
                String objName = (String) jsonChild.get("objName");
                Globals.SCOnames.add(objName);
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
                    Globals.SCThreads_snippet += "\tpublic static Thread_"+Globals.total_numthreads+" scThread_"+Globals.total_numthreads+";\n";
                    
                    Globals.Adding_SCThreads_snippet += "\t\tGlobals.listSCObjects.get("+(Globals.i_object-1)+").threads.add(Globals.scThread_"+Globals.total_numthreads+");\n";
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
                            bw.close(); 
                        }catch (IOException e){}
                    }//End for
                    try{
                        file = new File("SCprogram.java");
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
                        //bw.write ("\t\tGlobals.cucumberKey = false;\n"); //
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
                JSONArray jsonSounds = (JSONArray) jsonChild.get("sounds");
                JSONObject jsonSon;
                for(int i = 0 ; i < jsonSounds.size() ; i++){
                	if(Globals.soundsParsing==false){
                   		Globals.soundsParsing = true;
                   		Globals.SCObjets_AddListSnippet +="\t\ttry{\n";
                   	}
                	
                	caux ="";
                	jsonSon = (JSONObject) jsonSounds.get(i);
                	caux ="new SCSound(\"";
                	caux += (String) jsonSon.get("soundName")+"\",";
                	caux += jsonSon.get("soundID") + ",";
                	caux += "\""+(String) jsonSon.get("md5")+"\",";
                	caux += jsonSon.get("sampleCount") + ",";
                	caux += jsonSon.get("rate") + ",";
                	caux += "\""+(String) jsonSon.get("format")+"\",";
                	caux += "\""+ workingDir + "/scratch/" +"\")";
                	System.out.println(workingDir);
                	Globals.SCObjets_AddListSnippet += "\t\tGlobals.listSCSounds.add("+caux+");\n";
                }
                if(Globals.soundsParsing == true){
                	Globals.soundsParsing = false;
                	Globals.SCObjets_AddListSnippet += "\t\t}catch(Exception e){}\n";
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
            if(jsonVar != null){
	            while(it4.hasNext()){
	            	JSONObject jsonChild = (JSONObject) it4.next();
	            	String caux = " ";
	            	caux = "new SCVariable(\"";
	            	caux+= (String) jsonChild.get("name")+"\",";
	            	caux+= "(double)"+jsonChild.get("value")+",";
	            	caux+= jsonChild.get("isPersistent")+")";
	            	
	            	Globals.SCObjets_AddListSnippet += "\t\tGlobals.listSCVariables.add("+caux+");\n";
	            	
	            }
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
            bw.write("\tpublic static int currentCostumeIndex = 0;\n");
            bw.write("\tpublic static int wScreen = 480;\n");
            bw.write("\tpublic static int hScreen = 360;\n");
            bw.write("\tpublic static int volume = 100;\n");
            bw.write(Globals.Globals_snippet);
            bw.write("\tpublic static int tempo = 60;\n");
            bw.write("\tpublic static boolean infloop = true ;\n");
            bw.write("\tpublic static boolean cucumberKey = true;\n");
            bw.write("\tpublic static boolean cucumberKey_msg = true;\n");
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
            bw.write("\tpublic static ArrayList<SCVariable> listSCVariables = new ArrayList<SCVariable>();\n");
            bw.write("\tpublic static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();\n");
            bw.write("\tpublic static ArrayList<Costume> listBackgrounds = new ArrayList <Costume>();\n");
            bw.write("\tpublic static ArrayList<Thread> listScripts = new ArrayList <Thread>();\n");
            bw.write("\tpublic static ArrayList<SCSound> listSCSounds = new ArrayList <SCSound>();\n");
            bw.write("\tpublic static ArrayList<SCMessage> listSCMessage_sent = new ArrayList <SCMessage>();\n");
            bw.write("\tpublic static ArrayList<SCMessage> listSCMessage_received = new ArrayList <SCMessage>();\n");
            //Global functions
            bw.write("\tpublic static double getSCValueByName(String id){\n");
            bw.write("\t\tdouble res=-999999;\n");
            bw.write("\t\tfor(int i=0;i< Globals.listSCVariables.size();i++){\n");
            bw.write("\t\t\tif(Globals.listSCVariables.get(i).name.equals(id)){res = Globals.listSCVariables.get(i).value;}\n");
            bw.write("\t\t}\n");
            bw.write("\t\treturn res;\n");
            bw.write("\t}\n");
            
            bw.write("\tpublic static int getDurationByName(String id){\n");
            bw.write("\t\tint res =0;\n");
            bw.write("\t\tfor(int i=0;i<Globals.listSCSounds.size();i++){\n");
            bw.write("\t\t\tif(Globals.listSCSounds.get(i).soundName.equals(id)){res = Globals.listSCSounds.get(i).duration; }\n");
            bw.write("\t\t}\n");
            bw.write("\t\treturn res;\n");
            bw.write("\t}\n");
            
            
            
            
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
            bw.write(Globals.Adding_SCThreads_snippet);
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
	public static String Globals_snippet = "";
	public static String MessageEvents_snippet = "";
	public static String SCObjets_snippet ="";
	public static String SCObjets_AddListSnippet ="";
	public static String SCThreads_snippet = "";
	public static String Adding_SCThreads_snippet = "";
	public static String Listener_snippet = "";
	public static String KeyPress_snippet = "";
	public static int clevel = 0;
	public static boolean soundsParsing = false;
	public static boolean openControl = false;
	public static boolean msgSending = false;
	
	public static ArrayList<String> SCOnames = new ArrayList<String>();
}