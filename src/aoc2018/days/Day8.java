package aoc2018.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import aoc2018.Day;

public class Day8 extends Day {

	@Override
	public void part1(String content) {
		String[] strings = content.split(" ");
		List<Integer> values = new ArrayList<Integer>();
		for (String value: strings) {
			values.add(Integer.parseInt(value));
		}
		
		Node currentNode = new Node(-1, null, 1, 1, 0); // outer node
		currentNode.isRoot = true;
		int totalMetadata = 0;
		for (int i = 0; i < values.size();) {
			while (currentNode.remainingChildren == 0 && !currentNode.isRoot) {
				int staticI = i;
				for (; i < staticI + currentNode.metadataLength; i++) {
					totalMetadata += values.get(i);
					currentNode.metadata.add(values.get(i));
				}
				
				currentNode = currentNode.parent;
			}
			
			if (i < values.size()) {
				int children = values.get(i++);
				int metadataLength = values.get(i++);
				
				Node newNode = new Node(i, currentNode, children, children, metadataLength);
				
				currentNode.remainingChildren--;
				
				currentNode = newNode;
			}
		}
		
		System.out.println(totalMetadata);
	}

	@Override
	public void part2(String content) {
		String[] strings = content.split(" ");
		List<Integer> values = new ArrayList<Integer>();
		for (String value: strings) {
			values.add(Integer.parseInt(value));
		}
		
		Node rootNode = new Node(-1, null, 1, 1, 0); // outer node
		rootNode.isRoot = true;
		Node currentNode = rootNode;
		List<Node> allNodes = new ArrayList<Node>();
		for (int i = 0; i < values.size();) {
			while (currentNode.remainingChildren == 0 && !currentNode.isRoot) {
				int staticI = i;
				for (; i < staticI + currentNode.metadataLength; i++) {
					currentNode.metadata.add(values.get(i));
				}
				
				currentNode = currentNode.parent;
			}
			
			if (i < values.size()) {
				int children = values.get(i++);
				int metadataLength = values.get(i++);
				
				Node newNode = new Node(i, currentNode, children, children, metadataLength);
				
				currentNode.remainingChildren--;
				
				currentNode = newNode;
				allNodes.add(newNode);
			}
		}
		
		/*for (Node node: allNodes) {
			node.print();
		}
		System.out.println("---");*/
		
		rootNode.metadata.add(1);
		
		System.out.println(getValue(rootNode, allNodes));
	}

	static class Node {
		public int id;
		public Node parent;
		public int metadataLength;
		public List<Integer> metadata;
		public int remainingChildren;
		public int totalChildren;
		public boolean isRoot;
		
		public Node(int id, Node parent, int remainingChildren, int totalChildren, int metadataLength) {
			this.id = id;
			this.parent = parent;
			this.metadataLength = metadataLength;
			this.metadata = new ArrayList<Integer>();
			this.remainingChildren = remainingChildren;
			this.totalChildren = totalChildren;
		}
		
		public void print() {
			String parentID = "-";
			if (this.parent != null) {
				parentID = Integer.toString(parent.id);
			}
			
			System.out.println(id + ": " + parentID + " " + metadataLength + "(" + Arrays.toString(metadata.toArray()) + ") " + remainingChildren + "/" + totalChildren);
		}
	}
	
	private static int getValue(Node node, List<Node> allNodes) {
		if (node.totalChildren == 0) {
			int sum = 0;
			for (int value: node.metadata) {
				sum += value;
			}
			return sum;
		} else {
			int sum = 0;
			
			List<Node> children = new ArrayList<Node>();
			for (Node testNode: allNodes) {
				if (testNode.parent.id == node.id) {
					children.add(testNode);
				}
			}
			
			Collections.sort(children, new Comparator<Node>() {
				@Override
				public int compare(Node arg0, Node arg1) {
					return Integer.compare(arg0.id, arg1.id);
				}
			});
			
			for (int value: node.metadata) {
				if (value <= children.size()) {
					sum += getValue(children.get(value - 1), allNodes);
				}
			}
			return sum;
		}
	}

}
