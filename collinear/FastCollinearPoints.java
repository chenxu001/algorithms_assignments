/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private int numOfS;
    private LineSegment[] seg;
    private Point[] endOfS;
    private double[] slopeOfS;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        this.points = points;
        if (this.points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        int n = points.length;
        this.numOfS = 0;
        this.seg = new LineSegment[n];
        this.endOfS = new Point[n];
        this.slopeOfS = new double[n];

        for (int i = 0; i < n; i++) {
            if (this.points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            Point[] orderPoints = new Point[n];
            for (int t = 0; t < n; t++) {
                orderPoints[t] = this.points[t];
            }

            for (int j = i + 1; j < n; j++) {
                if (orderPoints[i].compareTo(orderPoints[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
            Arrays.sort(orderPoints, orderPoints[i].slopeOrder());
            Point startEnd;
            Point stopEnd;
            int startOfC = 1;
            while (startOfC < n) {
                if (orderPoints[0].compareTo(orderPoints[startOfC]) == -1) {
                    startEnd = orderPoints[0];
                    stopEnd = orderPoints[startOfC];
                }
                else {
                    stopEnd = orderPoints[0];
                    startEnd = orderPoints[startOfC];
                }

                int q = 1;

                while (startOfC + q < n && orderPoints[0].slopeOrder()
                                                         .compare(orderPoints[startOfC],
                                                                  orderPoints[startOfC + q]) == 0) {
                    if (orderPoints[startOfC + q].compareTo(stopEnd) == 1) {
                        stopEnd = orderPoints[startOfC + q];
                    }
                    if (orderPoints[startOfC + q].compareTo(startEnd) == -1) {
                        startEnd = orderPoints[startOfC + q];
                    }
                    q += 1;
                }

                if (q >= 3) {
                    LineSegment oneSegment = new LineSegment(startEnd, stopEnd);
                    if (this.numOfS == 0) {
                        this.seg[this.numOfS] = oneSegment;
                        this.endOfS[this.numOfS] = startEnd;
                        this.slopeOfS[this.numOfS] = startEnd.slopeTo(stopEnd);
                        this.numOfS += 1;
                    }
                    else {
                        int notEqual = 0;
                        for (int p = 0; p < this.numOfS; p++) {
                            if ((Math.abs(slopeOfS[p] - startEnd.slopeTo(stopEnd)) < 0.000001
                                    || slopeOfS[p] == startEnd.slopeTo(stopEnd))
                                    && (
                                    Math.abs(slopeOfS[p] - endOfS[p].slopeTo(stopEnd)) < 0.000001
                                            || slopeOfS[p] == endOfS[p].slopeTo(stopEnd))) {
                                break;
                            }
                            else {
                                notEqual += 1;
                            }
                        }
                        if (notEqual == this.numOfS) {
                            this.seg[this.numOfS] = oneSegment;
                            this.endOfS[this.numOfS] = startEnd;
                            this.slopeOfS[this.numOfS] = startEnd.slopeTo(stopEnd);
                            this.numOfS += 1;
                        }
                    }
                }
                startOfC += q;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numOfS;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] finalSeg = new LineSegment[this.numOfS];
        for (int i = 0; i < this.numOfS; i++) {
            finalSeg[i] = this.seg[i];
        }
        return finalSeg;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
