package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
	private MazeCell[][] maze;
	private MazeCell startCell, endCell;
	private int rowCount, columnCount;
	
	// generates a random maze.
	public void generateRandomMaze(int rowCount, int columnCount){
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		
		MazeGenerator generator = new MazeGenerator(this.rowCount, this.columnCount);
		this.maze = generator.generateNewMaze();
		this.startCell = generator.getStartCell();
		this.endCell = generator.getEndCell();
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public MazeCell getEndCell() {
		return endCell;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public MazeCell getStartCell() {
		return startCell;
	}
	
	// check if the row number is within the maze.
	private boolean inBoundsX(int number){
		return number >= 0 && number < maze[0].length;
	}

	// check if the column number is within the maze.
	private boolean inBoundsY(int number){
		return number >= 0 && number < maze.length;
	}
	
	// links all cells together.
	private void initializeMaze(){
		for (int columnIndex = 0; columnIndex < maze.length; columnIndex++){
			for (int rowIndex = 0; rowIndex < this.maze[columnIndex].length; rowIndex++){
				if (maze[columnIndex][rowIndex].IsWall() || maze[columnIndex][rowIndex].IsEnd()){
					continue;
				}
				
				// checks the left cell.
				if (inBoundsX(rowIndex - 1) && (!maze[columnIndex][rowIndex - 1].IsWall() && !maze[columnIndex][rowIndex - 1].IsStart())){
					maze[columnIndex][rowIndex].addAdjacentCell(maze[columnIndex][rowIndex - 1]);
				}
				
				// checks the right cell.
				if (inBoundsX(rowIndex + 1) && (!maze[columnIndex][rowIndex + 1].IsWall() && !maze[columnIndex][rowIndex + 1].IsStart())){
					maze[columnIndex][rowIndex].addAdjacentCell(maze[columnIndex][rowIndex + 1]);
				}
				
				// checks the above cell.
				if (inBoundsY(columnIndex - 1) && (!maze[columnIndex - 1][rowIndex].IsWall() && !maze[columnIndex - 1][rowIndex].IsStart())){
					maze[columnIndex][rowIndex].addAdjacentCell(maze[columnIndex - 1][rowIndex]);
				}
				
				// checks the below cell.
				if (inBoundsY(columnIndex + 1) && (!maze[columnIndex + 1][rowIndex].IsWall() && !maze[columnIndex + 1][rowIndex].IsStart())){
					maze[columnIndex][rowIndex].addAdjacentCell(maze[columnIndex + 1][rowIndex]);
				}
			}
		}
	}
	
	// prints the maze.
	public void printMaze(){
		for (int j = 0; j < this.maze[0].length; j++){
			for (int i = 0; i < maze.length; i++){
				System.out.print(maze[i][j].toString() + " ");
			}
			System.out.println();
		}
	}
	
	// reads the maze from a file.
	public void readMazeFromFile(){
		String mazeRow;
		String[] mazeDimensions, rowCells;
		int rowIndex = 0;
		
		try {
			FileReader file = new FileReader("maze.txt");
			BufferedReader read = new BufferedReader(file);
			
			// read the maze's dimensions.
			mazeDimensions = read.readLine().trim().split(" ");
			rowCount = Integer.parseInt(mazeDimensions[0]);
			columnCount = Integer.parseInt(mazeDimensions[1]);
			
			// create a new maze with these dimensions.
			maze = new MazeCell[columnCount][rowCount];
			
			// read the maze row by row.
			while((mazeRow = read.readLine()) != null){
				rowCells = mazeRow.trim().split(" ");
				
				for (int i = 0; i < rowCells.length; i++){
					MazeCell cell = new MazeCell(rowIndex, i);
					
					if (rowCells[i].equals("S")){
						cell.setIsStart(true);
						startCell = cell;
					}
					else if (rowCells[i].equals("E")){
						cell.setIsEnd(true);
						endCell = cell;
					}
					else if (rowCells[i].equals("#")){
						cell.setIsWall(true);
					}
					else{
						cell.setSteps(Integer.parseInt(rowCells[i]));
					}
					
					maze[i][rowIndex] = cell;
				}
				
				rowIndex++;
			}
			
			read.close();
			initializeMaze();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void setHeuristic(int heuristicsType){
		// Manhattan distance.
		if (heuristicsType == 1){
			for (int columnIndex = 0; columnIndex < maze.length; columnIndex++){
				for (int rowIndex = 0; rowIndex < this.maze[columnIndex].length; rowIndex++){
					if (maze[columnIndex][rowIndex].IsWall()){
						continue;
					}
					
					maze[columnIndex][rowIndex].setDistanceToGoal(Math.abs(maze[columnIndex][rowIndex].getRowNumber() - this.endCell.getRowNumber())
					+ Math.abs(maze[columnIndex][rowIndex].getColumnNumber() - this.endCell.getColumnNumber()));
				}
			}
		}
		else{
			for (int columnIndex = 0; columnIndex < maze.length; columnIndex++){
				for (int rowIndex = 0; rowIndex < this.maze[columnIndex].length; rowIndex++){
					if (maze[columnIndex][rowIndex].IsWall()){
						continue;
					}
					
					maze[columnIndex][rowIndex].setDistanceToGoal(Math.sqrt(Math.pow(maze[columnIndex][rowIndex].getRowNumber() - this.endCell.getRowNumber(), 2)
					+ Math.pow(maze[columnIndex][rowIndex].getColumnNumber() - this.endCell.getColumnNumber(), 2)));
				}
			}
		}
	}
}
