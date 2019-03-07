package coursework;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;

/**
 * Search class
 * Used to find an Object in an ArrayList.
 *
 * @author Abdullah Ibne Atiq, Harvind Sokhal, Ed Bencito
 */
public class Search {
    
    /**
     * Function to search for a string in an ArrayList.
     * 
     * @param text ArrayList
     * @param search Object to search
     * @return Search outcome
     */
    public String search(String[] text, String search) {
        int count = 0;
        
        for(String s: text) {
            if(s.equalsIgnoreCase(search)) {
                count++;
            }
        }
        if(count > 0) {
            if(count > 1){
                return "\"" + search + "\" appears " + count + " times.";
            }
            else {
                return "\"" + search + "\" appears " + count + " time.";
            }
        }        
        else {
            return "\"" + search + "\" not found.";
        }
    }
    
    /**
     * Function to style the search result TextArea
     * 
     * @param text Text to be set in text box
     * @param textArea Existing JTextArea component
     * @param fontColour Colour of text
     * @param backgroundColour Background colour
     * @param fnt Font to be applied
     */
    public void formatSearch(String text, JTextArea textArea, Color fontColour, Color backgroundColour, Font fnt) {
        textArea.setText(text);
        textArea.setForeground(fontColour);
        textArea.setBackground(backgroundColour);
        textArea.setFont(fnt);
    }
}
