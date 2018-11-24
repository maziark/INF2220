public class Koe<T> extends LinkedList<T>{
    public Koe (){
	super();
    }
    
    
    @Override
    public void settInn (T element){
	Node temp = new Node (element);
	if (super.size > 0){    
	    super.tail.next = temp;
	    super.tail = temp;
	    super.size ++;
	}else{
	    super.settInn(element);
	}
	
    }
}