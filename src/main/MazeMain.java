package main;

import java.util.Scanner;

import ai.AStarMazeSolver3D;
import ai.BFSMazeSolver3D;
import ai.DFSMazeSolver3D;
import ai.GreedyMazeSolver3D;
import ai.UCSMazeSolver3D;
import maze.Maze3D;

public class MazeMain {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// a 3D maze.
		Maze3D maze = new Maze3D();
		
		System.out.println("Enter the number of one of the options below:");
		System.out.println("1. read the maze from file.");
		System.out.println("2. generate a random maze.");
		
		String mazeSourceChoice = scanner.next();
		
		if (mazeSourceChoice.equals("1")){
			maze.readMazeFromFile();
		}
		else if (mazeSourceChoice.equals("2")){
			int rowCount = 0, columnCount = 0, floorCount = 0;
			
			// read row count.
			System.out.println("Enter number of rows in the maze (minimum is 3).");
			rowCount = scanner.nextInt();
			if (rowCount < 3){
				System.out.println("wrong input, the program is exiting.");
				System.exit(-1);
			}
			
			// read column count.
			System.out.println("Enter number of columns in the maze (minimum is 3).");
			columnCount = scanner.nextInt();
			if (columnCount < 3){
				System.out.println("wrong input, the program is exiting.");
				System.exit(-1);
			}
			
			// read floor count.
			System.out.println("Enter number of floors in the maze (minimum is 1).");
			floorCount = scanner.nextInt();
			if (floorCount < 1){
				System.out.println("wrong input, the program is exiting.");
				System.exit(-1);
			}
			
			maze.generateRandomMaze(rowCount, columnCount, floorCount);
		}
		else{
			System.out.println("wrong choice, the program is exiting.");
			System.exit(-1);
		}
		
		System.out.println("Enter the number of one of the options below:");
		System.out.println("1. solve using DFS");
		System.out.println("2. solve using BFS");
		System.out.println("3. solve using UCS");
		System.out.println("4. solve using Greedy");
		System.out.println("5. solve using A*");
		
		String algorithmSolutionChoice = scanner.next();
		
		if (algorithmSolutionChoice.equals("1")){
			DFSMazeSolver3D DFSSolver = new DFSMazeSolver3D(maze);
			if (DFSSolver.solve()){
				DFSSolver.printSolution();
			}
			else{
				System.out.println("maze has no solution.");
			}
		}
		else if (algorithmSolutionChoice.equals("2")){
			BFSMazeSolver3D BFSSolver = new BFSMazeSolver3D(maze);
			if (BFSSolver.solve()){
				BFSSolver.printSolution();
			}
			else{
				System.out.println("maze has no solution.");
			}
		}
		else if (algorithmSolutionChoice.equals("3")){
			UCSMazeSolver3D UCSSolver = new UCSMazeSolver3D(maze);
			if (UCSSolver.solve()){
				UCSSolver.printSolution();
			}
			else{
				System.out.println("maze has no solution.");
			}
		}
		else if (algorithmSolutionChoice.equals("4")){
			GreedyMazeSolver3D greedySolver = new GreedyMazeSolver3D(maze);
			
			System.out.println("Enter the number of one of the Heuristics below:");
			System.out.println("1. Manhattan Distance.");
			System.out.println("2. Euclidean Distance.");
			
			String heuristicType = scanner.next();
			
			if (heuristicType.equals("1")){
				greedySolver.setHeuristic(1);
			}
			else if (heuristicType.equals("2")){
				greedySolver.setHeuristic(2);
			}
			else{
				System.out.println("wrong choice, the program is exiting.");
				System.exit(-1);
			}
			
			if (greedySolver.solve()){
				greedySolver.printSolution();
			}
			else{
				System.out.println("maze has no solution.");
			}
		}
		else if (algorithmSolutionChoice.equals("5")){
			AStarMazeSolver3D aStarsolver = new AStarMazeSolver3D(maze);
			aStarsolver.setHeuristic(1);
			if (aStarsolver.solve()){
				aStarsolver.printSolution();
			}
			else{
				System.out.println("maze has no solution.");
			}
		}
		else{
			System.out.println("wrong choice, the program is exiting.");
			System.exit(-1);
		}
		
		scanner.close();
	}
}
