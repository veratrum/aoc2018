package aoc2018.days;

import aoc2018.Day;

public class Day5 extends Day {

	@Override
	public void part1(String content) {
		char previousLetter = '0';
		for (int i = 0; i < content.length(); i++) {
			char letter = content.charAt(i);
			
			if (previousLetter != '0') {
				if (Character.isUpperCase(letter)) {
					if (Character.toLowerCase(letter) == previousLetter) {
						content = content.substring(0, i - 1) + content.substring(i + 1, content.length());
						i = -1;
						previousLetter = '0';
						continue;
					}
				} else if (Character.isLowerCase(letter)) {
					if (Character.toUpperCase(letter) == previousLetter) {
						content = content.substring(0, i - 1) + content.substring(i + 1, content.length());
						i = -1;
						previousLetter = '0';
						continue;
					}
				}
			}
			
			previousLetter = letter;
		}
		
		System.out.println(content.length());
	}

	@Override
	public void part2(String content) {
		char previousLetter = '0';
		
		int bestScore = 999999;
		for (int j = 97; j <= 122; j++) {
			String character = Character.toString((char) j);
			String upperCharacter = Character.toString(Character.toUpperCase((char) j));
			
			String newContent = content.replaceAll(character, "");
			newContent = newContent.replaceAll(upperCharacter, "");
			
			for (int i = 0; i < newContent.length(); i++) {
				char letter = newContent.charAt(i);
				
				if (previousLetter != '0') {
					if (Character.isUpperCase(letter)) {
						if (Character.toLowerCase(letter) == previousLetter) {
							newContent = newContent.substring(0, i - 1) + newContent.substring(i + 1, newContent.length());
							i = -1;
							previousLetter = '0';
							continue;
						}
					} else if (Character.isLowerCase(letter)) {
						if (Character.toUpperCase(letter) == previousLetter) {
							newContent = newContent.substring(0, i - 1) + newContent.substring(i + 1, newContent.length());
							i = -1;
							previousLetter = '0';
							continue;
						}
					}
				}
				
				previousLetter = letter;
			}
			
			if (bestScore > newContent.length()) {
				bestScore = newContent.length();
			}
		}
		
		System.out.println(bestScore);
	}

}
