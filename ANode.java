
abstract class ANode<T> {
  
  ANode<T> next;
  ANode<T> prev;
  
  //Constructor
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }
  
  //Zero argument constructor
  ANode() {
  }
  
  public boolean whatType() {
  	return false;
  }
  
  // returns the number of nodes in a list
  int size() {
  	if(this.next == null) {
  		return 1;
  	}
  	else {
  		return 1 + this.next.size();
  	}
  }
  
  //is this list empty
  boolean isEmpty() {
 	 return (this.next == null) || (this.prev == null);
  }
  
  T getData() {
  	return null;
  }
  
  // removes a node from a linked list
  void removeANode() {
  	if(!(this.isEmpty())) {
  		ANode<T> nextNode = this.next;
  		ANode<T> prevNode = this.prev;
  		prevNode.next = nextNode;
  		nextNode.prev = prevNode;
  	}
  }
  
  public ANode<T> getNext(){
  	return null;
  }
}





