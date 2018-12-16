package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day16 extends Day {

	private final int ADDR = 0;
	private final int ADDI = 1;
	private final int MULR = 2;
	private final int MULI = 3;
	private final int BANR = 4;
	private final int BANI = 5;
	private final int BORR = 6;
	private final int BORI = 7;
	private final int SETR = 8;
	private final int SETI = 9;
	private final int GTIR = 10;
	private final int GTRI = 11;
	private final int GTRR = 12;
	private final int EQIR = 13;
	private final int EQRI = 14;
	private final int EQRR = 15;
	
	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int threeOrMore = 0;
		for (int i = 0; i < rows.length; i += 4) {
			if (rows[i].length() == 0) {
				break;
			}

			String array = rows[i].split("\\[")[1].replaceAll("\\]", "");
			String[] items = array.split(", ");
			int[] input = new int[4];
			for (int j = 0; j < 4; j++) {
				input[j] = Integer.parseInt(items[j]);
			}
			
			array = rows[i + 1];
			items = array.split(" ");
			int[] instruction = new int[4];
			for (int j = 0; j < 4; j++) {
				instruction[j] = Integer.parseInt(items[j]);
			}
			
			array = rows[i + 2].split("\\[")[1].replaceAll("\\]", "");
			items = array.split(", ");
			int[] output = new int[4];
			for (int j = 0; j < 4; j++) {
				output[j] = Integer.parseInt(items[j]);
			}
			
			int totalEqual = 0;
			for (int j = 0; j < 16; j++) {
				int[] testOutput = run(j, input, instruction[1], instruction[2], instruction[3]);
				
				boolean equal = true;
				for (int k = 0; k < 4; k++) {
					if (testOutput[k] != output[k]) {
						equal = false;
					}
				}
				
				if (equal) {
					totalEqual++;
				}
			}
			
			if (totalEqual >= 3) {
				threeOrMore++;
			}
		}
		
		System.out.println(threeOrMore);
	}

	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		List<List<Integer>> codes = new ArrayList<List<Integer>>();
		for (int i = 0; i < 16; i++) {
			codes.add(new ArrayList<Integer>());
			for (int j = 0; j < 16; j++) {
				codes.get(i).add(j);
			}
		}
		
		int i = 0;
		for (; i < rows.length; i += 4) {
			if (rows[i].length() == 0) {
				break;
			}

			String array = rows[i].split("\\[")[1].replaceAll("\\]", "");
			String[] items = array.split(", ");
			int[] input = new int[4];
			for (int j = 0; j < 4; j++) {
				input[j] = Integer.parseInt(items[j]);
			}
			
			array = rows[i + 1];
			items = array.split(" ");
			int[] instruction = new int[4];
			for (int j = 0; j < 4; j++) {
				instruction[j] = Integer.parseInt(items[j]);
			}
			
			array = rows[i + 2].split("\\[")[1].replaceAll("\\]", "");
			items = array.split(", ");
			int[] output = new int[4];
			for (int j = 0; j < 4; j++) {
				output[j] = Integer.parseInt(items[j]);
			}
			
			List<Integer> possible = new ArrayList<Integer>();
			for (int j = 0; j < 16; j++) {
				int[] testOutput = run(j, input, instruction[1], instruction[2], instruction[3]);
				
				boolean equal = true;
				for (int k = 0; k < 4; k++) {
					if (testOutput[k] != output[k]) {
						equal = false;
					}
				}
				
				if (equal) {
					possible.add(j);
				}
			}
			
			for (int j = 0; j < codes.get(instruction[0]).size(); j++) {
				if (!possible.contains(codes.get(instruction[0]).get(j))) {
					codes.get(instruction[0]).remove(j);
					j--;
				}
			}
		}
		
		/*for (int j = 0; j < 16; j++) {
			String p = " ]";
			for (int k = 0; k < codes.get(j).size(); k++) {
				p += codes.get(j).get(k) + ",";
			}
			
			System.out.println(j + ": " + codes.get(j).size() + " " + p);
		}*/
		
		// found by hand
		List<Integer> trueCodes = new ArrayList<Integer>();
		trueCodes.add(1);
		trueCodes.add(5);
		trueCodes.add(10);
		trueCodes.add(6);
		trueCodes.add(15);
		trueCodes.add(7);
		trueCodes.add(12);
		trueCodes.add(8);
		trueCodes.add(3);
		trueCodes.add(9);
		trueCodes.add(4);
		trueCodes.add(11);
		trueCodes.add(13);
		trueCodes.add(14);
		trueCodes.add(0);
		trueCodes.add(2);
		
		int[] input = new int[4];
		input[0] = 0;
		input[1] = 0;
		input[2] = 0;
		input[3] = 0;
		for (; i < rows.length; i++) {
			String[] items = rows[i].split(" ");
			
			if (items.length != 4) {
				continue;
			}
			
			int[] instruction = new int[4];
			for (int j = 0; j < 4; j++) {
				instruction[j] = Integer.parseInt(items[j]);
			}
			
			instruction[0] = trueCodes.get(instruction[0]);
			
			input = run(instruction[0], input, instruction[1], instruction[2], instruction[3]);
		}
		
		System.out.println(input[0]);
	}
	
	private int[] run(int code, int[] input, int a, int b, int c) {
		input = input.clone();
		switch (code) {
		case ADDR:
			input[c] = input[a] + input[b];
			break;
		case ADDI:
			input[c] = input[a] + b;
			break;
		case MULR:
			input[c] = input[a] * input[b];
			break;
		case MULI:
			input[c] = input[a] * b;
			break;
		case BANR:
			input[c] = input[a] & input[b];
			break;
		case BANI:
			input[c] = input[a] & b;
			break;
		case BORR:
			input[c] = input[a] | input[b];
			break;
		case BORI:
			input[c] = input[a] | b;
			break;
		case SETR:
			input[c] = input[a];
			break;
		case SETI:
			input[c] = a;
			break;
		case GTIR:
			if (a > input[b]) {
				input[c] = 1;
			} else {
				input[c] = 0;
			}
			break;
		case GTRI:
			if (input[a] > b) {
				input[c] = 1;
			} else {
				input[c] = 0;
			}
			break;
		case GTRR:
			if (input[a] > input[b]) {
				input[c] = 1;
			} else {
				input[c] = 0;
			}
			break;
		case EQIR:
			if (a == input[b]) {
				input[c] = 1;
			} else {
				input[c] = 0;
			}
			break;
		case EQRI:
			if (input[a] == b) {
				input[c] = 1;
			} else {
				input[c] = 0;
			}
			break;
		case EQRR:
			if (input[a] == input[b]) {
				input[c] = 1;
			} else {
				input[c] = 0;
			}
			break;
		}
		
		return input;
	}

}
