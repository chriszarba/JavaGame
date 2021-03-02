
class Node<T> extends ANode<T> {

  T data;

  //Constructor
  Node(T data) {
    this.next = null;
    this.prev = null;
    this.data = data;

  }

  //Convienience Constructor
  Node(T data, ANode<T> nextOne, ANode<T> prevOne) {
    super(nextOne, prevOne);
    if (nextOne == null || prevOne == null) {
      throw new IllegalArgumentException("Both nodes must have values. One or more is null.");
    }
    else {
      this.data = data;
    }
    nextOne.prev = this;
    prevOne.next = this;
    
  }
  
  // return the data stored by this node
  T getData() {
  	return this.data;
  }

	public boolean whatType() {
		return true;
	}

	public ANode<T> getNext(){
		return this.next;
	}

}