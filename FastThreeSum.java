/*This program implements the fast three sum algorithm
 * that uses Insertion Sort to first sort the input array
 * and then uses Binary Search to search for -(a[i] + a[j])
 * 
 * To execute
 * javac FastThreeSum.java
 * java FastThreeSum <input_filename> <array_size>
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FastThreeSum {

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
		        System.err.println("Argument" + args[1] + " must be an integer");
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
		
		int[] inputArray =  new int[arraySize];
			
			//start clock for measuring running time
			long start = System.currentTimeMillis();
			//Sort the input numbers in ascending using Insertion sort algorithm
			inputArray = InsertionSort(inputArray);
			int n = inputArray.length;
			int count=0;
			for(int i=0; i<n;i++)
			{
				for(int j=i+1;j<n;j++)
				{
					//A pair inputArray[i] and inputArray[j] is part of a triple that sums to 0 if
					// and only if the value -(inputArray[i] + inputArray[j]) exists in the array
					if (BinarySearch(-(inputArray[i]+inputArray[j]), inputArray) > j)
						count++;
				}
			}
			//stop the clock
			long now = System.currentTimeMillis();
			double timeElapsed = (now - start) / 1000.0;
			System.out.println("\nThree sum count:"+count);
			System.out.println("\nInput array size:"+n);
			System.out.println("\nTime elapsed:"+timeElapsed);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*This method is used to perform Insertion sort 
	 * on the input array of numbers 
	 */
	static int[] InsertionSort(int[] inputArray)
	{
		 int i,j,temp;
		 for(i=1;i<inputArray.length;i++)
		 {
			 //sort all numbers from i to 1
			 for(j=i;j>0;j--)
			 {
				 //if inputArray[j-1]>inputArray[j] then swap the numbers
				 if(inputArray[j-1]>inputArray[j])
				 {
					 temp = inputArray[j-1];
					 inputArray[j-1] = inputArray[j];
					 inputArray[j] = temp;
				 }
			 }
		 }
		 return inputArray;
	}
	 
	/*Perform Binary search on the input array for the 
	 * key which is the negative of sum of two numbers
	 * A pair inputArray[i] and inputArray[j] is part of a triple that sums to 0 if
	 * and only if the value -(inputArray[i] + inputArray[j]) exists in the array					
	 */	
	static int BinarySearch(int key, int[] array)
	{	
		 int lo=0,mid;
		 int hi = array.length-1;
		 while(lo <= hi)
		 {
			 //calculate middle index of array 
			 mid = lo + (hi-lo)/2;
			 //if key less than mid index, then search in first half of array
			 if(key < array[mid])
				 hi = mid -1;
			//if key greater than mid index, then search in second half of array
			 else if(key > array[mid])
				 lo = lo +1;
			 else return mid;
		 }
		 return -1;
	}
}
