import java.util.*;

public class prob_hard {

		static final char EMPTY = '_';
		static final char HERO = 'h';
		static final char CORE = 'c';
		static final char TOWER = 't';
		static final char ENEMY = 'e';
		static char[][] map;
		static int enemiesDefeated,currentWeapons;
		static int totalEnemies,totalWeapons;
		static boolean dead;
		static int heroRow= -1, heroCol= -1;
		static int coreRow= -1, coreCol= -1;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		StringTokenizer st;
		String line="";
		totalEnemies = enemiesDefeated = 0;
		totalWeapons = currentWeapons = 0;
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
			if(checkPosition(ENEMY,row,col))
				totalEnemies++;
			else if(checkPosition(TOWER,row,col))
				totalWeapons++;
			else if(checkPosition(HERO,row,col)) {
				if(heroRow !=-1) {
					System.err.println("Error: It can't be several main heroes!");
					System.exit(-1);
				} else {
					heroRow=row;
					heroCol=col;
				}
			} else if(checkPosition(CORE,row,col)) {
				if(coreRow !=-1) {
					System.err.println("Error: It can't be several cores!");
					System.exit(-1);
				} else {
					coreRow=row;
					coreCol=col;
				}
			}
			line = sc.nextLine();
		}

		if(heroRow==-1 || coreRow==-1) {
			System.err.println("Error: It has to be a hero and a core!");
			System.exit(-1);
		}

		if(totalEnemies!=totalWeapons) {
			System.err.println("Error: It has to be the same number of enemies and towers!");
			System.exit(-1);
		}

		System.out.println("Game Status:");
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
		if(enemiesDefeated==totalEnemies){
			System.out.println("You have cleaned the battle arena! Congratulations!");
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
		} else {
			int newRow = heroRow+despRow;
			int newCol = heroCol+despCol;
			if(checkPosition(TOWER,newRow,newCol)) {
				currentWeapons++;
			} else if(checkPosition(ENEMY,newRow,newCol)) {
				if(currentWeapons==0)
					dead = true;
				else {
					currentWeapons--;
					enemiesDefeated++;
				}
			}
			map[heroRow][heroCol] = EMPTY;
			map[newRow][newCol] = HERO;
			heroRow=newRow;
			heroCol=newCol;
			//System.out.println("Last movement ("+despRow+","+despCol+")");
		}
	}

	static boolean checkPosition(char value,int row, int col) {
		return map[row][col]==value? true:false;
	}

	static void printStatus() {
		System.out.println("[Enemy heroes left="+(totalEnemies-enemiesDefeated)+"][Current weapons="+currentWeapons+"]");
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
