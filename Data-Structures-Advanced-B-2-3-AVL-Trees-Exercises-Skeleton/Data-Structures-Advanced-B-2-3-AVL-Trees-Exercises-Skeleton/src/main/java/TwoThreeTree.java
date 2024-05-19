public class TwoThreeTree<K extends Comparable<K>> {
    private TreeNode<K> root;

    public static class TreeNode<K> {
         K leftKey;
         K rightKey;

         TreeNode<K> leftChild;
         TreeNode<K> middleChild;
         TreeNode<K> rightChild;

        private TreeNode(K key) {
            this.leftKey = key;
        }

        public TreeNode(K root, K leftValue, K rightValue) {
			this.leftKey = root;
			this.leftChild = new TreeNode<>(leftValue);
			this.rightChild = new TreeNode<>(rightValue);
		}
        
        public TreeNode(K root, TreeNode<K> left, TreeNode<K> right) {
        	this.leftKey = root;
        	
        	this.leftChild = left;
        	this.rightChild = right;
        }
		boolean isThreeNode() {
            return rightKey != null;
        }

        boolean isTwoNode() {
            return rightKey == null;
        }

        boolean isLeaf() {
            return this.leftChild == null && this.middleChild == null && this.rightChild == null;
        }
    }

    public void insert(K key) {
    	if(this.root == null) {
    		this.root = new TreeNode<>(key);
    		return;
    	}
    	
    	TreeNode<K> insert = this.insert(this.root, key);
    	if(insert != null) {
    		this.root = insert;
    	}
    }

    private TreeNode<K> insert(TreeNode<K> root, K key) {
    	if(root == null) {
    		return new TreeNode<>(key);
    	}
    	if(root.isLeaf()) {
    		if(root.isTwoNode()) {
    			if(key.compareTo(root.leftKey) < 0) {
    				root.rightKey = root.leftKey;
    				root.leftKey = key;
    			}
    			else {
    				root.rightKey = key;
    			}
    			return null;
    		}
    		
    			K left = root.leftKey;
    			K middle = key;
    			K right = root.rightKey;
    			if(key.compareTo(root.leftKey) < 0) {
    				middle = left;
    				left = key;
    			}
    			else if(key.compareTo(root.rightKey) > 0) {
    				middle = right;
    				right = key;
    			}
    			
    			return new TreeNode<K>(middle, left, right);
    		
    	}
    	TreeNode<K> toFix = null;
    	if(root.isTwoNode() && key.compareTo(root.leftKey) < 0) {
    		toFix = insert(root.leftChild, key);
    	}
    	else if(root.isTwoNode() && key.compareTo(root.leftKey) > 0) {
    		toFix = insert(root.rightChild, key);
    	}
    	else if(root.isThreeNode() && key.compareTo(root.leftKey) < 0) {
    		toFix = insert(root.leftChild, key);
    	}
    	else if(root.isThreeNode() && key.compareTo(root.rightKey) > 0) {
    		toFix = insert(root.rightChild, key);
    	}
    	else {
    		toFix = insert(root.middleChild, key);
    	}
    	
    	if(toFix == null) return null; 
    	
    	if(root.isTwoNode()) {
    		if(toFix.leftKey.compareTo(root.leftKey) < 0) {
	    		root.rightKey = root.leftKey;
	    		root.leftKey = toFix.leftKey;
	    		
	    		root.leftChild = toFix.leftChild;
	    		root.middleChild = toFix.rightChild;
    		}
    		else {
    			root.rightKey = toFix.leftKey;
    			
    			root.middleChild = toFix.leftChild;
    			root.rightChild = toFix.rightChild;
    		}
    		
    		return null;
    	}
    	K promoteValue = null;
    	TreeNode<K> left = null;
    	TreeNode<K> right = null;
    	if(toFix.leftKey.compareTo(root.leftKey) < 0) {
    		promoteValue = root.leftKey;
    		
    		left = toFix;
    		right = new TreeNode<>(root.rightKey, root.middleChild, root.rightChild);
    	}
    	else if(toFix.leftKey.compareTo(root.rightKey) > 0) {
    		promoteValue = root.rightKey;
    		
    		right = toFix;
    		left = new TreeNode<>(root.leftKey, root.leftChild, root.rightChild);
    	}
    	else {
    		promoteValue = toFix.leftKey;
    		
    		left = new TreeNode<>(root.leftKey, root.leftChild, toFix.leftChild);
    		right = new TreeNode<>(root.rightKey, toFix.rightChild, root.rightChild);
    		
    	}
    	
		return new TreeNode<>(promoteValue, left, right);
		// TODO Auto-generated method stub
		
	}

	public String getAsString() {
        StringBuilder out = new StringBuilder();
        recursivePrint(this.root, out);
        return out.toString().trim();
    }

    private void recursivePrint(TreeNode<K> node, StringBuilder out) {
        if (node == null) {
            return;
        }
        if (node.leftKey != null) {
            out.append(node.leftKey)
                    .append(" ");
        }
        if (node.rightKey != null) {
            out.append(node.rightKey).append(System.lineSeparator());
        } else {
            out.append(System.lineSeparator());
        }
        if (node.isTwoNode()) {
            recursivePrint(node.leftChild, out);
            recursivePrint(node.rightChild, out);
        } else if (node.isThreeNode()) {
            recursivePrint(node.leftChild, out);
            recursivePrint(node.middleChild, out);
            recursivePrint(node.rightChild, out);
        }
    }
    public TreeNode<K> getRoot(){
    	return root;
    }
}
