package coursework;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Coursework extends JFrame implements ActionListener, KeyListener {
    
    JPanel pnl = new JPanel(new BorderLayout());
    
    // Note text field
    JTextField txtShowText = new JTextField(20);
    
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
        System.out.println("model  not coded yet.");
    }

    private void view() {
        Font fnt = new Font("Georgia", Font.PLAIN, 24);
        
        JPanel pnlTop = new JPanel();
        
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
        
        pnlTop.add(menuBar);        
        add(pnlTop, BorderLayout.NORTH);
        
        JPanel pnlWest = new JPanel(new FlowLayout());
        
        JButton btnShowText = new JButton("Show note");
        btnShowText.setActionCommand("NewNote");
        btnShowText.addActionListener(this);
        
        pnlWest.add(btnShowText);
        pnlWest.add(txtShowText);
        
        add(pnlWest, BorderLayout.WEST);
        
        JPanel cen = new JPanel(new FlowLayout());
        
        JLabel cenlbl = new JLabel("Hello world");
        cenlbl.setFont(fnt);
        cen.add(cenlbl);
        
        add(cen, BorderLayout.CENTER);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Note Taking App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Show JFrame
        setVisible(true);
    }

    private void controller() {
        System.out.println("controller  not coded yet.");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if("NewNote".equals(e.getActionCommand())) {
            txtShowText.setText("This is a note.");
        }
        if("Close".equals(e.getActionCommand())) {
            txtShowText.setText("");
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
