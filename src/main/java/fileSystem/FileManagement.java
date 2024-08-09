package fileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManagement {

	private final String rootPath;

    public FileManagement() {
    	File[] rootDrive = File.listRoots();
    	String username = System.getProperty("user.name");
    	String root = rootDrive[0].toString();

    	this.rootPath = String.format("%sUsers/%s/csv/", root, username);

    	File folder = new File(rootPath);
    	
    	folder.mkdir();
    }

    public void write(Object o, String filename){
        try {
            FileWriter fw = new FileWriter(rootPath+filename, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(o);
            pw.close();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String read(String filename){
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(rootPath+filename))){
            String line;
            while((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void clear(String filename){
        try {
            FileWriter fw = new FileWriter(rootPath+filename, false);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("");
            pw.close();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
