package coursework;

/**
 * Course class
 * It is used to create a Course object with a course name.
 *
 * @author Abdullah Ibne Atiq
 */
public class Course {
    private String courseName;
    private int courseID;
    
    /**
     * Constructor
     */
    public Course() {
        
    }
    
    /**
     * Set the name of the course
     *
     * @param c Course name
     */
    public void setCourseName(String c) {
        String crse = c;
        
        courseName = crse;
    }
    
    /**
     * Get the name of the course
     *
     * @return Course name
     */
    public String getCourseName() {
        return courseName;
    }
    
    /**
     * Set the Course ID
     * 
     * @param id Course ID
     */
    public void setCourseID(int id) {
        courseID = id;
    }
    
    /**
     * Get the course ID
     * 
     * @return Course ID
     */
    public int getCourseID() {
        return courseID;
    }
}