/*
 * This program implements Percolation using Monte Carlo method 
 * using weighted quick union with path compression to estimate 
 * percolation threshold
 * 
 * To execute 
 javac Percolation.java
 java Percolate <size of grid row/column>
 */
import java.util.concurrent.ThreadLocalRandom;

public class Percolation {

	int[] id;
	int[][] grid;
	int topVirtualSite = 0;
	int btmVirtualSite;
	int count = 0;
	int n; //grid size
	private int[] size; // size of component for roots 
	public void CreateMatrix()
	{
		int i=0;
		int j=0;
		btmVirtualSite = n*n +1;
		id = new int[n*n +2];//N*N grid plus 2 virtual sites
		grid = new int[n][n];		
		size = new int[n*n +2];
		
		id[topVirtualSite] = topVirtualSite;
		id[btmVirtualSite] = btmVirtualSite;
		size[btmVirtualSite] = 1;
		
		//initialize all arrays
		for(i=0;i<n;i++)
			for(j=0;j<n;j++)
			{
				grid[i][j] = 0; //blocked
				id[i*n +j +1] = i*n + j +1; //root of site
				size[i*n +j] = 1;//size of component
			}		
	}
	
	//This method Opens a site for the arguments passed
	//and does union with open sites above, below, left and right
	 public void OpenRandomSite(int i, int j)
	 {
		 //check if open
		 if(grid[i][j] == 0)
		 {		 
			 //else open
			 grid[i][j] = 1;
			 count++;
			 
			 //if top row, union with top virtual site
			 if(i==0)
			 {
				 if(!connected(n*i+j+1,topVirtualSite))
						 union(n*i+j+1,topVirtualSite);
			 }
			 
			 //if bottom row, union with bottom virtual site
			 if(i==3)
			 {
				 if(!connected(btmVirtualSite,n*i+j+1))
						 union(btmVirtualSite,n*i+j+1);
			 }
			 
			 if(j<n-1 && grid[i][j+1]==1)
			 {
				 if(!connected(n*i+j+1, n*i+j+2))
					 union(n*i+j+1, n*i+j+2);//union with site on right
			 }
			 
			 if(j>0 && grid[i][j-1]==1)
			 {
				 if(!connected(n*i+j+1, n*i+j))
					 union(n*i+j+1, n*i+j);//union with site on left
			 }
			 
			 if(i>0 && grid[i-1][j]==1)
			 {
				 if(!connected(n*i+j+1, n*i+j-3))
					 union(n*i+j+1, n*i+j-3);//union with site on top
			 }
			 
			 if(i<n-1 && grid[i+1][j]==1)
			 {
				 if(!connected(n*i+j+1, n*i+j+5))
					 union(n*i+j+1, n*i+j+5);//union with site on bottom
			 }
		 }
	 }
	 
	 //Check if percolates by checking if top and
	 //bottom virtual sites are connected
	 public boolean Percolates()
	 {		 
		 return connected(topVirtualSite, btmVirtualSite);
	 }
	 
	 public static void main(String args[])
	 {		
		 Percolation perc = new Percolation();
		 try {
			 perc.n = Integer.parseInt(args[0]); //size of grid
		    } 
			catch (NumberFormatException e) {
		        System.err.println("Argument" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		
		 //create a matrix for N*N sites
		 perc.CreateMatrix();
		 
		 //if no percolation, then generate two random numbers between 0 and N-1
		 //for row and column
		 while(!perc.Percolates())
		 {
			 int i = ThreadLocalRandom.current().nextInt(0, perc.n); //row
			 int j = ThreadLocalRandom.current().nextInt(0, perc.n);//column
			 perc.OpenRandomSite(i,j);
		 }
		 
		 //calculate percolation threshold
		 double pstar = (double)perc.count/(perc.n*perc.n);
		 System.out.println("percolation threshold " + pstar);		 
	 }
	 
	 //weighted quick union with path compression Connected method
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
	 
}
