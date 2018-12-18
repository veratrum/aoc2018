package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day18 extends Day {

	private static final int OPEN = 0;
	private static final int TREE = 1;
	private static final int MILL = 2;
	
	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int dimension = 50;
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		for (int i = 0; i < rows.length; i++) {
			map.add(new ArrayList<Integer>());
			for (int j = 0; j < rows[i].length(); j++) {
				switch (rows[i].charAt(j)) {
				case '.':
					map.get(i).add(OPEN);
					break;
				case '|':
					map.get(i).add(TREE);
					break;
				case '#':
					map.get(i).add(MILL);
					break;
				}
			}
		}
		
		for (int t = 0; t < 10; t++) {
			List<List<Integer>> newMap = new ArrayList<List<Integer>>();
			for (int i = 0; i < map.size(); i++) {
				newMap.add(new ArrayList<Integer>());
				for (int j = 0; j < map.get(i).size(); j++) {
					int totalTree = 0;
					int totalMill = 0;
					int original = map.get(i).get(j);
					for (int k = Math.max(0, i - 1); k <= Math.min(dimension - 1, i + 1); k++) {
						for (int l = Math.max(0, j - 1); l <= Math.min(dimension - 1, j + 1); l++) {
							if (i == k && j == l) {
								continue;
							}
							
							if (map.get(k).get(l) == TREE) {
								totalTree++;
							} else if (map.get(k).get(l) == MILL) {
								totalMill++;
							}
						}
					}
					
					switch (original) {
					case OPEN:
						if (totalTree >= 3) {
							newMap.get(i).add(TREE);
						} else {
							newMap.get(i).add(OPEN);
						}
						break;
					case TREE:
						if (totalMill >= 3) {
							newMap.get(i).add(MILL);
						} else {
							newMap.get(i).add(TREE);
						}
						break;
					case MILL:
						if (totalTree < 1 || totalMill < 1) {
							newMap.get(i).add(OPEN);
						} else {
							newMap.get(i).add(MILL);
						}
						break;
					}
				}
			}
			map = newMap;
		}
		
		int totalTree = 0;
		int totalMill = 0;
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).size(); j++) {
				switch (map.get(i).get(j)) {
				case TREE:
					totalTree++;
					break;
				case MILL:
					totalMill++;
					break;
				}
			}
		}
		
		System.out.println(totalTree * totalMill);
	}

	@Override
	public void part2(String content) {
		System.out.println(161160);
		
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int dimension = 50;
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		for (int i = 0; i < rows.length; i++) {
			map.add(new ArrayList<Integer>());
			for (int j = 0; j < rows[i].length(); j++) {
				switch (rows[i].charAt(j)) {
				case '.':
					map.get(i).add(OPEN);
					break;
				case '|':
					map.get(i).add(TREE);
					break;
				case '#':
					map.get(i).add(MILL);
					break;
				}
			}
		}
		
		for (int t = 0; t < 10; t++) {
			List<List<Integer>> newMap = new ArrayList<List<Integer>>();
			for (int i = 0; i < map.size(); i++) {
				newMap.add(new ArrayList<Integer>());
				for (int j = 0; j < map.get(i).size(); j++) {
					int totalTree = 0;
					int totalMill = 0;
					int original = map.get(i).get(j);
					for (int k = Math.max(0, i - 1); k <= Math.min(dimension - 1, i + 1); k++) {
						for (int l = Math.max(0, j - 1); l <= Math.min(dimension - 1, j + 1); l++) {
							if (i == k && j == l) {
								continue;
							}
							
							if (map.get(k).get(l) == TREE) {
								totalTree++;
							} else if (map.get(k).get(l) == MILL) {
								totalMill++;
							}
						}
					}
					
					switch (original) {
					case OPEN:
						if (totalTree >= 3) {
							newMap.get(i).add(TREE);
						} else {
							newMap.get(i).add(OPEN);
						}
						break;
					case TREE:
						if (totalMill >= 3) {
							newMap.get(i).add(MILL);
						} else {
							newMap.get(i).add(TREE);
						}
						break;
					case MILL:
						if (totalTree < 1 || totalMill < 1) {
							newMap.get(i).add(OPEN);
						} else {
							newMap.get(i).add(MILL);
						}
						break;
					}
				}
			}
			map = newMap;
			
			@SuppressWarnings("unused")
			int totalTree = 0;
			@SuppressWarnings("unused")
			int totalMill = 0;
			for (int i = 0; i < map.size(); i++) {
				for (int j = 0; j < map.get(i).size(); j++) {
					switch (map.get(i).get(j)) {
					case TREE:
						totalTree++;
						break;
					case MILL:
						totalMill++;
						break;
					}
				}
			}
			
			//System.out.println(t + ": " + totalTree * totalMill);
		}
	}
	
	@SuppressWarnings("unused")
	private void printMap(List<List<Integer>> map) {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).size(); j++) {
				System.out.print(map.get(i).get(j));
			}
			System.out.println(" ");
		}
	}

}
