import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        while (true){menu();}


    }

    public static void menu() throws IOException {
        String input = "input.txt";
        String compressed = "Compressed File.txt";
        String decoded = "Decompressed file.txt";

        String filename;
        Scanner sc = new Scanner(System.in);

        //filename = "input.txt";
        System.out.println("**********************");
        System.out.println("      Main  Menu      ");
        System.out.println("**********************");
        System.out.print("Enter File Name:");
        filename = sc.nextLine();


        System.out.println("\nChoose an option to continue: ");
        System.out.println("1. Compress a file ");
        System.out.println("2. Decompress a file ");
        System.out.println("3. Exit ");
        System.out.print("Please enter your choice: ");


        int choice;
        choice = sc.nextInt();
        switch (choice){
            case 1:

                String str = FileHandler.readFile(input);
                Node root = buildHuffmanTree(str);
                char[] characters = str.toCharArray();
                Compression.compressFile(root, characters);
                Compress(str);
                break;
            case 2:
                Decompress(filename);
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.err.println("invalid input");
        }

    }

    public static void Compress(String fileContent) throws IOException {
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
        //System.out.println(HashFreq);
        PriorityQueue<Node> Queue = new PriorityQueue<>(HashFreq.size() + 1, new MyComparator());

        HashFreq.entrySet().forEach(entry -> {
            Node node = new Node();
            node.character = entry.getKey();
            node.frequency = entry.getValue();
            node.leftChild = null;
            node.rightChild = null;
            Queue.add(node);
        });

        Node root = new Node();
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

        //System.out.println(Queue.peek().frequency);
        return root;
    }


}