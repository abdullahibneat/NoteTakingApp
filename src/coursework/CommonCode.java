package coursework;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/**
 * Collection of commonly used methods.
 * 
 * @author Andy Wicks
 */
public class CommonCode {
    public static final String UK_DATE_TIME_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    public String ukDateAndTime;
    
    // These are some useful items.
    public final String userName = System.getProperty("user.name");
    public final String appDir = System.getProperty("user.dir");
    public final String os = System.getProperty("os.name");
    public final String fileSeparator = System.getProperty("file.separator");

    private ActionListener calledBy;

    /**
     * Constructor (with ActionListener)
     * 
     * @param call Reference to ActionListener
     */
    CommonCode(ActionListener call) {
        calledBy = call;
        initialiseVariables();
    }

    /**
     * Constructor
     */
    CommonCode() {
        initialiseVariables();
    }
    
    /**
     * This is used by CommonCode to set up the public variables
     */
    private void initialiseVariables(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat uk1sdf = new SimpleDateFormat(UK_DATE_TIME_FORMAT_NOW);
        ukDateAndTime = uk1sdf.format(cal.getTime());
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
    protected JMenuItem makeMenuItem(String txt,
            String actionCommand,
            String toolTipText,
            Font fnt) {
        JMenuItem mnuItem = new JMenuItem();
        mnuItem.setText(txt);
        mnuItem.setActionCommand(actionCommand);
        mnuItem.setToolTipText(toolTipText);
        mnuItem.setFont(fnt);
        mnuItem.addActionListener(calledBy);
        return mnuItem;
    }

    /**
     * Function to create a button with icon in a single line.
     *
     * @param imageName Name of PNG icon (without extension)
     * @param actionCommand Set actionCommand name
     * @param toolTipText Sets tool-tip text
     * @param altText Sets text of button when icon not found
     * @return button
     */
    protected JButton makeNavigationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
        //Look for the image.
        String imgLocation = System.getProperty("user.dir") + "\\icons\\"
                + imageName
                + ".png";
        //Create and initialize the button.
        JButton button = new JButton();
        button.setToolTipText(toolTipText);
        button.setActionCommand(actionCommand);
        button.addActionListener(calledBy);
        File fyle = new File(imgLocation);
        if (fyle.exists() && !fyle.isDirectory()) {
            // image found
            Icon img;
            img = new ImageIcon(imgLocation);
            button.setIcon(img);
        } else {
            // image NOT found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }
        return button;
    }

    /**
     * Automatically get the current date and time, with correct format
     *
     * @return Date
     */
    public String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat uksdf = new SimpleDateFormat(UK_DATE_TIME_FORMAT_NOW);
        ukDateAndTime = uksdf.format(cal.getTime());

        return ukDateAndTime;
    }

    /**
     * Function to read the contents of a file and return each line
     * as an element within an ArrayList.
     *
     * @param fileName File Name
     * @return File contents
     */
    public ArrayList<String> readTextFile(String fileName) {
        // Add <String> to fix raw type warning
        ArrayList<String> file = new ArrayList<>();
        String line;
        if ((fileName == null) || (fileName.equals(""))) {
            System.out.println("No file name specified.");
        } else {
            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));

                if (!in.ready()) {
                    throw new IOException();
                }

                while ((line = in.readLine()) != null) {
                    file.add(line);
                }
                in.close();
            } catch (IOException e) {
                System.out.println(e);
                file.add("File not found");
            }
        }

        return file;
    }
    
    /**
     * Change the contents of text file in its entirety, overwriting any
     * existing text.
     *
     * @param fn File name
     * @param outputText ArrayList to write
     * @throws java.io.FileNotFoundException
     */
    public void writeTextFile(String fn, ArrayList<String> outputText)
            throws FileNotFoundException, IOException {
        File fileName = new File(fn);
        try (Writer output = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < outputText.size(); i++) {
                output.write(outputText.get(i) + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
    
    /**
     * Method to determine term week for a given date
     * 
     * @param d Date
     * @return term week number
     */
    public String semesterWeek(String d) {
        try {
            String format = "dd-MM-yyyy";
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date date = df.parse(d);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            String output = "";
            if(week < 15 || week > 38) {
                // Semester 1
                if(week >= 39) {
                    week -= 38;
                    output = "Week 1." + week;
                }
                // Semester 2
                else if(week < 39) {
                    week -= 2;
                    output = "Week 2." + week;
                }
                return output;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Invalid term date";
    }
}
