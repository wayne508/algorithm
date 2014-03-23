public class PercolationStats {
 private double mean;
 private double stddev;
 private double[] results;
 private int times;
 private int scale;

 public PercolationStats(int N, int T) {
  if (N <= 0 || T <= 0)
   throw new IllegalArgumentException(
     "The two args of constructor must be positive!");

  times = T;
  scale = N;
  results = new double[times];

  int size = scale * scale;
  for (int i = 0; i < T; i++) {
   Percolation p = new Percolation(N);

   int[] blocks = new int[size];
   for (int b = 0; b < size; b++)
    blocks[b] = b;
   int blockCount = size;
   while (!p.percolates()) {
    int rnd = StdRandom.uniform(blockCount);
    int row = blocks[rnd] / scale + 1;
    int col = blocks[rnd] % scale + 1;
    //System.out.printf("size=%d, rnd=%d, row=%d, col=%d\n",
      //blockCount, rnd, row, col);
    blocks[rnd] = blocks[blockCount - 1];
    p.open(row, col);
    blockCount--;
   }
   results[i] = 1 - (double) blockCount / size;
  }

  mean = StdStats.mean(results);
  stddev = StdStats.stddev(results);
 }

 public double mean() {
  return mean;
 }

 public double stddev() {
  return stddev;
 }

 public double confidenceLo() {
  return mean - 1.96 * stddev / Math.sqrt(times);
 }

 public double confidenceHi() {
  return mean + 1.96 * stddev / Math.sqrt(times);
 }

 public static void main(String[] args) {
   if (args.length != 2) {
   System.err.println("Two args are needed!");
   return;
   }
   int a = Integer.parseInt(args[0]);
   int b = Integer.parseInt(args[1]);
  PercolationStats s = new PercolationStats(a, b);
  StdOut.printf("mean                    = %f\n", s.mean());
  StdOut.printf("stddev                  = %f\n", s.stddev());
  StdOut.printf("95%% confidence interval = %f, %f\n", s.confidenceLo(),
    s.confidenceHi());
 }
}