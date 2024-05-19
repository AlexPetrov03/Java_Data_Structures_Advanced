import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
    }

    public boolean contains(T item) {
        Node<T> node = this.search(this.root, item);
        return node != null;
    }

    public void insert(T item) {
        this.root = this.insert(this.root, item);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            node.left = this.insert(node.left, item);
        } else if (cmp > 0) {
            node.right = this.insert(node.right, item);
        }
        
        updateHeight(node);
        node = balance(node);

        return node;
    }
    
    private Node<T> rotateRight(Node<T> node){
    	Node<T> temp = node.left;
    	node.left = temp.right;
    	temp.right = node;
    	//updateHeight(node);
    	//updateHeight(temp);
    	return temp;
    	
    }
    private Node<T> rotateLeft(Node<T> node){
    	Node<T> temp = node.right;
    	node.right = temp.left;
    	temp.left = node;
    	//updateHeight(node);
    	//updateHeight(temp);
    	return temp;
    }
    
    private Node<T> balance(Node<T> node) {
    	int balanced = getHeight(node.left) - getHeight(node.right);
    	if(balanced < -1) {
    		return rotateLeft(node);
    	}
    	else if(balanced > 1) {
    		return rotateRight(node);
    	}
    	
    	return node;
    }

    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            return search(node.left, item);
        } else if (cmp > 0) {
            return search(node.right, item);
        }
        

        return node;
    }
    private void updateHeight(Node<T> node) {
    	node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }
    
    private int getHeight(Node<T> node) {
    	if(node == null) {
    		return -1;
    	}
    	 return node.height;
    }
}
