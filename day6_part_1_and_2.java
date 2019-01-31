package com.workday.transactions.rest.runtime;

import com.workday.utils.Tuple;
import it.unimi.dsi.fastutil.Hash;

import java.io.File;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TODO: add description
 *
 * @author ludim.castillo
 * @since Dec-2018
 */
public class Main {

    private static int[][] coordinates = new int[10][10];
    private static ArrayList<Point> points = new ArrayList<>();

    private static Map<Integer, Integer> counts = new HashMap<>();
    static final int GRID_SIZE = 1000;

    // Java Program to illustrate reading from Text File
    // using Scanner Class
    public static void main(String[] args)
	throws Exception {
        File file = new File("/Users/ludim.castillo/Downloads/input.txt");
        Scanner sc = new Scanner(file);

        int id = 0, x, y;
        while (sc.hasNextLine()) {
            String coordinate = sc.nextLine();
            coordinate = coordinate.replaceFirst("\\s+", "");
            String[] num = coordinate.split(",");
            x = Integer.valueOf(num[0]);
            y = Integer.valueOf(num[1]);
            System.out.println(x + " - " + y);

            points.add(new Point(id, x, y));
            id++;
        }

        initPlot();
        plot(points);
        print();
        part1();
        part2();
    }

    private static void plot(ArrayList<Point> points) {
        for (Point p : points) {
            for (int col = 0; col < 10; col++) {
                for (int row = 0; row < 10; row++) {
                    if (p.getX() == col && p.getY() == row) {
                        coordinates[col][row] = p.getId();
                    }
                }
            }
        }
    }

    private static void initPlot() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 10; row++) {
                coordinates[col][row] = -1;
            }
        }
    }

    private static void print() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 10; row++) {
                System.out.printf("%2d", coordinates[row][col]);
            }
            System.out.println();
        }
    }

    private static void part1() {
        Set<Integer> infinites = new HashSet<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int dist = getClosest(j, i);
                counts.merge(dist, 1, (x, y) -> x + y);
                //System.out.println(counts.get(j));

                //add inifinite to set
                if (i == 0 || i == GRID_SIZE - 1 || j == 0 || j == GRID_SIZE - 1) {
                    infinites.add(dist);
                }
            }
        }
        System.out.println("Part 1 => " + IntStream.range(0, points.size()).boxed().filter(x -> !infinites.contains(x)).map(
															    counts::get).mapToInt(x -> x).max().getAsInt());
    }

    private static void part2() {
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Point current = new Point(j, i);
                int sum = 0;
                for (Point each : points) {
                    sum += computeManhattanDistance(current, each);
                }
                if (sum < 10000) count++;
            }
        }
        System.out.println("Part 2 => " + count);
    }

    private static int getClosest(int x, int y) {
        Point point = new Point(x, y);
        Point closest = null;
        int minIndex = 0;
        int minDistance = 9999;
        for (int i = 0; i < points.size(); i++) {
            int dist = computeManhattanDistance(point, points.get(i));
            if (dist < minDistance) {
                minDistance = dist;
                minIndex = i;
                closest = points.get(i);
            }
            else {
                if (dist == minDistance) {
                    minDistance = dist;
                    minIndex = -1;
                }
            }
        }
        return minIndex;
    }

    private static int computeManhattanDistance(Point a, Point b) {
        int w = Math.abs(a.x - b.x);
        int h = Math.abs(a.y - b.y);
        return w + h;

    }

    static class Point {
        int id;
        int x;
        int y;

        Point(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getId() {
            return this.id;
        }

        int getX() {
            return this.x;
        }

        int getY() {
            return this.y;
        }
    }

}
