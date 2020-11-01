
import java.util.*;
import java.io.*;
class SolutionPostFix {
  
   //checking the preference of operators
private int getPreference(char c)
	{
		if(c=='+'|| c=='-') return 1;
		else if(c=='*' || c=='/') return 2;
		else return -1;
	}
   //calculating the postFix expression
private int calculatePostFix(List<String> postFixList)
	{
		Stack<Integer> stack = new Stack<>();
       //find the preference and calculate
		for(int i=0;i<postFixList.size();i++){
		String word = postFixList.get(i);
		if(word.length()==1 && (word.charAt(0)=='+'||word.charAt(0)=='-'||word.charAt(0)=='*'||word.charAt(0)=='/'))
		{
			int number2 = stack.pop();
			int number1 = stack.pop();
			if(word.charAt(0)=='+')
			{
				int number = number1+number2;
				stack.push(number);
			}
			else if(word.charAt(0)=='-')
			{
				int number = number1-number2;
				stack.push(number);
			}
			else if(word.charAt(0)=='*')
			{
				int number = number1*number2;
				stack.push(number);
			}
			else
			{
				int number = number1/number2;
				stack.push(number);
			}
		}
		else
			{
				int number = Integer.parseInt(word);
				stack.push(number);
			}
		}
		return stack.peek();
	}
  
   	//get the postFix expression only
	private List<String> getPostFixString(String s)
	{
		Stack<Character> stack = new Stack<>();
		List<String> postFixList = new ArrayList<>();
		boolean flag = false;
		for(int i=0;i<s.length();i++)
		{
			char word = s.charAt(i);
			if(word==' ')
			{
				continue;
			}
			if(word=='(')
			{
				stack.push(word);
				flag = false;
			}
			else if(word==')')
			{
				flag = false;
				while(!stack.isEmpty())
				{
					if(stack.peek()=='(')
					{
						stack.pop();
						break;
					}
					else
					{
						postFixList.add(stack.pop()+"");
					}
				}
			}
			else if(word=='+' || word=='-' || word=='*' || word=='/')
			{
				flag = false;
				if(stack.isEmpty())
				{
					stack.push(word);
				}
				else
				{
					while(!stack.isEmpty() && getPreference(stack.peek())>=getPreference(word))
					{
						postFixList.add(stack.pop()+"");
					}
					stack.push(word);
				}
			}
			else
			{
				if(flag)
				{
					String lastNumber = postFixList.get(postFixList.size()-1);
					lastNumber+=word;
					postFixList.set(postFixList.size()-1, lastNumber);
				}
				else
				{
					postFixList.add(word+"");
					flag = true;
				}
			}
		}
		while(!stack.isEmpty())
		{
			postFixList.add(stack.pop()+"");
		}
		return postFixList;
	}
  
	//calculate the postFix expression and evaluate
public int calculate(String s) 
	{
		List<String> postFixString = getPostFixString(s);
		return calculatePostFix(postFixString);
	}
   public static void main(String arg[])
   {
       SolutionPostFix f=new SolutionPostFix();
      
       //taking the input from user
      
       Scanner sc=new Scanner(System.in);
       System.out.println("Enter the Expression : ");
       String expression=sc.next();
      
       try
       {
           // Open given file in append mode.
    	   BufferedWriter out = new BufferedWriter(new FileWriter("expression.txt", true));
    	   int i=f.calculate(expression);
    	   String str=Integer.toString(i);
           out.write(str);
           out.write("\n");
           out.close();

           System.out.println("Successfully wrote to the file.");
       } 
       catch (IOException e) 
       {
    	   System.out.println("An error occurred.");
    	   e.printStackTrace();
       }
      
       System.out.println("The Postfix Expression is : \n"+f.getPostFixString(expression));
       System.out.println("The Evaluation of PostFix Expression : "+f.calculate(expression));

   }
}
