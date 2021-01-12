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
            decodings.put(key, value);
        }


        StringBuilder binary = new StringBuilder();
        char character;

        while (br.ready()) {
            character = (char) br.read();
            line = new String(new char[]{character});
            binary.append(AsciiToBinary(line));
        }


        if (zeroPadding > 0) {
            binary = binary.delete(binary.length() - zeroPadding, binary.length());
        }


        File f = new File("Decompressed file.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        Writer outputStream = new OutputStreamWriter(new FileOutputStream(f.getName(), false));

        int i = 0;
        char c = binary.charAt(i);
        String result;
        String key = "";
        while (i < binary.length()) {
            key += c;
            if (decodings.containsKey(key)) {

                result = decodings.get(key);
                outputStream.write(result);
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

}
