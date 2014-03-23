
public class Solver {
    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode prev;
        private int distance;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.distance = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode other) {
            return this.distance - other.distance;
        }
    }

    private final MinPQ<SearchNode> pqOrigin;
    private final MinPQ<SearchNode> pqTwin;
    private final SearchNode result;
    private final boolean solvable;
    //private final Board initial;
    //private final Board initTwin;
    

    public Solver(Board initial) { // find a solution to the initial board
                                   // (using the A* algorithm)
        //this.initial = initial;
        pqOrigin = new MinPQ<SearchNode>();
        pqOrigin.insert(new SearchNode(initial, 0, null));
        
        //this.initTwin = initial.twin();
        pqTwin = new MinPQ<SearchNode>();
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        
        boolean flag = false;
        SearchNode goal = null;
        while (true) {
            if (pqOrigin.isEmpty() && pqTwin.isEmpty())
                break;
            
            goal = step(pqOrigin);
            if (goal != null) {
                flag = true;
                break;
            }
            
            goal = step(pqTwin);
            if (goal != null) {
                goal = null;
                break;
            }
        }
        solvable = flag;
        result = goal;
    }

    public boolean isSolvable() { // is the initial board solvable?
        return solvable;
    }

    public int moves() { // min number of moves to solve initial board; -1 if no
                         // solution
        if (!solvable) return -1;
        return result.moves;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest
                                        // solution; null if no solution
        if (!solvable)
            return null;
        Stack<Board> s = new Stack<Board>();
        SearchNode tmp = result;
        while (tmp != null) {
            s.push(tmp.board);
            tmp = tmp.prev;
        }
        return s;
    }

    private static SearchNode step(MinPQ<SearchNode> q) {
        SearchNode tmp = q.delMin();
        if (tmp.board.isGoal())
            return tmp;
        for (Board b : tmp.board.neighbors()) {
            if (tmp.prev == null || !b.equals(tmp.prev.board)) {
                q.insert(new SearchNode(b, tmp.moves + 1, tmp));
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
