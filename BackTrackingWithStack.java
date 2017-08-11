/*
 * This program finds the route between destination and origin cities
 * If no route is found, then it returns as No route found
 * Trace of cities travelled is shown as:
 *  + Cityname (The node is traversed)
 *  - Cityname (backtracked. Return to parent node)
 *  
 *  To execute:
 *  javac BackTrackingWithStack.java
 *  java BackTrackingWithStack <City names file> <file with origin-destination of flights> <file with Requested city pairs>
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BackTrackingWithStack {

	static List<String> cityPairs = new ArrayList<String>(); 
	static List<String> route = new ArrayList<String>();
	static Map<String, Boolean> citiesServed = new HashMap<String, Boolean>();
	
	public static void main(String[] args) 
	{
		String origin="";
		String destination="";	
		String fileNameCities="";	
		String fileNamePairs="";	
		String fileNameRequests="";	
		
		//read file names
		if(args.length > 0)
		{
			fileNameCities = args[0];
			fileNamePairs = args[1];
			fileNameRequests = args[2];
		}
		else
		{
			System.out.println("Please input the files names in this order:");
			System.out.println("Cities Served");
			System.out.println("Flight origin-departure pairs");
			System.out.println("Requested city pairs");
			System.exit(-1);
		}
		//get file
		Scanner inFile;		 
		Path filePath;	
		
		//read cities served names from file
		try {
			filePath = Paths.get(fileNameCities);	
			inFile = new Scanner(filePath);		
			while(inFile.hasNext())
			{
				citiesServed.put(inFile.next(),false);
			}
			inFile.close();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
			 
		//read names of pairs of origin and destination that flights operate from
		try {
			filePath = Paths.get(fileNamePairs);
			String readInput;
			String[] cityPair = new String[2];
			inFile = new Scanner(filePath);
			while(inFile.hasNext())
			{
				readInput = inFile.next();
				cityPair = readInput.split(",");
				cityPairs.add(cityPair[0]);
				cityPairs.add(cityPair[1]);
			}						
			inFile.close();			
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
		
		//read origin-destination request pairs
		List<String> requestPairs = new ArrayList<String>(); 
		try {
			filePath = Paths.get(fileNameRequests);
			String readInput;
			String[] cityPair = new String[2];
			
			inFile = new Scanner(filePath);
			while(inFile.hasNext())
			{
				readInput = inFile.next();
				cityPair = readInput.split(",");
				requestPairs.add(cityPair[0]);
				requestPairs.add(cityPair[1]);
			}						
			inFile.close();					
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
		
		//get route for all requested city pairs
		for(int i=0; i<requestPairs.size();i+=2)
		{
			origin = requestPairs.get(i);
			destination = requestPairs.get(i+1);
			System.out.print("\nOrigin: " + origin);
			System.out.println(" Destination: "+ destination);
			
			//reset visited mark for all cities				
			citiesServed.replaceAll((k,v)->false);				
		
			//check if the airline serves the requested cities
			if(!(citiesServed.containsKey(origin) && citiesServed.containsKey(destination)))
			{
				System.out.println("Sorry the Airline does not operate out of the cities requested by you");
				continue;
			}
			
			System.out.println("Trace:");
			route.clear();
			
			Boolean found = FindRoute(origin,destination);
			if(found)
			{
				System.out.println("Route found:");
				for(int j=route.size()-1;j>=0;j--)
					System.out.print(route.get(j) + " ");	
				System.out.println();
			}
			else
				System.out.println("No Route found");
		}
	}
	
	//Find route recursively between origin and destination
	public static Boolean FindRoute(String origin,String destination)
	{
		Stack routeStack = new Stack();
		//puch origin to route
		routeStack.push(origin);
		
		//mark origin as visited
		citiesServed.replace(origin, true);
		System.out.println(" + " + origin);
		
		do
		{
			String top = routeStack.peek();
			//if top of stack is destination, then route found
			if(top.equals(destination))
			{		
				while(!routeStack.isEmpty())
					route.add(routeStack.pop());
				
				return true;
			}
			else
			{
				int sourceIndex = -1;
				//mark current node as traversed
				citiesServed.replace(top, true);
				
				for(int i=0; i<cityPairs.size(); i+=2)
				{
					if(cityPairs.get(i).toLowerCase().equals(top.toLowerCase()))
					{
						//check if any unvisited child nodes
						if(citiesServed.get(cityPairs.get(i+1))==false)
						{
							//add node to route
							routeStack.push(cityPairs.get(i+1));
							sourceIndex = i;
							System.out.println(" + " + cityPairs.get(i+1));
							break;
						}
					}
				}
				//backtracking
				if(sourceIndex == -1)
				{
					System.out.println(" - " + top);
					routeStack.pop();					
				}
			}
		}while(!routeStack.isEmpty() && routeStack.peek()!=destination);
		
		if(routeStack.isEmpty())
		{
			return false;
		}
		return false;
	}
	
	

}
