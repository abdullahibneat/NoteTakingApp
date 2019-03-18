/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Abdullah Ibne Atiq
 */
public class AllCoursework extends CommonCode {
    private final ArrayList<CourseworkItem> allCourseworkItems = new ArrayList<>();
    // Unique identifier: store ID the courseworkItem after will hold
    private int nextCourseworkID = 0;
    
    /**
     * Constructor
     */
    public AllCoursework() {
        readAllCourseworkItems();
    }
    
    /**
     * Method to read all coursework items from file
     */
    private void readAllCourseworkItems() {
        ArrayList<String> readCoursework = new ArrayList<>();
        
        readCoursework = readTextFile(appDir + "\\Coursework.txt");
        System.out.println(readCoursework.get(0));
        
        if("File not found".equals(readCoursework.get(0))) {
        } else {
            for(String str: readCoursework) {
                String[] tmp = str.split("\t");
                System.out.println(tmp);
                
                CourseworkItem c = new CourseworkItem();
                c.setCourseworkID(Integer.parseInt(tmp[0]));
                c.setCourseworkName(tmp[1]);
                c.setCourseID(Integer.parseInt(tmp[2]));
                c.setCourseworkOverview(tmp[3]);
                
                allCourseworkItems.add(c);
                
                if(nextCourseworkID <= c.getCourseworkID()) {
                    nextCourseworkID = c.getCourseworkID() + 1;
                }
            }
        }
    }
    
    /**
     * Method to add a new coursework item
     * 
     * @param courseID Course ID
     * @param name Course name
     * @param overview Course description
     */
    public void addNewCoursework(int courseID, String name, String overview) {
        CourseworkItem c = new CourseworkItem();
        c.setCourseID(courseID);
        c.setCourseworkID(nextCourseworkID);
        nextCourseworkID++;
        c.setCourseworkName(name);
        c.setCourseworkOverview(overview);
        
        allCourseworkItems.add(c);
        writeAllCoursework();
    }
    
    /**
     * Method to write all coursework item to file
     */
    private void writeAllCoursework() {
        String path = appDir + "\\Coursework.txt";
        ArrayList<String> writeCoursework = new ArrayList<>();
        
        for(CourseworkItem c: allCourseworkItems) {
            String tmp = c.getCourseworkID() + "\t";
            tmp += c.getCourseworkName() + "\t";
            tmp += c.getCourseID() + "\t";
            tmp += c.getCourseworkOverview();
            writeCoursework.add(tmp);
        }
        try {
            writeTextFile(path, writeCoursework);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }
    }
    
    /**
     * Method to retrieve all coursework items
     * 
     * @return Coursework items
     */
    public ArrayList<CourseworkItem> getAll() {
        return allCourseworkItems;
    }
    
    /**
     * Method to delete ALL coursework
     */
    public void deleteAllCoursework() {
        File coursesFile = new File(appDir + "\\Coursework.txt");
        coursesFile.delete();
        allCourseworkItems.clear();
        nextCourseworkID = 0;
    }
}
