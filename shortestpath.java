/*
Given (M, N) 2D matrix with each element containing integer cost 
find the min cost path from (0,0) to (M,N)

Coordinate convention: 
- (0,0) top left corner
- (M,N) bottom right corner
- possible directions: right, down, diagonal (right + down)
- No Dijkstra's algorithm. Use DP instead.

This solution modifies the given map. If it's read-only 
create another (M, N) matrix to store costs

NOTE: 
3 possible ways to get to the current location (i,j):
	(i - 1, j - 1), (i, j - 1), (i - 1, j)
	
MinCost(i,j) = Cost(i,j) + Min(
	MinCost(i - 1, j - 1),
	MinCost(i, j - 1),
	MinCost(i - 1, j),
)
*/

int findMinPath(int[][] map) {
	for (int i = 1; i < map.length; i++) {
		map[i][0] = map[i - 1][0] + map[i][0];
	}

	for (int i = 1; i < map[0].length; i++) {
		map[0][i] = map[0][i - 1] + map[0][i];
	}
	
	for (int row = 1; row < map.length; row++) {
		for (int col = 1; col < map[0].length; col++) {
			int diag = map[row - 1][col - 1];
			int left = map[row][col - 1];
			int up = map[row - 1][col];
			int minCandidate = Math.min(diag, Math.min(left, up));
			map[row][col] = map[row][col] + minCandidate;	
		}
	}
	return map[map.length - 1][map[0].length - 1];
}