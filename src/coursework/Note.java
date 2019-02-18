package coursework;

public class Note {
    private int noteID = 0;
    private String course = "";
    private String dayte = "";
    private String note = "";
    private CommonCode cc = new CommonCode();
    
    public Note() {
        
    }
    
    public void setNoteID(int n) {
        int nid = n;
        noteID = nid;
    }
    
    public int getNoteID() {
        return noteID;
    }
    
    public void setCourse(String c) {
        String crse = c;
        course = c;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setDayte() {
        dayte = cc.orderedDate;
    }
    
    public void setDayte(String d) {
        dayte = d;
    }
    
    public String getDayte() {
        return dayte;
    }
    
    public void setNote(String n) {
        note = n;
    }
    
    public String getNote() {
        return note;
    }
}