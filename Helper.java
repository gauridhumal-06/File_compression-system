package DAA_CP;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper
{
    public static String readFileAsString(String fileName) throws Exception
    {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
//        System.out.println(data);
        return data;
    }

    public static void writeFile(String fileName, String str) throws IOException
    {
        Path path = Paths.get(fileName);
        byte[] strToBytes = str.getBytes();
        Files.write(path, strToBytes);
    }

    public static long calcFileSize(String fileName)
    {
        Path path = Paths.get(fileName);
        long bytes=0;
        try {
            // size of a file (in bytes)
            bytes = Files.size(path);
            System.out.printf("\n%,d bytes%n", bytes);
            System.out.printf("%,d kilobytes%n", bytes / 1024);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bytes;
    }

    public static float calculateCompression(long inputFileSize,long outputFileSize)
    {
        float compressionPercentage = (1 - (outputFileSize/(float)inputFileSize))*100;
        return compressionPercentage;
//        System.out.println("\nCompression Percentage = "+compression_percentage+" % ");
    }

    // open = true means that we want ot show the Open Dialog window
    // open = false means that we want ot show the Save Dialog window
    public static String chooseFile(final boolean open)
    {
        JFileChooser fc = new JFileChooser("Open a File");
        File file = null;
        if(open && fc.showOpenDialog(null) == fc.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        else if(!open && fc.showSaveDialog(null) == fc.APPROVE_OPTION)
        {
            file = fc.getSelectedFile();
        }
        String name = file.getAbsolutePath();
       // System.out.println(name);
        return name;
    }
}
