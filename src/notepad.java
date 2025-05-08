import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Notepad extends JFrame implements ActionListener {
    private JTextArea textArea = new JTextArea();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem openFile = new JMenuItem("Open");
    private JMenuItem saveFile = new JMenuItem("Save");
    private JMenuItem close = new JMenuItem("Close");

    public Notepad() {
        this.setSize(500, 300);
        this.setTitle("Java Notepad");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.setJMenuBar(menuBar);
        menuBar.add(fileMenu);

        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openFile.addActionListener(this);
        fileMenu.add(openFile);

        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveFile.addActionListener(this);
        fileMenu.add(saveFile);

        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK));
        close.addActionListener(this);
        fileMenu.add(close);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            this.dispose();
        } else if (e.getSource() == openFile) {
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                textArea.setText("");
                try (Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()))) {
                    while (scan.hasNextLine()) {
                        textArea.append(scan.nextLine() + "\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
                }
            }
        } else if (e.getSource() == saveFile) {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try (BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()))) {
                    out.write(textArea.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Notepad app = new Notepad();
            app.setVisible(true);
        });
    }
}
