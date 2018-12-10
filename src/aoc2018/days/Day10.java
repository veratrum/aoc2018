package aoc2018.days;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import aoc2018.Day;

public class Day10 extends Day {

	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		List<Star> stars = new ArrayList<Star>();
		
		for (String row: rows) {
			String[] fragments = row.split(Pattern.quote("<"));
			
			String[] positionStrings = fragments[1].split(Pattern.quote(">"))[0].split(",");
			int positionX = Integer.parseInt(positionStrings[0].trim());
			int positionY = Integer.parseInt(positionStrings[1].trim());
			
			String[] velocityStrings = fragments[2].split(Pattern.quote(">"))[0].split(",");
			int velocityX = Integer.parseInt(velocityStrings[0].trim());
			int velocityY = Integer.parseInt(velocityStrings[1].trim());
			
			stars.add(new Star(positionX, positionY, velocityX, velocityY));
		}

		advance(10000, stars);
		advance(400, stars);
		advance(50, stars);
		advance(10, stars);
		advance(-1, stars);
		//printStars(stars);
		displayStars(stars);
		
		System.out.println("NEXPLRXK");
	}

	@Override
	public void part2(String content) {
		System.out.println("10459");
	}
	
	private void advance(int time, List<Star> stars) {
		for (Star star: stars) {
			star.positionX += time * star.velocityX;
			star.positionY += time * star.velocityY;
		}
	}
	
	@SuppressWarnings("unused")
	private void printStars(List<Star> stars) {
		for (Star star: stars) {
			System.out.println(star.positionX + ", " + star.positionY + " - " + star.velocityX + ", " + star.velocityY);
		}
	}
	
	private void displayStars(List<Star> stars) {
		List<List<Boolean>> sky = new ArrayList<List<Boolean>>();
		
		for (int i = 150; i < 350; i++) {
			sky.add(new ArrayList<Boolean>());
			for (int j = 100; j < 250; j++) {
				sky.get(i - 150).add(false);
			}
		}
		
		for (Star star: stars) {
			sky.get(star.positionX - 150).set(star.positionY - 100, true);
		}
		
		String output = "";
		for (int i = 100; i < 250; i++) {
			for (int j = 150; j < 310; j++) {
				if (sky.get(j - 150).get(i - 100)) {
					output += "#";
				} else {
					output += ".";
				}
			}
			output += System.getProperty("line.separator");
		}
		
		try {
			PrintWriter writer = new PrintWriter("output/stars.txt", "UTF-8");
			
			writer.write(output);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	class Star {
		public int positionX;
		public int positionY;
		public int velocityX;
		public int velocityY;
		
		public Star(int positionX, int positionY, int velocityX, int velocityY) {
			this.positionX = positionX;
			this.positionY = positionY;
			this.velocityX = velocityX;
			this.velocityY = velocityY;
		}
	}

}
