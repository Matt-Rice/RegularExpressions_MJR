/**
 *  * Program that accepts two inputs from the user, a .txt file corresponding to a book and another .txt file that contains a series of regex expressions
 * that correspond to these words and their variations: Gloomy, Haunting, Mysterious, Foreboding, Melancholic, Terror, and Desolate
 * The program will count the number of instances of each of these and print them to an output file.
 * @author Matt Rice
 * @version 1.0
 * Assignment 4
 * CS 322 - Compiler Construction
 * Spring 2024
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
    
    /**
     * Constructor for this class. It is not used in execution
     */
    public NovelProcessor(){
    }//end NovelProcessor

    /**
     * Method that will read a .txt file and output a HashMap with the keys being a regex pattern and the values being their occurrences throughout the text
     * @param bookFile the text to be scanned
     * @param regFile the file containing the regex patterns
     * @return a HashMap that contains the regex patterns and their corresponding occurrences throughout the text
     */
    public static HashMap <String, Integer> processNovel(String bookFile, String regFile){
        String line = "";
        HashMap <String, Integer> wordCount = new HashMap<String,Integer>();

        try{
            bookReader = new BufferedReader(new FileReader(bookFile));
            regReader = new BufferedReader(new FileReader(regFile));
            
            // Reads each line in the regex file and puts the pattern as the key and 0 as the value
            while ((line = regReader.readLine()) != null){
                wordCount.put(line.trim(), 0);
            }//end while
            
            // Outer while that will read each line of the book and count the occurrences of each pattern
            while((line = bookReader.readLine()) != null){

                for(String regString : wordCount.keySet()){
                    Pattern pattern = Pattern.compile(regString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher= pattern.matcher(line);

                    while(matcher.find()){
                        wordCount.put(regString, wordCount.get(regString)+1);
                    }//while
                }//for
            }//while
           
        }//try

        catch (IOException e){
            e.printStackTrace();;
        }//catch

        return wordCount;
    }//end processNovel

    /**
     * Ouputs the key value pairs into a .txt file and adds _wc to the end of the file name
     * @param map
     * @param outputFileName
     */
    public static void outputFile(HashMap<String,Integer> map, String outputFileName){
        try{
            
            outWriter = new PrintWriter(new FileWriter(outputFileName.replaceAll(".txt","_wc.txt")));
            
            for(HashMap.Entry<String, Integer> entry : map.entrySet()){           
                outWriter.println(entry.getKey() + "|" + entry.getValue());
            }//for
          
            outWriter.close();
        }//try

        catch(IOException e){
            e.printStackTrace();
        }//catch        
    }//end outputFile
    

    // Main method that will prompt the user to enter the .txt file to be searched and the file that contains the regex patterns and will call the method to process the novel and output the word count file
    public static void main(String[]args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file to be searched: ");
        String bookFile = scan.nextLine();
        
        System.out.println("Please enter the name of the file containing the regex expressions:");
        String regFile = scan.nextLine();

        HashMap<String, Integer> wordCount = processNovel(bookFile, regFile);

        outputFile(wordCount, bookFile);

        scan.close();

    }//end main

}//end NovelProcessor
