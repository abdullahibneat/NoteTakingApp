package coursework;

/**
 * Note class
 * It is used to create a Note object with an ID, course, date and name.
 *
 * @author Abdullah Ibne Atiq
 */
public class Note extends CommonCode {
    private int noteID = 0;
    private String course = "";
    private String dayte = "";
    private String note = "";
    
    /**
     * Constructor
     * 
     */
    public Note() {
        
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
     * @param c
     */
    public void setCourse(String c) {
        String crse = c;
        course = c;
    }
    
    /**
     * Return the course of this note.
     * @return Course
     */
    public String getCourse() {
        return course;
    }
    
    /**
     * Set the current date and time for this note.
     */
    public void setDayte() {
        dayte = ukDateAndTime;
    }
    
    /**
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