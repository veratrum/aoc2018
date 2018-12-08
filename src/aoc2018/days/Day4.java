package aoc2018.days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc2018.Day;

public class Day4 extends Day {

	@SuppressWarnings("deprecation")
	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		Arrays.sort(rows);
		
		// ID, hoursSlept
		Map<Integer, Integer> totalSlept = new HashMap<Integer, Integer>();
		
		// ID, numberDaysAsleepOnThatMinute
		Map<Integer, List<Integer>> schedule = new HashMap<Integer, List<Integer>>();
		
		int currentGuard = -1;
		int sleepStartMinute = -1;
		for (String row: rows) {
			String[] fragments = row.split("]");
			
			String part1 = fragments[0].replaceAll("\\[", "");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			Date date = null;
			try {
				date = format.parse(part1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			String[] fragments2 = fragments[1].split(" ");
			
			if (fragments2[1].equals("Guard")) {
				int id = Integer.parseInt(fragments2[2].replaceAll("#", ""));
				
				currentGuard = id;
				
				if (!totalSlept.containsKey(id)) {
					List<Integer> night = new ArrayList<Integer>();
					
					for (int i = 0; i < 60; i++) {
						night.add(0);
					}
					
					schedule.put(id, night);
				}
			} else if (fragments2[1].equals("falls")) {
				sleepStartMinute = date.getMinutes();
			} else if (fragments2[1].equals("wakes")) {
				for (int i = sleepStartMinute; i < date.getMinutes(); i++) {
					List<Integer> night = schedule.get(currentGuard);
					
					night.set(i, night.get(i) + 1);
				}
				
				int previouslySlept = 0;
				if (totalSlept.containsKey(currentGuard)) {
					previouslySlept = totalSlept.get(currentGuard);
				}
				
				totalSlept.put(currentGuard, previouslySlept + date.getMinutes() - sleepStartMinute);
			}
		}
		
		int max = -1;
		int maxGuard = -1;
		for (int guard: totalSlept.keySet()) {
			if (totalSlept.get(guard) > max) {
				max = totalSlept.get(guard);
				maxGuard = guard;
			}
		}
		
		max = -1;
		int maxValue = -1;
		for (int i = 0; i < schedule.get(maxGuard).size(); i++) {
			if (schedule.get(maxGuard).get(i) > max) {
				max = schedule.get(maxGuard).get(i);
				maxValue = i;
			}
		}
		
		System.out.println(maxGuard * maxValue);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		Arrays.sort(rows);
		
		// ID, hoursSlept
		Map<Integer, Integer> totalSlept = new HashMap<Integer, Integer>();
		
		// ID, numberDaysAsleepOnThatMinute
		Map<Integer, List<Integer>> schedule = new HashMap<Integer, List<Integer>>();
		
		int currentGuard = -1;
		int sleepStartMinute = -1;
		for (String row: rows) {
			String[] fragments = row.split("]");
			
			String part1 = fragments[0].replaceAll("\\[", "");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
			Date date = null;
			try {
				date = format.parse(part1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			String[] fragments2 = fragments[1].split(" ");
			
			if (fragments2[1].equals("Guard")) {
				int id = Integer.parseInt(fragments2[2].replaceAll("#", ""));
				
				currentGuard = id;
				
				if (!totalSlept.containsKey(id)) {
					List<Integer> night = new ArrayList<Integer>();
					
					for (int i = 0; i < 60; i++) {
						night.add(0);
					}
					
					schedule.put(id, night);
				}
			} else if (fragments2[1].equals("falls")) {
				sleepStartMinute = date.getMinutes();
			} else if (fragments2[1].equals("wakes")) {
				for (int i = sleepStartMinute; i < date.getMinutes(); i++) {
					List<Integer> night = schedule.get(currentGuard);
					
					night.set(i, night.get(i) + 1);
				}
				
				int previouslySlept = 0;
				if (totalSlept.containsKey(currentGuard)) {
					previouslySlept = totalSlept.get(currentGuard);
				}
				
				totalSlept.put(currentGuard, previouslySlept + date.getMinutes() - sleepStartMinute);
			}
		}
		
		int maxFreq = -1;
		int maxMinute = -1;
		int maxGuard = -1;
		for (int i = 0; i < 60; i++) {
			for (int guard: schedule.keySet()) {
				List<Integer> night = schedule.get(guard);
				
				if (night.get(i) > maxFreq) {
					maxFreq = night.get(i);
					maxMinute = i;
					maxGuard = guard;
				}
			}
		}
		
		System.out.println(maxMinute * maxGuard);
	}

}
