import krypto.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;
public class Monitor {
	boolean telegrafisterDone = false;
	boolean kryptografisterDone = false;
	int numberOfDoneTelegrafists = 0;
	int numberOfDoneKryptografist = 0;
	public int antalTelegrafister, antalKryptografister;
//	public ArrayList<Msg> msgs = new ArrayList<Msg>();
	//public ArrayList<Telegrafist> telegrafists = new ArrayList<Telegrafist>();
	public Koe <Msg> msgs = new Koe <Msg>();
	public Koe <Msg> dmsgs = new Koe <Msg>();
	
	public Kanal[] kanaler;
	
	public Thread[] telegrafister;
	public Thread[] kryptografister;
	public Monitor (int antalTelegrafister, int antalKryptografister, Thread[] telegrafister, Thread[] kryptografister, Kanal[] kanaler){
		this.antalTelegrafister = antalTelegrafister;
		this.antalKryptografister = antalKryptografister;
		this.telegrafister = telegrafister;
		this.kryptografister = kryptografister;
		this.kanaler = kanaler;
		

	}
	
	public synchronized void leverDekryptert (Msg msg){
		dmsgs.settInn(msg);
	}
	
	public Msg hentDekryptert (){
		return dmsgs.fjern();
	}
	
	public synchronized void leverKryptert (Msg msg){
		msgs.settInn(msg);
	}

	public synchronized Msg hentKryptert (){
		return msgs.fjern();
	}
	
	public void runDekrypter (){
		for (int i = 0; i < antalKryptografister; i++) kryptografister[i].start();
	}
	
	public synchronized void imDone(){
		numberOfDoneTelegrafists ++;
		//System.out.println (numberOfDoneTelegrafists);
		if (numberOfDoneTelegrafists >= antalTelegrafister) {
			telegrafisterDone = true;
			//runDekrypter();
		}
	}
	
	public synchronized void imDoneD(){
		numberOfDoneKryptografist ++;
		if (numberOfDoneKryptografist == antalKryptografister) {
			kryptografisterDone = true;
			
		}
	}
	
	/*public void writeInTheDamnFile() throws FileNotFoundException, UnsupportedEncodingException{
		//FileWriter f = new FileWriter (kanaler, this);
		//f.writeInFile();
	}*/
	/*public synchronized void imDoneDKrypting(){
		numberOfDoneDekrypting ++;
		if (numberOfDoneDekrypting == antalKryptografister ) kryptografisterDone = true;
	}*/
	
}