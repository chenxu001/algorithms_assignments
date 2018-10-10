/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CON_95 = 1.96;
    private final int nt;
    private final double[] percoArray;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        double m = n;
        nt = trials;
        percoArray = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perco = new Percolation(n);
            while (!perco.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                if (!perco.isOpen(r, c)) {
                    perco.open(r, c);
                }
            }
            percoArray[i] = perco.numberOfOpenSites() / (m * m);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percoArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percoArray);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (StdStats.mean(percoArray) - CON_95 * StdStats.stddev(percoArray) / Math.sqrt(nt));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (StdStats.mean(percoArray) + CON_95 * StdStats.stddev(percoArray) / Math.sqrt(nt));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats percosta = new PercolationStats(200, 100);
        System.out.println("mean                    = " + percosta.mean());
        System.out.println("stddev                  = " + percosta.stddev());
        System.out.println("95% confidence interval = [" + percosta.confidenceLo() + ", " + percosta
                .confidenceHi() + "]");
    }

}
