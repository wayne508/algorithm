
public class PointSET {
    private SET<Point2D> set;
    private final RectHV rect;
    
    public PointSET() {
        set = new SET<Point2D>();
        rect = new RectHV(0, 0, 1, 1);
    }
    
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    public int size() {
        return set.size();
    }
    
    public void insert(Point2D p) {
        if (!rect.contains(p))
            return;
        set.add(p);
    }
    
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    
    public void draw() {
        rect.draw();
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        for (Point2D p : set)
            p.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> iter = new Queue<Point2D>(); 
        for (Point2D p : set) {
            if (rect.contains(p))
                iter.enqueue(p);
        }
        return iter;
    }
    
    public Point2D nearest(Point2D p) {
        Point2D r = null;
        double distance = Double.MAX_VALUE;
        for (Point2D e : set) {
            double tmp = p.distanceTo(e);
            if (tmp < distance) {
                distance = tmp;
                r = e;
            }
        }
        return r;
    }
}
