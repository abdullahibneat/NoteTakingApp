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
    private String deadlineDate = "";
    private String alertDate = "";
    private boolean showAlert = false;
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
    public CourseworkItem(int crswrkID, String name, int crsID, String deadline, String alert, Boolean alertB, String coursework, ArrayList<String> crswrkRequirements, ArrayList<Boolean> crswrkFulfilled) {
        courseworkID = crswrkID;
        courseworkName = name;
        courseID = crsID;
        deadlineDate = deadline;
        showAlert = alertB;
        alertDate = alert;
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
        // Check the size of the ArrayList (requirements and fulfilled should match)
        // If doesn't match
        if(!(b.size() == courseworkRequirements.size())) {
            // Display warning
            JOptionPane.showMessageDialog(null, "Error saving coursework requirements checkboxes, they have been all set to unchecked.");
            // Add "false" if fulfilled < requirements
            if(b.size() < courseworkRequirements.size()) {
                for (String courseworkRequirement: courseworkRequirements) {
                    requirementsFulfilled.add(false);
                }
            }
            else {
                // Remove items if fulfilled > requirements
                while(b.size() > courseworkRequirements.size()) {
                    b.remove(b.size()-1);
                }
            }
        }
        requirementsFulfilled = b;
    }
    
    /**
     * Method to retrieve ArrayList of fulfilled requirements
     * 
     * @return ArrayList of boolean values
     */
    public ArrayList<Boolean> getRequirementsFulfilled() {
        return requirementsFulfilled;
    }
    
    /**
     * Method to set deadlineDate
     */
    public void setDeadlineDate(String d) {
        deadlineDate = d;
    }
    
    /**
     * Method to retrieve deadlineDate
     * 
     * @return deadlineDate
     */
    public String getDeadlineDate() {
        return deadlineDate;
    }
    
    /**
     * Method to set alertDate date
     * 
     * @param s Alert date
     */
    public void setAlertDate(String s) {
        alertDate = s;
    }
    
    /**
     * Method to retrieve alertDate date
     * 
     * @return Alert date
     */
    public String getAlertDate() {
        return alertDate;
    }
    
    /**
     * Method to set whether to show alertDate or not
     * 
     * @param b boolean
     */
    public void setDisplayAlert(boolean b) {
        showAlert = b;
    }
    
    /**
     * Method to retrieve boolean to show alertDate
     * 
     * @return boolean
     */
    public boolean getDisplayAlert() {
        return showAlert;
    }
}
