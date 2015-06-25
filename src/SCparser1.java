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
    
    public static String evalInstruct(JSONArray ins, int numthread, int numObject) {
    	String s="";
        //Events Snippets
        if(ins.get(0).equals("whenGreenFlag")){
            Globals.Listener_snippet+= "\t\t\tGlobals.scThread_"+Globals.total_numthreads+".start();\n" ;
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
        	
        	String steps = "";
        	
        	if(ins.get(1) instanceof String || ins.get(1) instanceof Double || ins.get(1) instanceof Integer){
        		//System.out.println("Es cadena");
        		steps = "Double steps = Double.parseDouble((String)ins.get(1));\n";
        	}
        	else{
        		//There is an operator expresion
        		//System.out.println("Es JASONArray");
        		JSONArray dataux = (JSONArray) ins.get(1);
        		if (dataux.get(0).equals("randomFrom:to:")){
        			steps += "Double steps = "+dataux.get(1)+" + Math.random()*"+dataux.get(2)+";\n"; 
        		}
        	}
        	if(Globals.openControl){
        		s += "\t\t\t//Move forward instruction\n";
        		s += "\t\t\t"+steps;
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
            //Foreach Object in the Json File
            while (it.hasNext()) {
            	Globals.i_object++;
                System.out.println("[NEW CHILD]");
                //Globals.SCObjets_snippet += "\tpublic static SCObject scObject_"+Globals.i_object+" = new SCObject();\n";
                JSONObject jsonChild = (JSONObject) it.next();
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
                
                Globals.SCObjets_AddListSnippet += "\t\tGlobals.listSCObjects.add(new SCObject(\""+objName+"\","+cci+","+sx+","+sy+","+d+",\""+rs+"\","+isd+","+iil+","+vis+"));\n";
                
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
                            	bw.write ("\t\t\ttry {\n");
                            	bw.write ("\t\t\t\tThread.sleep(1000);\n");
                            	bw.write ("\t\t\t} catch (InterruptedException e) {e.printStackTrace();}\n");
                            	bw.write ("\t\t}\n");
                            	bw.write ("\t\tGlobals.cucumberKey = false;\n");
                            	bw.write("\t}\n");
                            }
                            bw.close(); 
                        }catch (IOException e){}
                    }//End for
                    try{
                        file = new File("SCprogram.java");
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
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
                    	
                    	caux+= eElement.getAttribute("width")+",";
                       	caux+= eElement.getAttribute("height")+")";
                	}
                	else {
                		final BufferedImage bi = ImageIO.read(new File(workingDir + "/scratch/"+jsonCos.get("baseLayerID") + "."+formatFile));
                	}
                   	//System.out.println(caux);
                	Globals.SCObjets_AddListSnippet += "\t\tGlobals.listSCObjects.get("+(Globals.i_object-1)+").costumes.add("+caux+");\n";
                }
                
                
            }//End Object iterator
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
            bw.write("\tpublic static int steps ;\n");
            bw.write("\tpublic static int wScreen = 480;\n");
            bw.write("\tpublic static int hScreen = 360;\n");
            bw.write("\tpublic static boolean infloop = true ;\n");
            bw.write("\tpublic static boolean cucumberKey = true;\n");
            bw.write("\tpublic static boolean loop = true;\n");
            bw.write("\tpublic static long total_timeApp = 0;\n");
            bw.write("\tpublic static long timeApp = System.currentTimeMillis();\n");
            //bw.write(Globals.SCObjets_snippet);
            bw.write(Globals.SCThreads_snippet);
            bw.write("\tpublic static App appT = new App();\n");
            bw.write("\tpublic static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();\n");
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
        	if(Globals.cucumberBuilt == false){bw.write(Globals.Listener_snippet);}
        	
        	bw.write("\t\t\tGlobals.cucumberKey = false;\n");
        	bw.write("\t\t}\n");
        	
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
            bw.write("\t}\n");//Close run function
            bw.write("}\n");//Close App class
            bw.close(); 
        }catch (IOException e){}
    }
}
class Globals{
	public static boolean cucumberBuilt = true;
	public static int total_numthreads = 0;
	public static int i_object = 0;
	public static String SCObjets_snippet ="";
	public static String SCObjets_AddListSnippet ="";
	public static String SCThreads_snippet = "";
	public static String Listener_snippet = "";
	public static boolean openControl = false;
}