
class Queue<T> implements ICollection<T> {
  Deque<T> contents;
  Queue() {
    this.contents = new Deque<T>();
  }
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }
  public T remove() {
    return this.contents.removeFromHead();
  }
  public void add(T item) {
    this.contents.addAtTail(item); // NOTE: Different from Stack!
  }
  
  T peek() {
    return this.contents.getTailData();
  }
}
