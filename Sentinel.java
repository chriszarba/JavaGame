
class Sentinel<T> extends ANode<T> {
  
  
  //Main Constructor
  Sentinel(ANode<T> next, ANode<T> prev) {
    super(next, prev);
    next.prev = this;
    prev.next = this;
  }
  
  //Zero Argument constructor
  Sentinel() {
    this.next = this;
    this.prev = this; 
  }
   
  // returns this number of nodes in this list
  int size() {
  		return 0;
  }

  //is this list empty
  // only need to check next because if even one next exists then so does prev.
  boolean isEmpty() {
 	 return (this.next.equals(this));
  }
  
}