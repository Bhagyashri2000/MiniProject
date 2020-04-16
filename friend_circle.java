import java.util.*;
import java.text.*;
import java.io.Console;

class node
{
	String uname;
	Date dob;
	String status;
	int friend_count;
	//char[] password=new char[20];
	String password;
	
	node()
	{
		uname="";
		status="";
		friend_count=0;
	}
	
	node(String name,Date date2,String st,String p)
	{
		uname=name;
		dob=date2;
		status=st;
		password=p;
	}
}

public class friend_circle
{
	Scanner sc=new Scanner(System.in);
	void accept()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		
		System.out.println("Hey! Let's Create your account!!");
		System.out.println("Enter your User Name");
		String name=sc.next();
		
		
		System.out.println("enter your birth year");			//for storing the date of birth
	    int y=sc.nextInt();
		System.out.println("enter your birth month");
		int m=sc.nextInt();
		System.out.println("enter your birth date");
		int d=sc.nextInt();
		String date1=Integer.toString(d)+"-"+Integer.toString(m)+"-"+Integer.toString(y);
		
		Date date2=null;
		try
		{
			date2=sdf.parse(date1);							//converting the format of string in Date
			
		}
		catch (ParseException e) 							
		{
		    // TODO Auto-generated catch block
		  System.out.println("invalid date of birth");
		}
		
		System.out.println("Do you want to keep any staus?(y/n)");
		char ch=sc.next().charAt(0);
		String st="";
		if(ch=='y'||ch=='Y')
		{
			System.out.println("Here are some common status for your help!");
			System.out.println("");				//list to be appended
			
			System.out.println("Enter your status");
			st=sc.next();
		}
		System.out.println("Enter your password");
		
		//hidden password
		//Console con = null;					
		//con = System.console();   
		//char[] p=new char[20];		
		//char[] p=con.readPassword(); 
		String p=sc.next();
		node temp=new node(name,date2,st,p);
       
		
	}
	public static void main(String[] args)
	{
		
		
		friend_circle obj=new friend_circle();
		obj.accept();
		
	}
}


