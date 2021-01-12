import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileHandler {
    public static String readFile(String filename){
        String string = "";
        try{
            string = new String(Files.readAllBytes(Paths.get(filename)));
        }catch (IOException e){
            e.printStackTrace();
        }
        return string;
    }


    public static void WriteCompressedFile(Map<Character, String> huffmanCode , int Padding , StringBuilder ascii) throws IOException{
        File file = new File("Compressed File.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        Writer outputStream = new OutputStreamWriter(new FileOutputStream(file.getName(), false), "ISO_8859_1");
        outputStream.write(Padding + "\n");
        outputStream.write(huffmanCode.size() + "\n");

        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            
            outputStream.write(entry.getKey() + entry.getValue() + "\n");

        }
        outputStream.write(String.valueOf(ascii));

        outputStream.flush();
        //System.out.println("File written Successfully");
        outputStream.close();
    }
}
