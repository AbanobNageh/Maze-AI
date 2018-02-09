package ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import maze.Maze;
import maze.MazeCell;

public class DFSMazeSolver {
	private Maze maze;
	private MazeCell startCell;
	ArrayList<MazeCell> mazeSolution = new ArrayList<MazeCell>();
	ArrayList<MazeCell> AdjacentCells = new ArrayList<MazeCell>();
	ArrayList<MazeCell> repeatedCells = new ArrayList<MazeCell>();
	
	public DFSMazeSolver(Maze maze){
		this.maze = maze;
		this.startCell = maze.getStartCell();
	}
	
	// used to remove already visited nodes in the path.
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
	
	public void printSolution(){
		this.maze.printMaze();
		
		for (int i = 0; i < this.mazeSolution.size(); i++){
			if (i + 1 == this.mazeSolution.size()){
				System.out.print(this.mazeSolution.get(i).toString() + " " + this.mazeSolution.get(i).getLocation());
			}
			else{
				System.out.print(this.mazeSolution.get(i).toString() + " " + this.mazeSolution.get(i).getLocation() + "--> ");
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
				this.AdjacentCells.add(AdjacentCells.get(i));
				return;
			}
		}
		
		filterAdjacentCells(AdjacentCells);
		for (int i = 0; i < AdjacentCells.size(); i++){
			setAdjacentCells(AdjacentCells.get(i), stepCount - 1);
		}
	}
	
	public Boolean solve(){
		Stack<MazeCell> stack = new Stack<MazeCell>();
		HashSet<MazeCell> visited = new HashSet<MazeCell>();
		
		stack.push(startCell);
		
		while(!stack.isEmpty()){
			MazeCell cell = stack.pop();
			if (visited.add(cell)){
				mazeSolution.add(cell);
			}
			
			if (cell.IsEnd()){
				return true;
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
		
		return false;
	}
}
