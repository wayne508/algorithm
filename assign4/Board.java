import java.util.Arrays;

public class Board {
    
    private final int[][] blocks;
    private final int dim;
    
    public Board(int[][] blocks) { // construct a board from an N-by-N array of
                                   // blocks
        dim = blocks.length;
        this.blocks = copyBlocks(blocks);
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() { // board dimension N
        return dim;
    }

    public int hamming() { // number of blocks out of place
        int count = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (blocks[i][j] != 0 && blocks[i][j] != i * dim + j + 1)
                    count++;
        return count;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and
                             // goal
        if (dim == 0) return 0;
        int distance = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
            distance += manhattan(i, j);
        return distance;
    }

    public boolean isGoal() { // is this board the goal board?
        if (blocks[dim - 1][dim - 1] != 0)
            return false;
        for (int i = 0; i < dim - 1; i++)
            for (int j = 0; j < dim; j++)
                if (blocks[i][j] != i * dim + j + 1)
                    return false;
        int i = dim - 1;
        for (int j = 0; j < dim - 1; j++)
            if (blocks[i][j] != i * dim + j + 1)
                    return false;
        return true;
    }

    public Board twin() { // a board obtained by exchanging two adjacent blocks
                          // in the same row
        int row, col;
        do {
            row = StdRandom.uniform(dim);
            col = StdRandom.uniform(dim - 1);
        } while (blocks[row][col] == 0 || blocks[row][col + 1] == 0);
        return move(row, col, row, col + 1);
    }

    public boolean equals(Object y) { // does this board equal y?
        if (!(y instanceof Board))
            return false;
        Board yb = (Board) y;
        if (dim != yb.dim)
            return false;
        for (int i = 0; i < dim; i++)
            if (!Arrays.equals(blocks[i], yb.blocks[i]))
                return false;
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        Queue<Board> q = new Queue<Board>();
        int i = 0, j = 0;
        firstFor:
        for (i = 0; i < dim; i++)
            for (j = 0; j < dim; j++)
                if (blocks[i][j] == 0)
                    break firstFor;
                
        if (i > 0)
            q.enqueue(move(i, j, i - 1, j));
        if (i < dim - 1)
            q.enqueue(move(i, j, i + 1, j));
        if (j > 0)
            q.enqueue(move(i, j, i, j - 1));
        if (j < dim - 1)
            q.enqueue(move(i, j, i, j + 1));
        return q;
    }

    public String toString() { // string representation of the board (in the
                               // output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(dim);
        for (int i = 0; i < dim; i++) {
            sb.append('\n');
            for (int j = 0; j < dim; j++) {
                sb.append(' ');
                sb.append(blocks[i][j]);
                if (j != dim - 1)
                    sb.append(' ');
            }
        }
        sb.append('\n');
        return sb.toString();
    }
    
    private static int[][] copyBlocks(int[][] y) {
        int n = y.length;
        int[][] x = new int[n][n];
        for (int i = 0; i < n; i++) 
            System.arraycopy(y[i], 0, x[i], 0, n);
        return x;
    }

    private Board move(int srcRow, int srcCol, int desRow, int desCol) {
        int[][] another = copyBlocks(blocks);
        int tmp = another[srcRow][srcCol];
        another[srcRow][srcCol] = another[desRow][desCol];
        another[desRow][desCol] = tmp;
        return new Board(another);
    }
    
    private int manhattan(int i, int j) {
        int num = blocks[i][j];
        if (num == 0) return 0;
        //int row = (num - 1) / dim;
        //int col = (num - 1) % dim;
        return Math.abs((num - 1) / dim - i) 
                + Math.abs((num - 1) % dim - j);
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board b = new Board(blocks);
        System.out.println(b.manhattan());
    }
}
