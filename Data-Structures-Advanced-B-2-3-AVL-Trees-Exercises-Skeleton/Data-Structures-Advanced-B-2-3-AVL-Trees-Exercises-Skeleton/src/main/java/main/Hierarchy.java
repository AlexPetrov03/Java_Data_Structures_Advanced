package main;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Hierarchy<T> implements IHierarchy<T> {

	private Map<T, HierarchyNode<T>> data;
	
	HierarchyNode<T> root;
	
	public Hierarchy(T element){
		this.data = new HashMap<>();
		 HierarchyNode<T> root = new HierarchyNode<>(element);
		 this.root = root;
		 data.put(element, root);
	}

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public void add(T element, T child) {
    	if(this.data.containsKey(child)) {
    		throw new IllegalArgumentException();
    	}
    	
    	HierarchyNode<T> parent = ensureExists(element);
    	HierarchyNode<T> nodeChild = new HierarchyNode<>(child);
    	
    	nodeChild.setParent(parent);
    	parent.getChildren().add(nodeChild);
    	
    	this.data.put(parent.getValue(), parent);
    	this.data.put(child, nodeChild);
    }

    @Override
    public void remove(T element) {
    	HierarchyNode<T> toRemove = ensureExists(element);
    	if(toRemove.getParent() == null) {
    		throw new IllegalStateException();
    	}
    	HierarchyNode<T> parent = toRemove.getParent();
    	
    	List<HierarchyNode<T>> children = toRemove.getChildren();
    	
    	parent.getChildren().addAll(children);
    	
    	for(int i = 0; i < children.size(); i++) {
    		children.get(i).setParent(parent);
    	}
    	
    	parent.getChildren().remove(toRemove);
    	
    	this.data.remove(toRemove.getValue());
    }
    
    private HierarchyNode<T> ensureExists(T key) {
    	HierarchyNode<T> node = this.data.get(key);
    	if(node == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	return node;
    }

    @Override
    public Iterable<T> getChildren(T element) {
        HierarchyNode<T> parent = ensureExists(element);
        List<T> newList = new ArrayList<>();
        for(int i = 0; i < parent.getChildren().size(); i++) {
        	newList.add(parent.getChildren().get(i).getValue());
        }
        return newList;
    }

    @Override
    public T getParent(T element) {
    	HierarchyNode<T> current = ensureExists(element);
        return current.getParent() == null ? null : current.getParent().getValue();
    }

    @Override
    public boolean contains(T element) {
        return this.data.containsKey(element);
    }

    @Override
    public Iterable<T> getCommonElements(IHierarchy<T> other) {
    	List<T> commonElements = new ArrayList<>();
    	this.data.keySet().forEach(k -> {
    		if(other.contains(k)) {
    			commonElements.add(k);
    		}
    	});
        return commonElements;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
        	
        	Deque<HierarchyNode<T>> deque = new ArrayDeque<>(
        			Collections.singletonList(root));
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return deque.size() > 0;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				HierarchyNode<T> nextElement = deque.poll();
				deque.addAll(nextElement.getChildren());
				return nextElement.getValue();
			}
        	
        };
    }
}
