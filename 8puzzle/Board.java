/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board {

    private int dim;
    private int[][] board;
    private int zeroPosition;
    int[] neighber;
    int moves;
    Board pre;

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.board = blocks;
        this.dim = this.board.length;
        this.moves = 0;
        this.pre = null;

        int[] neiCandi = new int[4];
        int n = 0;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.board[i][j] == 0) {
                    this.zeroPosition = i * this.dim + j + 1;
                    if (i > 0) {
                        neiCandi[0] = this.zeroPosition - this.dim;
                        n += 1;
                    }
                    if (i < this.dim - 1) {
                        neiCandi[1] = this.zeroPosition + this.dim;
                        n += 1;
                    }
                    if (j > 0) {
                        neiCandi[2] = this.zeroPosition - 1;
                        n += 1;
                    }
                    if (j < this.dim - 1) {
                        neiCandi[3] = this.zeroPosition + 1;
                        n += 1;
                    }
                }
            }
        }

        this.neighber = new int[n];
        int i = 0;
        for (int nei : neiCandi) {
            if (nei != 0) {
                this.neighber[i] = nei;
                i += 1;
            }
        }
    }

    // board dimension n
    public int dimension() {
        return this.dim;
    }

    // number of blocks out of place
    public int hamming() {
        int num = 0;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (i * this.dim + j + 1 != this.board[i][j]) {
                    num += 1;
                }
            }
        }
        return num - 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distances = 0;
        int distance;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.board[i][j] == 0) {
                    continue;
                }
                if (i * this.dim + j + 1 != this.board[i][j]) {
                    distance = Math.abs(i * this.dim + j + 1 - this.board[i][j]);
                    distance = distance / this.dim + distance % this.dim;
                    distances += distance;
                }
            }
        }
        return distances;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] tb = new int[this.dim][this.dim];
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                tb[i][j] = this.board[i][j];
            }
        }
        int tmp;
        if (tb[0][0] != 0) {
            tmp = tb[0][0];
            if (tb[0][1] != 0) {
                tb[0][0] = tb[0][1];
                tb[0][1] = tmp;
            }
            else {
                tb[0][0] = tb[1][0];
                tb[1][0] = tmp;
            }
        }
        else {
            tmp = tb[0][1];
            tb[0][1] = tb[1][0];
            tb[1][0] = tmp;
        }
        Board twinBoard = new Board(tb);
        return twinBoard;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass() || this.dim != ((Board) y).dim) {
            return false;
        }
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (((Board) y).board[i][j] != this.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        return new Iterable<Board>() {

            public Iterator<Board> iterator() {

                return new Iterator<Board>() {

                    private int n = neighber.length;
                    private int i = 0;

                    public boolean hasNext() {
                        return (n > 0);
                    }

                    public Board next() {
                        if (n > 0) {
                            int[][] nei = new int[dim][dim];
                            for (int i = 0; i < dim; i++) {
                                for (int j = 0; j < dim; j++) {
                                    nei[i][j] = board[i][j];
                                }
                            }
                            int tmp = nei[(neighber[i] - 1) / dim][(neighber[i] - 1) % dim];
                            nei[(zeroPosition - 1) / dim][(zeroPosition - 1) % dim] = tmp;
                            nei[(neighber[i] - 1) / dim][(neighber[i] - 1) % dim] = 0;
                            Board nextNei = new Board(nei);
                            nextNei.moves = moves + 1;
                            nextNei.pre = new Board(board);
                            i += 1;
                            n -= 1;
                            return nextNei;
                        }
                        return null;
                    }

                    public void remove() {
                        throw new java.lang.UnsupportedOperationException();
                    }
                };
            }
        };
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String result = String.valueOf(this.dim) + '\n';
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.board[i][j] < 10) {
                    result = result + ' ' + String.valueOf(this.board[i][j]) + ' ';
                }
                else {
                    result = result + String.valueOf(this.board[i][j]) + ' ';
                }
            }
            result += '\n';
        }
        return result;
    }


    public static void main(String[] args) {

        int[][] test = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                test[i][j] = i * 3 + j + 1;
            }
        }
        test[2][2] = 8;
        test[2][1] = 0;
        Board testBoard = new Board(test);
        for (Board nei : testBoard.neighbors()) {
            StdOut.println(nei);
        }

    }
}
