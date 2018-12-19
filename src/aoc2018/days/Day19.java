package aoc2018.days;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aoc2018.Day;

public class Day19 extends Day {

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
	private final int IP = 16;
	
	private final String[] stringCodesArray = {
			"addr",
			"addi",
			"mulr",
			"muli",
			"banr",
			"bani",
			"borr",
			"bori",
			"setr",
			"seti",
			"gtir",
			"gtri",
			"gtrr",
			"eqir",
			"eqri",
			"eqrr",
			"#ip"
	};
	
	private List<String> stringCodes;

	@Override
	public void part1(String content) {
		// 1 + 911
		
		String[] rows = content.split(System.getProperty("line.separator"));
		
		stringCodes = Arrays.asList(stringCodesArray);
		
		List<List<Integer>> program = new ArrayList<List<Integer>>();
		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			if (row.length() == 0) {
				break;
			}

			String[] items = row.split(" ");
			List<Integer> instruction = new ArrayList<Integer>();
			
			instruction.add(stringCodes.indexOf(items[0]));
			int maxJ = 4;
			if (instruction.get(0) == IP) {
				maxJ = 2;
			}
			
			for (int j = 1; j < maxJ; j++) {
				instruction.add(Integer.parseInt(items[j]));
			}
			
			program.add(instruction);
		}
		
		int[] registers = {0, 0, 0, 0, 0, 0, 0}; // last one holds index of instruction pointer
		registers[6] = 4;
		
		for (int i = 0; i >= 0 && i < 100 && i < program.size();) {
			List<Integer> instruction = program.get(i);
			
			String out = "ip=" + registers[registers[6]] + " [";
			for (int j = 0; j < 6; j++) {
				out += registers[j];
				if (j != 5) {
					out += ", ";
				}
			}

			out += "] " + stringCodesArray[instruction.get(0)] + " " + instruction.get(1) + " ";
			out += instruction.get(2) + " " + instruction.get(3) + " [";
			
			if (instruction.get(0) == IP) {
				registers = run(instruction.get(0), registers, instruction.get(1), 0, 0);
			} else {
				registers = run(instruction.get(0), registers, instruction.get(1), instruction.get(2), instruction.get(3));
			}
			
			for (int j = 0; j < 6; j++) {
				out += registers[j];
				if (j != 5) {
					out += ", ";
				}
			}
			out += "]\r\n";
			
			registers[registers[6]]++;
			i = registers[registers[6]];
			
			try {
				FileOutputStream file = new FileOutputStream("output/program.txt", true);
				file.write(out.getBytes());
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(registers[0]);
	}

	@Override
	public void part2(String content) {
		// 1 + 431 + 24481 + 10551311
		
		String[] rows = content.split(System.getProperty("line.separator"));
		
		stringCodes = Arrays.asList(stringCodesArray);
		
		List<List<Integer>> program = new ArrayList<List<Integer>>();
		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			if (row.length() == 0) {
				break;
			}

			String[] items = row.split(" ");
			List<Integer> instruction = new ArrayList<Integer>();
			
			instruction.add(stringCodes.indexOf(items[0]));
			int maxJ = 4;
			if (instruction.get(0) == IP) {
				maxJ = 2;
			}
			
			for (int j = 1; j < maxJ; j++) {
				instruction.add(Integer.parseInt(items[j]));
			}
			
			program.add(instruction);
		}
		
		int[] registers = {1, 0, 0, 0, 0, 0, 0}; // last one holds index of instruction pointer
		registers[6] = 4;
		
		for (int i = 0; i >= 0 && i < 100 && i < program.size();) {
			List<Integer> instruction = program.get(i);
			
			String out = "ip=" + registers[registers[6]] + " [";
			for (int j = 0; j < 6; j++) {
				out += registers[j];
				if (j != 5) {
					out += ", ";
				}
			}

			out += "] " + stringCodesArray[instruction.get(0)] + " " + instruction.get(1) + " ";
			out += instruction.get(2) + " " + instruction.get(3) + " [";
			
			if (instruction.get(0) == IP) {
				registers = run(instruction.get(0), registers, instruction.get(1), 0, 0);
			} else {
				registers = run(instruction.get(0), registers, instruction.get(1), instruction.get(2), instruction.get(3));
			}
			
			for (int j = 0; j < 6; j++) {
				out += registers[j];
				if (j != 5) {
					out += ", ";
				}
			}
			out += "]\r\n";
			
			registers[registers[6]]++;
			i = registers[registers[6]];
			
			try {
				FileOutputStream file = new FileOutputStream("output/program.txt", true);
				file.write(out.getBytes());
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(registers[0]);
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
		case IP:
			input[6] = input[a];
			break;
		}
		
		return input;
	}

}
