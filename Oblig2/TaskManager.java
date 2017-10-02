import java.util.*;
import java.io.*;
public class TaskManager {

	class Properties {
		int staff;
		int time;
	}

	List <Task>			viewed;
	List <Task>			toBeDone;
	List <Task>			Q;
	List <Task>			startingPoints;
	HashMap <Integer, Task>		allTasks;
	Graph <Task, Properties>	tasks;
	
	
	public TaskManager (){
		tasks 	= new Graph <Task, Properties>();
		Q 	= new ArrayList <Task> ();
		viewed 	= new ArrayList <Task> ();
	}
	
	void setStartingPoints(){
		System.out.println ("Set Starting point");
		for (Task t: allTasks.values()){
			//System.out.println ("Here " + t.toString());
			//System.out.println (t.inEdges.size());
			if (t.inEdges.size() == 0) {
				startingPoints.add(t);
				System.out.println ("Starting points " + t.toString());
			}
		}
	}

	void addNextTaskInQ (Task t, boolean setTime){
		//System.out.println ("add Next value for " + t.toString());
		for (Task tmp: t.outEdges) {
			//System.out.println (tmp.toString());
			boolean flag = true;
			for (int req: tmp.inEdges)
				if (!viewed.contains (allTasks.get(req))) flag = false;
			if (setTime && flag) tmp.setES (t.earliestFinish);
			if (flag) Q.add(tmp);
		}
	}


	boolean initiateGraph (String fileName){
		int numberOfTasks = 0;
		try{
			Scanner input = new Scanner (new File(fileName));
			numberOfTasks = Integer.parseInt(input.next());
			for (int i = 1; i <= numberOfTasks; i++) tasks.addNode (new Task(i));

			for (int i = 0; i < numberOfTasks; i++){
				Properties p = new Properties ();
				tasks.keySet().get(i).id	= Integer.parseInt(input.next());
				tasks.keySet().get(i).name	= input.next();
				p.time				= Integer.parseInt(input.next());
				p.staff				= Integer.parseInt(input.next());
				int requirement = Integer.parseInt(input.next());
				while (requirement != 0){
					tmp.inEdges.add(requirement);
					requirement = Integer.parseInt(input.next());
				}
				tmp.cntPredecessors = tmp.inEdges.size();
				allTasks.put(tmp.id, tmp);
			}
			return true;
		}catch (Exception e){
			System.out.println (e);
			return false;
		}
	}
	
	int OptimizeTime (){
		Q = new ArrayList <Task> ();
		viewed = new ArrayList <Task> ();
		Task current = startingPoints.get(0);
		int timeUnit = 0;
		current.setES(timeUnit);
		timeUnit = current.earliestFinish;
		viewed.add(current);
		addNextTaskInQ(current, true);
		while (Q.size() > 0){
			String strViewed = "";
			String strQ = "";
			//System.out.println ("Now running " + current.toString () + "Time :  " + current.earliestFinish);
			//for (Task t: viewed) strViewed += " " + t.toString();
			//for (Task t: Q) strQ += " " + t.toString ();
			//System.out.println (strViewed +  "\n" + strQ + "\n");
			
			
			current = Q.remove(0);
			//if (viewed.contains(current)) return false;
			viewed.add(current);
			addNextTaskInQ (current, true);
		}
		System.out.println (current.earliestFinish);
		return timeUnit;

	}
	
	public static void main (String [] arg){
		TaskManager t = new TaskManager();
	
	}

}
