import java.util.Arrays;

public class Fast {
    private static void fast(Point[] points) {
        int size = points.length;
        Point[] tmp = new Point[size];
        for (int i = 0; i < size; i++) {
            Point src = points[i];
            for (int t = 0; t < size; t++)
                tmp[t] = points[t];
            Arrays.sort(tmp, src.SLOPE_ORDER);
            
            int j = 1; 
            while (j < size) {
                if (src.slopeTo(tmp[j]) == Double.NEGATIVE_INFINITY) {
                    j++;
                    continue;
                }
                int start = j;
                double slop = src.slopeTo(tmp[start]);
                while (++j < size && src.slopeTo(tmp[j]) == slop);
                int end = j;
                if (end - start >= 3) {
                    // TODO output results
                    if (src.compareTo(tmp[start]) < 0) {
                        StdOut.printf("%s -> ", src);
                        for (int p = start; p < end - 1; p++)
                            StdOut.printf("%s -> ", tmp[p]);
                        StdOut.printf("%s\n", tmp[end - 1]);
                        src.drawTo(tmp[end - 1]);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        Arrays.sort(points);
        StdDraw.setPenRadius();

        fast(points);
    }
}
