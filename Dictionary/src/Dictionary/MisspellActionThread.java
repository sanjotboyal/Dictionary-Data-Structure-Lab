package Dictionary;

import java.io.*;
import static java.lang.Character.isLetter;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */
public class MisspellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    /**
     * Constructor for objects of class MisspellActionThread
     *
     * @param controller
     */
    public MisspellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "check.txt";
        dictionaryFileName = "sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;

    }

    @Override
    public void run() {

        // ADD CODE HERE TO LOAD DICTIONARY
        loadDictionary("src/Dictionary/sampleDictionary.txt",myDictionary);
        

        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });
        
       
        // ADD CODE HERE TO CALL checkWords
        checkWords("src/Dictionary/check.txt",myDictionary);
        

    }

    /**
     * Load the words into the dictionary.
     *
     * @param theFileName The name of the file holding the words to put in the
     * dictionary.
     * @param theDictionary The dictionary to load.
     */
    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String correctWord;

            input = new Scanner(new File(theFileName));
            
            //Loops through entire file
            while(input.hasNext()){
                //gets the string
                inString = input.nextLine();
                //correct word is equal to that documents values
                correctWord = inString;
                //add the key and value to the dictionary
                theDictionary.add(inString, correctWord);
            }
            //set its loaded to true 
            dictionaryLoaded = true;
            
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the words to check, check them, then put Wordlets into myLines. When
     * a single line has been read do an animation step to wait for the user.
     *
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String aWord;
            
            
            input = new Scanner(new File(theFileName));
            
            //read the entire text file
            while(input.hasNext()){
                inString = input.nextLine();
                //delimeters
                String delimeters = "\":?;!)([, .]\"";
                
                //set string tokenizer (true value returns delimeters)
                StringTokenizer sT = new StringTokenizer(inString, delimeters,true);
                
                //sets each word to a seperate wordlet
                while(sT.hasMoreTokens()){
                    aWord = sT.nextToken();
                    System.out.println(aWord);
                    
                    //create wordlet and it it too lines               
                    myLines.addWordlet(new Wordlet(aWord,checkWord(aWord,myDictionary)));
                    
                }
                
                //move to nextLine
                myLines.nextLine();
                //show Line
                showLines(myLines);
                
                
            }
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Check the spelling of a single word.
     *
     */
    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
        boolean result = false;
        
        //if the words first character is not a letter (so its a punctuation and its not a component of a word. 
        if((!(isLetter(word.charAt(0))))&& (word.length()<2)){
            //sets punctuation to true (blue value)
            result = true;
        }
        //else if dictionary's value is equal to the searched word (true)
        else if(theDictionary.getValue(word) != null && theDictionary.getValue(word).equals(word)){
            result = true;
        //else its false 
        }else{
            result = false;
        }

        return result;

    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }

} // end class MisspellActionThread

