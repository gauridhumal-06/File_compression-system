package DAA_CP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RLE extends JFrame
{
//    JButton encodeBtn, decodeBtn;
    JLabel inputFileSizeLabel,outputFileSizeLabel,compressionRatioLabel;
    Helper helper;
//    private JPanel RLE;
    private JLabel rleLabel;
    private JTextField inputFileSizeTextField;
    private JTextField outputFileSizeTextField;
    private JTextField compressionRatioTextField;
    private JButton encodeButton;
    private JButton decodeButton;
    private JPanel rlePanel;

    RLE()
    {
        // Helper Object
        helper = new Helper();

        setLayout(null);
        setContentPane(rlePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputFileSizeLabel.setVisible(false);
        outputFileSizeLabel.setVisible(false);
        compressionRatioLabel.setVisible(false);
        inputFileSizeTextField.setVisible(false);
        outputFileSizeTextField.setVisible(false);
        compressionRatioTextField.setVisible(false);

        pack(); //Window class in Java and it sizes the frame so that
        // all its contents are at or above their preferred sizes
        setLocationRelativeTo(null);
        setVisible(true);

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String inputPath = helper.chooseFile(true);
                //System.out.println(path);
                String str = null;
                try {
                    str = helper.readFileAsString(inputPath); // Reads the file from the given
                    // chosen file and it gets Stored in str,
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Gets the string and prints the given string and the encoded string
                String encoded_str=encode(str); // Encode calls string it is a method
                System.out.print("\nInput String = "+str);
                System.out.print("\nEncoded String = "+encoded_str);

                String outputPath = helper.chooseFile(false);
                try {
                    helper.writeFile(outputPath,encoded_str);
                    //
                    System.out.println("File Saved Successfully");
                    long inputFileSize = helper.calcFileSize(inputPath); //Size of the file which have inputed
                    long outputFileSize = helper.calcFileSize(outputPath); // Size of the output file after encoding
                    float compressionRatio = helper.calculateCompression(inputFileSize,outputFileSize);
                    System.out.println("Input File - "+inputFileSize);
                    System.out.println("Output File - "+outputFileSize);

                    inputFileSizeLabel.setVisible(true);
                    outputFileSizeLabel.setVisible(true);
                    compressionRatioLabel.setVisible(true);
                    inputFileSizeTextField.setVisible(true);
                    outputFileSizeTextField.setVisible(true);
                    compressionRatioTextField.setVisible(true);

                    inputFileSizeTextField.setText(Long.toString(inputFileSize));
                    outputFileSizeTextField.setText(Long.toString(outputFileSize));
                    compressionRatioTextField.setText(Float.toString(compressionRatio));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String inputPath = helper.chooseFile(true);
                //System.out.println(path);
                String str = null;
                try {
                    str = helper.readFileAsString(inputPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String encoded_str = decode(str);
                System.out.print("\nInput String = "+str);
                System.out.print("\nDecoded String = "+encoded_str);

                String outputPath = helper.chooseFile(false);
                try {
                    helper.writeFile(outputPath,encoded_str);
                    System.out.println("File Saved Successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Perform Run–length encoding (RLE) data compression algorithm
    // on string `str`
    public static String encode(String str) // Here it gets called
    {
        // stores output string
        String encoding = "";

        // base case
        if (str == null) {
            return encoding;
        }

        int count;

        for (int i = 0; i < str.length(); i++)
        {
            // count occurrences of character at index `i`
            count = 1;
            while (i + 1 < str.length() && str.charAt(i) == str.charAt(i + 1))
            {
                count++;
                i++;
            }

            // append current character and its count to the result
            encoding += String.valueOf(count) + str.charAt(i);
        }
        return encoding;
    }

    public static String decode(String str)
    {
        StringBuilder dest = new StringBuilder();

        for (int i = 1; i < str.length(); i = i + 2) {
            char charAt = str.charAt(i);
            int cnt = str.charAt(i - 1) - '0';
            for (int j = 0; j < cnt; j++) {
                dest.append(charAt);
            }
        }
        return (dest.toString());
    }

//    public static void main(String[] args) throws Exception
//    {
//        new Dashboard().setVisible(true);
//
//        Helper obj=new Helper();
//        //String str = "AAABBBBBBBB  CCCCDDDDD";
//        //String input_file=file_obj.chooseFile();
//        String input_file="D:\\STUDY\\JAVA QUESTIONS\\src\\DAA_CP\\Input.txt";
//        String output_file="D:\\STUDY\\JAVA QUESTIONS\\src\\DAA_CP\\RLE_Output.txt";
//
//        //String str = obj.readFileAsString(input_file);
//        //String encoded_str=encode(str);
//        //System.out.print("\nInput String = "+str);
//        //System.out.print("\nEncoded String = "+encoded_str);
//
//        //obj.writeFile(output_file,encoded_str);
//
//        long input_file_size = obj.calcFileSize(input_file);
//        long output_file_size = obj.calcFileSize(output_file);
//
//        obj.cal_compression(input_file_size,output_file_size);
//    }
}
