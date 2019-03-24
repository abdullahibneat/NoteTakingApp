package coursework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * Main program
 *
 * @author Abdullah Ibne Atiq, Ed Bencito, Harvind Sokhal
 */
public class Coursework extends JFrame implements ActionListener, KeyListener, FocusListener {

    private final Font fnt = new Font("Georgia", Font.PLAIN, 24);
    // Note input
    private final JTextArea txtNewNote = new JTextArea();
    // Note output
    private final JPanel pnlDisplayNotes = new JPanel();
    private ButtonGroup notesRadioGroup = new ButtonGroup();    
    // Add <String> to fix raw type warning
    private final JComboBox<String> courseList = new JComboBox<>();
    private String crse = "";
    private final AllNotes allNotes = new AllNotes();
    private final CommonCode cc = new CommonCode(this);
    private final AllCourses allCourses = new AllCourses();
    // Search toolbar textfield
    JTextField searchField = new JTextField();
    // Coursework items
    private final AllCoursework allCoursework = new AllCoursework();
    private ButtonGroup courseworkButtonGroup = new ButtonGroup();
    private ArrayList<JCheckBox> allCheckBoxes = new ArrayList<>();
    // Display coursework in sidebar
    private final JPanel sideBarPnl = new JPanel();
    // Dialog
    private JDialog courseworkInputDialog;
    private JTextArea courseworkOverviewInput;
    private JTextField courseworkNameInput;
    private JTextArea courseworkRequirementsInput;
    // Edit note dialog
    private int selectedNote = 0;
    // Edit coursework dialog
    private int selectedCoursework = 0;
    private boolean editCoursework = false;
    private final JDialog editNoteDialog = new JDialog(this, "Edit note");
    private final JTextArea editNoteTxt = new JTextArea();
    private final JButton applyButton = new JButton("Apply");
    // Statistics dialog
    private final JDialog statsDialog = new JDialog(this, "All time statistics");
    private final JTextArea statsTxt = new JTextArea();
    // Toolbar
    JToolBar toolBar = new JToolBar();
    JCheckBoxMenuItem toggleToolbar = new JCheckBoxMenuItem("Show toolbar", true);

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
     * This method will check if the Notes.txt and Courses.txt files exist.
     * 
     * If they do, nothing happens.
     * 
     * If Courses.txt exists, but Notes.txt does not, a message will appear
     * informing the user that all courses are empty.
     * 
     * If Notes.txt exists, but Courses.txt does not exist,
     * loop through all the notes, and create a Courses.txt file
     * with all the courses that appear in Notes.txt.
     * 
     * Otherwise, neither file exists, and this method creates dummy
     * contents (2 courses with 1 note each).
     */
    private void model() {        
        File notesFile = new File(allNotes.path);
        File coursesFile = new File(allCourses.path);
        File courseworkFile = new File(allCoursework.path);
        
        // If Courses.txt doesn't exist
        if(!coursesFile.exists()) {
            
            // If Courses.txt AND Notes.txt don't exists,
            // Display a welcome message
            JOptionPane.showMessageDialog(null, "Welcome first time user!");
            
            // Create dummy content
            if(!notesFile.exists()) {
                addSampleCourses();
                addSampleNotes();
            }
            
            // If ONLY Courses.txt doesn't exist
            // Fetch all courses from notes, and reconstruct Courses.txt
            else {
                JOptionPane.showMessageDialog(null, "Warning: found notes, but no course specified. Generating courses...");
                ArrayList<Integer> allCoursesID = new ArrayList<>();
                for(Note n: allNotes.getAllNotes()) {
                    if(allCoursesID.contains(n.getCourseID())) {
                    }
                    else {
                        allCoursesID.add(n.getCourseID());
                        allCourses.addCourse(n.getCourseID(), "Course name for ID " + n.getCourseID() + " not found");
                    }
                }
            }
        }
        
        // If Notes.txt doesn't exist
        // Show warning
        else {
            // If Notes.txt doesn't exist
            // Show warning
            if(!notesFile.exists()) {
                JOptionPane.showMessageDialog(null, "Found courses but no notes.");
            }
        }
        // If Coursework.txt doesn't exist
        if(!courseworkFile.exists()) {
            JOptionPane.showMessageDialog(null, "No coursework");
            addSampleCourseworkItem();
        }
    }
    
    /**
     * Add sample courses
     */
    private void addSampleCourses() {
        allCourses.addCourse("COMP1752");
        allCourses.addCourse("COMP1753");    
    }
    
    /**
     * Add sample notes
     */
    private void addSampleNotes() {
        allNotes.addNote(0, "Arrays are of fixed length and are inflexible.");
        allNotes.addNote(1, "ArrayList can be added to and items can be deleted.");
    }
    
    /**
     * Add sample coursework
     */
    private void addSampleCourseworkItem() {
        ArrayList<String> requirements = new ArrayList<>();
        requirements.add("Sample requirement 1");
        requirements.add("Example 2");
        ArrayList<Boolean> fulfilled = new ArrayList<>();
        fulfilled.add(false);
        fulfilled.add(true);
        allCoursework.addNewCoursework(0, "Sample coursework", "You can add multiple coursework for each course!", requirements, fulfilled);
    }

    /**
     * View
     *
     */
    private void view() {
        setTitle("Note Taking App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 500));
        
        // Menu bar
        menuBar();
        
        // Tool bar
        toolBar();

        // Main view
        JSplitPane mainView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainView.setOneTouchExpandable(true);
        mainView.setResizeWeight(0.8);
        
        // Add the center panel
        mainView.setLeftComponent(panelCentre());
        
        // Add the sidebar
        mainView.setRightComponent(panelEast());
        
        add(mainView, BorderLayout.CENTER);

        // Show JFrame
        setVisible(true);
        
        // Other dialogs (all hidden)
        addCourseworkDialog();
        editNoteDialog();
        showStatistics();
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
        JMenu deleteMenu = new JMenu("Delete");
        deleteMenu.setFont(fnt);
        JMenu advancedMenu = new JMenu("Advanced");
        advancedMenu.setFont(fnt);
        JMenu amendMenu = new JMenu("Amend");
        amendMenu.setFont(fnt);
        JMenu viewMenu = new JMenu("View");
        viewMenu.setFont(fnt);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(fnt);
        
        newMenu.add(cc.makeMenuItem("Note", "NewNote", "Add a new note", fnt));
        newMenu.add(cc.makeMenuItem("Course", "AddCourse", "Add a new course", fnt));
        newMenu.add(cc.makeMenuItem("Coursework", "AddCoursework", "Add a new coursework", fnt));
        fileMenu.add(newMenu);
        fileMenu.add(cc.makeMenuItem("Exit", "Exit", "Exit from this program", fnt));
        editMenu.add(cc.makeMenuItem("Find...", "SearchMenu", "Find in notes", fnt));
        deleteMenu.add(cc.makeMenuItem("Delete selected note", "DeleteNote", "Delete the currently selected note", fnt));
        deleteMenu.add(cc.makeMenuItem("Delete selected coursework", "DeleteCoursework", "Delete the currently selected coursework", fnt));
        editMenu.add(deleteMenu);
        amendMenu.add(cc.makeMenuItem("Course name", "EditCourseName", "Change name of current course", fnt));
        amendMenu.add(cc.makeMenuItem("Selected Note", "EditSelectedNote", "Change the contents of the currently selected note", fnt));
        amendMenu.add(cc.makeMenuItem("Selected Coursework", "EditSelectedCoursework", "Change the contents of the currently selected coursework", fnt));
        editMenu.add(amendMenu);
        advancedMenu.add(cc.makeMenuItem("Reset notes and courses", "DeleteAll", "Deletes all notes and courses", fnt));
        editMenu.add(advancedMenu);
        toggleToolbar.setFont(fnt);
        toggleToolbar.addActionListener(this);
        toggleToolbar.setActionCommand("ToggleToolbar");
        viewMenu.add(toggleToolbar);
        viewMenu.add(cc.makeMenuItem("Statistics", "Stats", "View statistics", fnt));
        helpMenu.add(cc.makeMenuItem("About", "About", "About this program", fnt));
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        // Combobox for list of courses
        courseList.setFont(fnt);
        courseList.addActionListener(this);
        courseList.setActionCommand("Course");
        courseList.setMaximumSize(new Dimension(3000, 30));
        // Place the combobox on the right side
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(courseList);
        
        this.setJMenuBar(menuBar);
    }
    
    /**
     * Tool bar
     */
    private void toolBar() {
        JButton button = null;
        // makeButton(imgName, actionCommand, toolTipText, altText)
        toolBar.add(cc.makeNavigationButton("Create", "NewNote", "New Note", "New"));
        toolBar.add(cc.makeNavigationButton("No", "Close", "Close this note", "Close"));
        toolBar.add(cc.makeNavigationButton("book", "AddCourse", "Add a new course", "Add course"));
        toolBar.add(cc.makeNavigationButton("Bookmark", "AddCoursework", "Add a new coursework", "Add coursework"));
        // Search field
        toolBar.add(Box.createHorizontalGlue());
        searchField.setMinimumSize(new Dimension(5000, 30));
        searchField.setMaximumSize(new Dimension(5000, 30));
        searchField.setFont(fnt);
        searchField.addKeyListener(this);
        toolBar.add(searchField);
        toolBar.addSeparator();
        toolBar.add(cc.makeNavigationButton("Search", "SearchField", "Search for this text", "Search"));

        add(toolBar, BorderLayout.NORTH);    
    }
    
    /**
     * Central panel
     * 
     * Returns a JPanel component with a slide pane (top component: all notes,
     * bottom component: new note)
     * 
     * @return Central panel
     */
    private JPanel panelCentre() {
        JPanel cen = new JPanel();
        cen.setLayout(new BoxLayout(cen, BoxLayout.Y_AXIS));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.6);
        
        // Display notes as radio buttons
        pnlDisplayNotes.setLayout(new BoxLayout(pnlDisplayNotes, BoxLayout.Y_AXIS));
        // Make panel scrollable vertically
        JScrollPane scrollPane = new JScrollPane(pnlDisplayNotes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        splitPane.setTopComponent(scrollPane);
        
        // Panel with new note textarea and add button
        JPanel noteInputPanel = new JPanel();
        noteInputPanel.setLayout(new BoxLayout(noteInputPanel, BoxLayout.X_AXIS));
        scrollPane = new JScrollPane(txtNewNote, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        txtNewNote.setFont(fnt);
        txtNewNote.setLineWrap(true);
        txtNewNote.setForeground(Color.GRAY);
        txtNewNote.setText("Write a new note here...");
        txtNewNote.addFocusListener(this);
        txtNewNote.addKeyListener(this);
        // New note button
        JButton addNote = new JButton("Add note");
        addNote.setFont(fnt);
        addNote.addActionListener(this);
        addNote.setActionCommand("NewNote");
        noteInputPanel.add(scrollPane);
        noteInputPanel.add(addNote);
        splitPane.setBottomComponent(noteInputPanel);
        
        cen.add(splitPane);

        return cen;
    }
    
    /**
     * East panel
     * 
     * Returns a JPanel component with a JTextArea
     * 
     * @return Sidebar panel
     * 
     */
    private JPanel panelEast() {
        JPanel pnlEast = new JPanel();
        pnlEast.setAutoscrolls(true);
        pnlEast.setLayout(new BoxLayout(pnlEast, BoxLayout.Y_AXIS));
        
        sideBarPnl.setLayout(new BoxLayout(sideBarPnl, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sideBarPnl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlEast.add(scrollPane);
        
        return pnlEast;
    }

    /**
     * Controller
     *
     */
    private void controller() {
        addAllCourses();
        addAllNotes();
        addAllCoursework();
    }
    
    /**
     * Load all courses into the JComboBox.
     */
    private void addAllCourses() {
        courseList.removeAllItems();
        // Add courses to combobox
        for (Course c : allCourses.getAllCourses()) {
            courseList.addItem(c.getCourseName());
        }
        crse = courseList.getItemAt(0);
        
        // Create an "All Courses" item to be displayed at the end
        courseList.addItem("All Courses");
    }

    /**
     * Loads all notes into the text field on runtime.
     *
     */
    private void addAllNotes() {
        pnlDisplayNotes.removeAll();
        notesRadioGroup = new ButtonGroup();
        JRadioButton radioButton;
        for (Note n : allNotes.getAllNotes()) {
            // If user selects "All Courses" from dropdown, show all the notes
            if(crse.equals("All Courses") || n.getCourseID() == allCourses.toCourseID(crse)){
                radioButton = new JRadioButton(n.getNote());
                // Use the name field as the NoteID
                radioButton.setName(Integer.toString(n.getNoteID()));
                radioButton.addActionListener(this);
                // Assign change in radio button status to an ActionCommand (will change global variable selectedNote with NoteID)
                radioButton.setActionCommand("SelectNote");
                radioButton.setFont(fnt);
                notesRadioGroup.add(radioButton);
                pnlDisplayNotes.add(radioButton);
            }
        }
        // Update the interface to display changes
        pnlDisplayNotes.revalidate();
        pnlDisplayNotes.repaint();
    }
    
    /**
     * Load all coursework item into the text area.
     */
    private void addAllCoursework() {
        // Empty the sidebar and ArrayList of JCheckBox buttons
        sideBarPnl.removeAll();
        allCheckBoxes = new ArrayList<>();
        courseworkButtonGroup = new ButtonGroup();
        
        for(CourseworkItem c: allCoursework.getAll()) {
            if(crse.equals("All Courses") || c.getCourseID() == allCourses.toCourseID(crse)) {
                // Show coursework name as a label
                JLabel courseworkName = new JLabel(c.getCourseworkName());
                courseworkName.setFont(fnt);
                sideBarPnl.add(courseworkName);
                
                // Add an edit radio button
                JRadioButton editBtn = new JRadioButton("Select coursework");
                // Use the name field as the CourseworkID
                editBtn.setName(Integer.toString(c.getCourseworkID()));
                editBtn.addActionListener(this);
                // Assign change in radio button status to an ActionCommand (will change global variable selectedCoursework with CourseworkID)
                editBtn.setActionCommand("SelectCoursework");
                courseworkButtonGroup.add(editBtn);
                sideBarPnl.add(editBtn);
                
                // If any requirements are found
                if(c.getCourseworkRequirements().size() > 0) {
                    // Show a label
                    sideBarPnl.add(new JLabel("Requirements"));
                    for (int i = 0; i < c.getCourseworkRequirements().size(); i++) {
                        // Create a JCheckBox for each requirement, get the requirement's name and status (completed or not)
                        JCheckBox chk = new JCheckBox(c.getCourseworkRequirements().get(i), c.getRequirementsFulfilled().get(i));
                        // Use the name field as the CourseworkID
                        chk.setName(Integer.toString(c.getCourseworkID()));
                        // Add this JCheckButton to the ArrayList
                        allCheckBoxes.add(chk);
                        chk.setFont(fnt);
                        chk.addActionListener(this);
                        // Assign change in radio button status to an ActionCommand (will store the new value to file)
                        chk.setActionCommand("CourseworkRequirementFulfilled");
                        sideBarPnl.add(chk);
                    }
                }
                
                // Coursework overview displayed within a text area
                JTextArea overview = new JTextArea(c.getCourseworkOverview());
                overview.setFont(fnt);
                // Wrap words
                overview.setWrapStyleWord(true);
                overview.setLineWrap(true);
                // Scroll pane to allow users to scroll vertically
                JScrollPane sp = new JScrollPane(overview, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                // Set preferred size
                sp.setPreferredSize(new Dimension(100, 500));
                sideBarPnl.add(sp);
                
                // Add spacing between coursework
                sideBarPnl.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }
        // Update the interface to display changes
        sideBarPnl.revalidate();
        sideBarPnl.repaint();
    }
    
    /**
     * Dialog to create a new coursework item
     */
    private JDialog addCourseworkDialog() {
        // Create a new dialog
        courseworkInputDialog = new JDialog(this, "Add a new coursework item");
        // Set minimum size
        courseworkInputDialog.setMinimumSize(new Dimension(500, 300));
        JPanel courseworkInputDialogPanel = new JPanel(new BorderLayout());

        // North panel
        JPanel n = new JPanel();
        n.setLayout(new BoxLayout(n, BoxLayout.X_AXIS));
        JLabel courseworkNameLabel = new JLabel("Coursework name");
        // Ask user for coursework name
        courseworkNameInput = new JTextField();
        n.add(courseworkNameLabel);
        n.add(courseworkNameInput);

        courseworkInputDialogPanel.add(n, BorderLayout.NORTH);

        // Center panel
        // Contains two panels, one for coursework overview and the other for requirements
        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        JPanel cNorth = new JPanel();
        cNorth.setLayout(new BoxLayout(cNorth, BoxLayout.X_AXIS));
        JPanel cSouth = new JPanel();
        cSouth.setLayout(new BoxLayout(cSouth, BoxLayout.X_AXIS));

        JLabel courseworkOverviewLabel = new JLabel("Coursework details");
        // Coursework overview input
        courseworkOverviewInput = new JTextArea();
        // Add line wrap to wrap text around
        courseworkOverviewInput.setWrapStyleWord(true);
        courseworkOverviewInput.setLineWrap(true);
        // Scroll pane to allow users to scroll vertically
        JScrollPane scrollPane = new JScrollPane(courseworkOverviewInput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cNorth.add(courseworkOverviewLabel);
        // Add spacing
        cNorth.add(Box.createRigidArea(new Dimension(10, 0)));
        cNorth.add(scrollPane);
        
        // Requirements label, HTML allows carriage return to create new lines
        JLabel courseworkRequirementsLabel = new JLabel("<html>Requirements<br><small>Add 1 requirement per line</small></html>");
        // Set the requirements label to be the same width as "Coursework details"
        courseworkRequirementsLabel.setPreferredSize(new Dimension(courseworkOverviewLabel.getPreferredSize().width, 50));
        courseworkRequirementsLabel.setMinimumSize(new Dimension(courseworkOverviewLabel.getPreferredSize().width, 50));
        courseworkRequirementsLabel.setMaximumSize(new Dimension(courseworkOverviewLabel.getPreferredSize().width, 50));
        // Requirements input
        courseworkRequirementsInput = new JTextArea();
        // Add line wrap to wrap text around
        courseworkRequirementsInput.setWrapStyleWord(true);
        courseworkRequirementsInput.setLineWrap(true);
        // Scroll pane to allow users to scroll vertically
        scrollPane = new JScrollPane(courseworkRequirementsInput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cSouth.add(courseworkRequirementsLabel);
        // Add spacing
        cSouth.add(Box.createRigidArea(new Dimension(10, 0)));
        cSouth.add(scrollPane);
        
        c.add(cNorth);
        c.add(cSouth);

        courseworkInputDialogPanel.add(c, BorderLayout.CENTER);

        // South panel
        // Has OK and CANCEL buttons
        JPanel s = new JPanel();
        s.setLayout(new BoxLayout(s, BoxLayout.X_AXIS));
        JButton ok = new JButton("Ok");
        ok.addActionListener(this);
        ok.setActionCommand("AddCourseworkItem");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setActionCommand("CloseDialog");
        s.add(Box.createGlue());
        s.add(ok);
        s.add(cancel);

        courseworkInputDialogPanel.add(s, BorderLayout.SOUTH);

        courseworkInputDialog.add(courseworkInputDialogPanel);
        
        // Hidden by default
        courseworkInputDialog.setVisible(false);
                
        return courseworkInputDialog;
    }
    
    /**
     * Dialog to edit a note
     */
    private JDialog editNoteDialog() {
        // Dialog minimum size
        editNoteDialog.setMinimumSize(new Dimension(500, 300));
        JPanel editNotePnl = new JPanel(new BorderLayout());
        
        JPanel cen = new JPanel();
        cen.setLayout(new BoxLayout(cen, BoxLayout.Y_AXIS));
        editNoteTxt.setFont(fnt);
        editNoteTxt.setLineWrap(true);
        editNoteTxt.setWrapStyleWord(true);
        // Retrieve note's content
        for(Note n: allNotes.getAllNotes()) {
            if(n.getNoteID() == selectedNote) {
                editNoteTxt.setText(n.getNote());
                break;
            }
        }
        JScrollPane edtiNoteTxtScroll = new JScrollPane(editNoteTxt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cen.add(edtiNoteTxtScroll);
        
        JPanel spnl = new JPanel();
        // Button to apply changes
        applyButton.setFont(fnt);
        applyButton.addActionListener(this);
        applyButton.setActionCommand("EditNote");
        spnl.add(applyButton);
        
        editNotePnl.add(cen, BorderLayout.CENTER);
        editNotePnl.add(spnl, BorderLayout.SOUTH);
        editNoteDialog.add(editNotePnl);
        // Dialog hidden at startup
        editNoteDialog.setVisible(false);
        return editNoteDialog;
    }
    
    private JDialog showStatistics() {
        // Dialog size
        statsDialog.setMinimumSize(new Dimension(500, 300));
        statsDialog.setMaximumSize(new Dimension(500, 300));
        
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.add(statsTxt);
        statsDialog.add(pnl);
        // Dialog hidden at startup
        statsDialog.setVisible(false);
        return statsDialog;
    }

    @Override
    /**
     * actionPerformed
     * 
     * Logic for UI behaviour
     */
    public void actionPerformed(ActionEvent e) {
        // "Add Note" button
        if ("NewNote".equals(e.getActionCommand())) {
            newNote();
        }
        
        // "Close" button
        if ("Close".equals(e.getActionCommand())) {
            txtNewNote.setForeground(Color.GRAY);
            txtNewNote.setText("Write a new note here...");
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
            } catch(NullPointerException err) {
                // This can be safely ignored
            } catch(Exception err) {
                JOptionPane.showMessageDialog(this, "An error occured while processing this course. If this error appears again, please restart the program.");
            }
            addAllNotes();
            addAllCoursework();
        }
        
        // "Add course" button
        if ("AddCourse".equals(e.getActionCommand())) {
            // Input dialog
            String newCourse = JOptionPane.showInputDialog("Enter course name");
            if(newCourse == null) {
                return;
            }
            if(newCourse.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "No course name entered");
            }
            else {
                if(allCourses.exists(newCourse)) {
                    JOptionPane.showMessageDialog(this, "Course exists already");
                }
                else {
                    // Create new course
                    allCourses.addCourse(newCourse);
                    addAllCourses();
                    courseList.setSelectedItem(newCourse);
                }
            }
        }
        
        // Search button
        if ("SearchMenu".equals(e.getActionCommand())) {
            String searchWord = JOptionPane.showInputDialog("Find current notes");
            JOptionPane.showMessageDialog(this, "Found " + allNotes.wordOccurrence(searchWord) + " occurrence(s) of " + searchWord + "\n" + allNotes.searchNoteByKeyword(searchWord));
        }
        if ("AddCoursework".equals(e.getActionCommand())) {
            crse = courseList.getSelectedItem().toString();
            if(crse.equalsIgnoreCase("All Courses")) {
                JOptionPane.showMessageDialog(this, "Select a course first!");
            }
            else {
                // Clear fields
                courseworkNameInput.setText("");
                courseworkOverviewInput.setText("");
                courseworkRequirementsInput.setText("");
                // Show dialog
                courseworkInputDialog.setVisible(true);
            }
        }
        if ("AddCourseworkItem".equals(e.getActionCommand())) {
            String courseworkName = courseworkNameInput.getText();
            String courseworkOverview = courseworkOverviewInput.getText();
            int courseID = allCourses.toCourseID(crse);
            if(courseworkName.equals("")) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
            }
            else if (courseworkOverview.equals("")) {
                JOptionPane.showMessageDialog(this, "Overview cannot be empty");
            }
            else {
                ArrayList<String> requirements = new ArrayList<>();
                ArrayList<Boolean> fulfilled = new ArrayList<>();
                if(courseworkRequirementsInput.getText().length() > 0) {
                    for(String s: courseworkRequirementsInput.getText().split("\n")) {
                        requirements.add(s);
                        fulfilled.add(false);
                    }
                }
                // If editing coursework
                if(editCoursework) {
                    allCoursework.editCoursework(selectedCoursework, courseworkName, courseworkOverview, requirements);
                    editCoursework = false;
                }
                // Otherwise is new coursework
                else {
                    allCoursework.addNewCoursework(courseID, courseworkName, courseworkOverview, requirements, fulfilled);
                }
                addAllCoursework();
                courseworkInputDialog.dispose();
            }
        }
        if ("CloseDialog".equals(e.getActionCommand())) {
            courseworkInputDialog.dispose();
        }
        if ("ToggleToolbar".equals(e.getActionCommand())) {
            toolBar.setVisible(toggleToolbar.isSelected());
        }
        if ("SearchField".equals(e.getActionCommand())) {
            search();
        }
        if ("EditCourseName".equals(e.getActionCommand())) {
            // Ask for new name
            String newCourseName = JOptionPane.showInputDialog("New course name:");
            
            if(newCourseName == null) {
                return;
            }
            
            if(newCourseName.equals("")) {
                JOptionPane.showMessageDialog(this, "No name entered...");
            }
            else {
                if(allCourses.exists(newCourseName)) {
                    JOptionPane.showMessageDialog(this, "Already exists");
                }
                else {
                    // Retrieve course ID
                    int currentCourseID = allCourses.toCourseID(crse);
                    // Apply change
                    allCourses.editCourseName(currentCourseID, newCourseName);
                    crse = newCourseName;
                    addAllCourses();
                    courseList.setSelectedItem(newCourseName);
                }
            }
        }
        if("SelectNote".equals(e.getActionCommand())) {
            // Create ArrayList of all radio buttons
            ArrayList<AbstractButton> listRadioButton = Collections.list(notesRadioGroup.getElements());
            for(AbstractButton button : listRadioButton) {
                // If selected button is found
                if(button.isSelected()) {
                    // The name was set to be Note ID
                    selectedNote = Integer.parseInt(button.getName());
                }
            }
        }
        if("EditSelectedNote".equals(e.getActionCommand())) {
            editNoteDialog.setVisible(true);
        }
        if("EditNote".equals(e.getActionCommand())) {
            if(editNoteTxt.getText().equals("")) {
                // User cleared text area
                JOptionPane.showMessageDialog(this, "Cannot be empty");
                editNoteDialog.setVisible(false);
            }
            else {
                // Submit changes to editNote method
                allNotes.editNote(selectedNote, editNoteTxt.getText());
                // Hide the JDialog
                editNoteDialog.setVisible(false);
                // Add all notes to JPanel
                addAllNotes();
            }
        }
        if("DeleteAll".equals(e.getActionCommand())) {
            Integer confirmDialog = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete ALL notes, coursework AND courses? This operation cannot be undone.");
            if(confirmDialog == JOptionPane.YES_OPTION) {
                allCoursework.deleteAllCoursework();
                allNotes.deleteAllNotes();
                allCourses.deleteAllCourses();
                this.setVisible(false);
                model();
                controller();
                this.setVisible(true);
            }
        }
        if("DeleteNote".equals(e.getActionCommand())) {
            int confirmDialog = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this note?");
            if(confirmDialog == JOptionPane.YES_OPTION) {
                allNotes.deleteNote(selectedNote);
                addAllNotes();
            }
        }
        if("Stats".equals(e.getActionCommand())) {
            String stats = "";
            stats += "Number of courses: " + allCourses.getAllCourses().size() + "\n";
            stats += "Number of notes: " + allNotes.getAllNotes().size() + "\n";
            stats += "Number of coursework: " + allCoursework.getAll().size() + "\n\n";
            stats += "Course with most notes: " + allCourses.toCourseName(Integer.parseInt(allNotes.courseWithMostNotes().get(0))) + " with " +allNotes.courseWithMostNotes().get(1) + " notes\n";
            stats += "Date most notes were written: " + allNotes.dateMostNotesWereWritten().get(0) + " with " + allNotes.dateMostNotesWereWritten().get(1) + " notes";
            statsTxt.setText(stats);
            // Show dialog
            statsDialog.setVisible(true);
        }
        if("SelectCoursework".equals(e.getActionCommand())) {
            // Create ArrayList of all radio buttons
            ArrayList<AbstractButton> courseworkRadioButtons = Collections.list(courseworkButtonGroup.getElements());
            for(AbstractButton button : courseworkRadioButtons) {
                // If selected button is found
                if(button.isSelected()) {
                    selectedCoursework = Integer.parseInt(button.getName());
                }
            }
        }
        // Editing coursework items
        if("EditSelectedCoursework".equals(e.getActionCommand())) {
            for(CourseworkItem c: allCoursework.getAll()) {
                // Find the CourseworkItem that matches the ID of the selected coursework
                if(c.getCourseworkID() == selectedCoursework) {
                    // Fill in all the input values
                    courseworkNameInput.setText(c.getCourseworkName());
                    courseworkOverviewInput.setText(c.getCourseworkOverview());
                    String requirements = "";
                    for(String s: c.getCourseworkRequirements()) {
                        requirements += s + "\n";
                    }
                    courseworkRequirementsInput.setText(requirements);
                    // Set this to true so we can re-use the "AddCourseworkItem" action command
                    editCoursework = true;
                    // Show dialog
                    courseworkInputDialog.setVisible(true);
                    // Terminate loop
                    break;
                }
            }
        }
        // If change in requirements JCheckBox
        if("CourseworkRequirementFulfilled".equals(e.getActionCommand())) {
            ArrayList<Boolean> fulfilled = new ArrayList<>();
            // Find the JCheckBox that triggered this ActionPerformed
            int courseworkID = Integer.parseInt(allCheckBoxes.get(allCheckBoxes.indexOf(e.getSource())).getName());
            // Reconstrunt the ArrayList for fulfilled requirements
            for(JCheckBox c: allCheckBoxes) {
                if(c.getName().equals(Integer.toString(courseworkID))) {
                    fulfilled.add(c.isSelected());
                }
            }
            // Replace previous ArrayList with the new one (method will also write to file)
            allCoursework.setRequirementsFulfilled(courseworkID, fulfilled);
        }
        if("DeleteCoursework".equals(e.getActionCommand())) {
            allCoursework.deleteCoursework(selectedCoursework);
            addAllCoursework();
        }
    }
    
    /**
     * Method to add a new note
     */
    private void newNote() {
        String newNote = txtNewNote.getText();
        if (newNote.equalsIgnoreCase("Write a new note here...") || newNote.equals("")) {
            JOptionPane.showMessageDialog(this, "Write a note first!");
        } else {
            if (crse.equals("All Courses")) {
                JOptionPane.showMessageDialog(this, "Select a course first!");
            } else {
                allNotes.addNote(allCourses.toCourseID(crse), newNote);
                addAllNotes();
                txtNewNote.setText("");
            }
        }
    }
    
    /**
     * method to perform a search using the search bar
     */
    private void search() {
        JOptionPane.showMessageDialog(this, "Found " + allNotes.wordOccurrence(searchField.getText()) + " occurrence(s) of " + searchField.getText() + "\n" + allNotes.searchNoteByKeyword(searchField.getText()));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped  not coded yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If ENTER key is pressed...
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            // ...in the new note text area
            if(e.getSource().equals(txtNewNote)) {
                // add the new note
                newNote();
            }
            // ...in the search bar
            if(e.getSource().equals(searchField)) {
                // perform the search
                search();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // If ENTER is pressed in the new note text area, after it's released we need to remove the \n character from it.
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(e.getSource().equals(txtNewNote)) {
                txtNewNote.setText("");
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(txtNewNote.getText().equalsIgnoreCase("Write a new note here...")) {
            txtNewNote.setForeground(Color.BLACK);
            txtNewNote.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(txtNewNote.getText().equalsIgnoreCase("")) {
            txtNewNote.setForeground(Color.GRAY);
            txtNewNote.setText("Write a new note here...");
        }
    }
}