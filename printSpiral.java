import java.util.*;

public class printSpiral {

	public static void main(String[] args) {
		int[][] mat1 = {
			{1,2,3},
			{4,5,6},
			{7,8,9}
		};
		printSpiral(mat1);
		int[][] mat2 = {
			{1,2,3,4,5},
			{6,7,8,9,10},
			{11,12,13,14,15}
		};
		printSpiral(mat2);
	}
	/*
	print 2d array in spriral form
	starting from top-left corner 

	123
	546
	789

	should print out 1 2 3 6 9 8 7 5 4
	*/
	public static void printSpiral(int[][] mat) {
		Direction dir = new Direction();
		boolean[][] visited = new boolean[mat.length][mat[0].length];
		Point curr = new Point(0,0);
		while (curr != null) {
			System.out.print(mat[curr.row][curr.col] + " ");
			visited[curr.row][curr.col] = true;
			// change row and col 
			curr = getNextLocation(curr, dir, visited);
		}
      System.out.println();
	}
	
	private static Point getNextLocation(Point curr, Direction dir, boolean[][] visited) {
		for (int i = 0; i < 4; i++) {
			Point next = dir.getNextPoint(curr);
			if (isValid(next, visited)) {
				return next;
			}
			dir.nextDirection();	
		}
		return null;		
	}
	private static boolean isValid(Point pt, boolean[][] visited) {
		boolean inRange = (pt.row >= 0 && pt.row < visited.length) &&
				(pt.col >= 0 && pt.col < visited[0].length);
		if (!inRange) {
			return false;
		} 
		return !visited[pt.row][pt.col];
	}
}
class Point {
	int row;
	int col;
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
class Direction {
	public static final int RIGHT = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int UP = 3;
	private int dir;
	public Direction() {
		dir = RIGHT;
	}
	public void nextDirection() {
		dir = (dir + 1) % 4;
	}
	public Point getNextPoint(Point curr) {
		if (dir == RIGHT) {
			return new Point(curr.row, curr.col + 1);
		} else if (dir == LEFT) {
			return new Point(curr.row, curr.col - 1);
		} else if (dir == UP) {
			return new Point(curr.row - 1, curr.col);
		} else { // DOWN
			return new Point(curr.row + 1, curr.col);
		}
	}
}


