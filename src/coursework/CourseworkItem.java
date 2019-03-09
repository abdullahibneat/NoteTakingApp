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
    
    public CourseworkItem() {
    }
    
    public void setCourseworkID(int n) {
        courseworkID = n;
    }
    
    public int getCourseworkID() {
        return courseworkID;
    }
    
    public void setCourseworkName(String s) {
        courseworkName = s;
    }
    
    public String getCourseworkName() {
        return courseworkName;
    }
    
    public void setCourseID(int n) {
        courseID = n;
    }
    
    public int getCourseID() {
        return courseID;
    }
    
    public void setCourseworkOverview(String s) {
        courseworkOverview = s;
    }
    
    public String getCourseworkOverview() {
        return courseworkOverview;
    }
}
