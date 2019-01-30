package ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import maze.Maze;
import maze.MazeCell;

public class AStarMazeSolver {
	private Maze maze;
	private MazeCell startCell;
	ArrayList<MazeCell> mazeSolution = new ArrayList<MazeCell>();;
	ArrayList<MazeCell> AdjacentCells = new ArrayList<MazeCell>();;
	ArrayList<MazeCell> repeatedCells = new ArrayList<MazeCell>();;
	
	public AStarMazeSolver(Maze maze2){
		this.maze = maze2;
		this.startCell = maze2.getStartCell();
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
				System.out.println(this.mazeSolution.get(i).toString() + " " + this.mazeSolution.get(i).getLocation() + " " + this.mazeSolution.get(i).getTotalCost());
			}
			else{
				System.out.println(this.mazeSolution.get(i).toString() + " " + this.mazeSolution.get(i).getLocation() + " " + this.mazeSolution.get(i).getTotalCost() + " --> ");
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
	
	public void setHeuristic(int heuristicsType){
		maze.setHeuristic(heuristicsType);
	}
	
	public Boolean solve(){
		PriorityQueue<MazeCell> priorityQueue = new PriorityQueue<MazeCell>();
		HashSet<MazeCell> visited = new HashSet<MazeCell>();
		
		startCell.setDistanceFromStart(0);
		startCell.setTotalCost();
		priorityQueue.add(startCell);
		
		while(!priorityQueue.isEmpty()){
			MazeCell cell = priorityQueue.poll();
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
					this.AdjacentCells.get(i).setDistanceFromStart(cell.getDistanceFromStart() + cell.getSteps());
					this.AdjacentCells.get(i).setTotalCost();
					priorityQueue.add(this.AdjacentCells.get(i));
					AdjacentCells.clear();
					break;
				}
			}
			
			for (int i = 0; i < this.AdjacentCells.size(); i++){
				if (!visited.contains(this.AdjacentCells.get(i))){
					this.AdjacentCells.get(i).setDistanceFromStart(cell.getDistanceFromStart() + cell.getSteps());
					this.AdjacentCells.get(i).setTotalCost();
					if (priorityQueue.contains(this.AdjacentCells.get(i))){
						priorityQueue.remove(this.AdjacentCells.get(i));
					}
					priorityQueue.add(this.AdjacentCells.get(i));
				}
			}
		}
		
		return false;
	}
}
