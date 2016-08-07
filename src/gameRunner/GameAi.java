package gameRunner;
import java.util.ArrayList;
/*
 * KEY :
 * bl = blank
 * vd = valid
 * vs = valid but no value HERE
 * vs0 = blank letter just placed
 * tl = triple letter
 * tw = triple word
 * dl = double letter
 * dw = double word
 * pl = just placed
 * '*' = blank
 */

public class GameAi
{
	final int BOARD_SIZE = 11;
	String word = "";
	int bigScore = 0;
	int turn = 0;
	int turnZeroLength = 0;
	int turnZeroScore = 0;

	Board board;
	boolean bigChecker = false;
	Tile[][] bigBoard = null;
	ArrayList<Character> bigTileBag = null;
	public GameAi(Board board1)
	{
		board = board1;
	}
	public void move(Tile[][] originalBoard, Board board, ArrayList<Character> tileBag)
	{
		bigScore = 0;
		//Make a temporary board to work with
		Tile[][] tempBoard = new Tile[BOARD_SIZE][BOARD_SIZE];
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			tempBoard[i] = originalBoard[i].clone();
		}

		if(turn == 0)
		{
			evaluateSpot(BOARD_SIZE / 2, BOARD_SIZE / 2, tempBoard, tileBag);
			evaluateDown((BOARD_SIZE / 2) - 1, BOARD_SIZE / 2, tempBoard, "", 0, tileBag, (BOARD_SIZE / 2) - 1, BOARD_SIZE / 2);
			evaluateRight(BOARD_SIZE / 2, (BOARD_SIZE / 2) - 1, tempBoard, "", 0, tileBag, BOARD_SIZE / 2, (BOARD_SIZE / 2) - 1);
			turn++;
		}
		else
		{
			for(int row = 0; row < BOARD_SIZE; row++)
			{
				for(int col = 0; col < BOARD_SIZE; col++)
				{
					//System.out.println("row " + row + " col " + col);
					if(tempBoard[row][col].getLetter() == '#')
					{
						bigChecker = false;
						evaluateSpot(row, col, tempBoard, tileBag);
					}
				}
			}
		}
		
		//print2DLetter(bigBoard);
		setValids();
		board.setBoard(bigBoard);
		System.out.println(word + "   " + bigScore);
		board.printBoard();
		System.out.println(bigTileBag);
		//print2DStatus(board.getBoardArray());
	}
	public void setValids()
	{
		for(int row = 0; row < BOARD_SIZE; row++)
		{
			for(int col = 0; col < BOARD_SIZE; col++)
			{
				if(bigBoard[row][col].getStatus().equals("vs0"))
				{
					bigBoard[row][col].setStatus("vs");
				}
				else if(bigBoard[row][col].getLetter() != '#')
				{
					bigBoard[row][col].setStatus("vd");
				}
			}
		}
	}
	
	public void evaluateSpot(int row, int col, Tile[][] tempBoard, ArrayList<Character> tileBag)
	{

		evaluateDown(row, col, tempBoard, "", 0, tileBag, row, col);
		evaluateRight(row, col, tempBoard, "", 0, tileBag, row, col);	

	}
	public void evaluateDown(int row, int col, Tile[][] oldBoard, String oldWord, int rightScore, ArrayList<Character> oldTileBag, int saveRow, int saveCol) //new board array at each different letter
	{
		//System.out.println("\n\n");
		//print2DLetter(oldBoard);
		//System.out.print(oldTileBag + "    " + rightScore + "    ");
		
		//System.out.println( "*" + oldWord + "*");
		if(oldTileBag.size() == 0 || row > BOARD_SIZE - 1 || col > BOARD_SIZE - 1 || row < 0 || col < 0)
		{
			return;
		}
		else if(oldBoard[row][col].getLetter() != '#')
		{
			Integer newRow = new Integer(row);
			Integer newRightScore = new Integer(rightScore);
			String newWord = new String(oldWord);
			newWord = newWord + oldBoard[row][col].getLetter();
			newRightScore ++;
			newRow ++;
			evaluateDown(newRow, col, oldBoard, newWord, newRightScore, oldTileBag, saveRow, saveCol); //when we add multipliers rightscore + 1 will change
		}
		else
		{
			for(int i = 0; i < oldTileBag.size(); i++)
			{
				if(oldTileBag.get(i) == '*')
				{
					
					for(int k = 97; k <= 122; k++)
					{
						ArrayList<Character> newTileBag = (ArrayList<Character>) oldTileBag.clone();
						Tile[][] newBoard = arrayCopy(oldBoard);
						String newWord = new String(oldWord);
						Integer newRow = new Integer(row);
						Integer newRightScore = new Integer(rightScore);
						char lett = (char)(k);
						newBoard[newRow][col].setLetter(lett);
						newBoard[newRow][col].setStatus("vs0");
						newTileBag.remove(i);
						newRightScore = newRightScore + 1;
						newRow++;
						newWord = newWord + lett;
						
						evaluateDown(newRow, col, newBoard, newWord, newRightScore, newTileBag, saveRow, saveCol);
						bigChecker = false;
						int a;
						if(!newTileBag.isEmpty())
							a = checkAlongVertical(newBoard, saveRow, saveCol);
						else
							a = checkAlongVertical(newBoard, saveRow, saveCol) + 35;
						if(a > 1)
						{
							if(a > bigScore)
							{
								bigScore = a;
								word = newWord;
								bigBoard = newBoard;
								bigTileBag = newTileBag;
							}
						}
					}	
				}
				else
				{
					ArrayList<Character> newTileBag = (ArrayList<Character>) oldTileBag.clone();
					Tile[][] newBoard = arrayCopy(oldBoard);
					String newWord = new String(oldWord);
					Integer newRow = new Integer(row);
					Integer newRightScore = new Integer(rightScore);
					char lett = newTileBag.get(i);
					newBoard[newRow][col].setLetter(lett);
					newTileBag.remove(i);
					newRightScore = newRightScore + 1;
					newRow++;
					newWord = newWord + lett;
					
					evaluateDown(newRow, col, newBoard, newWord, newRightScore, newTileBag, saveRow, saveCol);
					bigChecker = false;
					int a;
					if(!newTileBag.isEmpty())
						a = checkAlongVertical(newBoard, saveRow, saveCol);
					else
						a = checkAlongVertical(newBoard, saveRow, saveCol) + 35;
					if(a > 1)
					{
						if(a > bigScore)
						{
							bigScore = a;
							word = newWord;
							bigBoard = newBoard;
							bigTileBag = newTileBag;
						}
					}
				}
				
				
			}
		}
		//remember to add a finalcheck of word here
	}
	public void evaluateRight(int row, int col, Tile[][] oldBoard, String oldWord, int rightScore, ArrayList<Character> oldTileBag, int saveRow, int saveCol) //new board array at each different letter
	{
		//System.out.println("\n\n");
		//print2DLetter(oldBoard);
		//System.out.print(oldTileBag + "    " + rightScore + "    ");
		
		//System.out.println( "*" + oldWord + "*");
		if(oldTileBag.size() == 0 || row > BOARD_SIZE - 1 || col > BOARD_SIZE - 1 || row < 0 || col < 0)
		{
			return;
		}
		else if(oldBoard[row][col].getLetter() != ('#'))
		{
			Integer newCol = new Integer(col);
			Integer newRightScore = new Integer(rightScore);
			String newWord = new String(oldWord);
			newWord = newWord + oldBoard[row][col].getLetter();
			newRightScore ++;
			newCol ++;
			evaluateRight(row, newCol, oldBoard, newWord, newRightScore, oldTileBag, saveRow, saveCol); //when we add multipliers rightscore + 1 will change
		}
		else
		{
			for(int i = 0; i < oldTileBag.size(); i++)
			{
				if(oldTileBag.get(i) == '*')
				{
					
					for(int k = 97; k <= 122; k++)
					{
						ArrayList<Character> newTileBag = (ArrayList<Character>) oldTileBag.clone();
						Tile[][] newBoard = arrayCopy(oldBoard);
						String newWord = new String(oldWord);
						Integer newCol = new Integer(col);
						Integer newRightScore = new Integer(rightScore);
						char lett = (char)(k);
						newBoard[row][newCol].setLetter(lett);
						newBoard[row][newCol].setStatus("vs0");
						newTileBag.remove(i);
						newWord = newWord + lett;
						newRightScore = newRightScore + 1;
						newCol++;
						evaluateRight(row, newCol, newBoard, newWord, newRightScore, newTileBag, saveRow, saveCol);
						bigChecker = false;
						int a;
						if(!newTileBag.isEmpty())
							a = checkAlongHorizontal(newBoard, saveRow, saveCol);
						else
							a = checkAlongHorizontal(newBoard, saveRow, saveCol) + 35;
						if(a > 1)
						{
							if(a > bigScore)
							{
								bigScore = a;
								word = newWord;
								bigBoard = newBoard;
								bigTileBag = newTileBag;
							}
						}
					}
				}
				else
				{
					ArrayList<Character> newTileBag = (ArrayList<Character>) oldTileBag.clone();
					Tile[][] newBoard = arrayCopy(oldBoard);
					String newWord = new String(oldWord);
					Integer newCol = new Integer(col);
					Integer newRightScore = new Integer(rightScore);
					char lett = newTileBag.get(i);
					newBoard[row][newCol].setLetter(lett);
					newTileBag.remove(i);
					newWord = newWord + lett;
					newRightScore = newRightScore + 1;
					newCol++;
					evaluateRight(row, newCol, newBoard, newWord, newRightScore, newTileBag, saveRow, saveCol);
					bigChecker = false;
					int a;
					if(!newTileBag.isEmpty())
						a = checkAlongHorizontal(newBoard, saveRow, saveCol);
					else
						a = checkAlongHorizontal(newBoard, saveRow, saveCol) + 35;
					
					if(a > 1)
					{
						if(a > bigScore)
						{
							bigScore = a;
							word = newWord;
							bigBoard = newBoard;
							bigTileBag = newTileBag;
						}
					}
				}
			}
		}
	}
	
	//main method
	public int checkAlongHorizontal(Tile[][] finalBoard, int row, int col)
	{
		//System.out.println("ERROR");
		//print2DLetter(finalBoard);
		//System.out.println();
		int multiplier = 1;
		bigChecker = false;
		if(turn == 0)
			bigChecker = true;
		int score = 0;
		int score2 = 0;
		//check vertical
		while(true)
		{
			if(col == 0)
				break;
			if(col > 0)
			{
				if(!Character.isLetter(finalBoard[row][col - 1].getLetter()))
					break;
			}
			col --;
		}
		

		String wor = "";
		while(true)
		{
			if(col >= BOARD_SIZE)
				break;
			
			wor = wor + finalBoard[row][col].getLetter();
		
			if(finalBoard[row][col].getStatus().equals("bl") || finalBoard[row][col].getStatus().equals("vd"))
			{
				score += board.getValue(finalBoard[row][col].getLetter());
			}
			else if(finalBoard[row][col].getStatus() =="dl")
				score += (2 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus() =="tl")
				score += (3 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus() =="dw")
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 2;
			}
			else if(finalBoard[row][col].getStatus() =="tw")
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 3;
			}

			
			//System.out.println("score before" + score);
			if(!finalBoard[row][col].getStatus().equals("vd") && finalBoard[row][col].getLetter() != '#')
			{
				//System.out.print(finalBoard[row][col].getLetter() + "      ");
				//System.out.println(checkVertical(finalBoard, row, col));
				score2 += checkVertical(finalBoard, row, col);
			}
			//System.out.println("score after " + score);
			if(col < BOARD_SIZE - 1)
			{
				if(!Character.isLetter(finalBoard[row][col + 1].getLetter()))
					break;
			}
			
			col++;
		}
		
		if(bigChecker == false)
			return -999;
		if(!board.checkIfWord(wor))
		{
			if(wor.length() > 1)
			{
				return -999;
			}
			else
			{
				score = 0;
				score2 = 0;
			}
		}
		else //marker
		{
			//System.out.println(wor);
		}
		//System.out.println("score1 " + score);
		//System.out.println("score2 " + score);
		/*if(score * multiplier + score2 > 0)
		{
			System.out.println("HORIZONTAL");
			System.out.println(wor);
			System.out.println(score * multiplier + score2);
			System.out.println();
		}*/
		return score * multiplier + score2;
	}
	
	//submethod
	public int checkVertical(Tile[][] finalBoard, int row, int col)
	{
		int score = 0;
		int multiplier = 1;
		//System.out.println("horizontal : row " + row + "   col " + col);
		while(true)
		{
			if(row == 0)
				break;
			if(row > 0)
			{
				if(!Character.isLetter(finalBoard[row - 1][col].getLetter()))
					break;
			}
			row --;
		}
		String wor2 = "";
		while(true)
		{
			if(row >= BOARD_SIZE)
				break;
			wor2 = wor2 + finalBoard[row][col].getLetter();

			if(finalBoard[row][col].getStatus().equals("bl") || finalBoard[row][col].getStatus().equals("vd"))
				score += board.getValue(finalBoard[row][col].getLetter());
			else if(finalBoard[row][col].getStatus() =="dl")
				score += (2 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus() =="tl")
				score += (3 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus() =="dw")
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 2;
			}
			else if(finalBoard[row][col].getStatus() =="tw")
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 3;
			}
			
			if(checkSides(row, col, finalBoard) == true)
			{
				bigChecker = true;
			}
			
			if(row < BOARD_SIZE - 1)
			{
				if(!Character.isLetter(finalBoard[row + 1][col].getLetter()))
					break;
			}
			
			row++;
		}
		if(!board.checkIfWord(wor2))
		{
			//System.out.println("here " + wor2);
			if(wor2.length() > 1)
				return -999;
			if(wor2.length() == 1)
				return 0;
		}
		else
		{
			//System.out.println(wor2); //marker
		}
		/*if(score > 0)
		{
			System.out.println("VERTICAL");
			System.out.println(wor2);
			System.out.println(score * multiplier);
			System.out.println();
		}*/
		return score * multiplier;
	}
	
	//submethod
	public int checkHorizontal(Tile[][] finalBoard, int row, int col)
	{
		int score = 0;
		int multiplier = 1;
		if(turn == 0)
		{
			//System.out.println("marker 1");
			bigChecker = true;
		}
		//System.out.println("horizontal : row " + row + "   col " + col);
		while(true)
		{
			if(col == 0)
				break;
			if(col > 0)
			{
				if(!Character.isLetter(finalBoard[row][col - 1].getLetter()))
					break;
			}
			col --;
		}
		String wor2 = "";
		while(true)
		{
			if(col >= BOARD_SIZE)
				break;
			if(checkSides(row, col, finalBoard) == true)
			{
				//System.out.println("marker 2 : row " + row + " col " + col);
				bigChecker = true;
			}
			wor2 = wor2 + finalBoard[row][col].getLetter();
			
			//System.out.println("row " + row + " col " + col);
			//if(row == 6 && col == 10)
				//print2DLetter(finalBoard);
			if(finalBoard[row][col].getStatus().equals("bl") || finalBoard[row][col].getStatus().equals("vd"))
				score += board.getValue(finalBoard[row][col].getLetter());
			else if(finalBoard[row][col].getStatus() =="dl")
				score += (2 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus() =="tl")
				score += (3 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus() =="dw")
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 2;
			}
			else if(finalBoard[row][col].getStatus() =="tw")
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 3;
			}
			
			if(col < BOARD_SIZE - 1)
			{
				if(!Character.isLetter(finalBoard[row][col + 1].getLetter()))
					break;
			}
			
			col++;
		}
		if(!board.checkIfWord(wor2))
		{
			if(wor2.length() > 1)
				return -999;
			if(wor2.length() == 1)
				return 0;
		}
		else
		{
			//System.out.println(wor2); //marker
		}
		return score * multiplier;
	}
	public boolean checkSides(int row, int col, Tile[][] board)
	{
		if(row - 1 >= 0)
		{
			if(board[row - 1][col].getStatus().equals("vd") || board[row - 1][col].getStatus().equals("vs"))
			{
				return true;
			}
		}
		if(col - 1 >= 0)
		{
			if(board[row][col - 1].getStatus().equals("vd") || board[row][col - 1].getStatus().equals("vs"))
			{
				return true;
			}
		}
		if(col + 1 < BOARD_SIZE)
		{
			if(board[row][col + 1].getStatus().equals("vd") || board[row][col + 1].getStatus().equals("vs"))
			{
				return true;
			}
		}
		if(row + 1 < BOARD_SIZE)
		{
			if(board[row + 1][col].getStatus().equals("vd") || board[row + 1][col].getStatus().equals("vs"))
			{
				return true;
			}
		}
		return false;
	}
	
	//main method
	public int checkAlongVertical(Tile[][] finalBoard, int row, int col)
	{
		//print2D(finalBoard);
		//System.out.println();
		int score = 0;
		int score2 = 0;
		int multiplier = 1;
		//check vertical
		while(true)
		{
			if(row == 0)
				break;
			if(row > 0)
			{
				if(!Character.isLetter(finalBoard[row - 1][col].getLetter()))
					break;
			}
			row --;
		}
		
		String wor = "";
		while(true)
		{
			if(row >= BOARD_SIZE)
				break;
			wor = wor + finalBoard[row][col].getLetter();
			if(finalBoard[row][col].getStatus().equals("vd") || finalBoard[row][col].getStatus().equals("bl"))
				score += board.getValue(finalBoard[row][col].getLetter());
			else if(finalBoard[row][col].getStatus().equals("dl"))
				score += (2 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus().equals("tl"))
				score += (3 * board.getValue(finalBoard[row][col].getLetter()));
			else if(finalBoard[row][col].getStatus().equals("dw"))
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 2;
			}
			else if(finalBoard[row][col].getStatus().equals("tw"))
			{
				score += board.getValue(finalBoard[row][col].getLetter());
				multiplier = multiplier * 3;
			}
			if(!finalBoard[row][col].getStatus().equals("vd") && finalBoard[row][col].getLetter() != '#')
			{
				score2 += checkHorizontal(finalBoard, row, col);
			}
			
			
			if(row < BOARD_SIZE - 1)
			{
				if(!Character.isLetter(finalBoard[row + 1][col].getLetter()))
					break;
			}
			
			row++;
		}
		if(bigChecker == false)
		{
			return -999;
		}

		if(!board.checkIfWord(wor))
		{
			if(wor.length() > 1)
				return -999;
			else
			{
				score = 0;
				score2 = 0;
			}
		}
		else
		{
			//System.out.println(wor);
		} //marker
		
		return score * multiplier + score2;
		
	}
	public void print2DLetter(Tile[][] boardArray)
	{
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int x = 0; x < BOARD_SIZE; x++)
			{
				System.out.print(boardArray[i][x].getLetter());
				System.out.print(' ');
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public void print2DStatus(Tile[][] boardArray)
	{
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int x = 0; x < BOARD_SIZE; x++)
			{
				System.out.printf("%-3s", boardArray[i][x].getStatus());
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public Tile[][] arrayCopy(Tile[][] arr)
	{
		Tile[][] arr2 = new Tile[BOARD_SIZE][BOARD_SIZE];
		for(int r = 0; r < BOARD_SIZE; r++)
		{
			for(int i = 0; i < BOARD_SIZE; i++)
			{
				Tile t = new Tile(arr[r][i].getLetter(), arr[r][i].getStatus());
				arr2[r][i] = t;
			}
		}
		return arr2;
	}
}
