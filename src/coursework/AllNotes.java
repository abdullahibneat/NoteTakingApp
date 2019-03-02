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
    private ArrayList<Note> allNotes = new ArrayList<>();
    private String crse = "";
    
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
                n.setCourse(tmp[1]);
                n.setDayte(tmp[2]);
                n.setNote(tmp[3]);
                
                allNotes.add(n);
            }
        }
    }
    
    /**
     * Function to add a new note to the ArrayList and store it permanently inside the "Notes.txt" file.
     * NoteID is set automatically.
     *
     * @param course Course
     * @param note Note contents
     */
    public void addNote(String course, String note) {
        Note myNote = new Note();
        myNote.setNoteID(allNotes.size() + 1);
        myNote.setCourse(course);
        myNote.setDayte();
        myNote.setNote(note);
        allNotes.add(myNote);
        writeAllNotes();
    }
    
    /**
     * Not recommended
     * Function to add a new note to the ArrayList and store it permanently inside the "Notes.txt" file.
     * Note ID is defined manually.
     *
     * @param NoteID Note ID
     * @param course Course
     * @param note Note contents
     */
    public void addNote(int NoteID, String course, String note) {
        Note myNote = new Note();
        myNote.setNoteID(NoteID);
        myNote.setCourse(course);
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
            tmp += n.getCourse() + "\t";
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
}