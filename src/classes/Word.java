package classes;

public class Word {
	private String word;
	private int startRow;
	private int startColumn;
	private int endRow;
	private int endColumn;
	private boolean found;
	
	public Word(String word) {
		this.word = word; 
		startRow = -1; 
		startColumn = -1;
		endRow = -1;
		endColumn = -1;
		setFound(false);
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getStartColumn() {
		return startColumn;
	}
	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getEndColumn() {
		return endColumn;
	}
	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	} 
	
	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public void print() {
		System.out.print(word + " ");
		System.out.print(startRow + ":" + startColumn + " ");
		System.out.print(endRow + ":" + endColumn);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Word)) { 
            return false; 
        } 
		
		if (this.word.equals(((Word) o).getWord())) {
			return true;
		} else {
			return false;
		}
	}
}