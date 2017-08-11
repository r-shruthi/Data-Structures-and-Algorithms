import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/* This program implements the Weighted Quick Union With Path Compression algorithm 
 * for dynamic connectivity client
 * No of sites is fixed as 10000 in the program
 * 
 * To execute:
   javac WeightedQUWithPathCompression.java
   java WeightedQUWithPathCompression <filename> 
 */
public class WeightedQUWithPathCompression {

	private int[] id; 
	private int[] size; // size of component for roots 
	
	public WeightedQUWithPathCompression(int N)
	{
		id = new int[N];
		for (int i = 0; i < N; i++) 
			id[i] = i;
		//initialize the size of trees to 1
		size = new int[N];
		for (int i = 0; i < N; i++) 
			size[i] = 1;
	}
	
	//This method checks if two sites are connected
	//by comparing the id array
	public boolean connected(int p, int q)
	{ 
		return find(p) == find(q); 
	}
	
	//This method returns the root of the site  
	//by looping through the ids and sets the 
	//id of all sites along the path to the root
	private int find(int p)
	{ 
		int q = p;
		// Follow links to find a root.
		while (p != id[p]) 
			p = id[p];		
		
		//set id of parent elements to the root
		while(q != p)
		{
			int temp = id[q];
			id[q] = p;
			q = temp;
		}				
        return p;
	}
	
	//This method implements union of two components
	//setting id of p as q if size of p is smaller than q 
	// and vice versa
	public void union(int p, int q)
	{
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		// Make smaller root point to larger one.
		if (size[i] < size[j]) 
		{ 
			id[i] = j; 
			size[j] += size[i]; 
		}
		else 
		{ 
			id[j] = i; 
			size[i] += size[j]; 
		}
	}
	
	public static void main(String[] args)
	{ 
		int sites = 10000;
		
		// number of components
		int count = sites;		
		String fileName="";
		
		//read the filename
		if (args.length == 1)
		{
			fileName = args[0];		
		}
		else
		{
		  System.out.println("Please enter input as <filename>");
		  System.exit(1);
		}
		WeightedQUWithPathCompression uf = new WeightedQUWithPathCompression(sites);
		Path filePath = Paths.get(fileName);
		Scanner inFile;
		try {
			inFile = new Scanner(filePath);
			//read pairs of sites from the input array and check if they are connected
			long start = System.currentTimeMillis();
			while (inFile.hasNext())
			{
				int p = inFile.nextInt();
				int q = inFile.nextInt(); // Read pair to connect.
				if (uf.connected(p, q)) continue; // Ignore if connected.
				uf.union(p, q); // Combine components
				count--;
			}		
			long now = System.currentTimeMillis();
			//calculate elapsed time
			double timeElapsed = (now - start) / 1000.0000;
			System.out.println("\nTime elapsed:"+timeElapsed);
		
			System.out.println(count + " components");
			inFile.close();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
}
