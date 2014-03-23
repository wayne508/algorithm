
public class KdTree {
    private Node root;
    private int size;
    private RectHV rect = new RectHV(0, 0, 1, 1);
    private Point2D nearestP;

    private class Node {
        private Point2D point;
        private Node left, right;

        public Node(Point2D point) {
            this.point = point;
        }
    }
    
    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D p) {
        if (!rect.contains(p))
            return;
        root = insertX(root, p);
    }
    
    private Node insertX(Node node, Point2D p) {
        if (node == null) {
            ++size;
            return new Node(p);
        }
        if (p.equals(node.point))
            return node;
        if (p.x() < node.point.x()) 
            node.left = insertY(node.left, p);
        else
            node.right = insertY(node.right, p);
        return node;
    }
    
    private Node insertY(Node node, Point2D p) {
        if (node == null) {
            ++size;
            return new Node(p);
        }
        if (p.equals(node.point))
            return node;
        if (p.y() < node.point.y()) 
            node.left = insertX(node.left, p);
        else
            node.right = insertX(node.right, p);
        return node;
    }
    
    public boolean contains(Point2D p) {
        return findX(root, p);
    }
    
    private boolean findX(Node node, Point2D p) {
        if (node == null)
            return false;
        else if (p.equals(node.point))
            return true;
        else if (p.x() < node.point.x()) 
            return findY(node.left, p);
        else 
            return findY(node.right, p);
    }
    
    private boolean findY(Node node, Point2D p) {
        if (node == null)
            return false;
        else if (p.equals(node.point))
            return true;
        else if (p.y() < node.point.y()) 
            return findX(node.left, p);
        else 
            return findX(node.right, p);
    }
    
    public void draw() {
        StdDraw.setPenColor();
        StdDraw.setPenRadius();
        rect.draw();
        drawX(root, rect);
    }
    
    private void drawX(Node node, RectHV area) {
        if (node == null)
            return;
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        
        Point2D p1 = new Point2D(node.point.x(), area.ymin());
        Point2D p2 = new Point2D(node.point.x(), area.ymax());
        
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.RED);
        p1.drawTo(p2);
        drawY(node.left, 
                new RectHV(area.xmin(), area.ymin(), node.point.x(), area.ymax()));
        drawY(node.right, 
                new RectHV(node.point.x(), area.ymin(), area.xmax(), area.ymax()));
    }
    
    private void drawY(Node node, RectHV area) {
        if (node == null)
            return;
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        
        Point2D p1 = new Point2D(area.xmin(), node.point.y());
        Point2D p2 = new Point2D(area.xmax(), node.point.y());
        
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        p1.drawTo(p2);
        drawX(node.left, 
                new RectHV(area.xmin(), area.ymin(), area.xmax(), node.point.y()));
        drawX(node.right, 
                new RectHV(area.xmin(), node.point.y(), area.xmax(), area.ymax()));
    }
    
    public Iterable<Point2D> range(RectHV area) {
        Queue<Point2D> q = new Queue<Point2D>(); 
        rangeX(root, area, q);
        return q;
    }
    
    private void rangeX(Node node, RectHV area, Queue<Point2D> q) {
        if (node == null)
            return;
        if (area.contains(node.point))
            q.enqueue(node.point);
        if (area.xmin() < node.point.x())
            rangeY(node.left, area, q);
        if (area.xmax() >= node.point.x())
            rangeY(node.right, area, q);
    }
    
    private void rangeY(Node node, RectHV area, Queue<Point2D> q) {
        if (node == null)
            return;
        if (area.contains(node.point))
            q.enqueue(node.point);
        if (area.ymin() < node.point.y())
            rangeX(node.left, area, q);
        if (area.ymax() >= node.point.y())
            rangeX(node.right, area, q);
    }
    
    public Point2D nearest(Point2D p) {
        double minDis = Double.MAX_VALUE;
        nearestX(root, p, rect, minDis);
        return this.nearestP;
    }
    
    private double nearestX(Node node, Point2D p, RectHV area, double minDis) {
        if (node == null)
            return minDis;
        double currDis = p.distanceTo(node.point);
        if (currDis < minDis) {
            minDis = currDis;
            nearestP = node.point;
        }
        
        RectHV leftSide = new RectHV(area.xmin(), area.ymin(), 
                node.point.x(), area.ymax());
        RectHV rightSide = new RectHV(node.point.x(), area.ymin(), 
                area.xmax(), area.ymax());
        RectHV currSide, anotherSide;
        Node currNode, anotherNode;
        if (p.x() < node.point.x()) {
            currNode = node.left;
            currSide = leftSide;
            anotherNode = node.right;
            anotherSide = rightSide;
        } else {
            currNode = node.right;
            currSide = rightSide;
            anotherNode = node.left;
            anotherSide = leftSide;
        }
        minDis = nearestY(currNode, p, currSide, minDis); 
       
        if (anotherSide.distanceTo(p) < minDis) 
            minDis = nearestY(anotherNode, p, anotherSide, minDis);
        return minDis;
    }
    
    private double nearestY(Node node, Point2D p, RectHV area, double minDis) {
        if (node == null)
            return minDis;
        double currDis = p.distanceTo(node.point);
        if (currDis < minDis) {
            minDis = currDis;
            nearestP = node.point;
        }
        
        RectHV dnSide = new RectHV(area.xmin(), area.ymin(), 
                area.xmax(), node.point.y());
        RectHV upSide = new RectHV(area.xmin(), node.point.y(), 
                area.xmax(), area.ymax());
        RectHV currSide, anotherSide;
        Node currNode, anotherNode;
        if (p.y() < node.point.y()) {
            currSide = dnSide;
            currNode = node.left;
            anotherSide = upSide;
            anotherNode = node.right;
        } else {
            currSide = upSide;
            currNode = node.right;
            anotherSide = dnSide;
            anotherNode = node.left;
        }
        minDis = nearestX(currNode, p, currSide, minDis); 

        if (anotherSide.distanceTo(p) < minDis)
            minDis = nearestX(anotherNode, p, anotherSide, minDis);
        return minDis;
    }
}
