package gameRunner;

public class Tile
{
	private char letter;
	private String status;
	public Tile()
	{
		status = "bl";
		letter = '#';
	}
	
	public Tile(char let)
	{
		status = "bl";
		letter = let;
	}
	
	public Tile(char let, String stat)
	{
		status = stat;
		letter = let;
	}
	
	public char getLetter()
	{
		return letter;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String stat)
	{
		status = stat;
	}
	
	public void setLetter(char let)
	{
		letter = let;
	}
}

