/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Abdullah Ibne Atiq
 */
public class AllCourses extends CommonCode {
    private final ArrayList<Course> allCourses = new ArrayList<>();
    
    public AllCourses() {
        readAllCourses();
    }

    private void readAllCourses() {
        ArrayList<String> readCourses = new ArrayList<>();
        
        readCourses = readTextFile(appDir + "\\Courses.txt");
        System.out.println(readCourses.get(0));

        if("File Not Found".equals(readCourses.get(0))){
        } else {
            for(String str: readCourses){                
                Course c = new Course();
                c.setCourseName(str);
                allCourses.add(c);
            }
        }
    }
    
    public void addCourse(String course) {
        Course c = new Course();
        c.setCourseName(course);
        allCourses.add(c);
        writeAllCourses();
    }
    
    public ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    private void writeAllCourses() {
        String path = appDir + "\\Courses.txt";
        ArrayList<String> writeCourse = new ArrayList<>();
        
        for(Course c: allCourses) {
            String tmp = c.getCourseName();
            writeCourse.add(tmp);
        }
        try {
            writeTextFile(path, writeCourse);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }
    }
}