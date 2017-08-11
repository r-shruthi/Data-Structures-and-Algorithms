/* This program finds the end of a legal prefix expression
 * 
 * OUTPUT
 * If the expression is legal, then program returns index of last char
 * else it prints "Illegal prefix expression"
 * 
 * TO EXECUTE
	javac EndOfLegalPrefix.java
	java EndOfLegalPrefix <prefix expression>
 *
 */
public class EndOfLegalPrefix {
	static int start = 0;
	static String prefix = "";
	
	public static void main(String[] args) {
		//read the input into a static variable
		if(args.length>0)
		{
			prefix = args[0];
		}
		else
		{
			System.out.println("Please enter prefix expression");
			System.exit(-1);
		}
		//call the function to calculate end of prefix
		int endOfPrefix = endPre();
		if(endOfPrefix > -1)
			System.out.println("EndOfLegalPrefix = " + endOfPrefix);
		else
			System.out.println("Illegal prefix expression");		
	}
	
	/*This function returns the end of legal prefix expression 
	 * If illegal expression, then it returns -1
	 */
	private static int endPre()
	{
		if(start >= prefix.length())
			return -1;
		
		//Get the character at position 'start'
		String pre = prefix.substring(start, start + 1);
		
		//check if character is an identifier
		//if character, then  return it
		if(pre.matches("[a-zA-Z]"))
		{
			return start;
		}
		
		//if not character or operator, then it is not part of prefix legal dictionary
		if(!(pre.contains("+") || pre.contains("-") || pre.contains("*") || pre.contains("/")))
		{
			return -1;
		}
		
		//check the next character recursively until an identifier is found
		start++;
		int firstEnd = endPre();
		
		//find end of each prefix sub-expression
		if(firstEnd >- 1)
		{
			start++;
			return endPre();
		}
			
		return -1;	
		
	}

}
