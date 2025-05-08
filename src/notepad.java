import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Notepad extends JFrame implements ActionListener {
    // Declare the components of the Notepad application
    private JTextArea textArea = new JTextArea(); // Text area to display and edit text
    private JMenuBar menuBar = new JMenuBar();   // Menu bar to hold the menus
    private JMenu fileMenu = new JMenu("File");   // File menu in the menu bar
    private JMenuItem openFile = new JMenuItem("Open"); // Menu item to open a file
    private JMenuItem saveFile = new JMenuItem("Save"); // Menu item to save a file
    private JMenuItem close = new JMenuItem("Close");  // Menu item to close the application

    // Constructor for the Notepad class
    public Notepad() {
        // Frame setup
        this.setSize(500, 300);              // Set the size of the JFrame
        this.setTitle("Java Notepad Tutorial"); // Set the title of the JFrame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Set the default close operation

        // Text area setup
        textArea.setFont(new Font("Century Gothic", Font.BOLD, 12)); // Set font
        JScrollPane scrollPane = new JScrollPane(textArea);      // Add scroll bars
        this.getContentPane().add(scrollPane, BorderLayout.CENTER); // Add to the frame

        // Menu bar setup
        this.setJMenuBar(menuBar);              // Set the JFrame's menu bar
        menuBar.add(fileMenu);                  // Add the file menu to the menu bar

        // Open menu item setup
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); // Shortcut
        openFile.addActionListener(this);                          // Add action listener
        fileMenu.add(openFile);                                  // Add to file menu

        // Save menu item setup
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); // Shortcut
        saveFile.addActionListener(this);                          // Add action listener
        fileMenu.add(saveFile);                                  // Add to file menu

        // Close menu item setup
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK));  // Shortcut
        close.addActionListener(this);                           // Add action listener
        fileMenu.add(close);                                // Add to file menu
    }

    // Implementation of the actionPerformed method from ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the Close menu item
        if (e.getSource() == close) {
            this.dispose(); // Close the JFrame
        }
        // Handle the Open menu item
        else if (e.getSource() == openFile) {
            JFileChooser open = new JFileChooser(); // Create a file chooser dialog
            int option = open.showOpenDialog(this); // Show the open dialog
            // If the user selects a file and clicks "Open"
            if (option == JFileChooser.APPROVE_OPTION) {
                textArea.setText(""); // Clear the text area
                try (Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()))) {
                    // Read the file line by line and append to the text area
                    while (scan.hasNextLine()) {
                        textArea.append(scan.nextLine() + "\n"); // Add new line
                    }
                } catch (Exception ex) {
                    // Display an error message if an exception occurs
                    JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
                }
            }
        }
        // Handle the Save menu item
        else if (e.getSource() == saveFile) {
            JFileChooser save = new JFileChooser(); // Create a file chooser dialog
            int option = save.showSaveDialog(this); // Show the save dialog
            // If the user selects a location and clicks "Save"
            if (option == JFileChooser.APPROVE_OPTION) {
                try (BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()))) {
                    // Write the text from the text area to the file
                    out.write(textArea.getText());
                } catch (Exception ex) {
                    // Display an error message if an exception occurs
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
                }
            }
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Notepad app = new Notepad(); // Create an instance of the Notepad class
            app.setVisible(true);          // Make the JFrame visible
        });
    }
}
