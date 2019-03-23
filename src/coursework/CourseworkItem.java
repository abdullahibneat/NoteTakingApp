package coursework;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CourseworkItem class
 * It is used to create a requirement for a course module.
 *
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class CourseworkItem {
    private int courseworkID = 0;
    private String courseworkName = "";
    private int courseID = 0;
    private String courseworkOverview = "";
    private ArrayList<String> courseworkRequirements;
    private ArrayList<Boolean> requirementsFulfilled;
    
    /**
     * Constructor
     * 
     * @param crswrkID Coursework ID
     * @param name Coursework name
     * @param crsID Course ID
     * @param coursework Coursework overview
     * @param crswrkRequirements Requirements
     * @param crswrkFulfilled Requirements fulfilled
     */
    public CourseworkItem(int crswrkID, String name, int crsID, String coursework, ArrayList<String> crswrkRequirements, ArrayList<Boolean> crswrkFulfilled) {
        courseworkID = crswrkID;
        courseworkName = name;
        courseID = crsID;
        courseworkOverview = coursework;
        courseworkRequirements = crswrkRequirements;
        requirementsFulfilled = crswrkFulfilled;
    }
    
    /**
     * WARNING: This should only be used from within AllCoursework class.
     * Method to set coursework ID
     * 
     * @param n Coursework ID
     */
    public void setCourseworkID(int n) {
        courseworkID = n;
    }
    
    /**
     * Method to retrieve coursework ID
     * 
     * @return Coursework ID
     */
    public int getCourseworkID() {
        return courseworkID;
    }
    
    /**
     * Method to set the name for the coursework
     * 
     * @param s Coursework name
     */
    public void setCourseworkName(String s) {
        courseworkName = s;
    }
    
    /**
     * Method to retrieve name of coursework
     * 
     * @return Coursework name
     */
    public String getCourseworkName() {
        return courseworkName;
    }
    
    /**
     * Method to set course ID for this coursework
     * 
     * @param n Course ID
     */
    public void setCourseID(int n) {
        courseID = n;
    }
    
    /**
     * Method to retrieve the course ID
     * 
     * @return Course ID
     */
    public int getCourseID() {
        return courseID;
    }
    
    /**
     * Method to set the content for the coursework
     * 
     * @param s Coursework content
     */
    public void setCourseworkOverview(String s) {
        courseworkOverview = s;
    }
    
    /**
     * Method to retrieve coursework contents.
     * 
     * @return Coursework contents
     */
    public String getCourseworkOverview() {
        return courseworkOverview;
    }
    
    /**
     * Method to set the list of courseworkRequirements
     * 
     * @param r ArrayList of courseworkRequirements
     */
    public void setCourseworkRequirements(ArrayList<String> r) {
        courseworkRequirements = r;
    }
    
    /**
     * Method to retrieve the list of courseworkRequirements
     * 
     * @return List of courseworkRequirements
     */
    public ArrayList<String> getCourseworkRequirements() {
        return courseworkRequirements;
    }
    
    /**
     * Method to set the ArrayList of booleans about whether a requirement has been completed or not.
     * 
     * @param b ArrayList of type boolean
    */
    public void setRequirementsFulfilled(ArrayList<Boolean> b) {
        if(b.size() == courseworkRequirements.size()) {
            requirementsFulfilled = b;
        }
        else {
            JOptionPane.showMessageDialog(null, "Error saving coursework requirements checkboxes");
            for (int i = 0; i < courseworkRequirements.size(); i++) {
                requirementsFulfilled.add(false);
            }
        }
    }
    
    /**
     * Method to retrieve ArrayList of fulfilled requirements
     * 
     * @return ArrayList of boolean values
     */
    public ArrayList<Boolean> getRequirementsFulfilled() {
        return requirementsFulfilled;
    }
}
