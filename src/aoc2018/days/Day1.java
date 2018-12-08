package aoc2018.days;

import java.util.HashMap;
import java.util.Map;

import aoc2018.Day;

public class Day1 extends Day {

	@Override
	public void part1(String content) {
		int value = 0;
		
		String[] rows = content.split(System.getProperty("line.separator"));
		for (String row: rows) {
			value += Integer.parseInt(row);
		}
		
		System.out.println(value);
	}

	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int value = 0;
		boolean found = false;
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		int index = 0;
		
		while (!found) {
			value += Integer.parseInt(rows[index]);
			
			int frequency = map.getOrDefault(value, 0);
			if (frequency > 0) {
				System.out.println(value);
				found = true;
			}
			
			map.put(value, 1);
			
			index++;
			if (index == rows.length) {
				index = 0;
			}
		}
	}

}
