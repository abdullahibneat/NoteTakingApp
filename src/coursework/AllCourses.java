package coursework;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * AllCourses class
 * It holds all the Course objects within an array.
 *
 * @author Abdullah Ibne Atiq
 */
public class AllCourses extends CommonCode {    
    private final ArrayList<Course> allCourses = new ArrayList<>();
    // Unique identifier: store the ID the course after will hold
    private int nextCourseID = 0;
    
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
        ArrayList<String> readCourses = new ArrayList<>();
        
        readCourses = readTextFile(appDir + "\\Courses.txt");
        System.out.println(readCourses.get(0));

        if("File not found".equals(readCourses.get(0))){
        } else {
            for(String str: readCourses){
                String[] tmp = str.split("\t");
                Course c = new Course();
                c.setCourseID(Integer.parseInt(tmp[0]));
                c.setCourseName(tmp[1]);
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
        String path = appDir + "\\Courses.txt";
        ArrayList<String> writeCourse = new ArrayList<>();
        
        for(Course c: allCourses) {
            String tmp = c.getCourseID() + "\t";
            tmp += c.getCourseName();
            writeCourse.add(tmp);
        }
        try {
            writeTextFile(path, writeCourse);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }
    }
    
    public int toCourseID(String courseName) {
        for(Course c: allCourses) {
            if(c.getCourseName().equalsIgnoreCase(courseName)) {
                return c.getCourseID();
            }
        }
        JOptionPane.showMessageDialog(null, "Course " + courseName + " not found...");
        
        return -1;
    }
}