public class OrdnetLenkeliste<T extends Comparable<T>> extends Lenkeliste<T>{
	
	
	//Setter inn et element, slik at listen er ordnet fra minst til stoerst
	@Override
	public void settInn(T element){
		Node node = new Node(element);
		
		//Hvis listen er tom, setter man enkelt inn noden
		if(erTom()){
			forran = node;
			return;
		}
		
		//Hvis den nye noden er mindre enn forran, erstatter man forran med den nye noden
		if(node.data.compareTo(forran.data) < 0){
			node.neste = forran;
			forran.forrige = node;
			forran = node;
			return;
		}
		
		Node gjeldende = forran;
		while(gjeldende.neste != null){
			gjeldende = gjeldende.neste;
			if(node.data.compareTo(gjeldende.data) < 0){
				gjeldende.forrige.neste = node;
				node.forrige = gjeldende.forrige;
				node.neste = gjeldende;
				gjeldende.forrige = node;
				return;
			}
		}
		gjeldende.neste = node;
		node.forrige = gjeldende;
	}
	
}