package maze;

import java.util.ArrayList;

public class MazeCell3D implements Comparable<MazeCell3D>{
	private Boolean isWall = false;
	private Boolean isStart = false;
	private Boolean isEnd = false;
	private Boolean isPath = true;
	private Boolean isUpLadder = false;
	private Boolean isDownLadder = false;
	private Boolean isGenerated = false;
	private int steps = 1;
	private double totalCost = Double.MAX_VALUE;
	private double distanceToGoal = 0;
	private double distanceFromStart = 0;
	private int rowNumber;
	private int columnNumber;
	private int floorNumber;
	private ArrayList<MazeCell3D> adjacentCells = new ArrayList<MazeCell3D>();
	
	public MazeCell3D(int rowNumber, int columnNumber, int floorNumber){
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.floorNumber = floorNumber;
	}

	public void addAdjacentCell(MazeCell3D cell){
		this.adjacentCells.add(cell);
	}

	public void clearAdjacentCells (){
		this.adjacentCells.clear();
	}

	@Override
	public int compareTo(MazeCell3D cell) {
		if (this.totalCost < cell.getTotalCost()){
			return -1;
		}
		else if (this.totalCost == cell.getTotalCost()){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MazeCell3D other = (MazeCell3D) obj;
		if (columnNumber != other.columnNumber) {
			return false;
		}
		if (rowNumber != other.rowNumber) {
			return false;
		}
		if (floorNumber != other.floorNumber){
			return false;
		}
		return true;
	}

	public ArrayList<MazeCell3D> getAdjacentCells() {
		return adjacentCells;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public double getDistanceFromStart() {
		return distanceFromStart;
	}

	public double getDistanceToGoal() {
		return distanceToGoal;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}
	
	public String getLocation(){
		return "(" + this.rowNumber + ", " + this.columnNumber + ", " + this.floorNumber + ")";
	}
	
	public int getRowNumber() {
		return rowNumber;
	}
	
	public int getSteps() {
		return steps;
	}

	public double getTotalCost() {
		return totalCost;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnNumber;
		result = prime * result + rowNumber;
		result = prime * result + floorNumber;
		return result;
	}
	
	public Boolean isDownLadder() {
		return isDownLadder;
	}
	
	public Boolean IsEnd() {
		return isEnd;
	}
	
	public Boolean isGenerated() {
		return isGenerated;
	}

	public Boolean IsPath() {
		return isPath;
	}
	
	public Boolean IsStart() {
		return isStart;
	}
	
	public Boolean isUpLadder() {
		return isUpLadder;
	}
	
	public Boolean IsWall() {
		return isWall;
	}
	
	public void setDistanceFromStart(double distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}
	
	public void setDistanceToGoal(double distanceToGoal) {
		this.distanceToGoal = distanceToGoal;
	}
	
	public void setIsDownLadder(Boolean isDownLadder) {
		this.isDownLadder = isDownLadder;
	}
	
	public void setIsEnd(Boolean isEnd) {
		if (isEnd){
			this.isPath = false;
		}
		this.isEnd = isEnd;
	}
	
	public void setIsGenerated(Boolean isGenerated) {
		this.isGenerated = isGenerated;
	}
	
	public void setIsStart(Boolean isStart) {
		if (isStart){
			this.isPath = false;
		}
		this.isStart = isStart;
	}
	
	public void setIsUpLadder(Boolean isUpLadder) {
		this.isUpLadder = isUpLadder;
	}
	
	public void setIsWall(Boolean isWall) {
		if (isWall){
			this.isPath = false;
		}
		
		this.isWall = isWall;
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setTotalCost(){
		this.totalCost = this.distanceFromStart + this.distanceToGoal;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String string = "";
		if (isUpLadder()){
			string = "A";
		}
		else if (isDownLadder()){
			string = "D";
		}
		else if (IsPath()){
			string = Integer.toString(this.steps);
		}
		else if (IsWall()){
			string = "#";
		}
		else if (IsStart()){
			string = "S";
		}
		else if (IsEnd()){
			string = "E";
		}
		
		return string;
	}
}
