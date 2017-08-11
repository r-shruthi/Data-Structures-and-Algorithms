import java.util.ArrayList;
import java.util.List;

/**Stack Implementation using List
 * Operations:
 * Push(String element)
 * Pop()
 * Peek()
 ** 
 */

/**
 * @author shrut
 *
 */
public class Stack {

	List<String> stackList;
	int top = -1;
	String topObj;
	public Stack()
	{
		stackList = new ArrayList<String>();
	}
	
	public void push(String pushElement)
	{
		stackList.add(pushElement);
		top++;		
	}
	
	public String pop()
	{
		String topElement;
		if(top >= 0){
			topElement = stackList.remove(top);
			top--;
		}
		else
		{
			topElement = null;
		}
		return topElement;			
	}
	
	public String peek()
	{
		String topElement;
		if(top >= 0){
			topElement = stackList.get(top);
		}
		else
		{
			topElement = null;
		}
		return topElement;		
	}
	
	public Boolean isEmpty()
	{
		if(stackList.isEmpty())
			return true;
		else
			return false;
	}
	
}
