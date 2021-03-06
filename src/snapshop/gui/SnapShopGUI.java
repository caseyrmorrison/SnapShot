/*
 * SnapShop program
 * TCSS 305 4/30/13
 */

package snapshop.gui;

import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import snapshop.filters.AbstractFilter;
import snapshop.filters.EdgeDetectFilter;
import snapshop.filters.EdgeHighlightFilter;
import snapshop.filters.Filter;
import snapshop.filters.FlipHorizontalFilter;
import snapshop.filters.FlipVerticalFilter;
import snapshop.filters.GrayscaleFilter;
import snapshop.filters.SharpenFilter;
import snapshop.filters.SoftenFilter;
import snapshop.image.PixelImage;

/**
 * This program lets a user load an image and edit it.
 * The user clicks the open button to open a photo and load it into the editor.
 * Once the photo is loaded the user can edit it with the multiple filter buttons.
 * After the user is done they can save the image.
 * 
 * @author Casey Morrison
 * @version April 30, 2013
 */
@SuppressWarnings("serial")
public class SnapShopGUI extends JFrame {
  
  // class constants
  
//  /** A ToolKit. */
//  private static final Toolkit KIT = Toolkit.getDefaultToolkit();
//  
//  /** Used for division for screen position. */
//  private static final int DIVID_FOUR = 4;
//  
//  /** Used for division for screen position. */
//  private static final int DIVID_THREE = 3;
// 
//  /** The Dimension of the screen. */
//  private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
//  
//  /** The width of the screen. */
//  private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
//  
//  /** The height of the screen. */
//  private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
  
  /** Used for setting the undo button's visibility. */
  private static final int UNDO_VISIBLE = 9;
  
  /** The center panel used in the JFrame. */
  private static final JPanel CENTER_PANEL = new JPanel(new FlowLayout());
  
  /** Icon used for the close button. */
  private static final ImageIcon CLOSE = new ImageIcon("close.gif");
  
  /** Icon used for the undo button. */
  private static final ImageIcon UNDO = new ImageIcon("Undo-icon.png");
  
  /** The master panel used in the JFrame. */
  private static final JPanel MASTER_PANEL = new JPanel(new BorderLayout());
  
  /** The north panel used in the JFrame. */
  private static final JPanel NORTH_PANEL = new JPanel(new FlowLayout());
  
  /** The number of buttons to create. */
  private static final int NUM_BUTTONS = 7;
  
  /** Icon used for the open button. */
  private static final ImageIcon OPEN = new ImageIcon("Open16.gif");
  
  /** Icon used for the save button. */
  private static final ImageIcon SAVE_AS = new ImageIcon("Save16.gif");
  
  /** The south panel used in the JFrame. */
  private static final JPanel SOUTH_PANEL = new JPanel(new FlowLayout());
  
  /** List of buttons used for turning on or off. */
  private final List<JButton> my_buttonlist = new ArrayList<JButton>();
  
  /** List of names of the filters for the buttons. */
  private final List<AbstractFilter> my_filters = new ArrayList<AbstractFilter>();
  
  /** List of edits of the image. */
  private final List<PixelImage> my_edits = new ArrayList<PixelImage>();
  
  /** Button holder object for adding buttons. */
  private JButton my_holder;
  
  /** File chooser for opening and saving. */
  private final JFileChooser my_file_chooser = 
      new JFileChooser(System.getProperty("user.dir"));
  
  /** The file used after an edit. */
  private final File my_edited = new File("names");
  
  /** The file used for storing in list. */
  private final File my_edited_second = new File("name");
  
  /** The pixel image used for display and edits. */
  private PixelImage my_image;
  
  /** The JLabel used for showing the picture. */
  private JLabel my_next;
  
  /** Used for the position in the undo's redo's. */
  private int my_position;
  
   /**
   * Constructs a frame to demonstrate composite layout.
   */
  public SnapShopGUI() {
    super("TCSS 305 SnapShop - Spring 2013");
    my_filters.add(new EdgeDetectFilter());
    my_filters.add(new EdgeHighlightFilter()); 
    my_filters.add(new FlipHorizontalFilter());
    my_filters.add(new FlipVerticalFilter());
    my_filters.add(new GrayscaleFilter());
    my_filters.add(new SharpenFilter());
    my_filters.add(new SoftenFilter());
    my_holder = new JButton();
    my_position = 0;
  }
  
  /**
   * Sets up and displays the GUI for this application.
   */
  public void start() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    // position the frame in the center of the screen
//    setLocation(SCREEN_WIDTH / DIVID_FOUR - getWidth() / DIVID_FOUR,
//                      SCREEN_HEIGHT / DIVID_THREE - getHeight() / DIVID_THREE);
    setLocationByPlatform(true);
    setupComponents();  
    pack();
    setVisible(true);
  }
  
  /**
   * Sets up the components in this frame.
   */
  private void setupComponents() {
    //create the filter buttons.
    for (int i = 0; i < NUM_BUTTONS; i++) {
      final JButton temp = createFilterButton(my_filters.get(i));
      NORTH_PANEL.add(temp);
      my_buttonlist.add(temp);
    }
    //create the open button.
    my_holder = createOpenButton();
    SOUTH_PANEL.add(my_holder);
    
    //create the save button.
    my_holder = createSaveButton();
    SOUTH_PANEL.add(my_holder);
    my_buttonlist.add(my_holder);
    
    //create the close button.
    my_holder = createCloseButton();
    SOUTH_PANEL.add(my_holder);
    my_buttonlist.add(my_holder);
    
    //create the undo button.
    my_holder = createUndoButton();
    SOUTH_PANEL.add(my_holder);
    my_buttonlist.add(my_holder);
    
    //add everything to the master panel and then add that to the frame.
    setVisibleButtons(false);
    MASTER_PANEL.add(NORTH_PANEL, BorderLayout.NORTH);
    MASTER_PANEL.add(SOUTH_PANEL, BorderLayout.SOUTH);
    add(MASTER_PANEL);
  }
  
   /**
   * Creates a single button that calls a method on the specified object when
   * pressed.
   * 
   * @param the_filter The filter used for editing.
   * @return the button.
   */
  private JButton createFilterButton(final Filter the_filter) {
    final JButton button = new JButton(the_filter.getDescription());
    button.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        try {
          my_buttonlist.get(UNDO_VISIBLE).setEnabled(true);         
          the_filter.filter(my_image);
          my_image.save(my_edited);
          final PixelImage temp = PixelImage.load(my_edited);
          temp.save(my_edited_second);
          my_edits.add(temp);
          my_position++;    
          my_next.setIcon(new ImageIcon(my_image));
          setCenterPanel();
        } catch (final IOException e) {
          JOptionPane.showMessageDialog(null, "Invalid process.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    return button;
  }
  
  /**
   * This creates the save button for the GUI.
   * It assigns an action listener to the button that opens a file and displays it.
   * @return save - button that saves a file.
   */
  private JButton createSaveButton() {
    final JButton save = new JButton("Save As...", SAVE_AS);
    save.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        final int result = my_file_chooser.showSaveDialog(SnapShopGUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            my_image.save(my_file_chooser.getSelectedFile());
          } catch (final IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
    return save;
  }
  
  /**
   * Method used for setting the image displayed on the center panel.
   */
  private void setCenterPanel() {
    CENTER_PANEL.setVisible(true);
    CENTER_PANEL.removeAll();
    CENTER_PANEL.add(my_next);
    CENTER_PANEL.setBackground(Color.GRAY);
    CENTER_PANEL.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    MASTER_PANEL.add(CENTER_PANEL, BorderLayout.CENTER);
  }
  
  /**
   * This creates the open button for the GUI.
   * It assigns an action listener to the button that opens a file and displays it.
   * @return open - button that opens a file.
   */
  private JButton createOpenButton() {
    final JButton open = new JButton("Open...", OPEN);
    open.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        final int result = my_file_chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            my_position = 0; my_edits.clear();
            my_buttonlist.get(UNDO_VISIBLE).setEnabled(false);
            my_image = PixelImage.load(my_file_chooser.getSelectedFile());
            final PixelImage temp = PixelImage.load(my_file_chooser.getSelectedFile());
            my_edits.add(temp); my_next = new JLabel();
            my_next.setIcon(new ImageIcon(my_image)); setCenterPanel();
            setVisibleButtons(true); my_buttonlist.get(UNDO_VISIBLE).setEnabled(false);
          } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "File did not contain a valid image: " 
                + my_file_chooser.getSelectedFile(), "Invalid", JOptionPane.ERROR_MESSAGE);
          }
          pack();
        }
      }
    });
    return open;
  }
  
  /**
   * Create the close button that closes the image being edited.
   * @return close - the JButton that closes the image.
   */
  private JButton createCloseButton() {
    final JButton close = new JButton("Close Image", CLOSE);
    close.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        CENTER_PANEL.removeAll();
        CENTER_PANEL.setVisible(false);
        setVisibleButtons(false);
        pack();
        my_position = 0;
        my_edits.clear();
      }
    });
    return close;
  }
  
  /**
   * Creates the undo button which will take you back as far as you want.
   * @return undo - a button used for undoing the previous filter.
   */
  private JButton createUndoButton() {
    final JButton undo = new JButton("Undo", UNDO);
    undo.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        try {
          if (my_position > 0) {
            final PixelImage temp = my_edits.get(my_position - 1);
            temp.save(my_edited_second);
            my_next.setIcon(new ImageIcon(temp));
            my_image = PixelImage.load(my_edited_second);
            setCenterPanel();
            my_edits.remove(my_position);
            my_position--;
            if (my_position == 0) {
              my_buttonlist.get(UNDO_VISIBLE).setEnabled(false);
            }
          }
        } catch (final IOException e) {
          e.printStackTrace();
        }
      }
    });
    return undo;
  }
  
  /**
   * Sets the buttons visible or not visible.
   * @param the_value - true if visible false if not
   */
  private void setVisibleButtons(final boolean the_value) {
    for (JButton b : my_buttonlist) {
      b.setEnabled(the_value);
    }
  }  
}
