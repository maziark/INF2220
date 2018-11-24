import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class Lenkeliste<T> implements Liste<T>{
	
	protected Node forran = null;
	
	// Beregner antall elementer i listen
    public int storrelse(){
		
		int antall = 0;
		
		for(T t : this){
			antall++;
		}
				
		return antall;
	}
	
	//Sjekker om listen er tom
    public boolean erTom(){
		return storrelse() == 0;
	}
	

    /**
     * Setter inn et element i listen
     * @param   element     elementet som settes inn
     */
    public abstract void settInn(T element);
	

    /**
     * Fjerner et element fra listen. Hvis listen er tom,
     * returneres null.
     * @return      elementet
     */
    public T fjern(){
		if(erTom()){
			return null;
		}
		
		T data = forran.data;
		forran = forran.neste;
		return data;
		
	}
	
	
	protected class Node {
		
		protected T data;
		protected Node neste;
		protected Node forrige;
		
		Node(T data){
			this.data = data;
		}
		
	}
	
	@Override
	public Iterator iterator(){
		return new LenkelisteIterator();
	}
	
	private class LenkelisteIterator implements Iterator<T>{
		
		private Node gjeldende = forran;
		
		@Override
		public boolean hasNext(){
			return gjeldende != null;
		}
		
		@Override 
		public T next(){
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T data = gjeldende.data;
			gjeldende = gjeldende.neste;
			return data;
		}
		
		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
		
	}
	
}