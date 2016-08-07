package gameRunner;
import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.HashSet;

public class Board
{
	final int BOARD_SIZE = 11;
	private Tile[][] boardArray; 
	HashSet<String> words;
	Hashtable<Character, Integer> letterValue;
	
	public Board()
	{
		boardArray = new Tile[BOARD_SIZE][BOARD_SIZE];
		readWords();
		setLetterValues();
		fillBoard();
		setSpecialTiles();
	}
	public int getValue(char key)
	{
		//System.out.println(key + "    HERE");
		return letterValue.get(key);
	}
	private void setLetterValues()
	{
		letterValue = new Hashtable<Character, Integer>(26);
		letterValue.put('a', 1);
		letterValue.put('b', 4);
		letterValue.put('c', 4);
		letterValue.put('d', 2);
		letterValue.put('e', 1);
		letterValue.put('f', 4);
		letterValue.put('g', 3);
		letterValue.put('h', 3);
		letterValue.put('i', 1);
		letterValue.put('j', 10);
		letterValue.put('k', 5);
		letterValue.put('l', 2);
		letterValue.put('m', 4);
		letterValue.put('n', 2);
		letterValue.put('o', 1);
		letterValue.put('p', 4);
		letterValue.put('q', 10);
		letterValue.put('r', 1);
		letterValue.put('s', 1);
		letterValue.put('t', 1);
		letterValue.put('u', 2);
		letterValue.put('v', 5);
		letterValue.put('w', 4);
		letterValue.put('x', 8);
		letterValue.put('y', 3);
		letterValue.put('z', 10);
	}
	private void setSpecialTiles()
	{
		if(BOARD_SIZE == 11)
		{
			boardArray[0][0].setStatus("tl");
			boardArray[0][10].setStatus("tl");
			boardArray[3][3].setStatus("tl");
			boardArray[3][7].setStatus("tl");
			boardArray[7][3].setStatus("tl");
			boardArray[7][7].setStatus("tl");
			boardArray[10][0].setStatus("tl");
			boardArray[10][10].setStatus("tl");
			
			boardArray[0][2].setStatus("tw");
			boardArray[0][8].setStatus("tw");
			boardArray[2][0].setStatus("tw");
			boardArray[2][10].setStatus("tw");
			boardArray[8][0].setStatus("tw");
			boardArray[8][10].setStatus("tw");
			boardArray[10][2].setStatus("tw");
			boardArray[10][8].setStatus("tw");
			
			boardArray[1][1].setStatus("dw");
			boardArray[1][5].setStatus("dw");
			boardArray[1][9].setStatus("dw");
			boardArray[5][1].setStatus("dw");
			boardArray[5][9].setStatus("dw");
			boardArray[9][1].setStatus("dw");
			boardArray[9][5].setStatus("dw");
			boardArray[9][9].setStatus("dw");
			
			boardArray[2][2].setStatus("dl");
			boardArray[2][4].setStatus("dl");
			boardArray[2][6].setStatus("dl");
			boardArray[2][8].setStatus("dl");
			boardArray[4][2].setStatus("dl");
			boardArray[4][8].setStatus("dl");
			boardArray[6][2].setStatus("dl");
			boardArray[6][8].setStatus("dl");
			boardArray[8][2].setStatus("dl");
			boardArray[8][4].setStatus("dl");
			boardArray[8][6].setStatus("dl");
			boardArray[8][8].setStatus("dl");
		}
		else if(BOARD_SIZE == 15)
		{
			boardArray[0][6].setStatus("tl");
			boardArray[0][8].setStatus("tl");
			boardArray[3][3].setStatus("tl");
			boardArray[3][11].setStatus("tl");
			boardArray[5][5].setStatus("tl");
			boardArray[5][9].setStatus("tl");
			boardArray[6][0].setStatus("tl");
			boardArray[6][14].setStatus("tl");
			boardArray[8][0].setStatus("tl");
			boardArray[8][14].setStatus("tl");
			boardArray[9][5].setStatus("tl");
			boardArray[9][9].setStatus("tl");
			boardArray[11][3].setStatus("tl");
			boardArray[11][11].setStatus("tl");
			boardArray[14][6].setStatus("tl");
			boardArray[14][8].setStatus("tl");
			
			boardArray[0][3].setStatus("tw");
			boardArray[0][11].setStatus("tw");
			boardArray[3][0].setStatus("tw");
			boardArray[3][14].setStatus("tw");
			boardArray[11][0].setStatus("tw");
			boardArray[11][14].setStatus("tw");
			boardArray[14][3].setStatus("tw");
			boardArray[14][11].setStatus("tw");
			
			boardArray[1][5].setStatus("dw");
			boardArray[1][9].setStatus("dw");
			boardArray[3][7].setStatus("dw");
			boardArray[5][1].setStatus("dw");
			boardArray[5][13].setStatus("dw");
			boardArray[7][3].setStatus("dw");
			boardArray[7][11].setStatus("dw");
			boardArray[9][1].setStatus("dw");
			boardArray[9][13].setStatus("dw");
			boardArray[11][7].setStatus("dw");
			boardArray[13][5].setStatus("dw");
			boardArray[13][9].setStatus("dw");
			
			boardArray[1][2].setStatus("dl");
			boardArray[1][12].setStatus("dl");
			boardArray[2][1].setStatus("dl");
			boardArray[2][4].setStatus("dl");
			boardArray[2][10].setStatus("dl");
			boardArray[2][13].setStatus("dl");
			boardArray[4][2].setStatus("dl");
			boardArray[4][6].setStatus("dl");
			boardArray[4][8].setStatus("dl");
			boardArray[4][12].setStatus("dl");
			boardArray[6][4].setStatus("dl");
			boardArray[6][10].setStatus("dl");
			boardArray[8][4].setStatus("dl");
			boardArray[8][10].setStatus("dl");
			boardArray[10][2].setStatus("dl");
			boardArray[10][6].setStatus("dl");
			boardArray[10][8].setStatus("dl");
			boardArray[10][12].setStatus("dl");
			boardArray[12][1].setStatus("dl");
			boardArray[12][4].setStatus("dl");
			boardArray[12][10].setStatus("dl");
			boardArray[12][13].setStatus("dl");
			boardArray[13][2].setStatus("dl");
			boardArray[13][12].setStatus("dl");
			
		}
	}
	private void fillBoard()
	{
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int x = 0; x < BOARD_SIZE; x++)
			{
				boardArray[x][i] = new Tile();
			}
		}
	}
	public void setBoard(Tile[][] b)
	{
		boardArray = b;
	}

	public void addLetter(int row, int column, char c, String status)
	{
		c = Character.toLowerCase(c);
		boardArray[row][column].setLetter(c);
		boardArray[row][column].setStatus(status);
	}
	public char getChar(int row, int column)
	{
		return boardArray[row][column].getLetter();
	}
	public String getStatus(int row, int column)
	{
		return boardArray[row][column].getStatus();
	}
	public void printBoard()
	{
		System.out.print("   ");
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			System.out.print(i % 10 + " ");
		}
		System.out.println();
		
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			if(i < 10)
			{
				System.out.print(" ");
			}
			System.out.print(i + " ");
			for(int x = 0; x < BOARD_SIZE; x++)
			{
				System.out.print(boardArray[i][x].getLetter());
				System.out.print(' ');
			}
			System.out.println();
			System.out.println();
		}
	}
	public void printBoardStatus()
	{
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int x = 0; x < BOARD_SIZE; x++)
			{
				System.out.print(boardArray[i][x].getStatus());
				System.out.print(' ');
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public boolean readWords()
	{
		words = new HashSet<String>(172820);
		Scanner reader = null;
		try
		{
			reader = new Scanner(new File("wordlist.txt"));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		String line = reader.next();
		while(reader.hasNext())
		{
			words.add(line);
			line = reader.next();
		}
		words.add(line);
		return true;
	}
	public boolean checkIfWord(String word)
	{
		if(words.contains(word))
			return true;
		return false;
	}
	public Tile[][] getBoardArray()
	{
		return boardArray;
	}

}
