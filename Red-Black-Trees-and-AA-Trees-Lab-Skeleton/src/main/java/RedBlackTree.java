import java.util.function.Consumer;

public class RedBlackTree<T extends Comparable<T>> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
    private Node<T> root;

    public RedBlackTree() {
    	this.root = null;
    }

    private RedBlackTree(Node<T> node) {
        this.preOrderCopy(node);
    }

    private void preOrderCopy(Node<T> node) {
        if (node == null) {
            return;
        }

        this.insert(node.value);
        this.preOrderCopy(node.left);
        this.preOrderCopy(node.right);
    }

    public int getNodesCount() {
        return this.getNodesCount(this.root);
    }

    private int getNodesCount(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.count;
    }

    // TODO:
    //  Insert using iteration over the nodes
    //  You can make a recursive one it is up to you
    //  The difference is that the recursive call should
    //  return Node
    public void insert(T value) {
    	this.root = this.insert(root, value);
    	this.root.color = BLACK;
    }
    
    private Node<T> insert(Node<T> node, T value){
    	if(node == null) {
    		return new Node<>(value);
    	}
    	if(value.compareTo(node.value) < 0) {
    		node.left = this.insert(node.left, value);
    	}
    	else {
    		node.right = this.insert(node.right, value);
    	}
    	
    	if(!isRed(node.left) && isRed(node.right)) {
    		node = rotateLeft(node); 
    	}
    	else if(isRed(node.left) && isRed(node.left.left)) {
    		node = rotateRight(node);
    		flipColors(node);
    	}
    	else if(isRed(node.left) && isRed(node.left.right)) {
    		node.left = rotateLeft(node.left);
    		node = rotateRight(node);
    		flipColors(node);
    	}
    	else if(isRed(node.left) && isRed(node.right)) {
    		flipColors(node);
    	}
    	node.count = getNodesCount(node.left) + getNodesCount(node.right) + 1;
    	return node;
    }
    
    private Node<T> rotateLeft(Node<T> node){
    	Node<T> right = node.right;
    	
    	node.right = right.left;
    	right.left = node;
    	
    	right.color = BLACK;
    	node.color = RED;
    	
    	node.count = 1 + this.getNodesCount(node.left)
    		+ this.getNodesCount(node.right);
    	right.count = 1 + this.getNodesCount(right.left) + this.getNodesCount(right.right);

    	
    	return right;
    }
    
    private Node<T> rotateRight(Node<T> node){
    	Node<T> left = node.left;
    	
    	node.left = left.right;
    	left.right = node;
    	
    	left.color = BLACK;
    	node.color = RED;
    	
    	node.count = 1 + this.getNodesCount(node.left) + this.getNodesCount(node.right);
    	left.count = 1 + this.getNodesCount(left.left) + this.getNodesCount(left.right);
    	
    	return left;
    }
    
    private void flipColors(Node<T> node){
    	node.color = RED;
    	node.left.color = BLACK;
    	node.right.color = BLACK;
    }

    public boolean contains(T value) {
        return this.findElement(value) != null;
    }

    public RedBlackTree<T> search(T item) {
        return new RedBlackTree<>(this.findElement(item));
    }

    private Node<T> findElement(T item) {
        Node<T> current = this.root;
        while (current != null) {
            if (item.compareTo(current.value) < 0) {
                current = current.left;
            } else if (item.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }
        return current;
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, consumer);
        consumer.accept(node.value);
        this.eachInOrder(node.right, consumer);
    }
    
    private boolean isRed(Node<T> node) {
    	if(node == null) return false;
    	return node.color;
    }

    public static class Node<T extends Comparable<T>> {
        private T value;
        private Node<T> left;
        private Node<T> right;
        private boolean color;
        private int count;
        
        public Node(T value) {
        	this.value = value;
        	left = null;
        	right = null;
        	color = RED;
        	this.count = 1;
        }
        public Node(T value, boolean color) {
        	this.value = value;
        	this.left = null;
        	this.right = null;
        	this.color = color;
        	this.count = 1;
        }
    }
}

