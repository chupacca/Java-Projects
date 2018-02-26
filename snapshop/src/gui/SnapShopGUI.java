/*
 * TCSS 305
 * 
 * An SnapShop GUI that represents lets you open and edit pictures.
 */

package gui;

import filters.AbstractFilter;
import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Image Editor GUI to let you open and edit pictures.
 * 
 * @author Peter M Chu.
 * @version April 26, 2016.
 */
public final class SnapShopGUI {
   
    /** The frame for this application's GUI. */
    private final JFrame myFrame;
    
    /** The array of Filter Buttons. */
    private JButton[] myFilterBtns;
    
    /** The bottom three file buttons. */
    private JButton[] myFileBtns;
    
    /** The image that I will filter. */
    private PixelImage myImage;
    
    /** The file directory I am in. */
    private File myDirectory;
    
    /** The file chooser I will use. */
    private final JFileChooser myChooser;
    
    /** JLabel for myImage. */
    private JLabel myLabel;
    
    /**
     * Constructor to initialize fields.
     */
    public SnapShopGUI() {
        myFrame = new JFrame();
        final int seven = 7;
        myFilterBtns = new JButton[seven];
        final int three = 3;
        myFileBtns = new JButton[three];
        myDirectory = new File("sample_images");
        myChooser = new JFileChooser();
    }
    
    /**
     * Creates a single button that calls a method on the specified object when pressed.
     * 
     * @param theObject The object.
     * @return the button.
     */
    private JButton createButton(final Object theObject) {
        final String name = ((AbstractFilter) theObject).getDescription();
        final JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(final java.awt.event.ActionEvent theEvent) {
                ((AbstractFilter) theObject).filter(myImage);
                loadImage();
            }
        });
        button.setEnabled(false);
        return button;
    }
    
    /**
     * This is the start method that will be the first method runs to execute the GUI.
     */
    public void start() {
        
        myFrame.setTitle("TCSS 305 SnapShop");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);
        
        final String[] fileBtns = {"Open...", "Save As...", "Close Image"};
        
        final JPanel panel1 = new JPanel(new FlowLayout());
        
        for (int i = 0; i < fileBtns.length; i++) {
            final JButton button = new JButton(fileBtns[i]);
            final String name = button.getText();
            button.addActionListener(new ActionListener() {

                public void actionPerformed(final java.awt.event.ActionEvent theEvent) {
                    
                    if (name.equals(fileBtns[0])) {
                        openFile();
                        enableBtns();
                    } else if (name.equals(fileBtns[1])) {
                        saveFile();
                    } else {
                        myFrame.remove(myLabel);
                        myFrame.pack();
                    }
                }
            });
            if (i != 0) {
                button.setEnabled(false);
            }
            myFileBtns[i] = button;
            panel1.add(button);
        }

        final Object[] filterList = {new EdgeDetectFilter(), new EdgeHighlightFilter(),
                                     new FlipHorizontalFilter(), new FlipVerticalFilter(), 
                                     new GrayscaleFilter(), new SharpenFilter(), 
                                     new SoftenFilter()};

        final JPanel panel2 = new JPanel(new GridLayout(7, 1));
        
        for (int i = 0; i < filterList.length; i++) {
            myFilterBtns[i] = createButton(filterList[i]);
            panel2.add(myFilterBtns[i]);
        }
        
        myFrame.add(panel1, BorderLayout.SOUTH);
        myFrame.add(panel2, BorderLayout.WEST);
        myFrame.pack();
        
        myFrame.setVisible(true);
    }
    
    
    /**
     * Enables all the buttons.
     */
    public void enableBtns() {
        for (int i = 0; i < myFilterBtns.length; i++) {
            myFilterBtns[i].setEnabled(true);
        }
        for (int i = 0; i < myFileBtns.length; i++) {
            myFileBtns[i].setEnabled(true);
        }
    }
    
    

    /**
     * Opens an image for the user to edit.
     * @throws IOException.
     */
    public void openFile() {
        
        myChooser.setCurrentDirectory(myDirectory);
        
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF & PNG Images", "jpg", "gif", "png");
        myChooser.setFileFilter(filter);
        final int valInt = myChooser.showOpenDialog(myFileBtns[0]);
        
        if (valInt == JFileChooser.APPROVE_OPTION) {
            try {
                myImage = PixelImage.load(myChooser.getSelectedFile());
                myDirectory = myChooser.getSelectedFile();
                loadImage();
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "The selected file did "
                                              + "not contain a valid image");
            }
        }
    }
    
    
    /**
     * Loads the image onto the center.
     */
    public void loadImage() {

        if (myLabel != null) {
            myFrame.remove(myLabel);
        }
        myLabel = new JLabel("", SwingConstants.CENTER);
        myLabel.setIcon(new ImageIcon(myImage));
        myFrame.add(myLabel, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    /**
     * Saves the image.
     */
    public void saveFile() {
        final JFrame frame = new JFrame();
        myChooser.setDialogTitle("Save Image");   
        myChooser.setCurrentDirectory(myDirectory);
        final int userSelection = myChooser.showSaveDialog(frame);
         
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                final File saveLocation = myChooser.getSelectedFile();
                if (saveLocation.exists()) {
                    confirmDecision(saveLocation);
                } else {
                    myImage.save(saveLocation);
                    myDirectory = saveLocation;
                }
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Invalid save type!");
            }
        }
        
        
    }
    
    
    /**
     * Method to allow the user to confirm whether or not the user want to overwrite an image.
     * @param theLocation is the File that already exists.
     * @throws IOException 
     */
    public void confirmDecision(final File theLocation) throws IOException {
        
        int response;
        response = JOptionPane.showConfirmDialog(null, "This image already exists. "
                                                 + "Do want to overwrite?");
        if (response == JOptionPane.YES_OPTION) {
            myImage.save(theLocation);
            myDirectory = theLocation;
        } 
        
        
    }

}




