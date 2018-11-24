import java.util.*;
public class LinkedList<T> implements Liste<T> {

    protected int size;
    public Node head, tail;

    public LinkedList (){
	head = new Node ();
	tail = head;
	size = 0;
    }
    /**
     * Beregner antall elementer i listen
     * @return      antall elementer i listen
     */
    public int storrelse(){
	return size;
    }
    
    /**
     * Sjekker om listen er tom
     * @return      om listen er tom
     */
    public boolean erTom(){
	return (size == 0);
    }

    /**
     * Setter inn et element i listen
     * @param   element     elementet som settes inn
     */
    public void settInn(T element){
	Node temp = new Node (element);
	if (storrelse () > 0 ){
	    tail.next = temp;
	    tail = temp;
	}else {
	    head = temp;
	    tail = head;
	}
	size ++;
    }

    public void settInn(T element, Node afterThisNode){
	Node temp = new Node (element);
	if (tail == afterThisNode){
	    afterThisNode.next = temp;
	    tail = temp;
	}else {
	    temp.next = afterThisNode.next;
	    afterThisNode.next = temp;
	}
	size ++;
    }
    
    /**
     * Fjerner et element fra listen. Hvis listen er tom,
     * returneres null.
     * @return      elementet
     */
    public T fjern(){ 
	/* removes the first object */
	if (size == 0) {return null;}
	else {
	    Node temp = head;
	    head = head.next;
	    size --;
	    return (T)temp.element;
        }
    }
    
    public Iterator<T> iterator(){
        return new IteratorForNode();
    }
    
    class IteratorForNode <T> implements Iterator<T>{
	private Node current;
	
	public IteratorForNode (){
	    current = head;
	}
	
	public boolean hasNext(){
	    try{
	        return current.next != null;
	    }catch (NullPointerException E){
		return false;
	    }
	}
	
	public T next(){
	    Node temp = current;
	    current = current.next;
	    return (T) temp.element;
	}
	
	public void remove (){
	
	}
	
    }
    
    
    
    class Node<T> {
	T element;
	Node next;
	public Node (){
	    element = null;
	    next = null;
	}
	public Node (T element){
	    this.element = element;
	    next = null;
	}
	
	public Node (T element, Node next){
	    this.element = element;
	    this.next = next;
	}
    }

}
