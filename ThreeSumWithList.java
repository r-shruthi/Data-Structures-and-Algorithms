/* This program is an implementation of 3-sum algorithm
 * that finds triplets in the given array that sum to zero.
 * This program uses a List<Integers> to store the input list of numbers.
 * So, the input size need not be known while initializing the list
 * 
 * input arguments: file_name
 * To execute:
 * javac ThreeSumWithList.java
 * java ThreeSumWithList <input_filename>
 */
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
public class ThreeSumWithList {

	public static void main(String[] args) {
		String fileName;
		if (args.length == 1)
		{
			fileName = args[0];
		}
		else
		{
			System.out.println("Enter file name");		
			Scanner inputFile = new Scanner(System.in);		
			fileName = inputFile.nextLine();
			inputFile.close();	
		}		
		
		//get file
		Path filePath = Paths.get(fileName);
		Scanner inFile;
		try {
			inFile = new Scanner(filePath);
			//read the contents of input file into List
			List<Integer> inputArray = new ArrayList<>();
			while(inFile.hasNext())
			{
				 if (inFile.hasNextInt()) {
					 inputArray.add(inFile.nextInt());
				 }
				 else{
					 inFile.next();
				 }
			}
			inFile.close();
			
			//start the clock
			long start = System.currentTimeMillis();
			int n = inputArray.size();
			int count=0;
			for(int i=0; i<n;i++)
			{
				for(int j=i+1;j<n;j++)
				{
					for(int k=j+1;k<n;k++)
					{
						//check if sum of three numbers equal to zero
						if(inputArray.get(i) + inputArray.get(j) + inputArray.get(k) == 0)
						count++;
					}
				}
			}
			//stop clock
			long now = System.currentTimeMillis();
			
			//calculated running time for array accesses
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
