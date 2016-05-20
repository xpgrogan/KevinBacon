import java.io.*;
import java.util.*;

public class Node <E, V>
{
	E actor, name;
	V movie;
	int parent, kbNum;
	
	public Node(E act)
	{
		actor = act;
	}
	
	public void setKBNum(int k)
	{
		kbNum = k;
	}
	
	public int getKBNum()
	{
		return kbNum;
	}
	
	public E getActor()
	{
		return actor;
	}
	
	public void setName(E n)
	{
		name = n;
	}
	
	public E getName()
	{
		return name;
	}
	
	public void setMovie(V x)
	{
		movie = x;
	}
	
	public V getMovie()
	{
		return movie;
	}
	
	public void setParent(int p)
	{
		parent = p;
	}
	
	public int getParent()
	{
		return parent;
	}
}
