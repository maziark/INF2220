import krypto.*;
public class Telegrafist extends Thread{
	Kanal[] kanaler;
	int kanalID;
	boolean finished = false;
	String msg = "";
	int pos;
	int numberOfTelegrafist;
	Monitor monitor;
	public Telegrafist (int id, int numberOfTelegrafist ,Kanal[] kanaler, Monitor monitor){
		this.kanaler = kanaler;
//		this.kaanlID = this.kanaler[id].hentId();
		pos = id;
		this.monitor = monitor ;
		this.numberOfTelegrafist = numberOfTelegrafist;
	}
	
	public Msg getMsg(){ // parallel one!
		
		try {
			String msg = kanaler[pos].lytt();
		        //if (msg == null){ finished = true;}
			return new Msg(kanalID, msg);
		}catch (NullPointerException e){
			finished = true;
			return new Msg(kanalID, "");
		}
	}
	
	
	public void run(){
		while (!finished){
			String temp = kanaler[pos].lytt();
			if (temp!=null){
				//System.out.println (temp);
				monitor.leverKryptert(new Msg (kanaler[pos].hentId(), temp));
				
			}else{
				pos += numberOfTelegrafist;
				if (pos >= kanaler.length){
					finished = true;
					// Tell the monitor that I'm done!
				}
			}
		}
		//System.out.println ("Telegrafister Done!");
		monitor.imDone();
		
	}
}