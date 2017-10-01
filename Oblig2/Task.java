import java.util.*;
public class Task{
	int		id, time, staff;
	String		name;
	int		earliestStart , earliestFinish;
	int		latestStart, latestFinish;

	List<Task>	outEdges;
	List<Integer>	inEdges;
	int		cntPredecessors = 0;

	Task (){
		outEdges = new ArrayList <Task>();
		inEdges = new ArrayList <Integer>();
	}

	public void setES (int ES){
		this.earliestStart = ES;
		this.earliestFinish = this.earliestStart + this.time;
	}

	public void setLS (int LS){
		this.latestStart = LS;
		this.latestFinish = this.latestStart + this.time;
	}

	public boolean equals(Task to){
		return (this.id == to.id);
	}

	public String toString (){
		String tmp = "";
		tmp += id + " " + name;
		return tmp;
	}


}


