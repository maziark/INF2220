public class Msg implements Comparable<Msg>{
	int kanalID;
	public String msg;
	int msgID;
	private static int ID = 0;
	

	public Msg ( int kanalID, String msg){
		this.msgID = ID++;
		this.kanalID = kanalID;
		this.msg = msg;
	}
	
	public int compareTo (Msg other){
		int difference = this.msgID - other.msgID;
		return difference;
	}
	public void setMsg (String msg){
		this.msg = msg;
	}
	public String getMsg (){
		return msg;
	}
	public int getKanalId(){
		return kanalID;
	}
}