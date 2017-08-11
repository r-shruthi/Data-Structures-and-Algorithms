/* This program is an implementation of 3-sum algorithm
 * that finds triplets in the given array that sum to zero.
 * This program uses a Array to store the input list of numbers.
 * So, the input size has to be input while initializing the array
 * 
 * input arguments: file name, input size
 * To execute:
 * javac ThreeSumWithArrays.java
 * java ThreeSumWithArrays <input_filename> <array_size>
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ThreeSumWithArrays {
	
	public static void main(String[] args) {
		String fileName;
		int arraySize = 0;
		//read the command line arguments
		if (args.length == 2)
		{
			fileName = args[0];
			try {
				arraySize = Integer.parseInt(args[1]);
		    } 
			catch (NumberFormatException e) {
		        System.err.println("Argument" + args[1] + " must be an integer.");
		        System.exit(1);
		    }
		}
		else
		{
			System.out.println("Enter file name");		
			Scanner input = new Scanner(System.in);		
			fileName = input.nextLine();
			System.out.println("Enter Array Size");
			arraySize = input.hasNextInt()?input.nextInt():0;
			input.close();	
		}
		
		//check if input array size is greater than 0
		if(arraySize<=0)
		{
			System.out.println("Array Size needs to be a positive integer");
			System.exit(1);
		}
		
		//get file		
		int[] inputArray =  new int[arraySize];
		Path filePath = Paths.get(fileName);
		Scanner inFile;
		//read numbers from file into array
		try 
		{
			inFile = new Scanner(filePath);
			for(int i=0;i<arraySize;i++){
				if(inFile.hasNext())
				{
					if (inFile.hasNextInt()) 
						inputArray[i] = inFile.nextInt();				
					else
						inFile.next();
				}
			}
		
			inFile.close();
			int n = inputArray.length;
			int count=0;
			//start clock for measuring running time
			long start = System.currentTimeMillis();		
			for(int i=0; i<n;i++)
			{
				for(int j=i+1;j<n;j++)
				{
					for(int k=j+1;k<n;k++)
					{
						//check if sum of the three numbers is zero
						if(inputArray[i] + inputArray[j] + inputArray[k] == 0)
						count++;
					}
				}
			}
			//end clock
			long now = System.currentTimeMillis();
			//calculate running time
			double timeElapsed = (now - start) / 1000.0;
			System.out.println("\nThree sum count:"+count);
			System.out.println("\nInput array size:"+n);
			System.out.println("\nTime elapsed:"+timeElapsed);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
			
}
