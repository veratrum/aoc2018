package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day9 extends Day {

	@Override
	public void part1(String content) {
		int nPlayers = 404;
		int lastMarble = 71852;
		
		List<Integer> scores = new ArrayList<Integer>();
		for (int i = 0; i < nPlayers; i++) {
			scores.add(0);
		}
		
		List<Integer> marbles = new ArrayList<Integer>();
		int currentPosition = 1;
		marbles.add(0);
		
		for (int i = 1; i <= lastMarble; i++) {
			if (i % 23 == 0) {
				int removePosition = (((currentPosition - 7) % marbles.size()) + marbles.size()) % marbles.size();
				int removeValue = marbles.get(removePosition);
				marbles.remove(removePosition);
				
				currentPosition = removePosition;
				if (currentPosition == marbles.size()) {
					currentPosition = 0;
				}
				
				int playerIndex = i % nPlayers;
				scores.set(playerIndex, scores.get(playerIndex) + i + removeValue);
			} else {
				currentPosition += 2;
				currentPosition %= marbles.size();
				if (currentPosition == 0) {
					currentPosition = marbles.size();
				}
				
				marbles.add(currentPosition, i);
			}
		}
		
		int maxScore = -1;
		for (int score: scores) {
			if (score > maxScore) {
				maxScore = score;
			}
		}
		System.out.println(maxScore);
	}

	@Override
	public void part2(String content) {
		int nPlayers = 404;
		int lastMarble = 7185200;
		
		List<Long> scores = new ArrayList<Long>();
		for (int i = 0; i < nPlayers; i++) {
			scores.add(0L);
		}
		
		Item currentMarble = new Item(0);
		currentMarble.previous = currentMarble;
		currentMarble.next = currentMarble;
		
		for (int i = 1; i <= lastMarble; i++) {
			if (i % 23 == 0) {
				currentMarble = currentMarble.getPrevious(7);
				Item beforeRemove = currentMarble.previous;
				Item afterRemove = currentMarble.next;
				
				beforeRemove.next = afterRemove;
				afterRemove.previous = beforeRemove;
				
				int playerIndex = i % nPlayers;
				scores.set(playerIndex, scores.get(playerIndex) + i + currentMarble.value);
				
				currentMarble = afterRemove;
			} else {
				currentMarble = currentMarble.getNext(1);
				
				Item newMarble = new Item(i);
				newMarble.previous = currentMarble;
				newMarble.next = currentMarble.next;
				currentMarble.next.previous = newMarble;
				currentMarble.next = newMarble;
				currentMarble = newMarble;
			}
		}
		
		long maxScore = -1;
		for (long score: scores) {
			if (score > maxScore) {
				maxScore = score;
			}
		}
		System.out.println(maxScore);
	}
	
	class Item {
		public Item previous;
		public Item next;
		public int value;
		
		public Item(int value) {
			this.value = value;
		}
		
		public Item getPrevious(int n) {
			if (n == 0) {
				return this;
			} else {
				return previous.getPrevious(n - 1);
			}
		}
		
		public Item getNext(int n) {
			if (n == 0) {
				return this;
			} else {
				return next.getNext(n - 1);
			}
		}
	}

}
