import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by bozhidar on 30.11.16.
 */
public class FullTernarySearch {
    private int SIZE;
    private int RANDOM_RANGE;
    private ArrayList<Integer> numbers;
    private int key;
    private int high;
    private int low;
    private Random rand = new Random();

    public FullTernarySearch(int size, int randomRange) {
        this.SIZE = size;
        this.RANDOM_RANGE = randomRange;
        numbers = new ArrayList<>();
        for (int j = 0; j < SIZE; j++) {
            numbers.add(rand.nextInt(RANDOM_RANGE) + 1);
        }
        Collections.sort(numbers);
        key = rand.nextInt(RANDOM_RANGE) + 1;
        low = 0;
        high = this.SIZE - 1;
    }

    public long checkClose() {

        int diffFirst, diffMiddle, diffLast, averageValue;
        long startTime = System.nanoTime();
        while (high >= low) {
            //get the closest divider
            averageValue = (numbers.get(low) + numbers.get(high)) / 2;
            diffFirst = Math.abs(key - numbers.get(low));
            diffMiddle = Math.abs(key - averageValue);
            diffLast = Math.abs(key - numbers.get(high));
            //execute the appropriate method
            if (diffFirst < diffLast && diffFirst < diffMiddle) frontTernarySearch();
            else if (diffMiddle <= diffFirst && diffMiddle <= diffLast) middleTernarySearch();
            else backTernarySearch();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    public void frontTernarySearch() {
        int first = ((high - low) / 3) + low;
        int second = 2 * ((high - low) / 3) + low;
        int fNum = numbers.get(first);
        int sNum = numbers.get(second);
        if (fNum == key) { //equal to first divider
            high = low - 1;
        } else if (fNum > key) { //in the first third
            high = first - 1;
        } else if (sNum > key) { //in the second third
            high = second - 1;
            low = first + 1;
        } else if (sNum == key) { //equal to second divider
            high = low - 1;
        } else { //in the last third
            low = second + 1;
        }
    }

    public void middleTernarySearch() {
        int first = ((high - low) / 3) + low;
        int second = 2 * ((high - low) / 3) + low;
        int fNum = numbers.get(first);
        int sNum = numbers.get(second);
        if (fNum == key) { //equal to dividers
            high = low - 1;
        } else if (sNum == key) {
            high = low - 1;
        } else if (fNum < key && key < sNum) { //in the middle
            low = first + 1;
            high = second - 1;
        } else if (fNum > key) { //in the first third
            high = first - 1;
        } else { //in the third third
            low = second + 1;
        }
    }

    public void backTernarySearch() {
        int first = ((high - low) / 3) + low;
        int second = 2 * ((high - low) / 3) + low;
        int fNum = numbers.get(first);
        int sNum = numbers.get(second);

        if (sNum == key) { //equal to second divider
            high = low - 1;
        } else if (key > sNum) { //in the last third
            low = second + 1;
        } else if (fNum < key) { //in the second third
            low = first + 1;
            high = second - 1;
        } else if (fNum == key) { //equal to first divider
            high = low - 1;
        } else { //in the first third
            high = first - 1;
        }
    }

    public long binarySearch() {
        int low = 0;
        int high = numbers.size() - 1;

        int diffFirst, diffLast;

        long startTime = System.nanoTime();
        while (high >= low) {
            diffFirst = Math.abs(key - numbers.get(low));
            diffLast = Math.abs(key - numbers.get(high));
            int middle = (low + high) / 2;
            if (diffFirst < diffLast) {
                if (numbers.get(middle) == key) {
                    high = low - 1;
                } else if (numbers.get(middle) > key) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }

            } else {

                if (numbers.get(middle) == key) {
                    high = low - 1;
                } else if (numbers.get(middle) < key) {
                    low = middle + 1;
                } else {
                    high = middle - 1;
                }
            }
        }
        long endTime = System.nanoTime();
        return (endTime - startTime);

    }

    public void generateKey() {
        key = rand.nextInt(RANDOM_RANGE) + 1;
    }
}
