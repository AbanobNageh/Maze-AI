package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
	private MazeCell[][] maze;
	private MazeCell startCell, endCell;
	private int rowCount, columnCount;
	private ArrayList<MazeCell> mazeSolution = new ArrayList<MazeCell>();
	private ArrayList<MazeCell> AdjacentCells = new ArrayList<MazeCell>();
	private ArrayList<MazeCell> repeatedCells = new ArrayList<MazeCell>();
	
	public MazeGenerator(int rowCount, int columnCount){
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		
		maze = new MazeCell[columnCount][rowCount];
	}
	
	private void filterAdjacentCells(ArrayList<MazeCell> AdjacentCells){
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
	public MazeCell[][] generateNewMaze(){
		initializeRandomMaze();
		setSolutionPath();
		generateRandomWalls();
		
		return maze;
	}
	
	private void generateRandomWalls(){
		Random random = new Random();
		
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < this.maze[i].length; j++){
				if (maze[i][j].isGenerated()){
					continue;
				}
				
				int randomNumber = random.nextInt(10);
				if (randomNumber > 7){
					continue;
				}
				else{
					maze[i][j].setIsWall(true);
				}
			}
		}
		
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < this.maze[i].length; j++){
				if (maze[i][j].IsWall() || maze[i][j].IsEnd()){
					continue;
				}
				
				maze[i][j].clearAdjacentCells();
				if (inBoundsX(j - 1) && (!maze[i][j - 1].IsWall() && !maze[i][j - 1].IsStart())){
					maze[i][j].addAdjacentCell(maze[i][j - 1]);
				}
				
				if (inBoundsX(j + 1) && (!maze[i][j + 1].IsWall() && !maze[i][j + 1].IsStart())){
					maze[i][j].addAdjacentCell(maze[i][j + 1]);
				}
				
				if (inBoundsY(i - 1) && (!maze[i - 1][j].IsWall() && !maze[i - 1][j].IsStart())){
					maze[i][j].addAdjacentCell(maze[i - 1][j]);
				}
				
				if (inBoundsY(i + 1) && (!maze[i + 1][j].IsWall() && !maze[i + 1][j].IsStart())){
					maze[i][j].addAdjacentCell(maze[i + 1][j]);
				}
			}
		}
	}
	
	public MazeCell getEndCell() {
		return endCell;
	}
	
	public MazeCell getStartCell() {
		return startCell;
	}

	private boolean inBoundsX(int number){
		return number >= 0 && number < maze[0].length;
	}
	
	private boolean inBoundsY(int number){
		return number >= 0 && number < maze.length;
	}
	
	private void initializeRandomMaze(){
		Random random = new Random();
		
		// create the maze cells and initialize each to a random number.
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < this.maze[i].length; j++){
				maze[i][j] = new MazeCell(j, i);
				maze[i][j].setSteps(random.nextInt(5) + 1);
			}
		}
		
		// set a random start and end.
		setRandomStart();
		setRandomEnd();
		
		// set adjacent cells for each cell.
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < this.maze[i].length; j++){
				if (maze[i][j].IsWall() || maze[i][j].IsEnd()){
					continue;
				}
				
				if (inBoundsX(j - 1) && (!maze[i][j - 1].IsWall() && !maze[i][j - 1].IsStart())){
					maze[i][j].addAdjacentCell(maze[i][j - 1]);
				}
				
				if (inBoundsX(j + 1) && (!maze[i][j + 1].IsWall() && !maze[i][j + 1].IsStart())){
					maze[i][j].addAdjacentCell(maze[i][j + 1]);
				}
				
				if (inBoundsY(i - 1) && (!maze[i - 1][j].IsWall() && !maze[i - 1][j].IsStart())){
					maze[i][j].addAdjacentCell(maze[i - 1][j]);
				}
				
				if (inBoundsY(i + 1) && (!maze[i + 1][j].IsWall() && !maze[i + 1][j].IsStart())){
					maze[i][j].addAdjacentCell(maze[i + 1][j]);
				}
			}
		}
	}
	
	private void setAdjacentCells(MazeCell cell, int stepCount){
		repeatedCells.add(cell);
		
		if (stepCount == 0){
			this.AdjacentCells.add(cell);
			return;
		}
		
		ArrayList<MazeCell> AdjacentCells = cell.getAdjacentCells();
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
		int endRowNumber = 0, endColumnNumber = 0;
		int endLocation = random.nextInt(4);
		
		// 0 is up.
		if (endLocation == 0){
			endRowNumber = 0;
			endColumnNumber = random.nextInt(this.columnCount);
		}
		// 1 is right.
		else if (endLocation == 1){
			endRowNumber = random.nextInt(this.rowCount);
			endColumnNumber = this.columnCount - 1;
		}
		// 2 is down.
		else if (endLocation == 2){
			endRowNumber = this.rowCount - 1;
			endColumnNumber = random.nextInt(this.columnCount);
		}
		// 3 is left.
		else if (endLocation == 3){
			endRowNumber = random.nextInt(this.rowCount);
			endColumnNumber = 0;
		}
		
		if (maze[endColumnNumber][endRowNumber].IsStart()){
			setRandomEnd();
		}
		else{
			maze[endColumnNumber][endRowNumber].setIsEnd(true);
			endCell = maze[endColumnNumber][endRowNumber];
		}
		
	}
	
	private void setRandomStart(){
		Random random = new Random();
		int startRowNumber = 0, startColumnNumber = 0;
		int startLocation = random.nextInt(4);
		
		// 0 is up.
		if (startLocation == 0){
			startRowNumber = 0;
			startColumnNumber = random.nextInt(this.columnCount);
		}
		// 1 is right.
		else if (startLocation == 1){
			startRowNumber = random.nextInt(this.rowCount);
			startColumnNumber = this.columnCount - 1;
		}
		// 1 is down.
		else if (startLocation == 2){
			startRowNumber = this.rowCount - 1;
			startColumnNumber = random.nextInt(this.columnCount);
		}
		// 1 is left.
		else if (startLocation == 3){
			startRowNumber = random.nextInt(this.rowCount);
			startColumnNumber = 0;
		}
		
		maze[startColumnNumber][startRowNumber].setIsStart(true);
		maze[startColumnNumber][startRowNumber].setSteps(1);
		startCell = maze[startColumnNumber][startRowNumber];
	}
	
	private void setSolutionPath(){

		Stack<MazeCell> stack = new Stack<MazeCell>();
		HashSet<MazeCell> visited = new HashSet<MazeCell>();
		
		startCell.setIsGenerated(true);
		stack.push(startCell);
		
		while(!stack.isEmpty()){
			MazeCell cell = stack.pop();
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
