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
public class SCparser1 {
	
	public static void main(String[] args) {
        
        parseFile();
	}
    
    public static String evalInstruct(String command, int numthread, int numObject) {
    	String s="";
        //Events
        if(command.equals("whenGreenFlag")){
            Globals.Listener_snippet+= "\t\t\tGlobals.scThread_"+Globals.total_numthreads+".start();\n" ;
        }
        //Movement
        if(command.equals("gotoX:y:")){
            
        }
        if(command.equals("setRotationStyle ")){
        
        }
        
        //Control
        if(command.equals("doForever")){
        	s = "\tpublic void run(){\n";
        	s+= "\t\tfor(int i=0; i < Globals.steps; i++){\n";
        	s+= "\t\t\tSystem.out.println(\"[Thread"+numthread+"] - Step:\"+i);\n";
        	s+= "\t\t\ttry {\n";
        	s+= "\t\t\t\tThread.sleep(1000);\n";
        	s+= "\t\t\t} catch (InterruptedException e) {e.printStackTrace();}\n";
        	s+= "\t\t}\n";
        	s+= "\t\tGlobals.cucumberKey = false;\n";
        	s+= "\t}\n";
        }
        
        
        return s;
    }
    public static void parseFile() {
        
        //Aux variables
        final String filePath = "project.json";
        FileReader reader = null;
        
        //Writing The header lines
        String imports = "//Scratch parsed in Java \n";
        imports += "package cucumber.features;\n"
        		+ "import java.awt.event.KeyAdapter;\n"
        		+ "import java.awt.event.KeyEvent;\n"
        		+ "import java.util.ArrayList;\n"
        		+ "import javax.swing.JFrame;\n"
        		+ "import javax.swing.JTextField;\n";
        File file;
        FileWriter fw; 
        BufferedWriter bw;
        try{
            file = new File("programa.java");
            if(!file.exists()){
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
                Globals.SCObjets_snippet += "\tpublic static SCObject scObject_"+Globals.i_object+" = new SCObject();\n";
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
                        file = new File("programa.java");
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
                        bw.write("class Thread_"+Globals.total_numthreads+" extends Thread {\n");  //Writing the header
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
                            file = new File("programa.java");
                            fw = new FileWriter(file.getAbsoluteFile(), true);
                            bw = new BufferedWriter(fw);
                            bw.write("\t//"+ins.get(0)+"\n"); 
                            bw.write(evalInstruct((String) ins.get(0),Globals.total_numthreads,Globals.i_object));
                            bw.close(); 
                        }catch (IOException e){}
                    }//End for
                    try{
                        file = new File("programa.java");
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
                        bw.write("}\n");  
                        bw.close(); 
                    }catch (IOException e){}
                }//End For
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
            file = new File("programa.java");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write("class Globals {\n"); 
            bw.write("\tpublic static int steps ;\n");
            bw.write("\tpublic static boolean cucumberKey = true;\n");
            bw.write("\tpublic static boolean loop = true;\n");
            bw.write("\tpublic static long total_timeApp = 0;\n");
            bw.write("\tpublic static long timeApp = System.currentTimeMillis();\n");
            bw.write(Globals.SCObjets_snippet);
            bw.write(Globals.SCThreads_snippet);
            bw.write("\tpublic static App appT = new App();\n");
            bw.write("\tpublic static ArrayList<SCObject> listSCObjects = new ArrayList<SCObject>();\n");
            bw.write("}\n");  
            bw.close(); 
        }catch (IOException e){}
        
        //Writing the KeyListener Class snippet
        try{
        	file = new File("programa.java");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
        	bw.write("class MKeyListener extends KeyAdapter {\n");
        	bw.write("\t@Override public void keyPressed(KeyEvent event) {\n");
        	bw.write("\t\tchar ch = event.getKeyChar();\n");
        	
        	bw.write("\t\tif (ch == 'q') { //Quit the mainLoop\n");
        	bw.write("\t\t\tGlobals.loop = false;\n");
        	bw.write("\t\t}\n");
        	
        	bw.write("\t\tif (event.getKeyCode() == KeyEvent.VK_ENTER) {\n");
        	bw.write(Globals.Listener_snippet);
        	bw.write("\t\t\tGlobals.cucumberKey = false;\n");
        	bw.write("\t\t}\n");
        	
        	bw.write("\t}\n");
        	bw.write("}\n");
            bw.close(); 
        }catch (IOException e){}
        //Writing the App main snippet
        try{
        	file = new File("programa.java");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            
            bw.write("class App extends Thread{ {\n");
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
	public static int total_numthreads = 0;
	public static int i_object = 0;
	public static String SCObjets_snippet ="";
	public static String SCObjets_AddListSnippet ="";
	public static String SCThreads_snippet = "";
	public static String Listener_snippet = "";
}