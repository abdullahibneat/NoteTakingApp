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
    
    /**
     * Constructor
     */
    public AllCoursework() {
        readAllCourseworkItems();
    }
    
    /**
     * Method to read all coursework items from file
     */
    private void readAllCourseworkItems() {
        ArrayList<String> readCoursework = readTextFile(appDir + "\\Coursework.txt");
        
        if("File not found".equals(readCoursework.get(0))) {
        } else {
            for(String str: readCoursework) {
                // tmp should look like: {Integer cID, String cName, Integer courseID, String cOverview}
                String[] tmp = str.split("\t");
                
                CourseworkItem c = new CourseworkItem();
                try {
                    c.setCourseworkID(Integer.parseInt(tmp[0]));
                    c.setCourseworkName(tmp[1]);
                    c.setCourseID(Integer.parseInt(tmp[2]));
                    c.setCourseworkOverview(tmp[3]);

                    allCourseworkItems.add(c);
                } catch(Exception e) {
                    // If any error occurs (e.g. NumberFormatException, IndexOutOfBounds)
                    JOptionPane.showMessageDialog(null, "Error while parsing Coursework.txt file.");
                    return;
                }
                
                if(nextCourseworkID <= c.getCourseworkID()) {
                    nextCourseworkID = c.getCourseworkID() + 1;
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
        CourseworkItem c = new CourseworkItem();
        c.setCourseID(courseID);
        c.setCourseworkID(nextCourseworkID);
        nextCourseworkID++;
        c.setCourseworkName(name);
        c.setCourseworkOverview(overview);
        
        allCourseworkItems.add(c);
        writeAllCoursework();
    }
    
    /**
     * Method to write all coursework item to file
     */
    private void writeAllCoursework() {
        String path = appDir + "\\Coursework.txt";
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
        File coursesFile = new File(appDir + "\\Coursework.txt");
        // Delete Coursework.txt
        coursesFile.delete();
        // Clear ArrayList
        allCourseworkItems.clear();
        // Reset nextCourseworkID
        nextCourseworkID = 0;
    }
}
