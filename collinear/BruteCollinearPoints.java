/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private Point[] points;
    private int numOfS;
    private LineSegment[] seg;
    private Point[] endOfS;
    private double[] slopeOfS;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        this.points = points;
        if (this.points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.numOfS = 0;
        int n = points.length;
        this.seg = new LineSegment[n];
        this.endOfS = new Point[n];
        this.slopeOfS = new double[n];

        for (int i = 0; i < n; i++) {
            if (this.points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                if (this.points[i].compareTo(this.points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }

                int conum = 0;
                Point oneEnd = this.points[i];
                Point otherEnd = this.points[j];
                if (oneEnd.compareTo(otherEnd) == 1) {
                    oneEnd = this.points[j];
                    otherEnd = this.points[i];
                }

                for (int k = j + 1; k < n; k++) {
                    if ((Math.abs(this.points[i].slopeTo(this.points[j]) - this.points[i]
                            .slopeTo(this.points[k])) < 0.000001) || (
                            this.points[i].slopeTo(this.points[j]) == this.points[i]
                                    .slopeTo(this.points[k]))) {
                        conum += 1;
                        if (otherEnd.compareTo(this.points[k]) == -1) {
                            otherEnd = this.points[k];
                        }
                        if (oneEnd.compareTo(this.points[k]) == 1) {
                            oneEnd = this.points[k];
                        }
                    }
                }
                if (conum >= 2) {
                    LineSegment oneSegment = new LineSegment(oneEnd, otherEnd);

                    if (this.numOfS == 0) {
                        this.seg[this.numOfS] = oneSegment;
                        this.endOfS[this.numOfS] = oneEnd;
                        this.slopeOfS[this.numOfS] = oneEnd.slopeTo(otherEnd);
                        this.numOfS += 1;
                    }
                    else {
                        int notEqual = 0;
                        for (int p = 0; p < this.numOfS; p++) {
                            if ((Math.abs(slopeOfS[p] - oneEnd.slopeTo(otherEnd)) < 0.000001
                                    || slopeOfS[p] == oneEnd.slopeTo(otherEnd))
                                    && (
                                    Math.abs(slopeOfS[p] - endOfS[p].slopeTo(otherEnd)) < 0.000001
                                            || slopeOfS[p] == endOfS[p].slopeTo(otherEnd))) {
                                break;
                            }
                            else {
                                notEqual += 1;
                            }
                        }
                        if (notEqual == this.numOfS) {
                            this.seg[this.numOfS] = oneSegment;
                            this.endOfS[this.numOfS] = oneEnd;
                            this.slopeOfS[this.numOfS] = oneEnd.slopeTo(otherEnd);
                            this.numOfS += 1;
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }

}
