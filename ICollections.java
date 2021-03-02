// Represents a mutable collection of items
interface ICollection<T> {
  // Is this collection empty?
  boolean isEmpty();
  // EFFECT: adds the item to the collection
  void add(T item);
  // Returns the first item of the collection
  // EFFECT: removes that first item
  T remove();
}