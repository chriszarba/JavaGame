
class Stack<T> implements ICollection<T> {
  Deque<T> contents;
  
  Stack() {
    this.contents = new Deque<T>();
  }
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }
  public T remove() {
    return this.contents.removeFromHead();
  }
  
  public void add(T item) {
    this.contents.addAtHead(item);
  }
  
  void push(T item) {
  	this.add(item);
  }
  
  T pop() {
  	return this.remove();
  }
  
  T peek() {
    return this.contents.getHeadData();
  }
  
}