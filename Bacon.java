import java.io.*;
import java.util.*;

//"/Users/Xavier/Desktop/AlgoLabs/Lab4/actor_list.txt"
//"/Users/Xavier/Desktop/AlgoLabs/Lab4/actors.txt"
//"/Users/Xavier/Desktop/AlgoLabs/Lab4/appearances.txt"
//"/Users/Xavier/Desktop/AlgoLabs/Lab4/movie_list.txt"
public class Bacon
{
	int actorListCount, movieListCount, personNum;
	String person, line;
	Scanner scan1, scan2, scan3, scan4, scan5;
	List<int[]> appearances = new ArrayList<int[]>();
	List<String> actor_list = new ArrayList<String>();
	List<String> movie_list = new ArrayList<String>();
	ArrayList<ArrayList<Node>> adj = new ArrayList<ArrayList<Node>>();
	HashMap<Object, Object> hash;
	Node temp, temp2, temp3;
	int kevin = 0;
	int bfsCount = 0;
	int kbNum = 0;
	
	public void bacon() throws FileNotFoundException
	{
		long start = System.currentTimeMillis();
		
		scan1 = new Scanner(new File("/Users/Xavier/Desktop/AlgoLabs/Lab4/actor_list.txt"));
		scan2 = new Scanner(new File("/Users/Xavier/Desktop/AlgoLabs/Lab4/movie_list.txt"));
		scan3 = new Scanner(new File("/Users/Xavier/Desktop/AlgoLabs/Lab4/appearances.txt"));
		scan4 = new Scanner(new File("/Users/Xavier/Desktop/AlgoLabs/Lab4/actors.txt"));
		
		//This is reading in the actor_list and saving it into an ArrayList<String>
		actorListCount = 0;
		while(scan1.hasNext())
		{
			line = scan1.nextLine();
			if(line.contains("Bacon, Kevin"))
				kevin = actorListCount;
			actor_list.add(line);
//			System.out.println(actorListCount + " " + line);
			actorListCount++;
		}
		
		//This is reading in the movie_list and saving it into an ArrayList<String>
		movieListCount = 0;
		while(scan2.hasNext())
		{
			line = scan2.nextLine();
			movie_list.add(line);
			movieListCount++;
			//System.out.println(line);
		}
		
		//This is reading in the appearances and saving it into an ArrayList<int[]>
		int appcount = 0;
		while(scan3.hasNext())
		{
			line = scan3.nextLine();
			String[] str = line.split(" ");
			int[] ints = new int[str.length];
			for(int x = 0; x < str.length; x++)
			{
				ints[x] = Integer.parseInt(str[x]);
			}
			appearances.add(ints);
			appcount++;
		}		
		
		//This for loop is just initiating the elements of the Arraylist<ArrayList<Node>>
		for(int u = 0; u < actor_list.size(); u++)
		{
			adj.add(u, new ArrayList<Node>());
		}

		//This part of the program fills out the adjacency list according to the appearances file
		Node first, second;
		for(int x = 0; x < appearances.size(); x++)
		{
			int[] ints = appearances.get(x);
			for(int y = 1; y < ints.length; y++)
			{
				first = new Node(ints[y]);
				first.setName(actor_list.get(ints[y]));
				first.setMovie(movie_list.get(ints[0]));
				first.setParent(-1);
				for(int z = y + 1; z < ints.length; z++)
				{
					second = new Node(ints[z]);
					second.setName(actor_list.get(ints[z]));
					second.setMovie(movie_list.get(ints[0]));
					second.setParent(-1);
					if(adj.get(ints[y]).contains(second) == false)
					{
						adj.get(ints[y]).add(second);
					}
					if(adj.get(ints[z]).contains(first) == false)
					{
						adj.get(ints[z]).add(first);
					}
				}
			}
		}
//		for(int v = 0; v < adj.get(13406).size(); v++)
//		{
//			System.out.println("Movie, Name, and Number: " + adj.get(13406).get(v).getMovie() + ", " + adj.get(13406).get(v).getName() + ", " + adj.get(13406).get(v).getActor());
//		}
		
		//Static BFS to assign parents for the Bacon Tree
		hash = new HashMap<Object, Object>();
		for(int v = 0; v < adj.get(kevin).size(); v++)
		{
			bfsCount = 1;
			if(adj.get(kevin).get(v).getParent() == -1)
			{
				if(adj.get(kevin).get(v).getActor().equals(13406))
				{
					hash.put((int)adj.get(kevin).get(v).getActor(), adj.get(kevin).get(v));
				}
				else
				{
					adj.get(kevin).get(v).setParent(kevin);
					adj.get(kevin).get(v).setKBNum(bfsCount);
					if(hash.containsKey((int)adj.get(kevin).get(v).getActor()) == false)
					{
						hash.put((int)adj.get(kevin).get(v).getActor(), adj.get(kevin).get(v));
					}
				}
			}
			bfs((int) adj.get(kevin).get(v).getActor());
		}
		
		//Using scan4 to find the Kevin Bacon Number of the actors/actresses in the actors file
		System.out.println("\nProgram Start: \n");
		System.out.println("Number of Movies: " + movieListCount);
		System.out.println("Number of Actors/Actresses: " + actorListCount);
		System.out.println("Kevin Bacon's number is: " + kevin + "\n");
		while(scan4.hasNext())
		{
			personNum = -1;
			person = scan4.nextLine();
			for(int t = 0; t < actor_list.size(); t++)
			{
				if(actor_list.get(t).equals(person) == true)
				{
					personNum = t;
				}
				else
				{
					personNum = personNum;
				}
			}
			if(personNum != -1)
			{
				if(hash.containsKey(personNum) == true)
				{
					temp = (Node)hash.get(personNum);
					if(temp.getParent() == -1)
					{
						System.out.println("Kevin Bacon has a Bacon number of " + kbNum + ".\n");
					}
					else
					{
						kbNum = 0;
						kbNum++;
						temp2 = (Node)hash.get(temp.getParent());
						System.out.println(temp.getName() + " was in " + temp.getMovie() + " with " + temp2.getName() + ".");
						baconize(temp2);
					}
				}
				else
				{
					System.out.println(person + " was not found.\n");
				}
				
			}
			else
			{
				System.out.println(person + " was not found!!!\n");
			}
		}
		
//		System.out.println(adj.get(kevin).get(0).getParent() +  ", " + adj.get(kevin).get(0).getActor());
//		System.out.println(adj.get(15431).get(0).getParent() + ", " + adj.get(15431).get(0).getActor());
//		System.out.println(adj.get(68708).get(0).getParent() + ", " + adj.get(68708).get(0).getActor());
//		System.out.println(adj.get(114638).get(2).getParent() + ", " + adj.get(114638).get(2).getActor());
		
		long end = System.currentTimeMillis();
		long fin = end - start;
		System.out.println("\nTime in milliseconds for program to run : " + fin);
	}
	
	//The actual recursive bfs method that is assigning parents for the static Bacon Tree
	public <E> void bfs(int n)
	{
		while(bfsCount <= 5)
		{
			for(int x = 0; x < adj.get(n).size(); x++)
			{
				bfsCount = bfsCount + 1;
				if(adj.get(n).get(x).getParent() == -1)
				{
					if(adj.get(n).get(x).getActor().equals(13406))
					{
						hash.put((int)adj.get(n).get(x).getActor(), adj.get(n).get(x));
					}
					else
					{
						adj.get(n).get(x).setParent(n);
						adj.get(n).get(x).setKBNum(bfsCount);
						if(hash.containsKey((int)adj.get(n).get(x).getActor()) == false)
						{
							hash.put((int)adj.get(n).get(x).getActor(), adj.get(n).get(x));
						}
					}
				}
				bfs((int) adj.get(n).get(x).getActor());
			}
		}
	}
	
	public void baconize(Node x)
	{
		temp2 = x;
		temp3 = (Node)hash.get(temp2.getParent());
		kbNum++;
		if(temp2.getParent() == 13406)
		{
			System.out.println(temp2.getName() + " was in " + temp2.getMovie() + " with " + temp3.getName() + ".");
			System.out.println(temp.getName() + " has a Bacon number of " + kbNum + ".\n");
		}
		else
		{
			System.out.println(temp2.getName() + " was in " + temp2.getMovie() + " with " + temp3.getName() + ".");
			baconize(temp3);
		}
	}
}
