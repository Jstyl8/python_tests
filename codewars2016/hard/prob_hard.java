import java.util.*;

public class prob_hard {

		static final char EMPTY = '_';
		static final char PLAYER = 'p';
		static final char MATERIAL = 'm';
		static final char ENEMY = 'e';
		static char[][] map;
		static int currentMaterials;
		static int totalMaterials;
		static boolean dead;
		static int playerRow= -1, playerCol= -1;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		StringTokenizer st;
		String line="";
		totalMaterials = currentMaterials = 0;
		int maxRows,maxCols=0;
		
		//Create map
		st = new StringTokenizer(sc.nextLine());
		maxRows = Integer.parseInt(st.nextToken());
		maxCols = Integer.parseInt(st.nextToken());
		map = new char[maxRows][maxCols];
		//populate empty map
		for (int i=0; i < maxRows ; i++ ) {
			for (int j=0; j < maxCols ; j++ ) {
				map[i][j]=EMPTY;
			}
		}

		//Populate map with elements
		line = sc.nextLine();
		while(!line.equals("."))
		{
			st = new StringTokenizer(line);
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			map[row][col]=st.nextToken().charAt(0);
			if(checkPosition(MATERIAL,row,col))
				totalMaterials++;
			else if(checkPosition(PLAYER,row,col)) {
				if(playerRow !=-1) {
					System.err.println("Error: It can't be several players!");
					System.exit(-1);
				} else {
					playerRow=row;
					playerCol=col;
				}
			}

			line = sc.nextLine();
		}

		if(playerRow==-1) {
			System.err.println("Error: It has to be a player!");
			System.exit(-1);
		}

		System.out.println("Initial state");
		//Read and execute movements
		while(true)
		{
			printMap();
			printStatus();
			if(isFinished())
				break;

			st = new StringTokenizer(sc.nextLine());
			int despRow = Integer.parseInt(st.nextToken());
			int despCol = Integer.parseInt(st.nextToken());
			execMove(despRow,despCol);
		}
		
	}
	static boolean isFinished() {
		if(currentMaterials==totalMaterials){
			System.out.println("You have collected all the materials! Congratulations!");
			return true;
		}else if(dead) {
			System.out.println("You died!");
			return true;
		}
		return false;
	}
	static void execMove(int despRow, int despCol)
	{
		if(despRow > 1 || despCol > 1 || despRow < -1 || despCol < -1) {
			System.out.println("Can't move that distance! ("+despRow+","+despCol+")");
		}
		else {
			int newRow = playerRow+despRow;
			int newCol = playerCol+despCol;
			if(checkPosition(MATERIAL,newRow,newCol)) {
				currentMaterials++;
			}
			else if(checkPosition(ENEMY,newRow,newCol)) {
				dead = true;
			}
			map[playerRow][playerCol] = EMPTY;
			map[newRow][newCol] = PLAYER;
			playerRow=newRow;
			playerCol=newCol;
			//System.out.println("Last movement ("+despRow+","+despCol+")");
		}
	}

	static boolean checkPosition(char value,int row, int col) {
		return map[row][col]==value? true:false;
	}

	static void printStatus() {
		System.out.println("Materials collected "+currentMaterials+"/"+totalMaterials);
	}

	static void printMap() {
		for (int i=0; i < map.length ; i++ ) {
			for (int j=0; j < map[0].length ; j++ ) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
	}

}
