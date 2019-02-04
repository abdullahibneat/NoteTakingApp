package coursework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class Coursework extends JFrame implements ActionListener, KeyListener {
    
    JPanel pnl = new JPanel(new BorderLayout());
    
    // Note input
    JTextArea txtNewNote = new JTextArea();
    // Note output
    JTextArea txtDisplaynotes = new JTextArea();
    ArrayList<String> note = new ArrayList<>();
    
    public static void main(String[] args) {
        Coursework prg = new Coursework();
    }
    
    // Using MVC
    public Coursework() {
        model();
        view();
        controller();
    }

    private void model() {
        // Add notes to array
        note.add("Arrays are of fixed length and are inflexible.");
        note.add("ArrayList can be added to and items can be deleted.");
    }

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
        
        this.setJMenuBar(menuBar);
        
        JToolBar toolBar = new JToolBar();
        
        JButton button = null;
        // makeButton(imgName, toolTipText, actionCommand, altText)
        button = makeButton("Create", "New Note", "NewNote", "New");
        toolBar.add(button);
        button = makeButton("closed door", "Close this note", "Close", "Close");
        toolBar.add(button);
        button = makeButton("exit", "Exit from this program", "Exit", "Exit");
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

    private void controller() {
        addAllNotes();
    }
    
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
    
    protected JButton makeButton(
            String imgName,
            String toolTipText,
            String actionCommand,
            String altText){
        JButton button = new JButton();
        button.setToolTipText(toolTipText);
        button.setActionCommand(actionCommand);
        
        // Look for the image
        String imgLocation = System.getProperty("user.dir")
                + "\\icons\\"
                + imgName
                + ".png";
        
        File fyle = new File(imgLocation);
        if(fyle.exists() && !fyle.isDirectory()) {
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
    
    private void addAllNotes() {
        String allNotes = "";
        
        for(String n: note) {
            allNotes += n + "\n";
        }
        
        txtDisplaynotes.setText(allNotes);
    }
    
    private void addNote(String text) {
        note.add(text);
        addAllNotes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("NewNote".equals(e.getActionCommand())) {
            String newNote = txtNewNote.getText();
            if(newNote.equals("")) {
            } else {
                addNote(newNote);
                txtNewNote.setText("");
            }
        }
        if("Close".equals(e.getActionCommand())) {
            txtNewNote.setText("");
        }
        if("Exit".equals(e.getActionCommand())) {
            System.exit(0);
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
