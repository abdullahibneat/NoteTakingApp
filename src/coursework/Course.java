package coursework;

/**
 * Course class
 * It is used to create a Course object with a course name.
 *
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class Course {
    private String courseName;
    private int courseID;
    
    /**
     * Constructor
     * 
     * @param c Course name
     * @param id Course ID
     */
    public Course(int id, String c) {
        courseID = id;
        courseName = c;
    }
    
    /**
     * Set the name of the course
     *
     * @param c Course name
     */
    public void setCourseName(String c) {
        courseName = c;
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