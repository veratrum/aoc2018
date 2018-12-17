package aoc2018.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aoc2018.Day;

public class Day15 extends Day {
	
	private int WALL = 0;
	private int FLOOR = 1;
	
	private final int UP = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int DOWN = 3;
	
	private int unitID = 1;
	
	class Unit implements Comparable<Unit> {
		public boolean isElf;
		public int health;
		public int attack;
		public Point p;
		public int id;
		
		public Unit(boolean isElf, int x, int y, int attack) {
			this.isElf = isElf;
			this.health = 200;
			this.attack = attack;
			this.p = new Point(x, y);
			this.id = unitID++;
		}
		
		public void move(int direction) {
			this.p.move(direction);
		}

		@Override
		public int compareTo(Unit unit2) {
			return this.p.compareTo(unit2.p);
		}
		
		@Override
		public String toString() {
			String side = isElf ? "Elf" : "Gob";
			return "[ " + side + " " + this.id + " ] " + this.health + " " + this.p;
		}
	}
	
	class Point implements Comparable<Point>, Cloneable {
		public int x;
		public int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void move(int direction) {
			switch (direction) {
			case UP:
				this.y--;
				break;
			case DOWN:
				this.y++;
				break;
			case RIGHT:
				this.x++;
				break;
			case LEFT:
				this.x--;
				break;
			}
		}

		@Override
		public int compareTo(Point point2) {
			if (this.y == point2.y) {
				return Integer.compare(this.x, point2.x);
			}
			
			return Integer.compare(this.y, point2.y);
		}
		
		@Override
		public Point clone() {
			try {
				return (Point) super.clone();
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}
		
		@Override
		public boolean equals(Object object2) {
			Point point2 = (Point) object2;
			
			return (this.x == point2.x && this.y == point2.y);
		}
		
		@Override
		public String toString() {
			return "(" + this.x + ", " + this.y + ")";
		}
	}
	
	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		List<Unit> units = new ArrayList<Unit>();
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		for (int i = 0; i < rows.length; i++) {
			map.add(new ArrayList<Integer>());
			for (int j = 0; j < rows[i].length(); j++) {
				int value = -1;
				switch(rows[i].charAt(j)) {
				case '#':
					value = WALL;
					break;
				case 'E':
					value = FLOOR;
					units.add(new Unit(true, j, i, 3));
					break;
				case 'G':
					value = FLOOR;
					units.add(new Unit(false, j, i, 3));
					break;
				case '.':
					value = FLOOR;
					break;
				}
				
				map.get(i).add(value);
			}
		}
		
		boolean done = false;
		int turn = 0;
		int outcome = -1;
		main:
		while (!done) {
			Collections.sort(units);
			
			for (int i = 0; !done && i < units.size(); i++) {
				Unit unit = units.get(i);
				
				
				List<Unit> targetUnits = getTargetUnits(units, unit);
				if (targetUnits.size() == 0) { // one side has achieved victory
					done = true;
					outcome = getOutcome(turn, units);
					System.out.println(outcome);
					break main;
				}
				
				Unit adjacentEnemy = isAdjacentToEnemy(unit, targetUnits, map);
				if (adjacentEnemy == null) {
					List<Point> inRangeSquares = getInRangeSquares(targetUnits, units, unit, map);
					if (inRangeSquares.size() == 0) { // unit is blocked in by other units or something
						continue;
					}
					
					List<Point> bestPath = getPath(unit.p, inRangeSquares, map, units);
					if (bestPath != null) { // a valid move exists
						unit.p = bestPath.get(1);
					}
					
					Unit adjacentEnemy2 = isAdjacentToEnemy(unit, targetUnits, map);
					if (adjacentEnemy2 != null) {
						adjacentEnemy2.health -= unit.attack;
						if (adjacentEnemy2.health <= 0) {
							int index = units.indexOf(adjacentEnemy2);
							if (index < i) {
								i--;
							}
							units.remove(adjacentEnemy2);
						}
					}
				} else {
					adjacentEnemy.health -= unit.attack;
					if (adjacentEnemy.health <= 0) {
						int index = units.indexOf(adjacentEnemy);
						if (index < i) {
							i--;
						}
						units.remove(adjacentEnemy);
					}
				}
			}
			turn++;
		}
	}

	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));

		int elfAttack = 3;
		int turn = 0;
		int outcome = -1;
		int initialElves = 10;
		int finalElves = 999;
		while (initialElves != finalElves) {
			turn = 0;
			outcome = -1;
			
			List<Unit> units = new ArrayList<Unit>();
			
			List<List<Integer>> map = new ArrayList<List<Integer>>();
			for (int i = 0; i < rows.length; i++) {
				map.add(new ArrayList<Integer>());
				for (int j = 0; j < rows[i].length(); j++) {
					int value = -1;
					switch(rows[i].charAt(j)) {
					case '#':
						value = WALL;
						break;
					case 'E':
						value = FLOOR;
						units.add(new Unit(true, j, i, elfAttack));
						break;
					case 'G':
						value = FLOOR;
						units.add(new Unit(false, j, i, 3));
						break;
					case '.':
						value = FLOOR;
						break;
					}
					
					map.get(i).add(value);
				}
			}
			
			boolean done = false;
			main:
			while (!done) {
				Collections.sort(units);
				
				for (int i = 0; !done && i < units.size(); i++) {
					Unit unit = units.get(i);
					
					
					List<Unit> targetUnits = getTargetUnits(units, unit);
					if (targetUnits.size() == 0) { // one side has achieved victory
						done = true;
						finalElves = countElves(units);
						outcome = getOutcome(turn, units);
						break main;
					}
					
					Unit adjacentEnemy = isAdjacentToEnemy(unit, targetUnits, map);
					if (adjacentEnemy == null) {
						List<Point> inRangeSquares = getInRangeSquares(targetUnits, units, unit, map);
						if (inRangeSquares.size() == 0) { // unit is blocked in by other units or something
							continue;
						}
						
						List<Point> bestPath = getPath(unit.p, inRangeSquares, map, units);
						if (bestPath != null) { // a valid move exists
							unit.p = bestPath.get(1);
						}
						
						Unit adjacentEnemy2 = isAdjacentToEnemy(unit, targetUnits, map);
						if (adjacentEnemy2 != null) {
							adjacentEnemy2.health -= unit.attack;
							if (adjacentEnemy2.health <= 0) {
								int index = units.indexOf(adjacentEnemy2);
								if (index < i) {
									i--;
								}
								units.remove(adjacentEnemy2);
							}
						}
					} else {
						adjacentEnemy.health -= unit.attack;
						if (adjacentEnemy.health <= 0) {
							int index = units.indexOf(adjacentEnemy);
							if (index < i) {
								i--;
							}
							units.remove(adjacentEnemy);
						}
					}
				}
				turn++;
			}
			
			elfAttack++;
		}
		
		System.out.println(outcome);
	}

	private int countElves(List<Unit> units) {
		int count = 0;
		for (Unit unit: units) {
			if (unit.isElf) {
				count++;
			}
		}
		return count;
	}

	private int getOutcome(int turn, List<Unit> units) {
		int sum = 0;
		for (Unit unit: units) {
			sum += unit.health;
		}
		
		return turn * sum;
	}
	
	private Unit isAdjacentToEnemy(Unit unit, List<Unit> targetUnits, List<List<Integer>> map) {
		List<Unit> adjacentUnits = new ArrayList<Unit>();
		for (int i = 0; i < 4; i++) {
			Point test = unit.p.clone();
			test.move(i);
			
			for (Unit testUnit: targetUnits) {
				if (test.equals(testUnit.p)) {
					adjacentUnits.add(testUnit);
				}
			}
		}
		
		if (adjacentUnits.size() == 0) {
			return null;
		} else {
			Unit bestUnit = adjacentUnits.get(0);
			for (int i = 1; i < adjacentUnits.size(); i++) {
				Unit testUnit = adjacentUnits.get(i);
				if (testUnit.health < bestUnit.health) {
					bestUnit = testUnit;
				}
			}
			return bestUnit;
		}
	}
	
	private List<Unit> getTargetUnits(List<Unit> units, Unit unit) {
		List<Unit> targets = new ArrayList<Unit>();
		for (Unit test: units) {
			if (test.isElf != unit.isElf) {
				targets.add(test);
			}
		}
		
		return targets;
	}
	
	private List<Point> getInRangeSquares(List<Unit> targetUnits, List<Unit> allUnits,
			Unit unit, List<List<Integer>> map) {
		List<Point> inRangeSquares = new ArrayList<Point>();
		
		for (Unit testUnit: targetUnits) {
			for (int i = 0; i < 4; i++) {
				Point test = testUnit.p.clone();
				test.move(i);
				
				if (isSquareOpenExcept(allUnits, map, test, unit.p)) {
					if (!pathContains(inRangeSquares, test)) { // don't add same point twice
						inRangeSquares.add(test);
					}
				}
			}
		}
		
		Collections.sort(inRangeSquares);
		
		return inRangeSquares;
	}
	
	private List<Point> getPath(Point start, List<Point> targets, List<List<Integer>> map, List<Unit> units) {
		List<List<Point>> paths = new ArrayList<List<Point>>();
		paths.add(new ArrayList<Point>());
		paths.get(0).add(start);
		List<List<Point>> newPaths = new ArrayList<List<Point>>();
		boolean done = false;
		List<Point> goodPath = null;
		List<Point> visitedPoints = new ArrayList<Point>();
		visitedPoints.add(start);
		for (int i = 0; !done && i < paths.size(); i++) {
			List<Point> path = paths.get(i);
			
			for (int j = 0; !done && j < 4; j++) {
				Point tip = path.get(path.size() - 1).clone();
				tip.move(j);
				
				// clone path
				List<Point> newPath = new ArrayList<Point>();
				for (Point p: path) {
					newPath.add(p.clone());
				}
				
				if (visitedPoints.contains(tip)) {
					continue;
				}
				
				newPath.add(tip);
				
				if (targets.contains(tip)) {
					done = true;
					goodPath = newPath;
					break;
				}
				
				if (!isSquareOpen(units, map, tip)) {
					continue;
				}
				
				visitedPoints.add(tip);
				newPaths.add(newPath);
			}
			
			if (i == paths.size() - 1) {
				paths = newPaths;
				newPaths = new ArrayList<List<Point>>();
				i = -1;
			}
		}
		
		return goodPath;
	}
	
	private boolean isSquareOpen(List<Unit> units, List<List<Integer>> map, Point p) {
		if (map.get(p.y).get(p.x) == WALL) {
			return false;
		}
		
		for (Unit unit: units) {
			if (unit.p.x == p.x && unit.p.y == p.y) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isSquareOpenExcept(List<Unit> units, List<List<Integer>> map, Point p, Point except) {
		if (except.equals(p)) {
			return true;
		}
		
		if (map.get(p.y).get(p.x) == WALL) {
			return false;
		}
		
		for (Unit unit: units) {
			if (unit.p.x == p.x && unit.p.y == p.y) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean pathContains(List<Point> path, Point point) {
		for (Point test: path) {
			if (point.equals(test)) {
				return true;
			}
		}
		
		return false;
	}

}
