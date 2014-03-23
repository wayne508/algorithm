import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
 private Node first;
 private Node last;
 private int count;
 
 public Deque() {
  first = new Node();
  last = new Node();
  first.next = last;
  last.prev = first;
  count = 0;
 }
 
 public boolean isEmpty() {
  return count == 0;
 }
 
 public int size() {
  return count;
 }
 
 public void addFirst(Item item) {
  if (item == null)
   throw new NullPointerException();
  Node node = new Node();
  node.item = item;
  
  first.next.prev = node;
  node.next = first.next;
  
  first.next = node;
  node.prev = first;
  
  count++;
 }
 
 public void addLast(Item item) {
  if (item == null)
   throw new NullPointerException();
  Node node = new Node();
  node.item = item;
  
  last.prev.next = node;
  node.prev = last.prev;
  
  node.next = last;
  last.prev = node;
  count++;
 }
 
 public Item removeFirst() {
  if (isEmpty())
   throw new NoSuchElementException();
  Node tmp = first.next;
  first.next = tmp.next;
  tmp.next.prev = first;
  tmp.prev = null;
  tmp.next = null;
  count--;
  return tmp.item;
 }
 
 public Item removeLast() {
  if (isEmpty())
   throw new NoSuchElementException();
  Node tmp = last.prev;
  last.prev = tmp.prev;
  tmp.prev.next = last;
  tmp.prev = null;
  tmp.next = null;
  count--;
  return tmp.item;
 }

 @Override
 public Iterator<Item> iterator() {
  return new DequeIterator();
 }

 private class Node {
  Item item;
  Node next;
  Node prev;
 }
 
 private class DequeIterator implements Iterator<Item> {
  private Node current = first;

  @Override
  public boolean hasNext() {
   return current.next != last;
  }

  @Override
  public Item next() {
   if (!hasNext())
    throw new NoSuchElementException();
   current = current.next;
   return current.item;
  }

  @Override
  public void remove() {
   throw new UnsupportedOperationException();
  }
 }
 
 public static void main(String[] args) {
 }
}
