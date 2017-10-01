import java.util.*;

public class Graph <N, P>{ 	// N for the generic construction of Node
				// P for properties of each edge (weight or whatever)
	
	// Node is generic, it can be defined based on
	// the need of each task. But here I just make
	// a simple one, in case just a simple graph
	// algorithm is going to be implemented =>
	/*
	class Node {
		int	value;
		Node (int value){
			this.value = value;
		}
	}
	*/
	
	public 	Map <N		, Map <	N	, P>> thisGraph = new HashMap <N, Map <N, P>>();
	//	Edge<v1			<v2	, Weight>

	public boolean addNode (N newNode){
		if (thisGraph.keySet().contains (newNode))	return false;
		else {
			thisGraph.put(newNode, new HashMap <N, P> ());
			// If we decide to change the graph into a undirected
			// graph, all that needs to be done is to add a new node
			// when we are adding edges. Knowing that the only 
			// practical difference between directed and undirected
			// graph is the double linked directions.
			return true;
		}
	}
	
	public boolean addEdge (N from, N to, P properties){
		try {
			thisGraph.get(from).put(to, properties);
			return true;
		}catch (Exception e){
			// The only thing that can possibily go wrong
			// is that the node "from" be missing!
			System.out.println ("No such node! : " + e); 
			return false;
		}
	}
	
	// Maybe I add more functions if I see the need!

	// This function will figure if there is any path between
	// two nodes (from, to); If they be connected the list
	// path will find the path.
	public boolean isConnected (N from, N to, List <N> path){
		if (from.equals(to)) {
			path.add(from);
			return true;
		}for (N nxtNode : thisGraph.get(from).keySet()){
			if (isConnected (from, to, path)) {
				path.add(from);
				return true;}
		}return false;
	}

}