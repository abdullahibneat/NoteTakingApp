package coursework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * Main program
 *
 * @author Abdullah Ibne Atiq
 */
public class Coursework extends JFrame implements ActionListener, KeyListener {

    private final Font fnt = new Font("Georgia", Font.PLAIN, 24);
    // Note input
    private final JTextArea txtNewNote = new JTextArea();
    // Note output
    private final JTextArea txtDisplaynotes = new JTextArea();
    // Add <String> to fix raw type warning
    private final JComboBox<String> courseList = new JComboBox<>();
    private String crse = "";
    private final AllNotes allNotes = new AllNotes();
    private final CommonCode cc = new CommonCode(this);
    private final AllCourses allCourses = new AllCourses();
    // Search-related components
    private final Search search = new Search();
    private final JTextField searchBox = new JTextField();
    private final JTextArea text = new JTextArea();

    /**
     * Main class
     *
     * @param args
     */
    public static void main(String[] args) {
        Coursework prg = new Coursework();
    }

    /**
     * Constructor Use MVC
     */
    private Coursework() {
        model();
        view();
        controller();
    }

    /**
     * Model
     *
     */
    private void model() {        
        // Create note instances
        // ONLY REQUIRED ON FIRST TIME RUNNING THIS PROGRAM
//        Note nt = new Note();
//        
//        nt.setCourseID(0);
//        nt.setNote("Arrays are of fixed length and are inflexible.");
//        allNotes.addNote(nt.getCourseID(), nt.getNote());
//        
//        nt.setCourseID(1);
//        nt.setNote("ArrayList can be added to and items can be deleted.");
//        allNotes.addNote(nt.getCourseID(), nt.getNote());
//        
//        Course c = new Course();
//        
//        c.setCourseName("COMP1752");
//        allCourses.addCourse(c.getCourseName());
//        
//        c.setCourseName("COMP1753");
//        allCourses.addCourse(c.getCourseName());
    }

    /**
     * View
     *
     */
    private void view() {
        setTitle("Note Taking App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Menu bar
        menuBar();
        
        // Tool bar
        toolBar();

        // West panel
        panelWest();

        // Central panel
        panelCentre();

        // Show JFrame
        setVisible(true);
    }
    
    /**
     * Menu bar
     */
    private void menuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(fnt);
        JMenu newMenu = new JMenu("New");
        newMenu.setFont(fnt);
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(fnt);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(fnt);
        
        newMenu.add(cc.makeMenuItem("Note", "NewNote", "Add a new note", fnt));
        newMenu.add(cc.makeMenuItem("Course", "AddCourse", "Add a new course", fnt));
        fileMenu.add(newMenu);
        fileMenu.add(cc.makeMenuItem("Exit", "Exit", "Exit from this program", fnt));
        editMenu.add(cc.makeMenuItem("Find...", "Search1", "Find in notes", fnt));
        helpMenu.add(cc.makeMenuItem("About", "About", "About this program", fnt));
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        
        // Combobox for list of courses
        courseList.setFont(fnt);
        courseList.addActionListener(this);
        courseList.setActionCommand("Course");
        // Place the combobox on the right side
        menuBar.add(Box.createGlue());
        menuBar.add(courseList);
        
        this.setJMenuBar(menuBar);    
    }
    
    /**
     * Tool bar
     */
    private void toolBar() {
        JToolBar toolBar = new JToolBar();

        JButton button = null;
        // makeButton(imgName, actionCommand, toolTipText, altText)
        button = cc.makeNavigationButton("Create", "NewNote", "New Note", "New");
        toolBar.add(button);
        button = cc.makeNavigationButton("closed door", "Close", "Close this note", "Close");
        toolBar.add(button);
        button = cc.makeNavigationButton("exit", "Exit", "Exit from this program", "Exit");
        toolBar.add(button);
        button = cc.makeNavigationButton("book", "AddCourse", "Add a new course", "Add course");
        toolBar.add(button);

        add(toolBar, BorderLayout.NORTH);    
    }
    
    /**
     * West panel
     */
    private void panelWest() {
        JPanel pnlWest = new JPanel();
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
        pnlWest.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtNewNote.setFont(fnt);

        pnlWest.add(txtNewNote);

        JButton btnAddNote = new JButton("Add note");
        btnAddNote.setActionCommand("NewNote");
        btnAddNote.addActionListener(this);

        pnlWest.add(btnAddNote);
        
        // Temporary input box for search feature
        text.setFont(fnt);
        pnlWest.add(text);

        add(pnlWest, BorderLayout.WEST);    
    }
    
    /**
     * Central panel
     */
    private void panelCentre() {
        JPanel cen = new JPanel();
        cen.setLayout(new BoxLayout(cen, BoxLayout.Y_AXIS));
        cen.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtDisplaynotes.setFont(fnt);
        cen.add(txtDisplaynotes);

        add(cen, BorderLayout.CENTER);    
    }

    /**
     * Controller
     *
     */
    private void controller() {
        addAllCourses();
        addAllNotes();
    }
    
    private void addAllCourses() {
        courseList.removeAllItems();
        // Add courses to combobox
        for (Course c : allCourses.getAllCourses()) {
            courseList.addItem(c.getCourseName());
        }
        crse = courseList.getItemAt(0);
        
        // Create an "All Courses" item
        courseList.addItem("All Courses");
    }

    /**
     * Loads all notes into the text field on runtime.
     *
     */
    private void addAllNotes() {
        String txtNotes = "";

        for (Note n : allNotes.getAllNotes()) {
            // If user selects "All Courses" from dropdown, show all the notes
            if(crse.equals("All Courses")){
                txtNotes += n.getNote() + "\n";
            }
            else {
                // If user selects a specific course, show its notes
                int courseID = allCourses.toCourseID(crse);
                if(n.getCourseID() == courseID){
                    txtNotes += n.getNote() + "\n";
                }
            }
        }

        txtDisplaynotes.setText(txtNotes);
    }

    @Override
    /**
     * actionPerformed
     * Logic for user behaviour
     */
    public void actionPerformed(ActionEvent e) {
        // "Add Note" button
        if ("NewNote".equals(e.getActionCommand())) {
            if(!crse.equals("All Courses")) {
                String newNote = txtNewNote.getText();
                if (newNote.equals("")) {
                } else {
                    allNotes.addNote(allCourses.toCourseID(crse), newNote);
                    addAllNotes();
                    txtNewNote.setText("");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Select a course first!");
            }
        }
        
        // "Close" button
        if ("Close".equals(e.getActionCommand())) {
            txtNewNote.setText("");
        }
        
        // Exit button
        if ("Exit".equals(e.getActionCommand())) {
            System.exit(0);
        }
        // When selecting course from combobox, update crse
        if ("Course".equals(e.getActionCommand())) {
            // getSelectedItem() causes NullPointerException after a new course is created.
            // However, no functionality breaks
            try {
                // Simple sorting: add relevant notes every time ComboBox value is changed
                crse = courseList.getSelectedItem().toString();
            } catch(Exception err) {
                System.out.println("Error: " + err);
            }
            addAllNotes();
        }
        
        // "Add course" button
        if ("AddCourse".equals(e.getActionCommand())) {
            // Input dialog
            String newCourse = JOptionPane.showInputDialog("Enter course name");
            // Create new course
            allCourses.addCourse(newCourse);
            addAllCourses();
            courseList.setSelectedItem(newCourse);
        }
        
        // Search button
        if ("Search".equals(e.getActionCommand())) {
            
            // Add each word in string to ArrayList
            String[] searchText = text.getText().split(" ");
            
            // Perform search
            search.formatSearch(search.search(searchText, searchBox.getText()), txtNewNote, Color.yellow, Color.darkGray, fnt);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped  not coded yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed  not coded yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased  not coded yet.");
    }
}