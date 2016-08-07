package gameRunner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * TODO : blank tile handler
 */
public class WordsMain
{
	final static int BOARD_SIZE = 11;
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Board board = new Board();
		//board.addLetter(0, 0, 'd', "tw");
		//board.addLetter(3, 1, 'd', "tw");
		//board.printBoard();
		GameAi a = new GameAi(board);
		ArrayList<Character> bag = new ArrayList<Character>();
		Scanner reader = new Scanner(System.in);  // Reading from System.in
	
		//a.checkAlongHorizontal(board.getBoardArray(), 7, 3);
		//a.evaluateSpot(7, 3, board.getBoardArray(), bag);
		//a.move(board.getBoardArray(), board, bag);
		smartEdit(2, 10, "unfrees", "down", board, "bl");
		smartEdit(8, 4, "zenith", "right", board, "vd");
		board.addLetter(2, 10, 'u', "tw");
		board.addLetter(8, 10, 's', "tw");
		board.printBoard();
		System.out.println(a.checkAlongVertical(board.getBoardArray(), 2, 10));
		/*
		while(true)
		{
			System.out.println("Enter a command: ");
			System.out.println("1. add to bag");
			System.out.println("2. remove from bag");
			System.out.println("3. print out bag");
			System.out.println("4. print out board");
			System.out.println("5. print out board status");
			System.out.println("6. move");
			System.out.println("7. edit board");
			System.out.println("8. smart edit board");
			System.out.println("9+. exit");
			System.out.println();
			int command = reader.nextInt();
			if(command == 1)
			{
				while(true)
				{
					System.out.print("Enter a character :  ");
					char c = reader.next().charAt(0);
					if(!Character.isLetter(c) && c != '*')
						break;
					bag.add(c);
				}
			}
			else if(command == 2)
			{
				System.out.println("Remove a letter");
				Character c = reader.next().charAt(0);
				if(!bag.remove(c))
					System.out.println("That is not currently in the bag");
			}
			else if(command == 3)
			{
				System.out.println(bag);
			}
			else if(command == 4)
			{
				board.printBoard();
			}
			else if(command == 5)
			{
				board.printBoardStatus();
			}
			else if(command == 6)
			{
				a.move(board.getBoardArray(), board, bag);
				bag = a.bigTileBag;
			}
			else if(command == 7)
			{
				System.out.println(" Enter in this form : row column char status \r\n");
				int r = reader.nextInt();
				int c = reader.nextInt();
				char cha = reader.next().charAt(0);
				String stat = reader.next();
				board.addLetter(r, c, cha, stat);
			}
			else if(command == 8)
			{
				System.out.println(" Enter in this form : row col word direction(down or right) \r\n");
				int r = reader.nextInt();
				int c = reader.nextInt();
				String word = reader.next();
				String dir = reader.next();
				if(dir.equals("right"))
				{
					while(word.length() > 0)
					{
						if(board.getChar(r, c) == '#')
						{
							board.addLetter(r, c, word.charAt(0), "vd");
						}
						word = word.substring(1);
						c++;
					}
				}
				else
				{
					while(word.length() > 0)
					{
						if(board.getChar(r, c) == '#')
						{
							board.addLetter(r, c, word.charAt(0), "vd");
							
						}
						word = word.substring(1);
						r++;
					}
				}
			}
			else if(command >= 9)
			{
				break;
			}
		
		}*/
		//System.out.println(a.checkAlongHorizontal(board.getBoardArray(), 7, 7));
		//System.out.println(a.checkSides(6, 10, board.getBoardArray()));
		//a.move(board.getBoardArray(), board, bag);
		//System.out.println(checkValid());
	}
	public static void smartEdit(int r, int c, String word, String dir, Board board, String stat)
	{
		if(dir.equals("right"))
		{
			while(word.length() > 0)
			{
				if(board.getChar(r, c) == '#')
				{
					board.addLetter(r, c, word.charAt(0), stat);
				}
				word = word.substring(1);
				c++;
			}
		}
		else
		{
			while(word.length() > 0)
			{
				if(board.getChar(r, c) == '#')
				{
					board.addLetter(r, c, word.charAt(0), stat);
					
				}
				word = word.substring(1);
				r++;
			}
		}
	}

}
