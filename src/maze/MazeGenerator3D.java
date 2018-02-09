package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator3D {
	private MazeCell3D[][][] maze;
	private MazeCell3D startCell, endCell;
	private int rowCount, columnCount, floorCount;
	private ArrayList<MazeCell3D> mazeSolution = new ArrayList<MazeCell3D>();
	private ArrayList<MazeCell3D> AdjacentCells = new ArrayList<MazeCell3D>();
	private ArrayList<MazeCell3D> repeatedCells = new ArrayList<MazeCell3D>();
	
	public MazeGenerator3D(int rowCount, int columnCount, int floorCount){
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.floorCount = floorCount;
		
		maze = new MazeCell3D[floorCount][columnCount][rowCount];
	}
	
	// used to remove already visited nodes in the path.
	private void filterAdjacentCells(ArrayList<MazeCell3D> AdjacentCells){
		for (int i = AdjacentCells.size() - 1; i > -1; i--){
			for (int j = 0; j < this.repeatedCells.size(); j++){
				if (AdjacentCells.get(i).equals(repeatedCells.get(j))){
					AdjacentCells.remove(i);
					break;
				}
			}
		}
	}
	
	// generates a new maze
	public MazeCell3D[][][] generateNewMaze(){
		initializeRandomMaze();
		setSolutionPath();
		generateRandomWalls();
		
		return maze;
	}
	
	private void generateRandomWalls(){
		Random random = new Random();
		
		for (int floorIndex = 0; floorIndex < maze.length; floorIndex++){
			for (int columnIndex = 0; columnIndex < this.maze[floorIndex].length; columnIndex++){
				for(int rowIndexk = 0; rowIndexk < this.maze[floorIndex][columnIndex].length; rowIndexk++){
					if (maze[floorIndex][columnIndex][rowIndexk].isGenerated()){
						continue;
					}
					
					int randomNumber = random.nextInt(10);
					if (randomNumber > 7){
						continue;
					}
					else{
						maze[floorIndex][columnIndex][rowIndexk].setIsWall(true);
					}
				}
			}
		}
		
		linkCells();
	}
	
	public MazeCell3D getEndCell() {
		return endCell;
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
	
	private void initializeRandomMaze(){
		Random random = new Random();
		
		// create the maze cells and initialize each to a random number.
		for (int floorIndex = 0; floorIndex < maze.length; floorIndex++){
			for (int columnIndex = 0; columnIndex < this.maze[floorIndex].length; columnIndex++){
				for (int rowIndex = 0; rowIndex < this.maze[floorIndex][columnIndex].length; rowIndex++){
					maze[floorIndex][columnIndex][rowIndex] = new MazeCell3D(rowIndex, columnIndex, floorIndex);
					maze[floorIndex][columnIndex][rowIndex].setSteps(random.nextInt(5) + 1);
				}
			}
		}
		
		// set a random start and end.
		setRandomStart();
		setRandomEnd();
		setRandomLadders();
		
		// set adjacent cells for each cell.
		linkCells();
	}
	
	private void linkCells(){
		for (int floorIndex = 0; floorIndex < this.maze.length; floorIndex++){
			for (int columnIndex = 0; columnIndex < this.maze[0].length; columnIndex++){
				for (int rowIndex = 0; rowIndex < this.maze[0][0].length; rowIndex++){
					maze[floorIndex][columnIndex][rowIndex].clearAdjacentCells();
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
	
	private void setAdjacentCells(MazeCell3D cell, int stepCount){
		repeatedCells.add(cell);
		
		if (stepCount == 0){
			this.AdjacentCells.add(cell);
			return;
		}
		
		ArrayList<MazeCell3D> AdjacentCells = cell.getAdjacentCells();
		for (int i = 0; i < AdjacentCells.size(); i++){
			if (AdjacentCells.get(i).IsEnd()){
				AdjacentCells.get(i).setIsGenerated(true);
				this.AdjacentCells.add(AdjacentCells.get(i));
				return;
			}
		}
		
		filterAdjacentCells(AdjacentCells);
		for (int i = 0; i < AdjacentCells.size(); i++){
			AdjacentCells.get(i).setIsGenerated(true);
			setAdjacentCells(AdjacentCells.get(i), stepCount - 1);
		}
	}

	private void setRandomEnd(){
		Random random = new Random();
		int endRowNumber = 0, endColumnNumber = 0, endFloorNumber = 0;
		int endLocation = random.nextInt(4);
		
		// 0 is up.
		if (endLocation == 0){
			endRowNumber = 0;
			endColumnNumber = random.nextInt(this.columnCount);
			endFloorNumber = random.nextInt(this.floorCount);
		}
		// 1 is right.
		else if (endLocation == 1){
			endRowNumber = random.nextInt(this.rowCount);
			endColumnNumber = this.columnCount - 1;
			endFloorNumber = random.nextInt(this.floorCount);
		}
		// 2 is down.
		else if (endLocation == 2){
			endRowNumber = this.rowCount - 1;
			endColumnNumber = random.nextInt(this.columnCount);
			endFloorNumber = random.nextInt(this.floorCount);
		}
		// 3 is left.
		else if (endLocation == 3){
			endRowNumber = random.nextInt(this.rowCount);
			endColumnNumber = 0;
			endFloorNumber = random.nextInt(this.floorCount);
		}
		
		if (maze[endFloorNumber][endColumnNumber][endRowNumber].IsStart()){
			setRandomEnd();
		}
		else{
			maze[endFloorNumber][endColumnNumber][endRowNumber].setIsEnd(true);
			endCell = maze[endFloorNumber][endColumnNumber][endRowNumber];
		}
		
	}
	
	private void setRandomLadders(){
		Random random = new Random();
		int ladderRowNumber = 0, ladderColumnNumber = 0, ladderFloorNumber = 0;
		for (int floorIndex = 0; floorIndex < maze.length; floorIndex++){
			if (floorIndex + 1 == maze.length){
				break;
			}
			
			ladderRowNumber = random.nextInt(this.rowCount);
			ladderColumnNumber = random.nextInt(this.columnCount);
			ladderFloorNumber = floorIndex;
			
			while(maze[ladderFloorNumber][ladderColumnNumber][ladderRowNumber].isUpLadder() || maze[ladderFloorNumber][ladderColumnNumber][ladderRowNumber].isDownLadder()
					|| maze[ladderFloorNumber + 1][ladderColumnNumber][ladderRowNumber].isUpLadder() || maze[ladderFloorNumber + 1][ladderColumnNumber][ladderRowNumber].isDownLadder()){
				ladderRowNumber = random.nextInt(this.rowCount);
				ladderColumnNumber = random.nextInt(this.columnCount);
				ladderFloorNumber = floorIndex;
			}
			
			maze[ladderFloorNumber][ladderColumnNumber][ladderRowNumber].setIsUpLadder(true);
			maze[ladderFloorNumber + 1][ladderColumnNumber][ladderRowNumber].setIsDownLadder(true);
		}
	}
	
	private void setRandomStart(){
		Random random = new Random();
		int startRowNumber = 0, startColumnNumber = 0, startFloorNumber = 0;
		int startLocation = random.nextInt(4);
		
		// 0 is up.
		if (startLocation == 0){
			startRowNumber = 0;
			startColumnNumber = random.nextInt(this.columnCount);
			startFloorNumber = random.nextInt(this.floorCount);
		}
		// 1 is right.
		else if (startLocation == 1){
			startRowNumber = random.nextInt(this.rowCount);
			startColumnNumber = this.columnCount - 1;
			startFloorNumber = random.nextInt(this.floorCount);
		}
		// 1 is down.
		else if (startLocation == 2){
			startRowNumber = this.rowCount - 1;
			startColumnNumber = random.nextInt(this.columnCount);
			startFloorNumber = random.nextInt(this.floorCount);
		}
		// 1 is left.
		else if (startLocation == 3){
			startRowNumber = random.nextInt(this.rowCount);
			startColumnNumber = 0;
			startFloorNumber = random.nextInt(this.floorCount);
		}
		
		maze[startFloorNumber][startColumnNumber][startRowNumber].setIsStart(true);
		maze[startFloorNumber][startColumnNumber][startRowNumber].setSteps(1);
		startCell = maze[startFloorNumber][startColumnNumber][startRowNumber];
	}
	
	private void setSolutionPath(){

		Stack<MazeCell3D> stack = new Stack<MazeCell3D>();
		HashSet<MazeCell3D> visited = new HashSet<MazeCell3D>();
		
		startCell.setIsGenerated(true);
		stack.push(startCell);
		
		while(!stack.isEmpty()){
			MazeCell3D cell = stack.pop();
			if (visited.add(cell)){
				mazeSolution.add(cell);
			}
			
			if (cell.IsEnd()){
				break;
			}
			
			this.repeatedCells.clear();
			this.AdjacentCells.clear();
			setAdjacentCells(cell, cell.getSteps());
			for (int i = 0; i < this.AdjacentCells.size(); i++){
				if (this.AdjacentCells.get(i).IsEnd()){
					stack.push(this.AdjacentCells.get(i));
					break;
				}
				
				if (!visited.contains(this.AdjacentCells.get(i))){
					stack.push(this.AdjacentCells.get(i));
				}
			}
		}
		
		return;
	}
}
