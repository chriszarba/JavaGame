import java.util.Iterator;
import tester.*;

class ForwardDequeIterator<T> implements Iterator<T>{
  ANode<T> curr;

  ForwardDequeIterator(ANode<T> n){
    this.curr = n;
  }

  public boolean hasNext() {
    return curr.whatType();
  }

  public T next() {
    T answer = curr.getData();
    this.curr = curr.getNext();
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

}
class Deque<T> implements Iterable<T>{

  Sentinel<T> header;

  //Zero Argument Constructor
  Deque() {
    this.header = new Sentinel<T>();
  }

  // convenience constructor
  Deque(Sentinel<T> sentinel){
    this.header = sentinel;
  }

  // returns the head of the deque
  // if there is no head, it returns the header.
  ANode<T> getHead(){
    if(this.header.next == null) { 
      return this.header;
    }
    else {
      return this.header.next;
    }
  }

  // returns the tail of the deque
  // if there is no tail, it returns the header.
  ANode<T> getTail(){
    if(this.header.prev == null) { // is this field of field ok?
      return this.header;
    }
    else {
      return this.header.prev;
    }
  }

  // returns the number of nodes in a list deque
  int size() {
    return this.header.next.size();
  }

  // adds a given piece of data of type T as a node at the front of the list
  void addAtHead(T t) {
    Node<T> insert = new Node<T>(t);
    ANode<T> head = this.getHead();
    this.header.next = insert;
    insert.prev = this.header;
    head.prev = insert;
    insert.next = head;
  }

  // adds a given piece of data of type T as a node at the end of the list
  void addAtTail(T t) {
    Node<T> insert = new Node<T>(t);
    ANode<T> tail = this.getTail();
    this.header.prev = insert;
    insert.next = this.header;
    tail.next = insert;
    insert.prev = tail;
  }

  // removes and returns the first node of this list
  T removeFromHead(){
    if(this.header.isEmpty()) {
      throw new IllegalArgumentException("Can't remove from an empty list");
    }
    else {
      ANode<T> nextOne = this.header.next;
      this.header.next.removeANode();
      return nextOne.getData();
    }
  }

  //returns the first node of this list
  T getHeadData(){
    if(this.header.isEmpty()) {
      throw new IllegalArgumentException("Can't peek an empty list");
    }
    else {
      ANode<T> nextOne = this.header.next;
      return nextOne.getData();
    }
  }

  // removes and returns the last node of this list
  T removeFromTail(){
    if(this.header.isEmpty()) {
      throw new IllegalArgumentException("Can't remove from an empty list");
    }
    else {
      ANode<T> previous = this.header.prev;
      this.header.prev.removeANode();
      return previous.getData();
    }
  }

  //returns the last node of this list
  T getTailData(){
    if(this.header.isEmpty()) {
      throw new IllegalArgumentException("Can't remove from an empty list");
    }
    else {
      ANode<T> previous = this.header.prev;
      return previous.getData();
    }
  }


  // if the given ANode is not the sentinel, then remove it
  // otherwise do nothing
  void removeNode(ANode<T> node) {
    if(!(node.equals(this.header))) {
      node.removeANode();
    }
  }

  public Iterator<T> iterator() {
    return new ForwardDequeIterator<T>(this.header.next);
  }

  // return if this deque is empty or not
  boolean isEmpty() {
    return this.size() == 0;
  }

  boolean contains(T object) {
    for(T i : this) {
      if(i.equals(object)) {
        return true;
      }
    }
    return false;
  }

}

class ExamplesDeque {

  //In ExamplesDeque
  boolean testDequeIteration(Tester t) {
    Deque<String> dq = new Deque<String>();
    dq.addAtTail(", ");
    dq.addAtHead("Hello");
    dq.addAtTail("world!");
    String msg = "";
    for (String s : dq) {
      msg = msg.concat(s);
    }
    return t.checkExpect(msg, "Hello, world!");
  }
}


