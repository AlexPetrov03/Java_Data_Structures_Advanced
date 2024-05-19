package main;

import java.util.ArrayList;
import java.util.List;

public class HierarchyNode<T> {
	private T value;
	private HierarchyNode<T> parent;
	private List<HierarchyNode<T>> children;
	
	private int height;
	
	public HierarchyNode(T value) {
		this.value = value;
		this.parent = null;
		this.children = new ArrayList<>();
	}
	
	public void addChild(HierarchyNode<T> child) {
		children.add(child);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public HierarchyNode<T> getParent() {
		return parent;
	}

	public void setParent(HierarchyNode<T> parent) {
		this.parent = parent;
	}

	public List<HierarchyNode<T>> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<HierarchyNode<T>> children) {
		this.children = children;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
