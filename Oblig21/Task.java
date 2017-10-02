import java.util.*;
public class Task{
	int		id, time, staff;
	String		name;
	int		earliestStart , earliestFinish;
	int		latestStart, latestFinish;

	List<Task>	outEdges;
	List<Task>	inEdges;
	int		cntPredecessors = 0;

	Task (int id){
		this.id = id;
		outEdges = new ArrayList <Task>();
		inEdges = new ArrayList <Task>();
	}

	public void setES (int ES){
		this.earliestStart = ES;
		this.earliestFinish = this.earliestStart + this.time;
		this.setLF(this.earliestFinish);
	}

	public void setLF (int LF){
		this.latestStart = LF - time;
		this.latestFinish = LF;
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


