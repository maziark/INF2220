import krypto.*;
public class Kryptografist extends Thread{
	Koe <Msg> dmsgs;
	int ID;
	int antallKryptografister;
	Kanal[] kanaler;
	Monitor m;
	boolean finished = false;
	public Kryptografist (int ID, int antallKryptografister, Kanal[] kanaler, Monitor m){
		this.ID = ID;
		this.antallKryptografister = antallKryptografister;
		this.kanaler = kanaler;
		this.m = m;
	}
	
	
	
	public void run(){
		//System.out.println ("Dekrypter is running!");
		
		
		//System.out.println ("I'm running!");
		while(!finished){
			//System.out.println ("We are running!");
			
			//while(!m.telegrafisterDone){
				//System.out.println (m.telegrafisterDone);
			//}
			try {
				Msg temp = m.hentKryptert();
				temp.setMsg (Kryptografi.dekrypter(temp.getMsg()));
				//System.out.println (temp.getMsg());
				m.leverDekryptert (temp);
			}catch (NullPointerException e){
				if (m.telegrafisterDone) finished = true;
			}
		}
		m.imDoneD();
	}
}