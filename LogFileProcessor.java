import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class LogFileProcessor {

    private static int lineCount;

    public static HashMap<String,Integer> processIP(String fileName){
        HashMap<String, Integer> occurrences = new HashMap<String,Integer>();
        String ip = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        String line;
        lineCount = 0;
        Pattern pattern = Pattern.compile(ip);
        try{
            BufferedReader read = new BufferedReader(new FileReader(fileName));

            while((line = read.readLine()) != null){
                Matcher matcher= pattern.matcher(line);
                lineCount++;
                while(matcher.find()){
                    String match = matcher.group();

                    if(!occurrences.containsKey(match)){
                        occurrences.put(match, 0);
                    }
                    occurrences.put(match, occurrences.get(match)+1);
                }
            }
            read.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return occurrences;
    }

    public static HashMap<String,Integer> processUsers(String fileName){
        HashMap<String, Integer> occurrences = new HashMap<String,Integer>();
        String userPattern = "user\\s[a-z]+";
        String line;
        Pattern pattern = Pattern.compile(userPattern);
        try{
            BufferedReader read = new BufferedReader(new FileReader(fileName));

            while((line = read.readLine()) != null){
                Matcher matcher= pattern.matcher(line);

                while(matcher.find()){
                    String[] match = matcher.group().split(" ");
                    String username = match[1];
                    if(!occurrences.containsKey(username)){
                        occurrences.put(username, 0);
                    }
                    occurrences.put(username, occurrences.get(username)+1);
                }
            }
            read.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return occurrences;
    }

    public static int size(HashMap<String,Integer> map){
        return map.size();
    }

    public static void printHashMap(HashMap<String,Integer> map){
        for(HashMap.Entry<String,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }//for
    }
    public static void main(String[] args) {
        if(args.length != 2 ){
            System.out.println("When running the file, please include two argument, first, the file name to be processed, second either 0 to print the default output, 1 to print the IP addresses and the default, or 2 for the usernames and default output");
            return;
        }
        
        File logFile = new File(args[0]);

        if(!logFile.exists()){
            System.out.println("Please enter the path of a valid file for the first argument");
            return;
        }

        int printChoice = Integer.parseInt(args[1]);

        HashMap<String,Integer> ipMap = processIP(logFile.getName());
        HashMap<String,Integer> usrMap = processUsers(logFile.getName());

        if(printChoice == 1){
            printHashMap(ipMap);
        }
        else if(printChoice == 2){
            printHashMap(usrMap);
        }

        System.out.println(lineCount + " lines in the log file were parsed.\n" + 
                "There are " + ipMap.size() +" unique IP addresses in the log.\n" + //
                "There are " + usrMap.size() + " unique users in the log.");



    }

}
