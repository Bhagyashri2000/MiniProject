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
	node next;
	
	node()
	{
		uname="";
		status="";
		friend_count=0;
		next=null;
	}
	
	node(String name,Date date2,String st,String p)
	{
		uname=name;
		dob=date2;
		status=st;
		password=p;
		next=null;
	}
}

class connection
{
	int max=30;
	node[] head=new node[max];
	int head_count=0;
	Scanner sc=new Scanner(System.in);
	void signup()
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
		if(head_count<30)
		{
			head[head_count]=new node();
			head[head_count]=temp;
			head_count++;
		}
		else
		{
			System.out.println("SORRYYY!! The limit of number of accounts is reached!!");
		}
       
		
	}
	
	int login()
	{
		System.out.println("Enter your User Name");
		String name=sc.next();
		
		System.out.println("Enter your ID password");
		String pass=sc.next();
		
		int fl=0;
		int login_id=0;
		for(int i=0;i<head_count;i++)
		{
			//System.out.println(head[i].uname+" "+head[i].password);
			if((head[i].uname.equals(name))&&(head[i].password.equals(pass)))
			{
				System.out.println(head[i].uname+" "+head[i].password);
				
				fl=1;
				login_id=i;
				break;
			}
		}
		if(fl==0)
		{
			System.out.println("There is no such account exist with this name");
			return -1;
		}
		else
		{
			System.out.println("SUCCESSFULLY LOGIN");
			return login_id;
		}
			
	}
	
	void display_list(int login_id)
	{
		for(int i=0;i<head_count;i++)
		{
			if(i!=login_id)
			{
				System.out.println(head[i].uname);
			}
		}
	}
	
	void make_friend(int login_id)
	{
		display_list(login_id);
		System.out.println("Enter the friend name with whom you want to connect");
		String name=sc.next();
		int flag=0;
		int friend_id=0;
		
		for(int i=0;i<head_count;i++)
		{
			if(head[i].uname.equals(name))
			{
				flag=1;
				friend_id=i;
				break;
			}
		}
		if(flag==0)
		{
			System.out.println("Friend Not Found");
		}
		else
		{
			insert(login_id,friend_id);     //add edge u,v
			insert(friend_id,login_id);     //add edge v,u
		}
		
	}
	
	
	void insert(int x,int y)               //insert into adjacency list
	{
		node ptr=head[x];
		while(ptr.next!=null)
		{
			ptr=ptr.next;
		}
		ptr.next=new node(head[y].uname,head[y].dob,head[y].status,head[y].password);       //add y to end of list x
		
	}
}

public class friend_circle
{
	
	public static void main(String[] args)
	{
		
		
		connection obj=new connection();
		obj.signup();
		obj.signup();
		int login_id=obj.login();
		obj.make_friend(login_id);
		
	}
}


