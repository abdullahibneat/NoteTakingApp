package coursework;

import java.util.ArrayList;

/**
 * CourseworkItem class
 * It is used to create a requirement for a course module.
 *
 * @author Abdullah
 */
public class CourseworkItem {
    private int courseworkID = 0;
    private String courseworkName = "";
    private int courseID = 0;
    private String courseworkOverview = "";
    
    /**
     * Constructor
     */
    public CourseworkItem() {
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
}
