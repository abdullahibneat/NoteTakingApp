package coursework;

/**
 * Course class
 * It is used to create a Course object with a course name.
 *
 * @author Abdullah Ibne Atiq
 */
public class Course {
    private String courseName;
    
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
}