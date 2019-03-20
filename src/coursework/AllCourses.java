package coursework;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * AllCourses class
 * It holds all the Course objects within an array.
 *
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class AllCourses extends CommonCode {    
    private final ArrayList<Course> allCourses = new ArrayList<>();
    // Unique identifier: store the ID the course after will hold
    private int nextCourseID = 0;
    // Path to file
    public final String path = appDir + fileSeparator + "Courses.txt";
    
    /**
     * Constructor
     */
    public AllCourses() {
        readAllCourses();
    }

    /**
     * Function to read all the courses from the "Course.txt" file.
     */
    private void readAllCourses() {
        ArrayList<String> readCourses = readTextFile(path);

        if("File not found".equals(readCourses.get(0))){
        } else {
            for(String str: readCourses){
                // tmp should look like {Integer courseID, String courseName}
                String[] tmp = str.split("\t");
                Course c = new Course();
                try {
                    c.setCourseID(Integer.parseInt(tmp[0]));
                    c.setCourseName(tmp[1]);
                } catch(Exception e) {
                    // If any error occurs (e.g. NumberFormatException, IndexOutOfBounds)
                    JOptionPane.showMessageDialog(null, "Error while parsing Courses.txt file.");
                    return;
                }
                allCourses.add(c);
                
                if(nextCourseID <= c.getCourseID()) {
                    nextCourseID = c.getCourseID() + 1;
                }
            }
        }
    }
    
    /**
     * WARNING:
     * Prefer to use addCourse(String courseName)
     * This function might create duplicate IDs.
     * 
     * Function to add a new course to the ArrayList and store it permanently inside the "Courses.txt" file.
     *
     * @param courseID ID of course
     * @param course
     */
    public void addCourse(int courseID, String course) {
        Course c = new Course();
        c.setCourseName(course);
        c.setCourseID(courseID);
        allCourses.add(c);
        writeAllCourses();
    }
    
    /**
     * Function to add a new course to the ArrayList and store it permanently inside the "Courses.txt" file.
     *
     * @param course
     */
    public void addCourse(String course) {
        Course c = new Course();
        c.setCourseName(course);
        c.setCourseID(nextCourseID);
        nextCourseID++;
        allCourses.add(c);
        writeAllCourses();
    }
    
    /**
     * Get all the courses as an ArrayList.
     *
     * @return
     */
    public ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    /**
     * Function to write courses to disk.
     */
    private void writeAllCourses() {
        ArrayList<String> writeCourse = new ArrayList<>();
        
        for(Course c: allCourses) {
            // Format to write: courseID(tab)courseName
            writeCourse.add(c.getCourseID() + "\t" + c.getCourseName());
        }
        try {
            writeTextFile(path, writeCourse);
        } catch (Exception e) {
            // Error writing to disk
            JOptionPane.showMessageDialog(null, "Error while writing to file Course.txt.");
        }
    }
    
    /**
     * Method to convert a course name to course ID
     * 
     * @param courseName Name of course
     * @return Course ID
     */
    public int toCourseID(String courseName) {
        for(Course c: allCourses) {
            if(c.getCourseName().equalsIgnoreCase(courseName)) {
                return c.getCourseID();
            }
        }
        // Course name not found
        JOptionPane.showMessageDialog(null, "Course " + courseName + " not found...");        
        return -1;
    }
    
    /**
     * Method to convert course name into course ID
     * 
     * @param n Course ID
     * @return Name of course
     */
    public String toCourseName(int n) {
        for(Course c: allCourses) {
            if(c.getCourseID() == n) {
                return c.getCourseName();
            }
        }
        // CourseID not found
        return "Course not found.";
    }
    
    /**
     * Method to change the name of a course.
     * 
     * @param id ID of course to be changed
     * @param s New course name
     */
    public void editCourseName(int id, String s) {
        for(Course c: allCourses) {
            if(c.getCourseID() == id) {
                c.setCourseName(s);
                writeAllCourses();
            }
        }
    }
    
    /**
     * Method to check if a course name already exists
     * 
     * @param s Course name
     * @return true/false
     */
    public boolean exists(String s) {
        for(Course c: allCourses) {
            if(c.getCourseName().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method to delete ALL courses
     */
    public void deleteAllCourses() {
        File coursesFile = new File(path);
        // Delete Courses.txt
        coursesFile.delete();
        // Clear ArrayList
        allCourses.clear();
        // Reset nextCourseID
        nextCourseID = 0;
    }
}