import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        while (true) {
            menu();
        }
    }

    public static void menu() throws IOException {
        String input = "input.txt";
        String filename;
        Scanner sc = new Scanner(System.in);

        System.out.println("**********************");
        System.out.println("      Main  Menu      ");
        System.out.println("**********************");

        System.out.print("\nEnter File Name (Enter 0 to Exit):");
        filename = sc.nextLine();

        if (filename.equals("0"))
            System.exit(0);
        File file = new File(filename);
        if (!file.exists()) {
            System.err.println("File does not exist!");
            return;
        }


        System.out.println("\nChoose an option to continue: ");
        System.out.println("1. Compress file ");
        System.out.println("2. Decompress file ");
        System.out.println("3. Exit ");
        System.out.print("Please enter your choice: ");


        int choice;
        choice = sc.nextInt();

        switch (choice) {

            case 1:
                String str = FileHandler.readFile(input);
                Node root = buildHuffmanTree(str);
                char[] characters = str.toCharArray();
                long CompressionTime = System.nanoTime();
                Compression.compressFile(root, characters);
                Compress(str);
                CompressionTime = System.nanoTime() - CompressionTime;
                System.out.println("Total execution time: " + CompressionTime / 1000000 + "ms");
                double originalFileLength = str.length();
                File compressedFile = new File("Compressed File.txt");
                double compressedFileLength = compressedFile.length();
                double compressionRatio = (compressedFileLength / originalFileLength) * 100;
                System.out.print("Compression Ratio = ");
                System.out.print(String.format("%,.3f ", compressionRatio));
                System.out.println("%\n");
                break;

            case 2:
                long DecompressionTime = System.nanoTime();
                Decompress(filename);
                DecompressionTime = System.nanoTime() - DecompressionTime;
                System.out.println("Total execution time: " + DecompressionTime / 1000000 + "ms\n");
                break;

            case 3:
                System.exit(0);
                break;

            default:
                System.err.println("Invalid input");
        }

    }

    public static void Compress(String fileContent) {
        buildHuffmanTree(fileContent);
    }

    public static void Decompress(String filename) throws IOException {
        Decompression.DecompressFile(filename);
    }

    public static Node buildHuffmanTree(String str) {
        Map<Character, Integer> HashFreq = new HashMap<>();
        char[] characters = str.toCharArray();

        for (char c : characters) {
            if (HashFreq.containsKey(c)) {
                HashFreq.put(c, HashFreq.get(c) + 1);
            } else {
                HashFreq.put(c, 1);
            }
        }

        PriorityQueue<Node> Queue = new PriorityQueue<>(HashFreq.size() + 1, new MyComparator());

        HashFreq.entrySet().forEach(entry -> {
            Node node = new Node();
            node.character = entry.getKey();
            node.frequency = entry.getValue();
            node.leftChild = null;
            node.rightChild = null;
            Queue.add(node);
        });

        Node root;
        while (Queue.size() > 1) {
            Node n1 = Queue.poll();
            Node n2 = Queue.poll();
            Node sum = new Node();
            sum.character = 0;
            sum.frequency = n1.frequency + n2.frequency;
            sum.leftChild = n1;
            sum.rightChild = n2;
            Queue.add(sum);
        }
        root = Queue.peek();

        return root;
    }

}