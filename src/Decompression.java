import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Decompression {
    public static void DecompressFile(String filename) throws IOException {
        filename = "Compressed file.txt";
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));

        Map<String, String> decodings = new HashMap<>();
        int zeroPadding;
        int hashMapSize;

        String line = br.readLine();
        zeroPadding = Integer.parseInt(line);
        line = br.readLine();
        hashMapSize = Integer.parseInt(line);


        for (int i = 0; i < hashMapSize; i++) {
            String value = new String(new char[]{(char) br.read()});
            String key = br.readLine();
//            System.out.println(key + " " + value);
            decodings.put(key, value);
        }

        //System.out.println(zeroPadding + "\n" + hashMapSize);
        for (Map.Entry<String, String> entry : decodings.entrySet()) {
            //System.out.println(entry.getKey() + "=" + entry.getValue());
        }

        StringBuilder binary = new StringBuilder();
        char cd = 'd';
        //        String ascii = "";
        while (br.ready()) {
            cd = (char) br.read();
//            System.out.println(cd);
            line = new String(new char[]{cd});
//            ascii += line;
//            System.out.println(ascii);
            binary.append(AsciiToBinary(line));
            //System.out.println(line);
        }

        //System.out.println(binary);

        if (zeroPadding > 0) {

            binary = binary.delete(binary.length() - zeroPadding, binary.length());
            //System.out.println(binary);
        }


        File f = new File("Decompressed file.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        Writer outputStream = new OutputStreamWriter(new FileOutputStream(f.getName(), false));

        //System.out.println(binary);
        int i = 0;
        char c = binary.charAt(i);
        String out;
        String key = "";
        while (i < binary.length()) {
            key += c;
            if (decodings.containsKey(key)) {
                if (decodings.get(key).equals("\\n"))
                    out = "\n";
                else if (decodings.get(key).equals("\\r")) {
                    out = "";
                } else {
                    out = decodings.get(key);
                }
                //System.out.print(out);
                //System.out.println(key+" = "+out);
                outputStream.write(out);
                key = "";
            }

            i++;
            if (i < binary.length())
                c = binary.charAt(i);
        }

        outputStream.flush();
        outputStream.close();
    }

    public static String AsciiToBinary(String asciiString) {

        byte[] bytes = asciiString.getBytes(StandardCharsets.ISO_8859_1);
        StringBuilder binary = new StringBuilder(bytes.length * 8);

        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i];
            for (int j = 0; j < 8; j++) {
                if ((value & 128) == 0) {
                    binary.append(0);
                } else {
                    binary.append(1);
                }
                value <<= 1;
            }
        }
        return binary.toString();
    }
    //001110111110011011011100111110110011100101010110001110100101000011000111
    //001110111110011011011100111110110011100101010110001110100101000011000111
    //001110111110011011011100111110110011100101010110001110100101000011000111
}
