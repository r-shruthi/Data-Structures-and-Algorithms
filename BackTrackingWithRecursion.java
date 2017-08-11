/*
 * This program finds the route between destination and origin cities
 * If no route is found, then it returns as No route found
 * Trace of cities travelled is shown as:
 *  + Cityname (The node is traversed)
 *  - Cityname (backtracked. Return to parent node)
 *  
 *  To execute:
 *  javac BackTrackingWithRecursion.java
 *  java BackTrackingWithRecursion <City names file> <file with origin-destination of flights> <file with Requested city pairs>
 */
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BackTrackingWithRecursion {

	static List<String> cityPairs = new ArrayList<String>(); 
	static List<String> route = new ArrayList<String>();
	static Boolean found = false;
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
				//store contiguously
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
		try 
		{			
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
			
			//mark the origin as visited
			citiesServed.replace(origin, true);
			System.out.println("Trace:\n + " + origin);
			route.clear();
			route.add(origin);
			found = false;
						
			FindRoute(origin,destination);
			
			if(found)
			{
				System.out.println("Route found:");
				for(int j=0;j<route.size();j++)
					System.out.print(route.get(j) + " ");
				System.out.println();
			}
			else
				System.out.println("No Route found");
		}
		
	}
	
	//Find route recursively between origin and destination
	public static void FindRoute(String origin,String destination)
	{			
		int sourceIndex = -1;				
		for(int i=0; i<cityPairs.size(); i+=2)
		{			
			if(cityPairs.get(i).toLowerCase().equals(origin.toLowerCase()))
			{					
				//check for direct flights from source to destination				
				if(cityPairs.get(i+1).toLowerCase().equals(destination.toLowerCase()))
				{
					found =  true;
					route.add(cityPairs.get(i+1));
					System.out.println(" + " + destination);
					return;
				}
				//check if any unvisited child nodes
				else if(citiesServed.get(cityPairs.get(i+1))==false)
				{
					sourceIndex = i;					
				}
			}
		}	
		
		//backtracking when there are no flights from this location
		if(sourceIndex==-1)
		{
			int routeSize = route.size()-1;
			if(routeSize>=0)
			{
				origin = route.get(routeSize);
				route.remove(routeSize);
				System.out.println(" - " + origin);
			}
			if(routeSize>0)
			{
				//call the FindRoute function for parent node and final destination
				FindRoute(route.get(routeSize-1),destination);
			}
			else
				return;
			
		}
		else
		{
			//call the FindRoute function for current node and final destination
			citiesServed.replace(cityPairs.get(1 + sourceIndex), true);
			System.out.println(" + " + cityPairs.get(1 + sourceIndex));
			route.add(cityPairs.get(1 + sourceIndex));
			FindRoute(cityPairs.get(1 + sourceIndex),destination);
		}
			
		return;
	}

}



