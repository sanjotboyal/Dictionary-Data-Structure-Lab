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
            
            while(input.hasNext()){
                inString = input.nextLine();
                correctWord = inString;
                theDictionary.add(inString, correctWord);
            }
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
            
            while(input.hasNext()){
                inString = input.nextLine();
                
                String delimeters = "\":?;!)([, .]\"";
                
                StringTokenizer sT = new StringTokenizer(inString, delimeters,true);
                while(sT.hasMoreTokens()){
                    aWord = sT.nextToken();
                    //System.out.println(aWord);
                                       
                    myLines.addWordlet(new Wordlet(aWord,checkWord(aWord,myDictionary)));
                    
                }
                
                myLines.nextLine();
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

        if(!(isLetter(word.charAt(0)))){
            result = true;
        }
        else if(theDictionary.contains(word)){
            result = true;
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

