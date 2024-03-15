/**
 * Reads a Unix log file (provided by user as the first argument) and creates HashMaps one with each unique IP address and the number of times it occurs and another with unique usernames and their occurrences and if the second argument is 1, it prints the IP map, 2 prints the username map and any other number will print only the default
 * @author Matt Rice
 * @version 1.0
 * Assignment 4
 * CS 322 - Compiler Construction
 * Spring 2024
 */
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.ArrayList;

public class LogFileProcessor {

    private static int lineCount;

    /**
     * Processes a file and creates and returns an ArrayList of HashMaps with the unique usernames and IPs and the number of times they occur
     * @param fileName the name of the file to be processed
     * @return an ArrayList of HashMaps with index zero containing unique usernames followed by the number of times they occur and index one with the IP addresses
     */
    public static ArrayList<HashMap<String,Integer>> processMaps(String fileName){
        
        ArrayList<HashMap<String, Integer>> occurrences = new ArrayList<HashMap<String, Integer>>();

        occurrences.add(new HashMap<String,Integer>());
        occurrences.add(new HashMap<String, Integer>());

        String user = "user\\s[a-z]+";
        String ip = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        
        String line;
        
        Pattern usrPattern = Pattern.compile(user);
        Pattern ipPattern = Pattern.compile(ip);
        
        lineCount = 0;

        try{

            BufferedReader read = new BufferedReader(new FileReader(fileName));

            while((line = read.readLine()) != null){
                Matcher usrMatcher = usrPattern.matcher(line);
                Matcher ipMatcher = ipPattern.matcher(line);
               
                lineCount++;
                // Username find
                while(usrMatcher.find()){
                    String[] match = usrMatcher.group().split(" "); //match [0] is user as specified by the pattern
                    String username = match[1];
                   
                    //If it isn't in the map yet, put 0
                    if(!occurrences.get(0).containsKey(username)){
                        occurrences.get(0).put(username, 0);
                    }

                    occurrences.get(0).put(username, occurrences.get(0).get(username)+1);
                }//while

                //IP find
                while(ipMatcher.find()){
                    String IP = ipMatcher.group();
                    
                    //If it isn't in the map yet, put 0
                    if(!occurrences.get(1).containsKey(IP)){
                        occurrences.get(1).put(IP, 0);
                    }
                   
                    occurrences.get(1).put(IP, occurrences.get(1).get(IP)+1);
                }//while
            }//while
        
            read.close();
        
        }//try
        catch(IOException e){
            e.printStackTrace();
        }//catch
        return occurrences;
    }//end processUsers

    /**
     * Returns the size of a given HashMap
     * @param map the HashMap thats size will be returned
     * @return the size of the HashMap
     */
    public static int size(HashMap<String,Integer> map){
        return map.size();
    }//end size

    /**
     * Prints a Hashmap
     * @param map
     */
    public static void printHashMap(HashMap<String,Integer> map){
        for(HashMap.Entry<String,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }//for
    }//end printHashMap

    public static void main(String[] args) {
        if(args.length != 2 ){
            System.out.println("When running the file, please include two arguments, first, the file name to be processed, second either 0 to print the default output, 1 to print the IP addresses and the default, or 2 for the usernames and default output");
            return;
        }
        
        File logFile = new File(args[0]);

        if(!logFile.exists()){
            System.out.println("Please enter the path of a valid file for the first argument");
            return;
        }

        int printChoice = Integer.parseInt(args[1]);

        ArrayList<HashMap<String,Integer>> ocMaps = processMaps(logFile.getName());
        
        HashMap<String,Integer> ipMap = ocMaps.get(1);
        HashMap<String,Integer> usrMap = ocMaps.get(0);

        if(printChoice == 1){
            printHashMap(ipMap);
        }
        else if(printChoice == 2){
            printHashMap(usrMap);
        }

        System.out.println(lineCount + " lines in the log file were parsed.\n" + 
                "There are " + ipMap.size() +" unique IP addresses in the log.\n" + //
                "There are " + usrMap.size() + " unique users in the log.");



    }//end main

}//end LogFileProcessor
