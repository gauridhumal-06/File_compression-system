package DAA_CP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame
{
    JButton RLE_btn, LZ77_btn, LZSS_btn, Huffman_btn;
    private JPanel dashboardPanel;
    private JLabel fileCompressionLabel;
    private JButton rleButton;
    private JButton lz77Button;
    private JButton huffmanButton;

    Dashboard()
    {
        setLayout(null); // To  set layout of the container...position components absolutely
        setContentPane(dashboardPanel); // Add components to window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // The default behavior is to simply hide the
        // JFrame when the user closes the window. To change the default behavior,

        pack(); //Window class in Java and it sizes the frame so that
        // all its contents are at or above their preferred sizes
        setLocationRelativeTo(null); // For setting the window to center
        setVisible(true); // If we set true makes window visible or hides it.

        rleButton.addActionListener(new ActionListener() { // It is mandatory to add a component to an action
            // listener in order for you to add codes when the user clicks that particular component.
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //allows the application to perform customized
                // cleanup of an object returned by a producer method or producer field
                new RLE();
            }
        });

        lz77Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LZ77();
            }
        });

        huffmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
               new Huffman();
            }
        });
    }

    public static void main(String[] args)
    {
        new Dashboard();
    }
}
