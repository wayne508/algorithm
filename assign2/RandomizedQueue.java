import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
 private Item[] items;
 private int size;
 
 public RandomizedQueue() {
  items = (Item[]) new Object[4];
  size = 0;
 }
 
 public boolean isEmpty() {
  return size == 0;
 }
 
 public int size() {
  return size;
 }
 
 public void enqueue(Item item) {
  if (item == null)
   throw new NullPointerException();
  items[size++] = item;
  if (size == items.length)
    resize(items.length*2);
 }
 
 public Item dequeue() {
  if (isEmpty())
   throw new NoSuchElementException();
  int rnd = StdRandom.uniform(size);
  Item tmp = items[rnd];
  items[rnd] = items[size-1];
  items[size-1] = null;
  --size;
  if (size > 4 && size == items.length/4)
    resize(items.length/2);
  return tmp;
 }
 
 public Item sample() {
  if (isEmpty())
   throw new NoSuchElementException();
  int rnd = StdRandom.uniform(size);
  return items[rnd];
 }

 @Override
 public Iterator<Item> iterator() {
  return new RandomizedQueueIterator();
 }
 
 private void resize(int capacity) {
   Item[] tmp = (Item[]) new Object[capacity];
   for (int i = 0; i < size; i++)
     tmp[i] = items[i];
   items = tmp;
 }
 
 private class RandomizedQueueIterator implements Iterator<Item> {
  private int[] ind;
  private int current;
  
  public RandomizedQueueIterator() {
   ind = new int[size];
   for (int i = 0; i < size; i++)
    ind[i] = i;
   StdRandom.shuffle(ind);
   current = 0;
  }
  @Override
  public boolean hasNext() {
   return current < size;
  }
  @Override
  public Item next() {
   if (!hasNext())
    throw new NoSuchElementException();
   return items[ind[current++]];
  }
  @Override
  public void remove() {
   throw new UnsupportedOperationException();
  }
 }
 
 public static void main(String[] args) {
 }
}
