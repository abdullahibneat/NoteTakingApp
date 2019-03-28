package coursework;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
                    ArrayList<String> requirements = new ArrayList<>(Arrays.asList(tmp[7].split("%&")));
                    ArrayList<Boolean> fulfilled = new ArrayList<>();
                    for(String s: tmp[8].split("%&")) {
                        fulfilled.add(Boolean.parseBoolean(s));
                    }
                    CourseworkItem c = new CourseworkItem(Integer.parseInt(tmp[0]), tmp[1], Integer.parseInt(tmp[2]), tmp[3], tmp[4], Boolean.parseBoolean(tmp[5]), tmp[6], requirements, fulfilled);
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
     * @param deadline Deadline date
     * @param alert Alert date
     * @param showAlert Is alert to be displayed?
     * @param overview Course description
     * @param requirements List of requirements
     * @param fulfilled Requirements fulfilled values
     */
    public void addNewCoursework(int courseID, String name, String deadline, String alert, boolean showAlert, String overview, ArrayList<String> requirements, ArrayList<Boolean> fulfilled) {
        CourseworkItem c = new CourseworkItem(nextCourseworkID(), name, courseID, deadline, alert, showAlert, overview, requirements, fulfilled);
        allCourseworkItems.add(c);
        writeAllCoursework();
    }
    
    /**
     * Method to write all coursework item to file
     */
    private void writeAllCoursework() {
        ArrayList<String> writeCoursework = new ArrayList<>();
        
        for(CourseworkItem c: allCourseworkItems) {
            // Requirements will need to be split and stored as string
            ArrayList<String> requirements = c.getCourseworkRequirements();
            String requirementsString = "";
            for(String s: requirements) {
                requirementsString += s + "%&";
            }
            if(requirementsString.equals("")) requirementsString += "%&";
            // Checkbox values also need to be split and stored as string
            ArrayList<Boolean> fulfilled = c.getRequirementsFulfilled();
            String fulfilledString = "";
            for(Boolean b: fulfilled) {
                fulfilledString += b + "%&";
            }
            if(fulfilledString.equals("")) fulfilledString += "%&";
            String tmp = c.getCourseworkID() + "\t" + c.getCourseworkName() + "\t" + c.getCourseID() + "\t" + c.getDeadlineDate() + "\t" + c.getAlertDate() + "\t" + c.getDisplayAlert() + "\t" + c.getCourseworkOverview() + "\t" + requirementsString + "\t" + fulfilledString;
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
    
    /**
     * Method to edit a coursework
     * 
     * @param crswrkID Coursework ID
     * @param crswrkName Coursework Name
     * @param deadline Deadline date
     * @param alert Alert date
     * @param showAlert Show alert?
     * @param crswrkOverview Coursework Overview
     * @param requirements Requirements
     */
    public void editCoursework(int crswrkID, String crswrkName, String deadline, String alert, boolean showAlert, String crswrkOverview, ArrayList<String> requirements) {
        for(CourseworkItem c: allCourseworkItems) {
            // If CourseworkItem matches CourseworkID
            if(c.getCourseworkID() == crswrkID) {
                // Update all the variables
                c.setCourseworkName(crswrkName);
                c.setDeadlineDate(deadline);
                c.setAlertDate(alert);
                c.setDisplayAlert(showAlert);
                c.setCourseworkOverview(crswrkOverview);
                c.setCourseworkRequirements(requirements);
                // Create a new ArrayList for fulfilled status, all set to false
                ArrayList<Boolean> fulfilled = new ArrayList<>();
                for (String requirement : requirements) {
                    fulfilled.add(false);
                }
                c.setRequirementsFulfilled(fulfilled);
                // Write to disk
                writeAllCoursework();
                return;
            }
        }
    }
    
    /**
     * Method to update the status of requirements.
     * 
     * @param courseworkID Coursework ID
     * @param b ArrayList of boolean
     */
    public void setRequirementsFulfilled(int courseworkID, ArrayList<Boolean> b) {
        for(CourseworkItem c: allCourseworkItems) {
            // If CourseworkID mathces
            if(c.getCourseworkID() == courseworkID) {
                // Set new fulfilled ArrayList
                c.setRequirementsFulfilled(b);
                // Write to disk
                writeAllCoursework();
                return;
            }
        }
    }
    
    /**
     * Method to delete a CourseworkItem
     * 
     * @param id Coursework ID
    */
    public void deleteCoursework(int id) {
        for (int i = 0; i < allCourseworkItems.size(); i++) {
            // If CourseworkID matches
            if(allCourseworkItems.get(i).getCourseworkID() == id) {
                // Remove the CourseworkItem object
                allCourseworkItems.remove(i);
                // Apply changes to file
                writeAllCoursework();
                return;
            }
        }
    }
}
