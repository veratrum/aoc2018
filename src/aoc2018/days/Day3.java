package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day3 extends Day {

	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		List<List<Integer>> patch = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < 1000; i++) {
			patch.add(new ArrayList<Integer>());
			for (int j = 0; j < 1000; j++) {
				patch.get(i).add(0);
			}
		}
		
		List<Claim> claims = new ArrayList<Claim>();
		
		for (String row: rows) {
			claims.add(parseRow(row));
		}
		
		int total = 0;
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				for (int k = 0; patch.get(i).get(j) < 2 && k < claims.size(); k++) {
					Claim claim = claims.get(k);
					
					if (claim.contains(i, j)) {
						patch.get(i).set(j, patch.get(i).get(j) + 1);
					}
				}
				
				if (patch.get(i).get(j) >= 2) {
					total++;
				}
			}
		}
		
		System.out.println(total);
	}

	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));

		List<Claim> claims = new ArrayList<Claim>();
		
		for (String row: rows) {
			claims.add(parseRow(row));
		}
		
		for (int i = 0; i < claims.size(); i++) {
			boolean overlaps = false;
			
			for (int j = 0; !overlaps && j < claims.size(); j++) {
				if (i == j) {
					continue;
				}
				
				if (claims.get(i).overlaps(claims.get(j))) {
					overlaps = true;
				}
			}
			
			if (!overlaps) {
				System.out.println(claims.get(i).id);
			}
		}
	}
	
	private class Claim {
		public int id;
		public int x;
		public int y;
		public int width;
		public int height;

		public Claim(int id, int x, int y, int width, int height) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public boolean contains(int x, int y) {
			return this.x <= x && this.x + width > x && this.y <= y && this.y + height > y;
		}
		
		public boolean overlaps(Claim claim) {
			return x < claim.x + claim.width && x + width > claim.x && y < claim.y + claim.height && y + height > claim.y;
		}
	}
	
	private Claim parseRow(String row) {
		String[] fragments = row.split(" ");
		
		String[] coordinates = fragments[2].replaceAll(":", "").split(",");
		String[] dimensions = fragments[3].split("x");

		int id = Integer.parseInt(fragments[0].replaceAll("#", ""));
		int x = Integer.parseInt(coordinates[0]);
		int y = Integer.parseInt(coordinates[1]);
		int width = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
		
		return new Claim(id, x, y, width, height);
	}

}
