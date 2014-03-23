import java.util.Arrays;

public class Brute {

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
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                for (int k = j + 1; k < n; k++)
                    for (int l = k + 1; l < n; l++) {
                        double slop = points[i].slopeTo(points[j]);
                        if (slop == points[i].slopeTo(points[k])
                                && slop == points[i].slopeTo(points[l])) {
                            StdOut.printf("%s -> ", points[i]);
                            StdOut.printf("%s -> ", points[j]);
                            StdOut.printf("%s -> ", points[k]);
                            StdOut.printf("%s\n", points[l]);
                            points[i].drawTo(points[l]);
                        }
                    }
    }
}
