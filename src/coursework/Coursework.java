package coursework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 * Main program
 *
 * @author Abdullah Ibne Atiq
 */
public class Coursework extends JFrame implements ActionListener, KeyListener {

    private JPanel pnl = new JPanel(new BorderLayout());

    // Note input
    private JTextArea txtNewNote = new JTextArea();
    // Note output
    private JTextArea txtDisplaynotes = new JTextArea();
    private ArrayList<String> note = new ArrayList<>();
    private ArrayList<String> course = new ArrayList<>();
    // Add <String> to fix raw type warning
    private JComboBox<String> courseList = new JComboBox<String>();
    private String crse = "";
    private AllNotes allNotes = new AllNotes();
    private CommonCode cc = new CommonCode();
    // Store courses in text file
    private ArrayList<String> coursesFile = cc.readTextFile(cc.appDir + "//Courses.txt");

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
        for (String c : coursesFile) {
            course.add(c);
        }
        crse = course.get(0);

        // Create note instances
        // ONLY REQUIRED ON FIRST TIME RUNNING THIS PROGRAM
//        Note nt = new Note();
//        
//        nt.setNoteID(1);
//        nt.setCourse(crse);
//        nt.setNote("Arrays are of fixed length and are inflexible.");
//        allNotes.addNote(nt.getNoteID(), nt.getCourse(), nt.getNote());
//        
//        nt.setNoteID(2);
//        nt.setCourse(crse);
//        nt.setNote("ArrayList can be added to and items can be deleted.");
//        allNotes.addNote(nt.getNoteID(), nt.getCourse(), nt.getNote());
    }

    /**
     * View
     *
     */
    private void view() {
        Font fnt = new Font("Georgia", Font.PLAIN, 24);

        JMenuBar menuBar = new JMenuBar();

        JMenu note = new JMenu("Note");
        note.setToolTipText("Note tasks");
        note.setFont(fnt);

        // makeMenuItem(txt, actionCommand, toolTipText, fnt)
        note.add(makeMenuItem("New", "NewNote", "Create a new note.", fnt));
        note.addSeparator();
        note.add(makeMenuItem("Close", "Close", "Clear the current note.", fnt));

        menuBar.add(note);
        menuBar.add(makeMenuItem("Exit", "Exit", "Close this program.", fnt));

        // Add courses to combobox
        for (String crse : course) {
            courseList.addItem(crse);
        }
        courseList.setFont(fnt);
        courseList.setMaximumSize(courseList.getPreferredSize());
        courseList.addActionListener(this);
        courseList.setActionCommand("Course");

        // Add combobox to menubar
        menuBar.add(courseList);

        this.setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar();

        JButton button = null;
        // makeButton(imgName, actionCommand, toolTipText, altText)
        button = makeButton("Create", "NewNote", "New Note", "New");
        toolBar.add(button);
        button = makeButton("closed door", "Close", "Close this note", "Close");
        toolBar.add(button);
        button = makeButton("exit", "Exit", "Exit from this program", "Exit");
        toolBar.add(button);
        button = makeButton("book", "AddCourse", "Add a new course", "Add course");
        toolBar.add(button);

        add(toolBar, BorderLayout.NORTH);

        JPanel pnlWest = new JPanel();
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
        pnlWest.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtNewNote.setFont(fnt);

        pnlWest.add(txtNewNote);

        JButton btnAddNote = new JButton("Add note");
        btnAddNote.setActionCommand("NewNote");
        btnAddNote.addActionListener(this);

        pnlWest.add(btnAddNote);

        add(pnlWest, BorderLayout.WEST);

        JPanel cen = new JPanel();
        cen.setLayout(new BoxLayout(cen, BoxLayout.Y_AXIS));
        cen.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtDisplaynotes.setFont(fnt);
        cen.add(txtDisplaynotes);

        add(cen, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Note Taking App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show JFrame
        setVisible(true);
    }

    /**
     * Controller
     *
     */
    private void controller() {
        addAllNotes();
    }

    /**
     * Function to create JMenuItems in a single line.
     *
     * @param txt Sets text of menuItem
     * @param actionCommand Sets actionCommand name
     * @param toolTipText Sets tool-tip text
     * @param fnt Sets font
     * @return menuItem
     */
    protected JMenuItem makeMenuItem(
            String txt,
            String actionCommand,
            String toolTipText,
            Font fnt) {

        JMenuItem menuItem = new JMenuItem();
        menuItem.setText(txt);
        menuItem.setActionCommand(actionCommand);
        menuItem.setToolTipText(txt);
        menuItem.setFont(fnt);
        menuItem.addActionListener(this);

        return menuItem;
    }

    /**
     * Function to create a button with icon in a single line.
     *
     * @param imgName Name of PNG icon (without extension)
     * @param actionCommand Set actionCommand name
     * @param toolTipText Sets tool-tip text
     * @param altText Sets text of button when icon not found
     * @return button
     */
    protected JButton makeButton(
            String imgName,
            String actionCommand,
            String toolTipText,
            String altText) {
        JButton button = new JButton();
        button.setToolTipText(toolTipText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        // Look for the image
        String imgLocation = System.getProperty("user.dir")
                + "\\icons\\"
                + imgName
                + ".png";

        File fyle = new File(imgLocation);
        if (fyle.exists() && !fyle.isDirectory()) {
            // Image found
            Icon img = new ImageIcon(imgLocation);
            button.setIcon(img);
        } else {
            // Image NOT found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

    /**
     * Loads all notes into the text field on runtime.
     *
     */
    private void addAllNotes() {
        String txtNotes = "";

        for (Note n : allNotes.getAllNotes()) {
            txtNotes += n.getNote() + "\n";
        }

        txtDisplaynotes.setText(txtNotes);
    }

    /**
     * Create and store a new note.
     * 
     */
    private void addNote(String text) {
        Note nt = new Note();
        nt.setNoteID(allNotes.getAllNotes().size()+1);
        nt.setCourse(crse);
        nt.setNote(text);
        allNotes.addNote(nt.getNoteID(), nt.getCourse(), nt.getNote());
        addAllNotes();
    }

    @Override
    /**
     * actionPerformed
     * Logic for user behaviour
     */
    public void actionPerformed(ActionEvent e) {
        // "Add Note" button
        if ("NewNote".equals(e.getActionCommand())) {
            String newNote = txtNewNote.getText();
            if (newNote.equals("")) {
            } else {
                addNote(newNote);
                txtNewNote.setText("");
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
            crse = courseList.getSelectedItem().toString();
            System.out.println(crse);
        }
        
        // "Add course" button
        if ("AddCourse".equals(e.getActionCommand())) {
            // Input dialog
            String newCourse = JOptionPane.showInputDialog("Enter course name");
            // Add new course to arraylist
            coursesFile.add(newCourse);
            try {
                // Write new arraylist to file
                cc.writeTextFile(cc.appDir + "//Courses.txt", coursesFile);
                courseList.addItem(newCourse);
                // Auto-select new course in combobox
                courseList.setSelectedItem(newCourse);
            } catch (IOException ex) {
                System.err.println("Error while adding course to text file: " + ex);
            }
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
