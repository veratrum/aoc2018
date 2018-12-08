package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day6 extends Day {

	@Override
	public void part1(String content) {
		String[] lines = content.split(System.getProperty("line.separator"));
		
		List<Point> points = new ArrayList<Point>();
		for (String line: lines) {
			String[] components = line.split(", ");
			
			points.add(new Point(Integer.parseInt(components[0]), Integer.parseInt(components[1])));
		}
		
		List<List<Integer>> grid = new ArrayList<List<Integer>>();
		for (int i = 0; i < 400; i++) {
			grid.add(new ArrayList<Integer>());
			
			for (int j = 0; j < 400; j++) {
				int lowestLength = 999;
				List<Integer> closestPoints = new ArrayList<Integer>();
				for (int k = 0; k < points.size(); k++) {
					Point point = points.get(k);
					
					int distance = Math.abs(i - point.x) + Math.abs(j - point.y);
					if (distance < lowestLength) {
						lowestLength = distance;
						closestPoints = new ArrayList<Integer>();
						closestPoints.add(k);
					} else if (distance == lowestLength) {
						closestPoints.add(k);
					}
				}
				
				if (closestPoints.size() == 1) {
					int pointID = closestPoints.get(0);
					Point point = points.get(pointID);
					
					point.score++;
					if (i == 0 || j == 0 || i == 399 || j == 399) {
						point.disqualified = true;
					}
					
					grid.get(i).add(pointID);
				} else {
					grid.get(i).add(-1);
				}
			}
		}
		
		int maxValue = -1;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).disqualified) {
				continue;
			}
			
			if (points.get(i).score > maxValue) {
				maxValue = points.get(i).score;
			}
		}
		
		System.out.println(maxValue);
	}

	@Override
	public void part2(String content) {
		String[] lines = content.split(System.getProperty("line.separator"));
		
		List<Point> points = new ArrayList<Point>();
		for (String line: lines) {
			String[] components = line.split(", ");
			
			points.add(new Point(Integer.parseInt(components[0]), Integer.parseInt(components[1])));
		}
		
		int totalRegion = 0;
		for (int i = 0; i < 400; i++) {
			for (int j = 0; j < 400; j++) {
				int totalDistance = 0;
				
				for (int k = 0; k < points.size(); k++) {
					Point point = points.get(k);
					
					int distance = Math.abs(i - point.x) + Math.abs(j - point.y);
					
					totalDistance += distance;
				}
				
				if (totalDistance < 10000) {
					totalRegion++;
				}
			}
		}
		
		System.out.println(totalRegion);
	}
	
	static class Point {
		public int x;
		public int y;
		public int score;
		public boolean disqualified;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
			score = 0;
			disqualified = false;
		}
	}

}
