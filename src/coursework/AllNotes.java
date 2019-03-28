package coursework;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * AllNotes class
 * It holds all the Note objects within an array.
 *
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class AllNotes extends CommonCode {
    private final ArrayList<Note> allNotes = new ArrayList<>();
    // Unique identifier: store ID the note after will hold
    private int nextNoteID = 0;
    // Path to file
    public final String path = appDir + fileSeparator + "Notes.txt";
    
    /**
     * Constructor
     */
    public AllNotes() {
        readAllNotes();
    }
    
    /**
     * Method to increment nextNoteID by 1
     * 
     * @return next note ID
     */
    private int nextNoteID() {
        int id = nextNoteID;
        nextNoteID++;
        return id;
    }

    /**
     * Function to read all the notes from the "Notes.txt" file.
     */
    private void readAllNotes() {
        ArrayList<String> readNotes = readTextFile(path);
        
        if("File not found".equals(readNotes.get(0))) {
        } else {
            for(String str: readNotes) {
                // tmp should look like {Integer noteID, Integer courseID, String date, String note}
                String[] tmp = str.split("\t");
                try {
                    Note n = new Note(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), tmp[2], tmp[3]);
                    allNotes.add(n);
                    if(nextNoteID <= n.getNoteID()) {
                        nextNoteID = n.getNoteID() + 1;
                    }
                } catch(Exception e) {
                    // If any error occurs (e.g. NumberFormatException, IndexOutOfBounds)
                    JOptionPane.showMessageDialog(null, "Error while parsing Notes.txt file.");
                    return;
                }
            }
        }
    }
    
    /**
     * Function to add a new note to the ArrayList and store it permanently inside the "Notes.txt" file.
     * NoteID is set automatically.
     * 
     * To get a course ID based on course name, refer to:
     * @see coursework.AllCourses#toCourseID(java.lang.String)
     *
     * @param courseID CourseID
     * @param note Note contents
     */    
    public void addNote(int courseID, String note) {
        Note myNote = new Note(nextNoteID(), courseID, note);
        allNotes.add(myNote);
        writeAllNotes();    
    }
    
    /**
     * Get all the notes as an ArrayList.
     *
     * @return Notes
     */
    public ArrayList<Note> getAllNotes() {
        return allNotes;
    }

    /**
     * Function to write notes to disk.
     * 
     */
    private void writeAllNotes() {
        ArrayList<String> writeNote = new ArrayList<>();
        
        for(Note n: allNotes) {
            // Format to write: noteID(tab)courseID(tab)date(tab)note
            String tmp = n.getNoteID() + "\t" + n.getCourseID() + "\t" + n.getDayte() + "\t" + n.getNote();
            writeNote.add(tmp);
        }
        try {
            writeTextFile(path, writeNote);
        } catch (Exception e) {
            // Error writing to disk
            JOptionPane.showMessageDialog(null, "Error while writing to file Course.txt.");
        }
    }
    
    /**
     * Search method to find a note containing a keyword.
     * 
     * @param s Search keyword
     * @return ArrayList of String of all notes containing search keyword.
     */
    public ArrayList<String> searchNoteByKeyword(String s) {
        ArrayList<String> output = new ArrayList<>();
        for(Note n: allNotes) {
            if(n.getNote().toLowerCase().contains(s.toLowerCase())) {
                output.add(n.getNote());
            }
        }
        if(output.isEmpty()) {
            output.add(s + " not found.");
        }
        return output;
    }
    
    /**
     * Method to edit the contents of a note given it's ID.
     * 
     * @param id Note ID
     * @param s New note text
     */
    public void editNote(int id, String s) {
        for(Note n: allNotes) {
            if(n.getNoteID() == id) {
                n.setNote(s);
                writeAllNotes();
                return;
            }
        }
    }
    
    /**
     * Method to delete ALL notes
     */
    public void deleteAllNotes() {
        File coursesFile = new File(path);
        // Delete file
        coursesFile.delete();
        // Clear ArrayList
        allNotes.clear();
        // Reset nextNoteID
        nextNoteID = 0;
    }
    
    /**
     * Method to delete a specific note
     * 
     * @param id NoteID to delete
     */
    public void deleteNote(int id) {
        for (int i = 0; i < allNotes.size(); i++) {
            if(allNotes.get(i).getNoteID() == id) {
                allNotes.remove(i);
                writeAllNotes();
                return;
            }
        }
    }
    
    /**
     * Method to find occurrences of word in a note.
     * 
     * @param s Word to be searched
     * @return Number of occurrences
     */
    public Integer wordOccurrence(String s) {
        return wordOccurrence(s.toLowerCase(), 0, 0);
    }
    
    /**
     * Method to find the number of occurrences of a word in note using recursion
     * 
     * @param s Word to be searched
     * @param count Number of occurrences
     * @param i Index of note to start searching from
     * @return Number of occurrences
     */
    private Integer wordOccurrence(String s, int count, int i) {
        if(i >= allNotes.size()) {
            return count;
        }
        // Split the string when search word is found.
        // Add space at the end of the note so that if keywork is at the end it still gets counted.
        // -1 because, for example, in string "ABCBD", splitting at "B" produces: "A", "C", "D", 1 more than the actual occurrences.
        count += (allNotes.get(i).getNote().toLowerCase()+" ").split(s).length -1;
        return wordOccurrence(s, count, i+1);
    }
    
    /**
     * Method to find the course(s) with most notes.
     * 
     * @return ArrayList of String as follows: [Course ID(s), count]
     * (if multiple courseIDs have the most notes, they will be separated by commas)
     */
    public ArrayList<String> courseWithMostNotes() {
        return courseWithMostNotes("", 0);
    }
    
    /**
     * Method to find the course(s) with the most notes
     * 
     * @param allCourses String containing all courses found in notes
     * @param i Index of note to start searching from
     * @return ArrayList of String as follows: {Course ID(s), count}
     * (if multiple courseIDs have the most notes, they will be separated by commas)
     */
    private ArrayList<String> courseWithMostNotes(String allCourses, int i) {
        if(i >= allNotes.size()) {
            // Array of all courses found so far
            String[] tmp = allCourses.split(" ");
            // Set, removes duplicate courses
            Set<String> courseSet = new HashSet<>(Arrays.asList(tmp));
            
            // Store current maximum note count and corresponding courseID
            int highestCount = 0;
            String highestCourseID = tmp[0];
            
            // For each course in set
            for(String course: courseSet) {
                int count = 0;
                // For each course found in all notes
                for(String tmpCourse: tmp) {
                    // Increase count if courseID matches
                    if(tmpCourse.equals(course)) {
                        count++;
                    }
                }
                // If count is same as previous count, both courseIDs have same number of notes
                if (count == highestCount) {
                    highestCourseID += "," + course;
                }
                // If this courseID's count is higher than before, set highest as this course
                else if(count > highestCount) {
                    highestCount = count;
                    highestCourseID = course;
                }
            }
            
            // Create ArrayList for output, format {courseID(s), count}, return output
            ArrayList<String> output = new ArrayList<>();
            output.add(highestCourseID);
            output.add(Integer.toString(highestCount));
            return output;
        }
        
        // Get the courseID for this note
        allCourses += allNotes.get(i).getCourseID() + " ";
        return courseWithMostNotes(allCourses, i+1);
    }
    
    /**
     * Method to find the date in which the most notes were written
     * 
     * @return ArrayList of String as follows: [Date(s), count]
     * (if multiple dates have the most notes, they will be separated by commas)
     */
    public ArrayList<String> dateMostNotesWereWritten() {
        return dateMostNotesWereWritten("", 0);
    }
    
    /**
     * Method to find the date in which the most notes were written
     * 
     * @param allDates String containing all dates found in notes
     * @param i Index of note to start searching from
     * @return ArrayList of String as follows: [Date(s), count]
     * (if multiple dates have the most notes, they will be separated by commas)
     */
    private ArrayList<String> dateMostNotesWereWritten(String allDates, int i) {
        if(i >= allNotes.size()) {
            // Array of all dates found so far
            String[] tmp = allDates.split(" ");
            // Set, removes duplicate dates
            Set<String> datesSet = new HashSet<>(Arrays.asList(tmp));
            
            // Store current maximum date count and corresponding date
            int highestCount = 0;
            String highestDate = tmp[0];
            
            // For each date in set
            for(String date: datesSet) {
                int count = 0;
                // For each date found in all notes
                for(String tmpDate: tmp) {
                    // Increase count if date matches
                    if(tmpDate.equals(date)) {
                        count++;
                    }
                }
                // If count is same as previous count, both dates have same number of notes written
                if (count == highestCount) {
                    highestDate += "," + date;
                }
                // If this date's count is higher than before, set highest as this date
                else if(count > highestCount) {
                    highestCount = count;
                    highestDate = date;
                }
            }
            
            // Create ArrayList for output, format {courseID(s), count}, return output
            ArrayList<String> output = new ArrayList<>();
            output.add(highestDate);
            output.add(Integer.toString(highestCount));
            return output;
        }
        allDates += allNotes.get(i).getDayte().split(" ")[0] + " ";
        return dateMostNotesWereWritten(allDates, i+1);
    }
}