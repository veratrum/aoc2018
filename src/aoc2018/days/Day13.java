package aoc2018.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import aoc2018.Day;

public class Day13 extends Day {

	class Cart {
		public int x;
		public int y;
		public int direction; // 0 = up, clockwise
		public int turnCounter; // out of 3
		
		public Cart(int x, int y, int direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
			this.turnCounter = 0;
		}
	}
	
	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		List<Cart> carts = new ArrayList<Cart>();
		
		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			map.add(new ArrayList<Integer>());
			for (int j = 0; j < row.length(); j++) {
				char character = row.charAt(j);
				
				if (character == ' ') {
					map.get(i).add(0);
				} else if (character == '-' || character == '|') {
					map.get(i).add(1);
				} else if (character == '/') {
					map.get(i).add(3);
				} else if (character == '\\') {
					map.get(i).add(4);
				} else if (character == '^') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 0));
				} else if (character == '>') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 1));
				} else if (character == 'v') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 2));
				} else if (character == '<') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 3));
				} else {
					map.get(i).add(2);
				}
			}
		}
		
		boolean done = false;
		for (int t = 0; !done && t < 1000; t++) {
			Collections.sort(carts, new Comparator<Cart>() {

				@Override
				public int compare(Cart cart1, Cart cart2) {
					if (cart1.y == cart2.y) {
						return Integer.compare(cart1.x, cart2.x);
					}
					
					return Integer.compare(cart1.y, cart2.y);
				}
				
			});
			
			for (int i = 0; !done && i < carts.size(); i++) {
				Cart cart = carts.get(i);
				
				//System.out.println("t: " + t + ", (" + cart.x + ", " + cart.y + ")");
				
				List<Integer> pos;
				switch (map.get(cart.y).get(cart.x)) {
				case 1:
					pos = moveDirection(cart.x, cart.y, cart.direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					break;
				case 2:
					//System.out.println("dir: " + cart.direction + " count: " + cart.turnCounter);
					int direction = (((cart.direction + cart.turnCounter - 1) % 4) + 4) % 4;
					cart.turnCounter = (cart.turnCounter + 1) % 3;
					cart.direction = direction;
					pos = moveDirection(cart.x, cart.y, direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					//System.out.println("dir: " + cart.direction + " count: " + cart.turnCounter);
					break;
				case 3: // '/'
					switch (cart.direction) {
					case 0:
						cart.direction = 1;
						break;
					case 1:
						cart.direction = 0;
						break;
					case 2:
						cart.direction = 3;
						break;
					case 3:
						cart.direction = 2;
						break;
					}
					pos = moveDirection(cart.x, cart.y, cart.direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					break;
				case 4: // '\'
					switch (cart.direction) {
					case 0:
						cart.direction = 3;
						break;
					case 1:
						cart.direction = 2;
						break;
					case 2:
						cart.direction = 1;
						break;
					case 3:
						cart.direction = 0;
						break;
					}
					pos = moveDirection(cart.x, cart.y, cart.direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					break;
				}
				
				for (int j = 0; !done && j < carts.size(); j++) {
					if (i == j) {
						continue;
					}
					
					Cart cart2 = carts.get(j);
					
					if (cart.x == cart2.x && cart.y == cart2.y) {
						System.out.println(cart.x + "," + cart.y);
						done = true;
					}
				}
			}
		}
	}

	@Override
	public void part2(String content) {
String[] rows = content.split(System.getProperty("line.separator"));
		
		List<List<Integer>> map = new ArrayList<List<Integer>>();
		List<Cart> carts = new ArrayList<Cart>();
		
		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			map.add(new ArrayList<Integer>());
			for (int j = 0; j < row.length(); j++) {
				char character = row.charAt(j);
				
				if (character == ' ') {
					map.get(i).add(0);
				} else if (character == '-' || character == '|') {
					map.get(i).add(1);
				} else if (character == '/') {
					map.get(i).add(3);
				} else if (character == '\\') {
					map.get(i).add(4);
				} else if (character == '^') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 0));
				} else if (character == '>') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 1));
				} else if (character == 'v') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 2));
				} else if (character == '<') {
					map.get(i).add(1);
					carts.add(new Cart(j, i, 3));
				} else {
					map.get(i).add(2);
				}
			}
		}
		
		boolean done = false;
		for (int t = 0; !done && t < 100000; t++) {
			Collections.sort(carts, new Comparator<Cart>() {

				@Override
				public int compare(Cart cart1, Cart cart2) {
					if (cart1.y == cart2.y) {
						return Integer.compare(cart1.x, cart2.x);
					}
					
					return Integer.compare(cart1.y, cart2.y);
				}
				
			});
			
			for (int i = 0; !done && i < carts.size(); i++) {
				Cart cart = carts.get(i);
				
				//System.out.println("t: " + t + ", (" + cart.x + ", " + cart.y + ")");
				
				List<Integer> pos;
				switch (map.get(cart.y).get(cart.x)) {
				case 1:
					pos = moveDirection(cart.x, cart.y, cart.direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					break;
				case 2:
					//System.out.println("dir: " + cart.direction + " count: " + cart.turnCounter);
					int direction = (((cart.direction + cart.turnCounter - 1) % 4) + 4) % 4;
					cart.turnCounter = (cart.turnCounter + 1) % 3;
					cart.direction = direction;
					pos = moveDirection(cart.x, cart.y, direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					//System.out.println("dir: " + cart.direction + " count: " + cart.turnCounter);
					break;
				case 3: // '/'
					switch (cart.direction) {
					case 0:
						cart.direction = 1;
						break;
					case 1:
						cart.direction = 0;
						break;
					case 2:
						cart.direction = 3;
						break;
					case 3:
						cart.direction = 2;
						break;
					}
					pos = moveDirection(cart.x, cart.y, cart.direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					break;
				case 4: // '\'
					switch (cart.direction) {
					case 0:
						cart.direction = 3;
						break;
					case 1:
						cart.direction = 2;
						break;
					case 2:
						cart.direction = 1;
						break;
					case 3:
						cart.direction = 0;
						break;
					}
					pos = moveDirection(cart.x, cart.y, cart.direction);
					cart.x = pos.get(0);
					cart.y = pos.get(1);
					break;
				}
				
				for (int j = 0; !done && j < carts.size(); j++) {
					if (i == j) {
						continue;
					}
					
					Cart cart2 = carts.get(j);
					
					if (cart.x == cart2.x && cart.y == cart2.y) {
						carts.remove(cart);
						carts.remove(cart2);
						if (j < i) {
							i--;
						}
						i--;
					}
				}
			}
			
			if (carts.size() == 1) {
				System.out.println(carts.get(0).x + "," + carts.get(0).y);
				done = true;
			}
		}
	}
	
	private List<Integer> moveDirection(int x, int y, int direction) {
		List<Integer> pos = new ArrayList<Integer>();
		
		switch (direction) {
		case 0:
			pos.add(x);
			pos.add(y - 1);
			break;
		case 1:
			pos.add(x + 1);
			pos.add(y);
			break;
		case 2:
			pos.add(x);
			pos.add(y + 1);
			break;
		case 3:
			pos.add(x - 1);
			pos.add(y);
			break;
		}
		
		return pos;
	}

}
