import java.util.function.Consumer;

class AATree<T extends Comparable<T>> {
	Node<T> root;
    public static class Node<T> {
        Node<T> left;
        Node<T> right;
        int level, count;
        T value;
        public Node(T value) {
        	this.value = value;
        	this.level = 1;
        	this.left = null;
        	this.right = null;
        	this.count = 1;
        }
    }

    public AATree() {
    	this.root = null;
    }

    public boolean isEmpty() {
        return this.countNodes() == 0;
    }

    public void clear() {
    	this.root = this.clear(this.root);
    }
    
    private Node<T> clear(Node<T> node){
    	if(node == null) { 
    		return null;
    	}
    	node.left = clear(node.left);
    	node.right = clear(node.right);
    	node = null;
    	return node;
    }

    public void insert(T element) {
    	this.root = insert(this.root, element);
    }
    
    private Node<T> insert(Node<T> node, T value){
    	if(node == null) {
    		return new Node<T>(value);
    	}
    	if(value.compareTo(node.value) < 0) {
    		node.left = insert(node.left, value);
    	}
    	else {
    		node.right = insert(node.right, value);
    	}
    	
    	node = this.skew(node);
    	node = this.split(node);
    	
    	node.count = 1 + this.getCount(node.left) + this.getCount(node.right);
    	
    	return node;
    }
    

    private Node<T> split(Node<T> node) {
		if(node.right == null || node.right.right == null) {
			return node;
		}
		
		if(node.level == node.right.level && node.level == node.right.right.level) {
			
			Node<T> result = node.right;
			node.right = null;
			result.left = node;
			result.level++;
			
			result.left.count--; 
			
			node = result;
		}
		return node;
	}

	private Node<T> skew(Node<T> node) {
		if(node.left == null) {
			return node;
		}
		if(node.level == node.left.level) {
			Node<T> result = node.left;
			result.right = node;
			node.left = null;
			node = result;
		}
		
		return node;
	}
	
	private int getCount(Node<T> node) {
		if(node == null) return 0;
		else return node.count;
	}

	public int countNodes() {
        if(this.root == null) {
        	return 0;
        }
        return this.root.count;
    }

    public boolean search(T element) {
    	Node<T> current = this.root;
        while (current != null) {
            if (element.compareTo(current.value) < 0) {
                current = current.left;
            } else if (element.compareTo(current.value) > 0) {
                current = current.right;
            } else if(element.compareTo(current.value) == 0){
                return true;
            }
        }
        return false;
    }

    public void inOrder(Consumer<T> consumer) {

    }

    public void preOrder(Consumer<T> consumer) {

    }

    public void postOrder(Consumer<T> consumer) {

    }
}