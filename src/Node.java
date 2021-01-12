public class Node {
    char character;
    int frequency;
    Node leftChild;
    Node rightChild;

    public static boolean isLeaf(Node root){
        return root.leftChild == null && root.rightChild == null;
    }

    public static void printNode(Node node){
        System.out.println(node.frequency+"-"+node.character);
    }
}
