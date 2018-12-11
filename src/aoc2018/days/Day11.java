package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day11 extends Day {

	@Override
	public void part1(String content) {
		int s = Integer.parseInt(content);
		
		int dimensions = 300;
		
		List<List<Integer>> values = new ArrayList<List<Integer>>();
		for (int i = 1; i <= dimensions; i++) {
			values.add(new ArrayList<Integer>());
			for (int j = 1; j <= dimensions; j++) {
				int value = (i + 10) * j + s;
				value %= 1000;
				value *= (i + 10);
				value %= 1000;
				value -= value % 100;
				value /= 100;
				value -= 5;
				
				values.get(i - 1).add(value);
			}
		}
		
		int maxSum = 0;
		int maxX = -1;
		int maxY = -1;
		for (int i = 0; i < dimensions - 2; i++) {
			for (int j = 0; j < dimensions - 2; j++) {
				int sum = 0;
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						sum += values.get(i + k).get(j + l);
					}
				}
				
				if (sum > maxSum) {
					maxSum = sum;
					maxX = i + 1;
					maxY = j + 1;
				}
			}
		}
		
		System.out.println(maxX + "," + maxY);
	}

	@Override
	public void part2(String content) {
		int s = Integer.parseInt(content);
		
		int dimensions = 300;
		
		List<List<Integer>> values = new ArrayList<List<Integer>>();
		for (int i = 1; i <= dimensions; i++) {
			values.add(new ArrayList<Integer>());
			for (int j = 1; j <= dimensions; j++) {
				int value = (i + 10) * j + s;
				value %= 1000;
				value *= (i + 10);
				value %= 1000;
				value -= value % 100;
				value /= 100;
				value -= 5;
				
				values.get(i - 1).add(value);
			}
		}
		
		int maxSum = 0;
		int maxX = -1;
		int maxY = -1;
		int maxSize = -1;
		for (int size = 1; size <= 300; size++) { // hey, it worked
			for (int i = 0; i < dimensions - size + 1; i++) {
				for (int j = 0; j < dimensions - size + 1; j++) {
					int sum = 0;
					for (int k = 0; k < size; k++) {
						for (int l = 0; l < size; l++) {
							sum += values.get(i + k).get(j + l);
						}
					}
					
					if (sum > maxSum) {
						maxSum = sum;
						maxX = i + 1;
						maxY = j + 1;
						maxSize = size;
					}
				}
			}
		}
		
		System.out.println(maxX + "," + maxY + "," + maxSize);
	}

}
