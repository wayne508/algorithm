public class Percolation {
 private boolean[][] open;
 private WeightedQuickUnionUF uf;
 private WeightedQuickUnionUF fullUf;
 private int size;

 public Percolation(int N) {
  size = N;
  open = new boolean[N][N];
  uf = new WeightedQuickUnionUF(N * N + 2);
  fullUf = new WeightedQuickUnionUF(N * N + 2);

  for (int i = 0; i < N; i++)
   for (int j = 0; j < N; j++)
    open[i][j] = false;
 }

 public void open(int i, int j) {
  if (i < 1 || i > size || j < 1 || j > size)
   throw new IndexOutOfBoundsException("index out of range!");
  
  setOpen(i, j);

  if (i == 1) {
   uf.union(0, pos(1, j));
   fullUf.union(0, pos(1, j));
  }
  
  if (i == size)
   uf.union(size * size + 1, pos(size, j));
  
  unionSite(i, j, i - 1, j);
  unionSite(i, j, i + 1, j);
  unionSite(i, j, i, j - 1);
  unionSite(i, j, i, j + 1);
 }

 public boolean isOpen(int i, int j) {
  if (i < 1 || i > size || j < 1 || j > size)
   throw new IndexOutOfBoundsException("index out of range!");
  return open[i - 1][j - 1];
 }

 public boolean isFull(int i, int j) {
  if (i < 1 || i > size || j < 1 || j > size)
   throw new IndexOutOfBoundsException("index out of range!");
  return fullUf.connected(0, pos(i, j));
 }

 public boolean percolates() {
  return uf.connected(0, size * size + 1);
 }

 private int pos(int i, int j) {
  return size * (i - 1) + j;
 }

 private void setOpen(int i, int j) {
  open[i - 1][j - 1] = true;
 }

 private void unionSite(int i, int j, int i2, int j2) {
  if (i2 < 1 || i2 > size || j2 < 1 || j2 > size)
   return;
  if (isOpen(i2, j2)) {
   uf.union(pos(i, j), pos(i2, j2));
   fullUf.union(pos(i, j), pos(i2, j2));
  }
 }
}