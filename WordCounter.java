/**
 * Creates a hashmap and goes through each word count file to output the total occurrences of each pattern throughout each book
 * @author Matt Rice
 * @version 1.0
 * Assignment 4
 * CS 322 - Compiler Construction
 * Spring 2024
 */
import java.io.*;
import java.util.HashMap;

public class WordCounter {
    public static void main(String[] args) {
          
        String directoryPath = System.getProperty("user.dir");

        HashMap<String,Integer> totalCount = new HashMap<String,Integer>();

        // Creates a file for the directory so all of the files ending in _wc.txt can be scanned
        File directory = new File(directoryPath);
        
        if(!directory.exists() || !directory.isDirectory()){
            System.out.println("Please run again and enter a valid directory path.");
            return;
        }
        // Goes through each file and will read it if it ends in _wc.txt it creates a FileReader that reads line by line and splits each line into the pattern 
        //and count and then either puts the pattern in the HashMap with a starting value of 0 then adds the file's count or adds the count from the current file to the total count
        for(File file : directory.listFiles()){
           
            if(file.getName().endsWith("_wc.txt")){
                try{
                    BufferedReader read = new BufferedReader(new FileReader(file));
                    String line;
                    
                    while((line = read.readLine()) != null){
                        int index = line.lastIndexOf("|");
                       
                        String pattern = line.substring(0, index);
                        int count = Integer.parseInt(line.substring(index+1));

                        if (!totalCount.containsKey(pattern)){
                            totalCount.put(pattern,0);
                        }
                            totalCount.put(pattern, totalCount.get(pattern)+ count);
                        
                    }//while
                    System.out.println(file.getName() + " done");
                    read.close();
                }//try
                
                catch(IOException e){
                    e.printStackTrace();;
                }//catch
           }//if
        }//for
        
        // Uses a PrintWriter to 
        try{
            PrintWriter outWriter = new PrintWriter(new FileWriter("total_wc.txt"));

            for (String pattern : totalCount.keySet()) {
                outWriter.println(pattern + "|" + totalCount.get(pattern));
            }//for

            outWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();;
        }

    }//end main
}//end WordCounter
