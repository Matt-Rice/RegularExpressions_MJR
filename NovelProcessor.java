/**
 * @author Matt Rice
 * @version 3-5-24
 * NovelProcessor.java
 * Program that accepts two inputs from the user, a .txt file corresponding to a book and another .txt file that contains a series of regex expressions
 * that correspond to these words and their variations: Gloomy, Haunting, Mysterious, Foreboding, Melancholic, Terror, and Desolate
 * The program will count the number of instances of each of these and print them to an output file.
 */

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.HashMap;

public class NovelProcessor {
    
    private static BufferedReader bookReader;
    private static BufferedReader regReader;
    private static PrintWriter outWriter;
    
    public NovelProcessor(){
    }

    public static HashMap <String, Integer> processNovel(String bookFile, String regFile){
        String line = "";
        HashMap <String, Integer> wordCount = new HashMap<String,Integer>();
        try{
            bookReader = new BufferedReader(new FileReader(bookFile));
            regReader = new BufferedReader(new FileReader(regFile));
            StringBuilder reg = new StringBuilder();
            
            while ((line = regReader.readLine()) != null){
                
                reg.append(line.trim()).append("|");
            }
            reg.deleteCharAt(reg.length()-1);

            String regString = reg.toString();
            System.out.println(regString);
            Pattern pattern = Pattern.compile(regString, Pattern.CASE_INSENSITIVE);

            while((line = bookReader.readLine()) != null){
                Matcher matcher= pattern.matcher(line);

                while(matcher.find()){
                    String word = matcher.group().toLowerCase();
                    wordCount.put(word, wordCount.getOrDefault(word, 0)+1);
                }
            }
           
        }
        catch (IOException e){
            System.out.println("File not Found");
        }
        return wordCount;
    }

    public static void outputFile(HashMap<String,Integer> map, String outputFileName){
        try{
            
            outWriter = new PrintWriter(new FileWriter(outputFileName.replaceAll(".txt","_wc.txt")));
            for(HashMap.Entry<String, Integer> entry : map.entrySet()){
           
                outWriter.println(entry.getKey() + " | " + entry.getValue());
            }
          
            outWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
    


    public static void main(String[]args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file to be searched: ");
        String bookFile = scan.nextLine();
        
        System.out.println("Please enter the name of the file containing the regex expressions:");
        String regFile = scan.nextLine();

        HashMap<String, Integer> wordCount = processNovel(bookFile, regFile);

        outputFile(wordCount, bookFile);



        scan.close();



    }

    
    



}
