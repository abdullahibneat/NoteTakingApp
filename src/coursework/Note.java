package coursework;

/**
 * Note class
 * It is used to create a Note object with an ID, course, date and name.
 *
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class Note extends CommonCode {
    private int noteID = 0;
    private int courseID = 0;
    private String dayte = "";
    private String note = "";
    
    /**
     * Constructor (with automatic date)
     * 
     * @param nid Note ID
     * @param cid Course ID
     * @param n Note
     */
    public Note(int nid, int cid, String n) {
        noteID = nid;
        courseID = cid;
        dayte = ukDateAndTime;
        note = n;
    }
    
    /**
     * WARNING: Only to be used for importing notes.
     * Constructor (with custom date)
     * 
     * @param nid Note ID
     * @param cid Course ID
     * @param d Date
     * @param n Note
     */
    public Note(int nid, int cid, String d, String n) {
        noteID = nid;
        courseID = cid;
        dayte = d;
        note = n;
    }
    
    /**
     * Set the ID for this note.
     * 
     * @param n Note ID
     */
    public void setNoteID(int n) {
        int nid = n;
        noteID = nid;
    }
    
    /**
     * Get the ID of this note.
     * 
     * @return Note ID
     */
    public int getNoteID() {
        return noteID;
    }
    
    /**
     * Set the course for the note.
     * 
     * @param id Course ID
     */
    public void setCourseID(int id) {
        courseID = id;
    }
    
    /**
     * Return the course of this note.
     * 
     * @return Course
     */
    public int getCourseID() {
        return courseID;
    }
    
    /**
     * Set the current date and time for this note.
     */
    public void setDayte() {
        dayte = ukDateAndTime;
    }
    
    /**
     * IMPORTANT:
     * Should only be used to import notes.
     * For new notes, use without parameter.
     * 
     * Set a custom date for this note. 
     * 
     * @param d
     */
    public void setDayte(String d) {
        dayte = d;
    }
    
    /**
     * Get the date of this note.
     * 
     * @return Date
     */
    public String getDayte() {
        return dayte;
    }
    
    /**
     * Set the text of this note.
     *
     * @param n Note contents
     */
    public void setNote(String n) {
        note = n;
    }
    
    /**
     * Get the contents of this note.
     *
     * @return Note contents
     */
    public String getNote() {
        return note;
    }
}