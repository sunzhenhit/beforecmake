package org.jenkinsci.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import hudson.model.BuildListener;

public class CommandRunner {
	
	public  boolean linuxcommand(String commands , BuildListener listener) throws IOException, InterruptedException {
	            String s =null;
	            Process p=null;
	            boolean rs = true;
	            listener.getLogger().println(commands);
	            p = Runtime.getRuntime().exec(commands);
	            BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	            
	            String errline = null;
	            while ((errline = brError.readLine()) != null) {
	             listener.getLogger().println(errline);
	             System.out.println(errline);
	            // rs = false;
	            }
	            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            while ((s = br.readLine()) != null) {
	            	listener.getLogger().println(s);
	            	System.out.println("line: " + s);
	            }
	            p.waitFor();
	            listener.getLogger().println("exit: " + p.exitValue());	            
	            p.destroy();
	      
	            return rs;
	    
	}
	
    public boolean deleteDirectory(String dir){
       
        if(!dir.endsWith(File.separator)){
            dir = dir+File.separator;
        }
        File dirFile = new File(dir);
       
        if(!dirFile.exists() || !dirFile.isDirectory()){
           
            return false;
        }
        boolean flag = true;
       
        File[] files = dirFile.listFiles();
        for(int i=0;i<files.length;i++){
          
            if(files[i].isFile()){
                flag = deleteFile(files[i].getAbsolutePath());
                if(!flag){
                    break;
                }
            }
           
            else{
                flag = deleteDirectory(files[i].getAbsolutePath());
                if(!flag){
                    break;
                }
            }
        }

        if(!flag){
           
            return false;
        }

      
        if(dirFile.delete()){
            
            return true;
        }else{
           
            return false;
        }
    }
    
    private boolean deleteFile(String fileName){
        File file = new File(fileName);
        if(file.isFile() && file.exists()){
            Boolean succeedDelete = file.delete();
            if(succeedDelete){
               
                return true;
            }
            else{
                
                return true;
            }
        }else{
            
            return false;
        }
    }
    
    public boolean exits(String dir) {
        if(!dir.endsWith(File.separator)){
            dir = dir+File.separator;
        }
        File dirFile = new File(dir);
       
        if(!dirFile.exists() || !dirFile.isDirectory()){
           
            return false;
        }else {
        	return true;
        }
        
        
    }

}
