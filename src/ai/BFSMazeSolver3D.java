package ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import maze.Maze3D;
import maze.MazeCell3D;

public class BFSMazeSolver3D {
	private Maze3D maze;
	private MazeCell3D startCell;
	ArrayList<MazeCell3D> mazeSolution = new ArrayList<MazeCell3D>();
	ArrayList<MazeCell3D> AdjacentCells = new ArrayList<MazeCell3D>();
	ArrayList<MazeCell3D> repeatedCells = new ArrayList<MazeCell3D>();
	
	public BFSMazeSolver3D(Maze3D maze){
		this.maze = maze;
		this.startCell = maze.getStartCell();
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
	
	private void setAdjacentCells(MazeCell3D cell, int stepCount){
		repeatedCells.add(cell);
		
		if (stepCount == 0){
			this.AdjacentCells.add(cell);
			return;
		}
		
		ArrayList<MazeCell3D> AdjacentCells = cell.getAdjacentCells();
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
		Queue<MazeCell3D> queue = new LinkedList<MazeCell3D>();
		HashSet<MazeCell3D> visited = new HashSet<MazeCell3D>();
		
		queue.add(startCell);
		
		while(!queue.isEmpty()){
			MazeCell3D cell = queue.poll();
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
					queue.add(this.AdjacentCells.get(i));
					AdjacentCells.clear();
					break;
				}
			}
			
			for (int i = 0; i < this.AdjacentCells.size(); i++){
				if (!visited.contains(this.AdjacentCells.get(i))){
					queue.add(this.AdjacentCells.get(i));
				}
			}
		}
		
		return false;
	}
}
