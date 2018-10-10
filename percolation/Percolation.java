/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private final int m;
    private final int top;
    private int numOS;
    private final WeightedQuickUnionUF wuf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        m = n;
        top = 0;
        numOS = 0;
        grid = new boolean[n][n];
        wuf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        for (int i = 1; i <= n; i++) {
            wuf.union(top, i);
        }
    }

    // map from 2D to 1D indices
    private int mapto(int row, int col) {
        return (m * (row - 1) + col);
    }

    // check arguments
    private void checkargu(int row, int col) {
        if (row < 1 || row > m || col < 1 || col > m) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkargu(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOS += 1;
            if ((row - 1) > 0 && isOpen(row - 1, col)) {
                wuf.union(mapto(row, col), mapto(row - 1, col));
            }
            if ((row + 1) <= m && isOpen(row + 1, col)) {
                wuf.union(mapto(row, col), mapto(row + 1, col));
            }
            if ((col - 1) > 0 && isOpen(row, col - 1)) {
                wuf.union(mapto(row, col), mapto(row, col - 1));
            }
            if ((col + 1) <= m && isOpen(row, col + 1)) {
                wuf.union(mapto(row, col), mapto(row, col + 1));
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkargu(row, col);
        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkargu(row, col);
        if (row == 1) return isOpen(row, col);
        else return wuf.connected(top, mapto(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOS;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= m; i++) {
            if (isFull(m, i)) return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation water = new Percolation(2);

        while (!water.percolates()) {
            int r = StdRandom.uniform(2) + 1;
            int c = StdRandom.uniform(2) + 1;
            if (!water.isOpen(r, c)) {
                water.open(r, c);
            }
        }

        int numofO = water.numberOfOpenSites();
        System.out.println(numofO);
    }

}
