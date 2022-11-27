package DAA_CP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


class LZ77 extends JFrame
{
    public static final int DEFAULT_BUFF_SIZE = 1024;
    protected int mBufferSize;
    protected Reader mIn;
    protected PrintWriter mOut;
    protected StringBuffer mSearchBuffer;

    JButton encodeBtn, decodeBtn;
    JLabel inputFileSizeLabel,outputFileSizeLabel,compressionRatioLabel;
    private JPanel lz77Panel;
    private JLabel lz77Label;
    private JTextField outputFileSizeTextField;
    private JTextField compressionRatioTextField;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextField inputFileSizeTextField;
    Helper helper;
    LZ77() {
        // Helper Object
        helper = new Helper();

        setLayout(null);
        setContentPane(lz77Panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputFileSizeLabel.setVisible(false);
        outputFileSizeLabel.setVisible(false);
        compressionRatioLabel.setVisible(false);
        inputFileSizeTextField.setVisible(false);
        outputFileSizeTextField.setVisible(false);
        compressionRatioTextField.setVisible(false);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String inputPath = helper.chooseFile(true);
                try {
                    compress(inputPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String outputPath = helper.chooseFile(true);
                System.out.println("File Saved Successfully");
                long inputFileSize = helper.calcFileSize(inputPath);
                long outputFileSize = helper.calcFileSize(outputPath);
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
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String inputPath = helper.chooseFile(true);
                String str = "";
                try {
                    str = unCompress(inputPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String outputPath = helper.chooseFile(false);
                try {
                    helper.writeFile(outputPath,str);
                    System.out.println("File Saved Successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void trimSearchBuffer() {
        if (mSearchBuffer.length() > mBufferSize) {
            mSearchBuffer =
                    mSearchBuffer.delete(0,  mSearchBuffer.length() - mBufferSize);
        }
    }

    public void compress(String infile) throws IOException {
        // set up input and output
        mIn = new BufferedReader(new FileReader(infile));
        mOut = new PrintWriter(new BufferedWriter(new FileWriter(infile+".lz77")));

        mBufferSize = DEFAULT_BUFF_SIZE;
        mSearchBuffer = new StringBuffer(mBufferSize);

        int nextChar;
        String currentMatch = "";
        int matchIndex = 0, tempIndex = 0;

        // while there are more characters - read a character
        while ((nextChar = mIn.read()) != -1) {
            // look in our search buffer for a match
            tempIndex = mSearchBuffer.indexOf(currentMatch + (char)nextChar);
            // if match then append nextChar to currentMatch
            // and update index of match
            if (tempIndex != -1) {
                currentMatch += (char)nextChar;
                matchIndex = tempIndex;
            } else {
                // found longest match, now should we encode it?
                String codedString =
                        "~"+matchIndex+"~"+currentMatch.length()+"~"+(char)nextChar;
                String concat = currentMatch + (char)nextChar;
                // is coded string shorter than raw text?
                if (codedString.length() <= concat.length()) {
                    mOut.print(codedString);
                    mSearchBuffer.append(concat); // append to the search buffer
                    currentMatch = "";
                    matchIndex = 0;
                } else {
                    // otherwise, output chars one at a time from
                    // currentMatch until we find a new match or
                    // run out of chars
                    currentMatch = concat; matchIndex = -1;
                    while (currentMatch.length() > 1 && matchIndex == -1) {
                        mOut.print(currentMatch.charAt(0));
                        mSearchBuffer.append(currentMatch.charAt(0));
                        currentMatch = currentMatch.substring(1, currentMatch.length());
                        matchIndex = mSearchBuffer.indexOf(currentMatch);
                    }
                }
                // Adjust search buffer size if necessary
                trimSearchBuffer();
            }
        }
        // flush any match we may have had when EOF encountered
        if (matchIndex != -1) {
            String codedString =
                    "~"+matchIndex+"~"+currentMatch.length();
            if (codedString.length() <= currentMatch.length()) {
                mOut.print("~"+matchIndex+"~"+currentMatch.length());
            } else {
                mOut.print(currentMatch);
            }
        }
        // close files
        mIn.close();
        mOut.flush(); mOut.close();
    }

    public String unCompress(String infile) throws IOException {
        mIn = new BufferedReader(new FileReader(infile));
        StreamTokenizer st = new StreamTokenizer(mIn);
        mBufferSize = DEFAULT_BUFF_SIZE;
        mSearchBuffer = new StringBuffer(mBufferSize);
        String str="";

        st.ordinaryChar((int)' ');
        st.ordinaryChar((int)'.');
        st.ordinaryChar((int)'-');
        st.ordinaryChar((int)'\n');
        st.wordChars((int)'\n', (int)'\n');
        st.wordChars((int)' ', (int)'}');

        int offset, length;
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            switch (st.ttype) {
                case StreamTokenizer.TT_WORD:
                    mSearchBuffer.append(st.sval);
                    System.out.print(st.sval);
                    str+=st.sval;
                    // Adjust search buffer size if necessary
                    trimSearchBuffer();
                    break;
                case StreamTokenizer.TT_NUMBER:
                    offset = (int)st.nval; // set the offset
                    st.nextToken(); // get the separator (hopefully)
                    if (st.ttype == StreamTokenizer.TT_WORD) {
                        // we got a word instead of the separator,
                        // therefore the first number read was actually part of a word
                        mSearchBuffer.append(offset+st.sval);
                        System.out.print(offset+st.sval);
                        str+=(offset+st.sval);
                        break; // break out of the switch
                    }
                    // if we got this far then we must be reading a
                    // substitution pointer
                    st.nextToken(); // get the length
                    length = (int)st.nval;
                    // output substring from search buffer
                    String output = mSearchBuffer.substring(offset, offset+length);
                    System.out.print(output);
                    mSearchBuffer.append(output);
                    str+=output;
                    // Adjust search buffer size if necessary
                    trimSearchBuffer();
                    break;
                default:
                    // consume a '~'
            }
        }
        mIn.close();
        return str;
    }
}