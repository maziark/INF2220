import java.util.*;
import java.io.*;

public class TaskManager {
	int numberOfTasks = 0;
	String fileName;
	Map <Integer, Task> tasks = new HashMap<Integer, Task> ();
	List <Task> viewed = new ArrayList <Task> ();
	List <Task> Q = new ArrayList <Task> ();
	List <Task> independentTasks = new ArrayList <Task> ();
	boolean isRealizable = true;
	boolean debug = true;
	
	
	public void print (String s){
		if (debug) System.out.println (s);
	}
	
	TaskManager (String fileName, String debug){
		this.fileName = fileName;
		this.debug = (debug.equals("d"));
		initiateTasks ();
		System.out.println (findIndependentTask ());
		
		OptimizeTasks ();
		fixLatestStart();
		
		printTasks ();
	}
	
	public void printTasks (){
		for (Task t: tasks.values()){
			System.out.println (t.toString());
			System.out.println ("\t" + t.earliestStart + "\t" + t.earliestFinish);
			System.out.println ("\t" + t.latestStart + "\t" + t.latestFinish);
		}
	}
	
	public void fixLatestStart(){
		for (Task t: tasks.values()){
			int LF = 1000000;
			for (Task next: t.outEdges)
				LF = Math.min (LF, next.earliestStart);
			
			if (t.outEdges.size () > 0) t.setLF (LF);
		}
	}
	
	public boolean findIndependentTask (){
		boolean flag = false;
		print ("Find Independent Task");
		for (Task t: tasks.values()){
			if (t.inEdges.size() == 0) {
				independentTasks.add(t);
				print (t.toString());
				flag = true;
			}
		}
		return flag;
	}
	
	boolean initiateTasks (){
		try{
			Scanner input = new Scanner (new File (this.fileName));
			this.numberOfTasks = Integer.parseInt (input.next());
			
			for (int i = 1; i < numberOfTasks + 1; i++)
				tasks.put(i, new Task(i));
			
			for (int i = 1; i < numberOfTasks + 1; i++){
				Task tmp = tasks.get(i);
				tmp.id = Integer.parseInt(input.next());
				tmp.name = input.next();
				tmp.time = Integer.parseInt(input.next());
				tmp.staff = Integer.parseInt(input.next());
				
				int count = 0;
				int pre = Integer.parseInt(input.next());
				while (pre != 0){
					count ++;
					ArrayList <Task> path = new ArrayList <Task> ();
					if (isConnected (tmp, tasks.get(pre), path)){
						isRealizable = false;
						path.add(tmp);
						print ("Not realizable with path : ");
						for (i = path.size()-1; i > -1; i--) print (path.get(i).toString());
						return false;
					}else{
						tmp.inEdges.add(tasks.get(pre));
						tasks.get(pre).outEdges.add(tmp);
						pre = Integer.parseInt(input.next());
					}
				}
				tmp.cntPredecessors = count;
			}
			
			
			for (int i = 1; i < numberOfTasks + 1; i++){
				print (tasks.get(i).toString());
				for (Task t: tasks.get(i).outEdges) print ("\t" + t.toString());
			}
			
			return true;
		}catch (Exception e){
			System.out.println (e);
			return false;
		}
	}
	
	public boolean isConnected (Task A, Task B, ArrayList <Task> path){
		if (A.equals(B)) {
			return true;
		}else{
			for (Task t: A.outEdges){
				if (isConnected(t, B, path)) {
					path.add(t);
					return true;
				}
			}return false;
		}
	}
	
	public void OptimizeTasks (){
		Q = new ArrayList <Task> ();
		viewed = new ArrayList <Task> ();
		for (Task t: independentTasks){
			Task current = t;
			current.setES (0);
			Q.add(current);
			while(Q.size() > 0){
				current = Q.remove(0);
				viewed.add(current);
				for (Task next: current.outEdges){
					next.setES (Math.max(current.earliestFinish, next.earliestStart));
					if (viewed.containsAll(next.inEdges)) Q.add(next);
				}
			}
		}
	}
	
	
	public static void main (String []arg){
		TaskManager t = new TaskManager (arg[0], arg[1]);
	}
}