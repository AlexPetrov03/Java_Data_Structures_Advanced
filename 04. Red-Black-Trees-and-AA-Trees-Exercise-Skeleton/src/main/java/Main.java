import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
    	RedBlackTree<String, Integer> redBlackTree = new RedBlackTree<>();
        String[] input = {
                "S",
                "E",
                "A",
                "R",
                "C",
                "H",
                "E",
                "X",
                "A",
                "M",
                "P",
                "L",
                "E"
        };
        
        for (int i = 0; i < input.length; i++) {
            redBlackTree.put(input[i], i);
        }
        
        System.out.println(redBlackTree.height());
    }
}
