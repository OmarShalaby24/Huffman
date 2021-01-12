import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Compression {

    public static void encode(Node root, String str, Map<Character, String> encodings) {

        if (root == null) {
            return;
        }
        // Found a leaf node
        if (Node.isLeaf(root)) {
            if (str.length() > 0) {
                encodings.put(root.character, str);
            } else {
                encodings.put(root.character, "1");
            }
        }

        encode(root.leftChild, str + '0', encodings);
        encode(root.rightChild, str + '1', encodings);

    }
    public static void compressFile(Node root, char[] characters) throws IOException {


        Map<Character, String> HuffCodes = new HashMap<>();
        encode(root, "", HuffCodes);
        // Print the Huffman codes
        //System.out.println("Huffman Codes are: " + HuffCodes);

        // Print encoded string
        StringBuilder encodedString = new StringBuilder();
        for (char c : characters) {
            encodedString.append(HuffCodes.get(c));
        }

        //System.out.println("Encoded string is: " + encodedString);


        int zeroPaddingNum = 0;
        if (((encodedString.length()) % 8) != 0) {
            zeroPaddingNum = 8 - (((encodedString.length()) % 8));
            for (int i = 0; i < zeroPaddingNum; i++) {
                encodedString.append("0");
            }
            //System.out.println("Encoded string with zero padding: " + encodedString);
        }


        StringBuilder asciii = new StringBuilder();

        for (int j = 0; j < (encodedString.length()) / 8; j++) {
            int ascii = Integer.parseInt(encodedString.substring(8 * j, (j + 1) * 8), 2);
            char x = (char) ascii;
            asciii.append(x);
            //asciii.append(x);
        }

        FileHandler.WriteCompressedFile(HuffCodes, zeroPaddingNum, asciii);
        //Decompression.DecompressFile("Compressed File.txt");
    }
}
