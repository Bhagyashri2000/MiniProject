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
		System.out.println("\nEnter your User Name");
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
		System.out.println("Enter your password");
		String p=sc.next();
		
		//System.out.println("Do you want to keep any status?(y/n)");
	//	char ch=sc.next().charAt(0);
		String st=null;
		//if(ch=='y'||ch=='Y')
		{
					st="Hey There! I am using Connect-Pal.";  //default status
					
			/*System.out.println("Enter your status");
			st=sc.next();*/
		}
		
		
		//hidden password
		//Console con = null;					
		//con = System.console();   
		//char[] p=new char[20];		
		//char[] p=con.readPassword(); 
		
		node temp=new node(name,date2,st,p);
		if(head_count<30)
		{
			System.out.println("\nAccount created successfully");
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
		System.out.println("\nEnter your User Name");
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
				//System.out.println(head[i].uname+" "+head[i].password);
				
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
			System.out.println("\n!!LOGIN SUCCESSFULL!!");
			after_login(login_id);
			return login_id;
		}
		
		
	}
	
	void displayProfile(int login_id)
	{
		
		System.out.println("___________________________________");
		System.out.println("\n\t\t "+head[login_id].uname+" ");
		System.out.println("\nStatus: "+head[login_id].status);
		System.out.println("\nDate Of Birth: "+head[login_id].dob);
		System.out.println("\n____________________________________");
	}
	
	int searchPerson(String ser)
	{
		int i;
		for(i=0;i<head_count;i++)
		{
			if(head[i].uname == ser)
			{
				return i;                         //returning the index of the required person
			}
		}
	
		return -1;                                //if not found we are return -1;
	}
	
	boolean check_friend(int id,String fr_name)
	{
		node ptr;
		ptr = head[id].next;
		
		while(ptr!=null)
		{
			if(ptr.uname==fr_name)
			{
				return true;
			}
			ptr = ptr.next;
		}
		
		
		return false;
	}
	
	void unfriend(int id,String fr_name)
	{
		node ptr,prev;
		prev = head[id];
		ptr = head[id].next;
		
		while(ptr!=null)
		{
			if(ptr.uname==fr_name)
			{
				if(ptr.next==null)
				{
					prev.next=null;
				}
				else
				{
					prev.next = ptr.next;
				}
			}
			prev = ptr;
			ptr = ptr.next;
		}
	}
	
	int mutualFriends(int x,int y )
	{
		node ptr1,ptr2;
		int compare,flag=0;
		ptr1 = head[x].next;   //starting from a node just after the head as we don't need to compare the head
		ptr2 = head[y].next;
		
		while(ptr1!=null && ptr2!=null)
		{
			compare = ptr1.uname.compareTo(ptr2.uname);
			if(compare ==0)
			{
				System.out.println(ptr1.uname);           //if both reference are same then it is a mutual friend print it
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
				flag = 1;
			}
			else if(compare<0)
			{
				ptr1 = ptr1.next;
			}
			else
			{
				ptr2 = ptr2.next;
			}
		}
		return flag;
	}
	
	void after_login(int login_id)
	{
		int c;
		System.out.println("\n\t\tYOUR PROFILE");
		displayProfile(login_id);
		
		//show the friends list who the user is following
		
		do
		{
			System.out.println("\n\n1.Add Friends");
			System.out.println("2.Search a person");
			System.out.println("3.Update Status");
			System.out.println("4.Find friends who have birthday in this month");
			System.out.println("5.Log-Out");
			System.out.print("Enter your choice ");
			c=sc.nextInt();
			
			switch(c)
			{
				  case 1:make_friend(login_id);
					     break;
					     
				  case 2:
				  {
					  String name;
					  int pal_id,flag;     //person ure searching's id
					  boolean check_fr;
					  System.out.print("Enter the username you want to search: ");
					  name = sc.next();
					  pal_id = searchPerson(name);
					  if(pal_id==-1)
					  {
						  System.out.print("\nSorry! Thst username doesn't exist.");
					  }
					  else
					  {
						  displayProfile(pal_id);
						  int ch=3;
						  do
						  {
							  System.out.println("\n\nMenu: ");
							  check_fr = check_friend(login_id,name);   //checking if they are friends or not
							  if(check_fr == false)           //indicating that the person you searched from isn't your friend yet and hence no reference to it
							  {
								  System.out.println("1.Friend them");
							  }
							  else
							  {
								  System.out.println("1.Unfriend them");
							  }
							  System.out.println("2.Display Common Friends");
							  System.out.println("3.Back to your profile");
							  System.out.print("Enter your choice: ");
							  ch = sc.nextInt();
							  
							  switch(ch)
							  {
								  case 1:
								  {
									  if(check_fr == false)           //indicating that the person you searched from isn't your friend yet and hence no reference to it
									  {
										  insert_sorted(login_id,pal_id);
										  insert_sorted(pal_id,login_id);
									  }
									  else
									  {
										  unfriend(login_id, name);
										  unfriend(pal_id,head[login_id].uname);
									  }
									  break;
								  }
								  
								  case 2:
								  {
									  flag = mutualFriends(login_id,pal_id);
									  if(flag == 0)
									  {
										  System.out.print("\nYou have no common pals!"); 
									  }
									  break;
								  }
								  case 3:
								  {
									  break;
								  }
								  default:
								  {
									  System.out.print("\nEnter a valid choice, between 1-3");
								  }
							  }
							  
						  }while(ch!=3);
						  
					  }
					  break;
				  
				  }  
				  case 3:update_status(login_id);
				         System.out.println("Status Updated to :"+head[login_id].status);
					     break;
					     
				  case 4:
					    break;
					    
				  case 5:
					  break;
				
			}
		}while(c!=5);
		
		
	}
	
	void update_status(int login_id)
	{
		System.out.println("\nHere are some common status for your help!");
		System.out.println("1.Available");
		System.out.println("2.Busy");	
		System.out.println("3.At work");	
		System.out.println("4.Sleeping");	
		System.out.println("5.Type your own status");
		int ch=sc.nextInt();
		
		if(ch==1)
		{
			head[login_id].status="Available";
		}
		else if(ch==2)
		{
			head[login_id].status="Busy";
		}
		else if(ch==3)
		{
			head[login_id].status="At work";
		}
		else if(ch==4)
		{
			head[login_id].status="Sleeping";
		}
		else if(ch==5)
		{
		System.out.println("Enter your updated status");
		head[login_id].status=sc.next();

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
		System.out.println("");
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
			insert_sorted(login_id,friend_id);     //add edge u,v
			insert_sorted(friend_id,login_id);     //add edge v,u
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
	
	void insert_sorted(int x, int y)
	{
		node temp = new node(head[y].uname,head[y].dob,head[y].status,head[y].password);  //the new node to be added
		node ptr,prev;
		ptr = head[x].next;
		prev = head[x];
		
		while(ptr.next!=null)
		{
			int comp = head[y].uname.compareTo(ptr.uname);
			if(comp<0)                                  //then the to be inserted name is smaller than pointed node 
			{
				temp.next = ptr;
				prev.next = temp;
				break;    //break form the loop as the node has been added		
			}
			
			prev = ptr;
			ptr = ptr.next;
		}
		
		if(temp.next == null)   //if the node has still not been assigned then check around the last node
		{
			int comp = head[y].uname.compareTo(ptr.uname);
			if(comp<0)
			{
				temp.next = ptr;
				prev.next = temp;
			}
			else
			{
				ptr.next = temp;
			}
		}
	}
}

public class friend_circle
{
	
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		
		connection obj=new connection();
		
		int choice;
		
		do
		{
			System.out.println("\n________________________");
			System.out.println("\n\t1.Sign-Up\t\t");
			System.out.println("\t2.Login   \t\t");
			System.out.println("_________________________");
			System.out.println("3.Close");
			System.out.println("\nEnter your choice ");
			System.out.println();
			choice=sc.nextInt();
			
			switch(choice)
			{
			  case 1:obj.signup();
				     break;
				     
			  case 2:int login_id=obj.login();
				     break;
				     
			  case 3:
				    break;
			
			}
		}while(choice!=3);
		
		
	}
}