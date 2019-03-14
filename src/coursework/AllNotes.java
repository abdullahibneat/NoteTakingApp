package coursework;

import java.io.IOException;
import java.util.ArrayList;

/**
 * AllNotes class
 * It holds all the Note objects within an array.
 *
 * @author Abdullah Ibne Atiq
 */
public class AllNotes extends CommonCode {
    private final ArrayList<Note> allNotes = new ArrayList<>();
    // Unique identifier: store ID the note after will hold
    private int nextNoteID = 0;
    
    /**
     * Constructor
     * 
     */
    public AllNotes() {
        readAllNotes();
    }

    /**
     * Function to read all the notes from the "Notes.txt" file.
     * 
     */
    private void readAllNotes() {
        ArrayList<String> readNotes = new ArrayList<>();
        
        readNotes = readTextFile(appDir + "\\Notes.txt");
        System.out.println(readNotes.get(0));
        
        if("File not found".equals(readNotes.get(0))) {
        } else {
            for(String str: readNotes) {
                String[] tmp = str.split("\t");
                
                Note n = new Note();
                n.setNoteID(Integer.parseInt(tmp[0]));
                n.setCourseID(Integer.parseInt(tmp[1]));
                n.setDayte(tmp[2]);
                n.setNote(tmp[3]);
                
                allNotes.add(n);
                
                if(nextNoteID <= n.getNoteID()) {
                    nextNoteID = n.getNoteID() + 1;
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
        Note myNote = new Note();
        myNote.setNoteID(nextNoteID);
        nextNoteID++;
        myNote.setCourseID(courseID);
        myNote.setDayte();
        myNote.setNote(note);
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
        String path = appDir + "\\Notes.txt";
        ArrayList<String> writeNote = new ArrayList<>();
        
        for(Note n: allNotes) {
            String tmp = n.getNoteID() + "\t";
            tmp += n.getCourseID() + "\t";
            tmp += n.getDayte() + "\t";
            tmp += n.getNote();
            writeNote.add(tmp);
        }
        try {
            writeTextFile(path, writeNote);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }
    }
    
    /**
     * Search method to find a note containing a keyword.
     * 
     * @param s Search keyword
     * @return Full notes containing search keyword
     */
    public String searchNoteByKeyword(String s) {
        String searchOutput = "";
        for(Note n: allNotes) {
            if(n.getNote().toLowerCase().contains(s.toLowerCase())) {
                searchOutput += n.getNote() + "\n";
            }
        }
        if(searchOutput.equals("")) {
            searchOutput = "\"" + s + "\" not found";
        }
        return searchOutput;
    }
}