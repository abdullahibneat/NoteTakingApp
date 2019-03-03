/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

/**
 *
 * @author Abdullah Ibne Atiq
 */
public class Course {
    private String courseName;
    
    public Course() {
        
    }
    
    public void setCourseName(String c) {
        String crse = c;
        
        courseName = crse;
    }
    
    public String getCourseName() {
        return courseName;
    }
}