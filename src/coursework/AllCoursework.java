package coursework;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * AllCoursework class
 * It holds all the CourseworkItem objects in an ArrayList.
 * 
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class AllCoursework extends CommonCode {
    private final ArrayList<CourseworkItem> allCourseworkItems = new ArrayList<>();
    // Unique identifier: store ID the courseworkItem after will hold
    private int nextCourseworkID = 0;
    // Path to file
    public final String path = appDir + fileSeparator + "Coursework.txt";
    
    /**
     * Constructor
     */
    public AllCoursework() {
        readAllCourseworkItems();
    }
    
    /**
     * Method to increment nextCourseworkID by 1
     * 
     * @return next coursework ID
     */
    private int nextCourseworkID() {
        int id = nextCourseworkID;
        nextCourseworkID++;
        return id;
    }
    
    /**
     * Method to read all coursework items from file
     */
    private void readAllCourseworkItems() {
        ArrayList<String> readCoursework = readTextFile(path);
        
        if("File not found".equals(readCoursework.get(0))) {
        } else {
            for(String str: readCoursework) {
                // tmp should look like: {Integer cID, String cName, Integer courseID, String cOverview}
                String[] tmp = str.split("\t");
                
                try {
                    CourseworkItem c = new CourseworkItem(Integer.parseInt(tmp[0]), tmp[1], Integer.parseInt(tmp[2]), tmp[3]);
                    allCourseworkItems.add(c);
                    if(nextCourseworkID <= c.getCourseworkID()) {
                        nextCourseworkID = c.getCourseworkID() + 1;
                    }
                } catch(Exception e) {
                    // If any error occurs (e.g. NumberFormatException, IndexOutOfBounds)
                    JOptionPane.showMessageDialog(null, "Error while parsing Coursework.txt file.");
                    return;
                }
            }
        }
    }
    
    /**
     * Method to add a new coursework item
     * 
     * @param courseID Course ID
     * @param name Course name
     * @param overview Course description
     */
    public void addNewCoursework(int courseID, String name, String overview) {
        CourseworkItem c = new CourseworkItem(nextCourseworkID(), name, courseID, overview);        
        allCourseworkItems.add(c);
        writeAllCoursework();
    }
    
    /**
     * Method to write all coursework item to file
     */
    private void writeAllCoursework() {
        ArrayList<String> writeCoursework = new ArrayList<>();
        
        for(CourseworkItem c: allCourseworkItems) {
            String tmp = c.getCourseworkID() + "\t" + c.getCourseworkName() + "\t" + c.getCourseID() + "\t" + c.getCourseworkOverview();
            writeCoursework.add(tmp);
        }
        try {
            writeTextFile(path, writeCoursework);
        } catch (Exception e) {
            // Error writing to disk
            JOptionPane.showMessageDialog(null, "Error while writing to file Coursework.txt.");
        }
    }
    
    /**
     * Method to retrieve all coursework items
     * 
     * @return Coursework items
     */
    public ArrayList<CourseworkItem> getAll() {
        return allCourseworkItems;
    }
    
    /**
     * Method to delete ALL coursework
     */
    public void deleteAllCoursework() {
        File coursesFile = new File(path);
        // Delete Coursework.txt
        coursesFile.delete();
        // Clear ArrayList
        allCourseworkItems.clear();
        // Reset nextCourseworkID
        nextCourseworkID = 0;
    }
}
