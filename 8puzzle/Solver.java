/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

    private MinPQ<Board> pq;
    private MinPQ<Board> pqTwin;
    private Stack<Board> rodeR;
    private Stack<Board> rode;
    private int moveSteps;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        pq = new MinPQ<Board>(PriorityOrder());
        pqTwin = new MinPQ<Board>(PriorityOrder());
        rodeR = new Stack<Board>();
        rode = new Stack<Board>();
        moveSteps = -1;

        Board iniTwin = initial.twin();
        Board searchNode = initial;
        Board searchTwin = iniTwin;
        rodeR.push(searchNode);

        while (!searchNode.isGoal() && !searchTwin.isGoal()) {
            for (Board nei : searchNode.neighbors()) {
                if (!nei.equals(searchNode.pre)) {
                    pq.insert(nei);
                }
            }
            searchNode = pq.delMin();
            rodeR.push(searchNode);

            for (Board nei : searchTwin.neighbors()) {
                if (!nei.equals(searchTwin.pre)) {
                    pqTwin.insert(nei);
                }
            }
            searchTwin = pqTwin.delMin();
        }

        if (searchNode.isGoal()) {
            moveSteps = searchNode.moves;
            Board step = rodeR.pop();
            rode.push(step);
            while (!rodeR.isEmpty()) {
                step = rodeR.pop();
                if (step.equals(rode.peek().pre)) {
                    rode.push(step);
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (moveSteps >= 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return moveSteps;
        }
        else {
            return -1;
        }

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    public boolean hasNext() {
                        return (!rode.isEmpty());
                    }

                    public Board next() {
                        return rode.pop();
                    }

                    public void remove() {
                        throw new java.lang.UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public Comparator<Board> PriorityOrder() {
        return new Priority();
    }

    private class Priority implements Comparator<Board> {
        public int compare(Board p, Board q) {
            int priP = p.manhattan() + moves();
            int priQ = q.manhattan() + moves();
            return Integer.compare(priP, priQ);
        }

    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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
