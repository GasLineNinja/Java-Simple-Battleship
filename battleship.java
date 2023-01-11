
/**************************************************************************************
 * Michael Strand
 * Assignment 1
 * 9/3/21
 * CompSci 337
 * 
 *This program is meant to simulate a guessing game similar to Battleship. Player one 
 *will enter coordinates for the placement of a "ship". Player two will then guess 
 *coordinates to try to "hit" player one's ship. Hits will be marked by an "X" and misses
 *will be marked by an "O". Game ends when 5 misses or 4 hits has been achieved. Final 
 *game board will be shown with ships location.
 *
 *************************************************************************************/

import java.util.*;

public class battleship {

	public static void main(String[] args) {

		// initializing needed variables
		final int boardSize = 10; //cannot be smaller than 4
		char[][] gameboard = new char[boardSize][boardSize];
		int x1, y1, x2, y2;
		int x;
		int y;
		int guessMiss = 0;
		int guessHit = 0;

		// declaring a scanner to read user input of board coordinates
		Scanner scr = new Scanner(System.in);

		// player 1 prompts to place a ship on x and y coordinates
		do {
			do {
				System.out.println("Please enter the x coordinate (0-" + (boardSize-1) + ") for your ships bow.");

				// validating user has input an int
				while (!scr.hasNextInt()) {
					String input = scr.next();
					System.out.println("\n" + input + " is not a valid coordinate.");
					System.out.println("Please enter the x coordinate (0-" + (boardSize-1) + ") for your ships bow.\n");
				}

				// user input saved into x1
				x1 = scr.nextInt();

			} while (x1 > (boardSize-1) || x1 < 0);

			do {
				System.out.println("Please enter the y coordinate (0-" + (boardSize-1) + ") for your ships bow.");

				// validating user has input an int
				while (!scr.hasNextInt()) {
					String input = scr.next();
					System.out.println("\n" + input + " is not a valid coordinate.");
					System.out.println("Please enter the y coordinate (0-" + (boardSize-1) + ") for your ships bow.\n");
				}

				// User input saved into y1
				y1 = scr.nextInt();

			} while (y1 > (boardSize-1) || y1 < 0);

			// placing an 'x' at x1, y1 bow coordinate
			gameboard[x1][y1] = 'x';

			do {
				System.out.println("Please enter the x coordinate (0-" + (boardSize-1) + ") for your ships stern.");

				// validating user has input an int
				while (!scr.hasNextInt()) {
					String input = scr.next();
					System.out.println("\n" + input + " is not a valid coordinate.");
					System.out.println("Please enter the x coordinate (0-" + (boardSize-1) + ") for your ships stern.\n");
				}

				// saving user input into x2
				x2 = scr.nextInt();

			} while (x2 > (boardSize-1) || x2 < 0);

			do {
				System.out.println("Please enter the y coordinate (0-" + (boardSize-1) + ") for your ships stern.");

				// validating user has input an int
				while (!scr.hasNextInt()) {
					String input = scr.next();
					System.out.println("\n" + input + " is not a valid coordinate.");
					System.out.println("Please enter the y coordinate (0-" + (boardSize-1) + ") for your ships bow.\n");
				}

				// saving user input into y2
				y2 = scr.nextInt();

			} while (y2 > (boardSize-1) || y2 < 0);

			// placing 'x' at x2, y2 stern coordinate
			gameboard[x2][y2] = 'x';

		} while (isValidShip(x1, y1, x2, y2) == false);

		// filling in rest of ship with 'x'
		if (x1 > x2) {
			gameboard[x1 - 2][y1] = 'x';
			gameboard[x1 - 1][y1] = 'x';
		} else if (x1 < x2) {
			gameboard[x1 + 1][y1] = 'x';
			gameboard[x1 + 2][y1] = 'x';
		} else if (y1 > y2) {
			gameboard[x1][y1 - 2] = 'x';
			gameboard[x1][y1 - 1] = 'x';
		} else if (y1 < y2) {
			gameboard[x1][y1 + 1] = 'x';
			gameboard[x1][y1 + 2] = 'x';
		}

		// clearing console
		System.out.println("\033[2J");

		// printing the board with just player ones ship placement hidden
		System.out.println("----------------------\n");
		printBoard(gameboard, false);
		System.out.println("\n----------------------");

		// prompts for player 2 to make guesses
		do {
			do {
				do {
					System.out.println("Enter the x value of your guess: ");

					// validating user input is an int
					while(!scr.hasNextInt()) {
						String input = scr.next();
						System.out.println("\n" + input + " is not a valid coordinate.");
						System.out.println("Enter the x value of your guess:\n");
					}

					x = scr.nextInt();

				} while(x > (gameboard.length-1) || x < 0);

				do {
					System.out.println("Enter the y value of your guess: ");

					// validating user input is an int
					while(!scr.hasNextInt()) {
						String input = scr.next();
						System.out.println("\n" + input + " is not a valid coordinate.");
						System.out.println("Enter the y value of your guess:\n");
					}

					y = scr.nextInt();

				} while(y > (gameboard.length-1) || y < 0);

			} while (isValidGuess(x, y, gameboard) == false);

			// Marking board with guesses
			if (gameboard[x][y] == 'x') {
				gameboard[x][y] = 'X';
				guessHit++;
			} else {
				gameboard[x][y] = 'O';
				guessMiss++;
			}

			System.out.println("----------------------\n");
			printBoard(gameboard, false);
			System.out.println("\nThere are " + (5-guessMiss) + " misses left.");
			System.out.println("\n----------------------");

		} while (guessMiss < 5 && guessHit < 4);

		// prints final gameboard
		System.out.println("\n----------------------\n");
		System.out.println("        Final\n");
		printBoard(gameboard, true);

		if (guessMiss == 5) {
			System.out.println("\nGame Over. Player 1 wins!");
		} else if (guessHit == 4) {
			System.out.println("\nGame Over. Player 2 wins!");
		}
		System.out.println("\n----------------------\n");

		scr.close();
	}

	// method generating a 10x10 game board
	static void printBoard(char[][] gameboard, boolean isFinal) {
		int num = 0;

		// Prints board after guesses showing hits and misses
		if (isFinal == false) {
			System.out.print("  ");
			for(int i=0; i<gameboard.length;i++) {
				System.out.print(i + " ");
			}

			System.out.println();

			for (int col = 0; col < gameboard.length; col++) {
				System.out.print(num + " ");
				num++;

				for (int row = 0; row < gameboard.length; row++) {

					if (gameboard[row][col] == 'X') {
						System.out.print(gameboard[row][col] + " ");
						continue;
					} else if (gameboard[row][col] == 'x') {
						System.out.print('.' + " ");
					} else if (gameboard[row][col] == 'O') {
						System.out.print(gameboard[row][col] + " ");
						continue;
					} else {
						System.out.print('.' + " ");
					}
				}
				System.out.println();
			}
		}

		// prints board after the game has ended showing hits, misses, and ship
		else {
			System.out.print("  ");
			for(int i=0; i<gameboard.length;i++) {
				System.out.print(i + " ");
			}

			System.out.println();
			
			for (int col = 0; col < gameboard.length; col++) {
				System.out.print(num + " ");
				num++;
				for (int row = 0; row < gameboard.length; row++) {
					if (gameboard[row][col] == 'x') {
						System.out.print(gameboard[row][col] + " ");
						continue;
					} else if (gameboard[row][col] == 'X') {
						System.out.print(gameboard[row][col] + " ");
						continue;
					} else if (gameboard[row][col] == 'O') {
						System.out.print(gameboard[row][col] + " ");
						continue;
					} else
						gameboard[row][col] = '.';
					System.out.print(gameboard[row][col] + " ");
				}
				System.out.println();
			}
		}
	}

	// method to check if coordinates are valid ship positions
	static boolean isValidShip(int x1, int y1, int x2, int y2) {

		// variables holding absolute value of subtracted coordinates
		int y = Math.abs(y1 - y2);
		int x = Math.abs(x1 - x2);

		// making sure ship is a concurrent four spaces
		if (x1 == x2 && y == 3) {
			return true;
		} else if (y1 == y2 && x == 3) {
			return true;
		}
		System.out.println("\nInvalid coordinates.");
		System.out.println("Bow and stern must have the same value for either x or y coordinates.");
		System.out.println("The coordinates that are not the same must have a difference of 3.\n");
		return false;
	}

	// method to check for valid guesses
	static boolean isValidGuess(int x, int y, char[][] gameboard) {

		// validating player guess falls inside board parameters
		if (x > gameboard.length || x < 0 || y > gameboard.length || y < 0) {
			System.out.println("\nCoordinates are outside valid area.");
			return false;
		}

		// validating player guess was not already made
		else if (gameboard[x][y] == 'O' || gameboard[x][y] == 'X') {
			System.out.println("\nYou already guessed this coordinate.");
			return false;
		}

		return true;
	}

}

