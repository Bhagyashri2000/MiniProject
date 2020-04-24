import java.util.*;
import java.text.*;
import java.time.*;

class node
{
	String uname;
	Date dob;
	String status;
	int friend_count;
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
		
		System.out.println("\nHey! Let's Create your account!!");
		int flag=0;			//validate name
		String name="";
		do
		{
			flag=0;
			System.out.println("\nEnter your User Name ");
			name=sc.nextLine();
			if(check_name(name)==1)
			{
				System.out.println("The entered user name already exist!!Try to have some different user name");
				flag=1;
			}
		}while(flag==1);
		
		System.out.println("enter your birth year");			//for storing the date of birth
	    int y=sc.nextInt();
		System.out.println("enter your birth month");
		int m=sc.nextInt();
		System.out.println("enter your birth date");
		int d=sc.nextInt();
		sc.nextLine();
		String date1=Integer.toString(d)+"-"+Integer.toString(m)+"-"+Integer.toString(y);
		Date date2=new Date();
		
		try
		{
			sdf.applyPattern("dd-MM-yyyy");
			date2=sdf.parse(date1);		
		}
		catch (ParseException e) 							
		{
		  System.out.println("invalid date of birth");
		}
		System.out.println("Enter your password");
		String p=sc.nextLine();
		
		String st=null;
		{
			st="Hey There! I am using Connect-Pal.";  //default status
					
		}
		
		node temp=new node(name,date2,st,p);
		if(head_count<30)
		{
			System.out.println("\n!!Account created successfully!!");
			head[head_count]=new node();
			head[head_count]=temp;
			head_count++;
		}
		else
		{
			System.out.println("SORRYYY!! The limit of number of accounts in Connect_pal is full!!");
		}
    
	}
	
	int check_name(String name)				//to check if duplicate name
	{
		for(int i=0;i<head_count;i++)
		{
			if(name.compareTo(head[i].uname)==0)
			{
				return 1;
			}
		}
		return 0;
	}
	
	int login()
	{
		System.out.println("\nEnter your User Name ");
		String name=sc.nextLine();
		
		
		System.out.println("Enter your ID password ");
		String pass=sc.nextLine();
		
		int fl=0;
		int login_id=0;
		for(int i=0;i<head_count;i++)
		{
			if((head[i].uname.equals(name))&&(head[i].password.equals(pass)))
			{
				
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
			
			SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM");
			
			Date currentdate=java.util.Calendar.getInstance().getTime();  //take current date
			if(sdf1.format(currentdate).compareTo(sdf1.format(head[login_id].dob))==0)
			{
				
				System.out.println("\n\n!!Connect_pal wishes you very happy and healthy birthday!!");
			}
			after_login(login_id);
			return login_id;
		}
		
		
	}
	
	
	void after_login(int login_id)
	{
		int c;
		System.out.println("\n\t\tYOUR PROFILE");
		displayProfile(login_id);
		news_feed(login_id);
		
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
			sc.nextLine();
			
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
					  name = sc.nextLine();
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
									  System.out.println("\n\t\tYOUR PROFILE");
									  displayProfile(login_id);
									  news_feed(login_id);
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
					     
				  case 4:find_birthday(login_id);
					    break;
					   
				  case 5:
					  break;
				
			}
		}while(c!=5);
		
		
	}
	
	void displayProfile(int login_id)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		String date3=null;
		
		date3=sdf.format(head[login_id].dob);
		System.out.println("________________________________________");
		System.out.println("\n\t\t "+head[login_id].uname+" ");
		System.out.println("\nStatus: "+head[login_id].status);
		System.out.println("\nDate Of Birth: "+date3);
		System.out.println("\n_________________________________________");
	}
	
	int searchPerson(String ser)			//TO find a random person on Connect_pal
	{
		int i;
		for(i=0;i<head_count;i++)
		{
			int comp = ser.compareTo(head[i].uname);
			if(comp==0)
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
			int comp = fr_name.compareTo(ptr.uname);
			if(comp==0)
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
			int comp = fr_name.compareTo(ptr.uname);
			if(comp==0)
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
	

	void news_feed(int login_id)
	{
		node ptr=head[login_id].next;
		if(ptr!=null)
		{
		System.out.println("\n\t#_Your Following_#");
		}
		while(ptr!=null)
		{
			System.out.println();
			System.out.println(ptr.uname+"");
			System.out.println("Status: "+ptr.status);
			System.out.println();
			ptr=ptr.next;
		}
	}
	
	void find_birthday(int login_id)
	{
		Date currentdate=java.util.Calendar.getInstance().getTime();  //take current date
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("MM");		//we need month only
		SimpleDateFormat sdf2=new SimpleDateFormat("dd");
		String b_month1=null;									//friend's bday month
		String b_month2=null;									//current bday month
		b_month2=sdf.format(currentdate);
		String b_date1=sdf2.format(currentdate);
		String b_date2=null;
		int today_date=Integer.parseInt(b_date1);
		
		node ptr=head[login_id].next;
		while(ptr!=null)
		{
			b_month1=sdf.format(ptr.dob);
			b_date2=sdf2.format(ptr.dob);
			int frnd_b_date=Integer.parseInt(b_date2);
			if(b_month1.equals(b_month2)&&today_date<=frnd_b_date)
			{
				SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");
				String friend_bday=sdf1.format(ptr.dob);
				System.out.println(ptr.uname + "  is having birthday in this month at "+friend_bday);
			}
			ptr=ptr.next;
		}
	}
	
	void update_status(int login_id)
	{

		String s="";
		System.out.println("\nHere are some common status for your help!");
		System.out.println("1.Available");
		System.out.println("2.Busy");	
		System.out.println("3.At work");	
		System.out.println("4.Sleeping");	
		System.out.println("5.Type your own status");
		int ch=sc.nextInt();
		sc.nextLine();
		
		if(ch==1)
		{
			s="Available";
			head[login_id].status="Available";
		}
		else if(ch==2)
		{
			s="Busy";
			head[login_id].status="Busy";
		}
		else if(ch==3)
		{
			s="At work";
			head[login_id].status="At work";
		}
		else if(ch==4)
		{
			s="Sleeping";
			head[login_id].status="Sleeping";
		}
		else if(ch==5)
		{
			System.out.println("Enter your updated status");
			s=sc.nextLine();
			head[login_id].status=s;
			
		}
		node ptr;
		for(int i=0;i<head_count;i++)
		{
			if(head[i]!=head[login_id])
			{
				ptr = head[i].next;
				while(ptr!=null)
				{
					if(ptr.uname==head[login_id].uname)
					{
						ptr.status=s;
					}
					ptr=ptr.next;
				 }
			 }
		}

	}
	
	void display_list(int login_id)
	{
		for(int i=0;i<head_count;i++)
		{
			if(i!=login_id&&check_friend(login_id,head[i].uname)==false)
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
		String name=sc.nextLine();
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
			
//			insert(login_id,friend_id);     //add edge u,v
//			insert(friend_id,login_id);     //add edge v,u
			insert_sorted(login_id,friend_id);     //add edge u,v
			insert_sorted(friend_id,login_id);     //add edge v,u
			System.out.println("you are successfully connected to "+head[friend_id].uname);
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
		
		
		
		if(head[x].next==null)
		{
			head[x].next = temp;
		}
		
		else
		{
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
			System.out.println("________________________");
			System.out.println("3.Close");
			System.out.println("\nEnter your choice ");
			System.out.println();
			choice=sc.nextInt();
			//sc.nextLine();
			
			switch(choice)
			{
			  case 1:sc.nextLine();
				  obj.signup();
				     break;
				     
			  case 2:
				  int login_id=obj.login();
				     break;
				     
			  case 3:
				    break;
			
			}
		}while(choice!=3);
		
		
	}
}

