package aoc2018.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc2018.Day;

public class Day7 extends Day {

	@Override
	public void part1(String content) {
		String[] lines = content.split(System.getProperty("line.separator"));
		
		Map<Character, List<Character>> dependencies = new HashMap<Character, List<Character>>();
		Map<Character, Boolean> charset = new HashMap<Character, Boolean>();
		
		for (String line: lines) {
			String[] words = line.split(" ");

			char characterA = words[1].charAt(0);
			char characterB = words[7].charAt(0);
			
			if (!dependencies.containsKey(characterB)) {
				dependencies.put(characterB, new ArrayList<Character>());
				charset.put(characterB, false);
			}
			
			if (!charset.containsKey(characterA)) {
				dependencies.put(characterA, new ArrayList<Character>());
				charset.put(characterA, false);
			}
			
			dependencies.get(characterB).add(characterA);
		}
		
		String order = "";
		for (int i = 0; i < charset.keySet().size(); i++) {
			List<Character> allValid = new ArrayList<Character>();
			for (char character: charset.keySet()) {
				if (charset.get(character)) {
					continue;
				}
				
				boolean valid = true;
				for (Character dependency: dependencies.get(character)) {
					if (!charset.get(dependency)) {
						valid = false;
					}
				}
				
				if (valid) {
					allValid.add(character);
				}
			}
			
			Collections.sort(allValid);
			order += allValid.get(0);
			charset.put(allValid.get(0), true);
		}
		
		System.out.println(order);
	}

	@Override
	public void part2(String content) {
		String[] lines = content.split(System.getProperty("line.separator"));
		
		Map<Character, List<Character>> dependencies = new HashMap<Character, List<Character>>();
		Map<Character, Boolean> charset = new HashMap<Character, Boolean>();
		Map<Character, Integer> remainingTime = new HashMap<Character, Integer>();
		
		for (String line: lines) {
			String[] words = line.split(" ");
	
			char characterA = words[1].charAt(0);
			char characterB = words[7].charAt(0);
			
			if (!dependencies.containsKey(characterB)) {
				dependencies.put(characterB, new ArrayList<Character>());
				charset.put(characterB, false);
				remainingTime.put(characterB, (int) characterB - 4);
			}
			
			if (!charset.containsKey(characterA)) {
				dependencies.put(characterA, new ArrayList<Character>());
				charset.put(characterA, false);
				remainingTime.put(characterA, (int) characterA - 4);
			}
			
			dependencies.get(characterB).add(characterA);
		}
		
		@SuppressWarnings("unused")
		String order = "";
		Map<Integer, Character> currentlyWorking = new HashMap<Integer, Character>();
		for (int i = 0; i < 5; i++) {
			currentlyWorking.put(i, '0');
		}
		
		boolean done = false;
		int time = 0;
		while (!done) {
			List<Character> allValid = new ArrayList<Character>();
			for (char character: charset.keySet()) {
				if (charset.get(character)) {
					continue;
				}
				
				boolean valid = true;
				for (Character dependency: dependencies.get(character)) {
					if (!charset.get(dependency) || remainingTime.get(dependency) > 0) {
						valid = false;
					}
				}
				
				if (valid) {
					allValid.add(character);
				}
			}
			
			Collections.sort(allValid);
			
			for (int i = 0; i < Math.min(5, allValid.size()); i++) {
				
				boolean assigned = false;
				for (int j = 0; !assigned && j < 5; j++) {
					Character c = currentlyWorking.get(j);
					if (c == '0') {
						currentlyWorking.put(j, allValid.get(i));
						assigned = true;
						charset.put(allValid.get(i), true);
						order += allValid.get(i);
					}
				}
			}
			
			for (int i = 0; i < 5; i++) {
				Character c = currentlyWorking.get(i);
				if (c != '0') {
					int timeLeft = remainingTime.get(c);
					timeLeft--;
					if (timeLeft == 0) {
						currentlyWorking.put(i, '0');
					}
					
					remainingTime.put(c, timeLeft);
				}
			}
			time++;
			
			boolean allDone = true;
			for (Character c: charset.keySet()) {
				if (remainingTime.get(c) != 0) {
					allDone = false;
				}
			}
			done = allDone;
		}
		
		System.out.println(time);
	}

}
