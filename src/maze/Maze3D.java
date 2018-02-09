package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maze3D {
	private MazeCell3D[][][] maze;
	private MazeCell3D startCell, endCell;
	private int rowCount, columnCount, floorCount;
	
	// generates a random maze.
	public void generateRandomMaze(int rowCount, int columnCount, int floorCount){
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.floorCount = floorCount;

		MazeGenerator3D generator = new MazeGenerator3D(this.rowCount, this.columnCount, this.floorCount);
		this.maze = generator.generateNewMaze();
		this.startCell = generator.getStartCell();
		this.endCell = generator.getEndCell();
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public MazeCell3D getEndCell() {
		return endCell;
	}
	
	public int getFloorCount() {
		return floorCount;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public MazeCell3D getStartCell() {
		return startCell;
	}
	
	// check if the row number is within the maze.
	private boolean inBoundsX(int number){
		return number >= 0 && number < maze[0][0].length;
	}

	// check if the column number is within the maze.
	private boolean inBoundsY(int number){
		return number >= 0 && number < maze[0].length;
	}
	
	// check if the floor number is within the maze.
	private boolean inBoundsZ(int number){
		return number >= 0 && number < maze.length;
	}
	
	private void initializeMaze(){
		for (int floorIndex = 0; floorIndex < this.maze.length; floorIndex++){
			for (int columnIndex = 0; columnIndex < this.maze[0].length; columnIndex++){
				for (int rowIndex = 0; rowIndex < this.maze[0][0].length; rowIndex++){
					if (maze[floorIndex][columnIndex][rowIndex].IsWall() || maze[floorIndex][columnIndex][rowIndex].IsEnd()){
						continue;
					}
					
					// checks the left cell.
					if (inBoundsX(rowIndex - 1) && (!maze[floorIndex][columnIndex][rowIndex - 1].IsWall() && !maze[floorIndex][columnIndex][rowIndex - 1].IsStart())){
						if (maze[floorIndex][columnIndex][rowIndex].isUpLadder()){
							if (inBoundsZ(floorIndex + 1) && (!maze[floorIndex + 1][columnIndex][rowIndex - 1].IsWall() && !maze[floorIndex + 1][columnIndex][rowIndex - 1].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex + 1][columnIndex][rowIndex - 1]);
							}
						}
						else if (maze[floorIndex][columnIndex][rowIndex].isDownLadder()){
							if (inBoundsZ(floorIndex - 1) && (!maze[floorIndex - 1][columnIndex][rowIndex - 1].IsWall() && !maze[floorIndex - 1][columnIndex][rowIndex - 1].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex - 1][columnIndex][rowIndex - 1]);
							}
						}
						else{
							maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex][columnIndex][rowIndex - 1]);
						}
					}
					
					// checks the right cell.
					if (inBoundsX(rowIndex + 1) && (!maze[floorIndex][columnIndex][rowIndex + 1].IsWall() && !maze[floorIndex][columnIndex][rowIndex + 1].IsStart())){
						if (maze[floorIndex][columnIndex][rowIndex].isUpLadder()){
							if (inBoundsZ(floorIndex + 1) && (!maze[floorIndex + 1][columnIndex][rowIndex + 1].IsWall() && !maze[floorIndex + 1][columnIndex][rowIndex + 1].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex + 1][columnIndex][rowIndex + 1]);
							}
						}
						else if (maze[floorIndex][columnIndex][rowIndex].isDownLadder()){
							if (inBoundsZ(floorIndex - 1) && (!maze[floorIndex - 1][columnIndex][rowIndex + 1].IsWall() && !maze[floorIndex - 1][columnIndex][rowIndex + 1].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex - 1][columnIndex][rowIndex + 1]);
							}
						}
						else{
							maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex][columnIndex][rowIndex + 1]);
						}
					}
					
					// checks the above cell.
					if (inBoundsY(columnIndex - 1) && (!maze[floorIndex][columnIndex - 1][rowIndex].IsWall() && !maze[floorIndex][columnIndex - 1][rowIndex].IsStart())){
						if (maze[floorIndex][columnIndex][rowIndex].isUpLadder()){
							if (inBoundsZ(floorIndex + 1) && (!maze[floorIndex + 1][columnIndex - 1][rowIndex].IsWall() && !maze[floorIndex + 1][columnIndex - 1][rowIndex].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex + 1][columnIndex - 1][rowIndex]);
							}
						}
						else if (maze[floorIndex][columnIndex][rowIndex].isDownLadder()){
							if (inBoundsZ(floorIndex - 1) && (!maze[floorIndex - 1][columnIndex - 1][rowIndex].IsWall() && !maze[floorIndex - 1][columnIndex - 1][rowIndex].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex - 1][columnIndex - 1][rowIndex]);
							}
						}
						else{
							maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex][columnIndex - 1][rowIndex]);
						}
					}
					
					// checks the below cell.
					if (inBoundsY(columnIndex + 1) && (!maze[floorIndex][columnIndex + 1][rowIndex].IsWall() && !maze[floorIndex][columnIndex + 1][rowIndex].IsStart())){
						if (maze[floorIndex][columnIndex][rowIndex].isUpLadder()){
							if (inBoundsZ(floorIndex + 1) && (!maze[floorIndex + 1][columnIndex + 1][rowIndex].IsWall() && !maze[floorIndex + 1][columnIndex + 1][rowIndex].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex + 1][columnIndex + 1][rowIndex]);
							}
						}
						else if (maze[floorIndex][columnIndex][rowIndex].isDownLadder()){
							if (inBoundsZ(floorIndex - 1) && (!maze[floorIndex - 1][columnIndex + 1][rowIndex].IsWall() && !maze[floorIndex - 1][columnIndex + 1][rowIndex].IsStart())){
								maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex - 1][columnIndex + 1][rowIndex]);
							}
						}
						else{
							maze[floorIndex][columnIndex][rowIndex].addAdjacentCell(maze[floorIndex][columnIndex + 1][rowIndex]);
						}
					}
				}
			}
		}
	}
	
	//prints the maze.
	public void printMaze(){
		for (int floorIndex = 0; floorIndex < this.maze.length; floorIndex++){
			for (int rowIndex = 0; rowIndex < this.maze[0][0].length; rowIndex++){
				for (int columnIndex = 0; columnIndex < this.maze[0].length; columnIndex++){
					System.out.print(maze[floorIndex][columnIndex][rowIndex].toString() + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	// reads the maze form file.
	public void readMazeFromFile(){
		String mazeRow;
		String[] mazeDimensions, rowCells;
		int rowIndex = 0, floorIndex = 0;
		
		try {
			FileReader file = new FileReader("maze.txt");
			BufferedReader read = new BufferedReader(file);
			
			// read the maze's dimensions.
			mazeDimensions = read.readLine().trim().split(" ");
			if (mazeDimensions.length == 2){
				rowCount = Integer.parseInt(mazeDimensions[0]);
				columnCount = Integer.parseInt(mazeDimensions[1]);
				floorCount = 1;
			}
			else{
				rowCount = Integer.parseInt(mazeDimensions[0]);
				columnCount = Integer.parseInt(mazeDimensions[1]);
				floorCount = Integer.parseInt(mazeDimensions[2]);
			}
			
			
			// create a new maze with these dimensions.
			maze = new MazeCell3D[floorCount][columnCount][rowCount];
			
			// read the maze row by row.
			while((mazeRow = read.readLine()) != null){
				if (mazeRow.equals("")){
					floorIndex++;
					continue;
				}
				
				rowCells = mazeRow.trim().split(" ");
				
				for (int columnIndex = 0; columnIndex < rowCells.length; columnIndex++){
					MazeCell3D cell = new MazeCell3D(rowIndex, columnIndex, floorIndex);
					
					if (rowCells[columnIndex].equals("S")){
						cell.setIsStart(true);
						startCell = cell;
					}
					else if (rowCells[columnIndex].equals("E")){
						cell.setIsEnd(true);
						endCell = cell;
					}
					else if (rowCells[columnIndex].equals("A")){
						cell.setIsUpLadder(true);
					}
					else if (rowCells[columnIndex].equals("D")){
						cell.setIsDownLadder(true);
					}
					else if (rowCells[columnIndex].equals("#")){
						cell.setIsWall(true);
					}
					else{
						cell.setSteps(Integer.parseInt(rowCells[columnIndex]));
					}
					
					maze[floorIndex][columnIndex][rowIndex] = cell;
				}
				
				rowIndex++;
				if (rowIndex == this.rowCount){
					rowIndex = 0;
				}
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
			for (int floorIndex = 0; floorIndex < this.maze.length; floorIndex++){
				for (int columnIndex = 0; columnIndex < this.maze[0].length; columnIndex++){
					for (int rowIndex = 0; rowIndex < this.maze[0][0].length; rowIndex++){
						if (maze[floorIndex][columnIndex][rowIndex].IsWall()){
							continue;
						}

						maze[floorIndex][columnIndex][rowIndex].setDistanceToGoal(Math.abs(maze[floorIndex][columnIndex][rowIndex].getRowNumber() - this.endCell.getRowNumber())
						+ Math.abs(maze[floorIndex][columnIndex][rowIndex].getColumnNumber() - this.endCell.getColumnNumber())
						+ Math.abs(maze[floorIndex][columnIndex][rowIndex].getFloorNumber() - this.endCell.getFloorNumber()));
					}
				}
			}
		}
		else{
			for (int floorIndex = 0; floorIndex < this.maze.length; floorIndex++){
				for (int columnIndex = 0; columnIndex < this.maze[0].length; columnIndex++){
					for (int rowIndex = 0; rowIndex < this.maze[0][0].length; rowIndex++){
						if (maze[floorIndex][columnIndex][rowIndex].IsWall()){
							continue;
						}

						maze[floorIndex][columnIndex][rowIndex].setDistanceToGoal(Math.sqrt(Math.pow(maze[floorIndex][columnIndex][rowIndex].getRowNumber() - this.endCell.getRowNumber(), 2)
						+ Math.pow(maze[floorIndex][columnIndex][rowIndex].getColumnNumber() - this.endCell.getColumnNumber(), 2)
						+ Math.pow(maze[floorIndex][columnIndex][rowIndex].getFloorNumber() - this.endCell.getFloorNumber(), 2)));
					}
				}
			}
		}
	}
}
