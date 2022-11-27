package DAA_CP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman extends JFrame
{
    JButton encodeBtn, decodeBtn;
    JLabel inputFileSizeLabel,outputFileSizeLabel,compressionRatioLabel;
    private JPanel huffmanPanel;
    private JLabel huffmanLabel;
    private JTextField outputFileSizeTextField;
    private JTextField compressionRatioTextField;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextField inputFileSizeTextField;
    static Helper helper;

    Huffman()
    {
        // Helper Object
        helper = new Helper();

//        // UI
//        encodeBtn=new JButton("Encode");
//        encodeBtn.setBackground(Color.BLACK);
//        encodeBtn.setForeground(Color.WHITE);
//        encodeBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
//        encodeBtn.setBounds(100,350,100,50);
//        encodeBtn.addActionListener(this);
//        add(encodeBtn);
//
//        decodeBtn = new JButton("Decode");
//        decodeBtn.setBackground(Color.BLACK);
//        decodeBtn.setForeground(Color.WHITE);
//        decodeBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
//        decodeBtn.setBounds(300,350,100,50);
//        decodeBtn.addActionListener(this);
//        add(decodeBtn);
//
//        inputFileSizeLabel = new JLabel();
//        inputFileSizeLabel.setFont(new Font("Tahoma",Font.PLAIN,20));
//        inputFileSizeLabel.setBounds(100,150,100,50);
//        add(inputFileSizeLabel);
//
//        outputFileSizeLabel = new JLabel();
//        outputFileSizeLabel.setFont(new Font("Tahoma",Font.PLAIN,20));
//        outputFileSizeLabel.setBounds(300,150,100,50);
//        add(outputFileSizeLabel);
//
//        compressionRatioLabel = new JLabel();
//        compressionRatioLabel.setFont(new Font("Tahoma",Font.PLAIN,20));
//        compressionRatioLabel.setBounds(200,250,100,50);
//        add(compressionRatioLabel);

        setLayout(null);
        setContentPane(huffmanPanel);
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
                //System.out.println(path);
                String str = null;
                try {
                    str = helper.readFileAsString(inputPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String arr[]=new String[2];
                arr=Main_Build_HuffmanTree(str);
                String encoded_str=arr[1];
                //Main_Build_HuffmanTree(str);
                System.out.print("\nInput String = "+str);
                //System.out.print("\nEncoded String = "+encoded_str);

                String outputPath = arr[0];
                try {
                    helper.writeFile(outputPath,encoded_str);
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
                //String encoded_str = decode(str);
                System.out.print("\nInput String = "+str);
                //System.out.print("\nDecoded String = "+encoded_str);

            /*String outputPath = helper.chooseFile(false);
            try {
                helper.writeFile(outputPath,encoded_str);
                System.out.println("File Saved Successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            }
        });
    }


    // Huffman Tree Traversing and storing the Huffman Codes in a dictionary.
    public static void encode_huffman(Huffman_Node root_node, String str,
                                      Map<Character, String> huffman_Code)
    {
        if (root_node == null) {
            return;
        }

        // if the root node is a leaf node
        if (is_Leaf(root_node)) {
            huffman_Code.put(root_node.charac, str.length() > 0 ? str : "1");
        }

        encode_huffman(root_node.left, str + '0', huffman_Code);
        encode_huffman(root_node.right, str + '1', huffman_Code);
    }

    // Huffman Tree Traversing and decoding the encoded string
    public static int decode_huffman(Huffman_Node root_node, int index, StringBuilder sb)
    {
        if (root_node == null) {
            return index;
        }

        // if the root node is a leaf node
        if (is_Leaf(root_node))
        {
            System.out.print(root_node.charac);
            return index;
        }

        index++;

        root_node = (sb.charAt(index) == '0') ? root_node.left : root_node.right;
        index = decode_huffman(root_node, index, sb);
        return index;
    }

    // This function checks if Huffman Tree contains only one single node
    public static boolean is_Leaf(Huffman_Node root_node) {
        return root_node.left == null && root_node.right == null;
    }

    // Main Huffman tree build function
    public static String[] Main_Build_HuffmanTree(String text)
    {
        String arr[]= new String[2];
        //arr[0]=Output File Path
        //arr[1]=Encoded String
        arr[0]="";
        arr[1]="";

        // Base case: empty string
        if (text == null || text.length() == 0) {
            return arr;
        }

        // Calculate the frequency of each character and store it in a map of dict

        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: text.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        // priority queue to store nodes of the Huffman tree
        // the highest priority item has the lowest frequency

        PriorityQueue<Huffman_Node> prio_queue;
        prio_queue = new PriorityQueue<>(Comparator.comparingInt(l -> l.frequency));

        // leaf node for each character, adding it to the priority queue.

        for (var entry: frequency.entrySet()) {
            prio_queue.add(new Huffman_Node(entry.getKey(), entry.getValue()));
        }

        //repeat the process till there is more than one node in the queue
        while (prio_queue.size() != 1)
        {
            // Then remove the two nodes with the highest priority and lowest frequency

            Huffman_Node left = prio_queue.poll();
            Huffman_Node right = prio_queue.poll();

            // Now create a new internal node with two children nodes, and the frequency will be the some of both nodes; add the new node to the priority queue.
            int sum = left.frequency + right.frequency;
            prio_queue.add(new Huffman_Node(null, sum, left, right));
        }

        Huffman_Node root_node = prio_queue.peek();

        // Huffman tree Traversing and storing the Huffman codes in a dict or map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode_huffman(root_node, "", huffmanCode);

        String keyFile = helper.chooseFile(false);

        arr[0]=helper.chooseFile(false);


        String map_value;
        for (Map.Entry<Character,String> entry : huffmanCode.entrySet())
        {
            map_value=entry.getKey()+"\t"+entry.getValue()+"\n";

            // Try block to check for exceptions
            try {

                // Open given file in append mode by creating an
                // object of BufferedWriter class
                BufferedWriter out = new BufferedWriter(
                        new FileWriter(keyFile, true));

                // Writing on output stream
                out.write(map_value);
                // Closing the connection
                out.close();
            }

            // Catch block to handle the exceptions
            catch (IOException e) {

                // Display message when exception occurs
                System.out.println("exception occurred" + e);
            }

            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }

        //===================================================================


        //===================================================================


        // Display the Huffman codes
        //System.out.println("The Huffman Codes for the given text are: " + huffmanCode);
        //System.out.println("The original text is: " + text);

        // display the encoded string
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        System.out.println("The encoded text is: " + sb);
        System.out.print("The decoded text is: ");

        if (is_Leaf(root_node))
        {
            // For input like a, aa, aaa, etc.
            while (root_node.frequency-- > 0) {
                System.out.print(root_node.charac);
            }
        }
        else {
            // Huffman Tree traversing with decoding the encoded string
            int index = -1;
            while (index < sb.length() - 1) {
                index = decode_huffman(root_node, index, sb);
            }
        }
        arr[1]=sb.toString();
        return arr;
    }

    // Call the Huffman code
//    public static void main(String[] args)
//    {
//        //String text = "This is delftstack";
//        //String text = "AAABBBBBBBB  CCCCDDDDDAAABBBBBBBB";
//        //Main_Build_HuffmanTree(text);
//        new Huffman().setVisible(true);
//    }
}

// A Tree node
class Huffman_Node
{
    Character charac;
    Integer frequency;
    Huffman_Node left = null, right = null;

    Huffman_Node(Character charac, Integer frequency)
    {
        this.charac = charac;
        this.frequency = frequency;
    }

    public Huffman_Node(Character charac, Integer frequency, Huffman_Node left, Huffman_Node right)
    {
        this.charac = charac;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}