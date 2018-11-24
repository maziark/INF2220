import krypto.*;
public class Oblig6{
	public static void main(String [] args){

		int antallTelegrafister = Integer.parseInt(args[0]);
		int antallKryptografister = Integer.parseInt(args[1]);
		
		Thread 	[]telegrafister 	= new Thread	[antallTelegrafister];
		Thread 	[]kryptografister	= new Thread	[antallKryptografister];
		
		Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
		
		Kanal[] kanaler = ops.hentKanalArray();
		
		Monitor m = new Monitor (antallTelegrafister, antallKryptografister, telegrafister, kryptografister, kanaler);
		
		Operasjonsleder op = new Operasjonsleder (kanaler, m);
		op.start();
		for (int i = 0; i < antallTelegrafister; i++){
			telegrafister[i] = new Thread( new Telegrafist (i, antallTelegrafister, kanaler, m));
			telegrafister[i].start();
		}
		
		for (int i = 0; i < antallKryptografister; i++){
			kryptografister[i] = new Thread(new Kryptografist (i, antallKryptografister, kanaler, m));
			kryptografister[i].start();
			
		}
	}
}