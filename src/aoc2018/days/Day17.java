package aoc2018.days;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day17 extends Day {

	private final int SAND = 0;
	private final int CLAY = 1;
	private final int STILL = 2;
	private final int FLOW = 3;
	private final int AQUIFER = 4;
	
	private class Point {
		public int x;
		public int y;
		
		public Point(int y, int x) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return "(" + y + ", " + x + ")";
		}
	}
	
	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int minX = 300;
		int maxX = 700;
		int minY = 999;
		int maxY = 0;
		for (String row: rows) {
			String[] fragments = row.split(", ");
			int testY = -1;
			if (fragments[0].charAt(0) == 'x') {
				testY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[1]);
				if (testY > maxY) {
					maxY = testY;
				}
				
				testY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[0]);
				if (testY < minY) {
					minY = testY;
				}
			} else {
				testY = Integer.parseInt(fragments[0].split("=")[1]);
				if (testY > maxY) {
					maxY = testY;
				} else if (testY < minY) {
					minY = testY;
				}
			}
		}
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		for (int i = 0; i <= maxY; i++) {
			map.add(new ArrayList<Integer>());
			for (int j = 0; j <= maxX - minX; j++) {
				map.get(i).add(SAND);
			}
		}
		
		for (String row: rows) {
			String[] fragments = row.split(", ");
			if (fragments[0].charAt(0) == 'x') {
				int x = Integer.parseInt(fragments[0].split("=")[1]);
				int startY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[0]);
				int endY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[1]);
				
				for (int i = startY; i <= endY; i++) {
					map.get(i).set(x - minX, CLAY);
				}
			} else {
				int y = Integer.parseInt(fragments[0].split("=")[1]);
				int startX = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[0]);
				int endX = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[1]);
				
				for (int i = startX - minX; i <= endX - minX; i++) {
					map.get(y).set(i, CLAY);
				}
			}
		}
		
		map.get(0).set(500 - minX, AQUIFER);
		map.get(1).set(500 - minX, FLOW);
		List<Point> flows = new ArrayList<Point>();
		flows.add(new Point(1, 500 - minX));
		List<Point> toAdd = new ArrayList<Point>();
		List<Point> toRemove = new ArrayList<Point>();
		
		for (int t = 0; t < 5000; t++) {
			for (Point flow: flows) {
				if (flow.y == maxY) {
					continue;
				}
				
				if (map.get(flow.y).get(flow.x) != FLOW) {
					continue;
				}

				switch (map.get(flow.y + 1).get(flow.x)) {
					case SAND:
						map.get(flow.y + 1).set(flow.x, FLOW);
						toAdd.add(new Point(flow.y + 1, flow.x));
						break;
					case CLAY:
					case STILL:
						if (map.get(flow.y).get(flow.x - 1) == FLOW && map.get(flow.y).get(flow.x + 1) == FLOW) {
							continue;
						}
						
						boolean leftBowl = false;
						boolean rightBowl = false;
						int leftX = -1;
						int rightX = -1;
						for (int i = flow.x; leftBowl == false; i--) {
							if (map.get(flow.y).get(i) != FLOW) {
								map.get(flow.y).set(i, FLOW);
								toAdd.add(new Point(flow.y, i));
							}
							
							if (map.get(flow.y + 1).get(i) != CLAY && map.get(flow.y + 1).get(i) != STILL) {
								break;
							}
							
							if (map.get(flow.y).get(i - 1) == CLAY) {
								leftBowl = true;
								leftX = i;
							}
						}
						
						for (int i = flow.x; rightBowl == false; i++) {
							if (map.get(flow.y).get(i) != FLOW) {
								map.get(flow.y).set(i, FLOW);
								toAdd.add(new Point(flow.y, i));
							}
							
							if (map.get(flow.y + 1).get(i) != CLAY && map.get(flow.y + 1).get(i) != STILL) {
								break;
							}
							
							if (map.get(flow.y).get(i + 1) == CLAY) {
								rightBowl = true;
								rightX = i;
							}
						}
						
						if (leftBowl && rightBowl) {
							for (int i = leftX; i <= rightX; i++) {
								toRemove.add(new Point(flow.y, i));
								map.get(flow.y).set(i, STILL);
							}
						}
						
						break;
				}
			}
			
			for (Point flow: toAdd) {
				flows.add(flow);
			}
			toAdd = new ArrayList<Point>();
			
			for (Point flow: toRemove) {
				removeFlow(flows, flow);
			}
			toRemove = new ArrayList<Point>();
		}
		
		int total = 1 - minY;
		for (int i = 0; i <= maxY; i++) {
			for (int j = 0; j <= maxX - minX; j++) {
				int value = map.get(i).get(j);
				if (value == FLOW || value == STILL) {
					total++;
				}
			}
		}
		System.out.println(total);

		//printMap(map, minX, maxX, maxY);
	}

	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int minX = 300;
		int maxX = 700;
		int minY = 999;
		int maxY = 0;
		for (String row: rows) {
			String[] fragments = row.split(", ");
			int testY = -1;
			if (fragments[0].charAt(0) == 'x') {
				testY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[1]);
				if (testY > maxY) {
					maxY = testY;
				}
				
				testY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[0]);
				if (testY < minY) {
					minY = testY;
				}
			} else {
				testY = Integer.parseInt(fragments[0].split("=")[1]);
				if (testY > maxY) {
					maxY = testY;
				} else if (testY < minY) {
					minY = testY;
				}
			}
		}
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		for (int i = 0; i <= maxY; i++) {
			map.add(new ArrayList<Integer>());
			for (int j = 0; j <= maxX - minX; j++) {
				map.get(i).add(SAND);
			}
		}
		
		for (String row: rows) {
			String[] fragments = row.split(", ");
			if (fragments[0].charAt(0) == 'x') {
				int x = Integer.parseInt(fragments[0].split("=")[1]);
				int startY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[0]);
				int endY = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[1]);
				
				for (int i = startY; i <= endY; i++) {
					map.get(i).set(x - minX, CLAY);
				}
			} else {
				int y = Integer.parseInt(fragments[0].split("=")[1]);
				int startX = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[0]);
				int endX = Integer.parseInt(fragments[1].split("=")[1].split("\\.\\.")[1]);
				
				for (int i = startX - minX; i <= endX - minX; i++) {
					map.get(y).set(i, CLAY);
				}
			}
		}
		
		map.get(0).set(500 - minX, AQUIFER);
		map.get(1).set(500 - minX, FLOW);
		List<Point> flows = new ArrayList<Point>();
		flows.add(new Point(1, 500 - minX));
		List<Point> toAdd = new ArrayList<Point>();
		List<Point> toRemove = new ArrayList<Point>();
		
		for (int t = 0; t < 5000; t++) {
			for (Point flow: flows) {
				if (flow.y == maxY) {
					continue;
				}
				
				if (map.get(flow.y).get(flow.x) != FLOW) {
					continue;
				}

				switch (map.get(flow.y + 1).get(flow.x)) {
					case SAND:
						map.get(flow.y + 1).set(flow.x, FLOW);
						toAdd.add(new Point(flow.y + 1, flow.x));
						break;
					case CLAY:
					case STILL:
						if (map.get(flow.y).get(flow.x - 1) == FLOW && map.get(flow.y).get(flow.x + 1) == FLOW) {
							continue;
						}
						
						boolean leftBowl = false;
						boolean rightBowl = false;
						int leftX = -1;
						int rightX = -1;
						for (int i = flow.x; leftBowl == false; i--) {
							if (map.get(flow.y).get(i) != FLOW) {
								map.get(flow.y).set(i, FLOW);
								toAdd.add(new Point(flow.y, i));
							}
							
							if (map.get(flow.y + 1).get(i) != CLAY && map.get(flow.y + 1).get(i) != STILL) {
								break;
							}
							
							if (map.get(flow.y).get(i - 1) == CLAY) {
								leftBowl = true;
								leftX = i;
							}
						}
						
						for (int i = flow.x; rightBowl == false; i++) {
							if (map.get(flow.y).get(i) != FLOW) {
								map.get(flow.y).set(i, FLOW);
								toAdd.add(new Point(flow.y, i));
							}
							
							if (map.get(flow.y + 1).get(i) != CLAY && map.get(flow.y + 1).get(i) != STILL) {
								break;
							}
							
							if (map.get(flow.y).get(i + 1) == CLAY) {
								rightBowl = true;
								rightX = i;
							}
						}
						
						if (leftBowl && rightBowl) {
							for (int i = leftX; i <= rightX; i++) {
								toRemove.add(new Point(flow.y, i));
								map.get(flow.y).set(i, STILL);
							}
						}
						
						break;
				}
			}
			
			for (Point flow: toAdd) {
				flows.add(flow);
			}
			toAdd = new ArrayList<Point>();
			
			for (Point flow: toRemove) {
				removeFlow(flows, flow);
			}
			toRemove = new ArrayList<Point>();
		}
		
		int total = 0;
		for (int i = 0; i <= maxY; i++) {
			for (int j = 0; j <= maxX - minX; j++) {
				int value = map.get(i).get(j);
				if (value == STILL) {
					total++;
				}
			}
		}
		System.out.println(total);
		
		//printMap(map, minX, maxX, maxY);
	}
	
	private void removeFlow(List<Point> flows, Point flowlike) {
		for (Point flow: flows) {
			if (flow.x == flowlike.x && flow.y == flowlike.y) {
				flows.remove(flow);
				return;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void printMap(List<List<Integer>> map, int minX, int maxX, int maxY) {
	    FileOutputStream file;
		try {
			file = new FileOutputStream("output/clays.txt", false);
			file.write("\r\n".getBytes());
			file.close();
			file = new FileOutputStream("output/clays.txt", true);
			for (int i = 0; i <= maxY; i++) {
				String row = "";
				for (int j = minX; j <= maxX; j++) {
					switch (map.get(i).get(j - minX)) {
						case SAND:
							row += ".";
							break;
						case CLAY:
							row += "#";
							break;
						case STILL:
							row += "~";
							break;
						case FLOW:
							row += "|";
							break;
						case AQUIFER:
							row += "+";
							break;
					}
				}
				file.write((row + "\r\n").getBytes());
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
