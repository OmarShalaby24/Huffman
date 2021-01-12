import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Compression {

    public static void encodeHuffmanTree(Node root, String str, Map<Character, String> encodings) {

        if (root == null) {
            return;
        }

        if (Node.isLeaf(root)) {
            if (str.length() > 0) {
                encodings.put(root.character, str);
            } else {
                encodings.put(root.character, "1");
            }
        }

        encodeHuffmanTree(root.leftChild, str + '0', encodings);
        encodeHuffmanTree(root.rightChild, str + '1', encodings);

    }

    public static void compressFile(Node root, char[] characters) throws IOException {


        Map<Character, String> HuffCodes = new HashMap<>();
        encodeHuffmanTree(root, "", HuffCodes);

        // Print the HashMap of Huffman codes
        System.out.println("\n**Huffman codes**");

        for (Map.Entry<Character, String> entry : HuffCodes.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }


        StringBuilder encodedString = new StringBuilder();
        for (char c : characters) {
            encodedString.append(HuffCodes.get(c));
        }


        int zeroPaddingNum = 0;

        if (((encodedString.length()) % 8) != 0) {
            zeroPaddingNum = 8 - (((encodedString.length()) % 8));
            for (int i = 0; i < zeroPaddingNum; i++) {
                encodedString.append("0");
            }
        }


        StringBuilder ascii = new StringBuilder();

        for (int j = 0; j < (encodedString.length()) / 8; j++) {
            int binary = Integer.parseInt(encodedString.substring(8 * j, (j + 1) * 8), 2);
            char x = (char) binary;
            ascii.append(x);
        }

        FileHandler.WriteCompressedFile(HuffCodes, zeroPaddingNum, ascii);
    }
}
