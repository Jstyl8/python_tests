import java.util.*;

public class prob_simplified {

		

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		StringTokenizer st;

 		final char EMPTY = '_';
		final char MATERIAL = 'm';
		int totalMaterials = 0;
		String line="";
		int maxRows,maxCols=0;
		char[][] map;
		
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
			if(map[row][col]==MATERIAL)
				totalMaterials++;

			line = sc.nextLine();
		}

		for (int i=0; i < map.length ; i++ ) {
			for (int j=0; j < map[0].length ; j++ ) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("Materials to collect:"+totalMaterials);
	}
}
