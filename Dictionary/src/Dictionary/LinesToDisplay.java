package Dictionary;

import java.util.Iterator;


/**
 * A class that will be used to display the lines of text that are corrected.
 *
 */
public class LinesToDisplay {

    public static final int LINES = 10;     // Display 10 lines
    private AList<Wordlet>[] lines;
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay
     */
    public LinesToDisplay() {
       //constructor for lines array 
       lines = (AList<Wordlet>[])new AList[LINES+1];
       //currentLine set to 0 
       currentLine = 0;
       
       //Loops through the array 
       for(int i =0; i<LINES+1; i++){
           lines[i] = new AList<>();
       }
       
    }

    /**
     * Add a new wordlet to the current line.
     *
     */
    public void addWordlet(Wordlet w) {
        //ADD CODE HERE TO ADD A WORDLET TO THE CURRENT LINE
        lines[currentLine].add(w);
        
    }

    /**
     * Go to the next line, if the number of lines has exceeded LINES, shift
     * them all up by one
     *
     */
    public void nextLine() {
        //ADD CODE TO HANDLE THE NEXT LINE
        
        //if the currentLine is equal to the max lines
        if (currentLine == LINES){
            //shift everything
            for(int i =0; i<LINES; i++){
                lines[i] = lines[i+1];
            }
            //create a new array list in the ending element 
            lines[LINES] = new AList<>();
        }
        else{
            //move the Current Line up
            currentLine++;
        }
    }

      
    public int getCurrentLine(){
        return currentLine;
    }
    
    public AList<Wordlet>[] getLines(){
        return lines;
    }
}
