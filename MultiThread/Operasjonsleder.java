import krypto.*;
import java.io.*;
public class Operasjonsleder extends Thread{
	
	Kanal []kanaler ;
	Monitor m;

	//public OrdnetLenkeliste <Msg> msgList = new OrdnetLenkeliste <Msg>();
	public OrdnetLenkeliste <Msg> allMsg = new OrdnetLenkeliste <Msg>();
	
	//public LinkedList
	
	
	public Operasjonsleder (Kanal[] kanaler, Monitor m){
		this.kanaler = kanaler;
		this.m = m;
		//System.out.println (m.kanaler.length);
	}
	
	
	public String prepareKanal (int id){
		/*
			All the msges are already sorted ( ordered list) so this one just 
			put all the msges from each kanal into a string to be used in WriteFile
			function
		*/
		String thisMsg = "";
		//msgList = new OrdnetLenkeListe <Msg> ();
		//System.out.println (allMsg.storrelse());
		for (Msg m: allMsg){
			//System.out.println (m.getMsg());
			if (m.getKanalId () == id) thisMsg += m.getMsg();
			//}catch (Exception e){}
		}
		return thisMsg;
	}
	
	public void run (){
		/*
			It will get msges from monitor (Those that are Decrypted)
			If it can't find any new msges it will check if the Decrypter
			has done working (then we have got them all)
			else we need to keep asking for the nxt one
		*/
		boolean finished = false;
		while (!(m.kryptografisterDone && finished)){
			try{
				Msg temp = m.hentDekryptert();
				temp.getMsg(); /* I have no clue, but without this line it won't write 
							into the file later in the program if you figured
							out why, just let me know! */ 

				allMsg.settInn(temp);
			}catch (Exception e){
				if (m.kryptografisterDone) finished = true;
			}
		}
		if (finished) {
			writeInFile();
			//System.out.println("I'm Done");
		}
	}
	public void writeInFile(){
	
		/*
			writes them in the file! 
		*/
		//orderAllMsges();
		//System.out.println (kanaler.length);
		for (int i = 0; i < kanaler.length; i++){
			//System.out.println (kanaler[i].hentId());
			String temp = prepareKanal(kanaler[i].hentId());
			//System.out.println (temp);
			try {
				File Fileright = new File("Kanal"+i+".txt");
				PrintWriter pw = new PrintWriter(Fileright, "utf-8");
				pw.println(temp);
				pw.close();
			}catch (Exception e){
			}
		}
	}


}