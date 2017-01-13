/**
 * Class implementing comparison between SKipList and Java's inbuilt TreeMap
 *
 * @author Rammurthy Mudimadugula
 * @version 1.0
 * 
 */

import java.util.Random;
import java.util.TreeMap;

public class Comparison {

	/**
     * Method to generate a uniformly random value between min and max integers.
     *
     * @param  min Minimum value to generate random number.
     * @param  max Maximum value to generate random number.
     * @return int random number between min and max integers.
     */
    private static int randomNumberGenerator(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max-min) + min;
        return randomNum;
    }

	public static void main (String[] args) {
		if (args.length == 2) {
			int n = Integer.parseInt(args[1])*1000000;
			if (args[0].equals("1")) {
				TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
		
				Timer t1 = new Timer();
				t1.start();

				for (int i = 1; i <= n; i++) {
					tmap.put(i, randomNumberGenerator(1, n*10));
				}

				t1.end();
				System.out.println("Time taken to insert " + args[1] + " million key, value pairs in TreeMap: " + t1);

				System.out.println();

				Timer t2 = new Timer();
				t2.start();

				tmap.containsValue(randomNumberGenerator(1, n*10));

				t2.end();
				System.out.println("Time taken to find a value in " + args[1] + " million key, value pairs in TreeMap: " + t2);

				System.out.println();

				Timer t3 = new Timer();
				t3.start();

				tmap.remove(randomNumberGenerator(1, n*10));

				t3.end();
				System.out.println("Time taken to remove a mapping in " + args[1] + " million key, value pairs in TreeMap: " + t3);

			} else if (args[0].equals("2")) {
				SkipListImpl<Integer> sl = new SkipListImpl<>();

				Timer t10 = new Timer();
				t10.start();

				for (int i = 1; i <= n; i++) {
					sl.add(randomNumberGenerator(1, n*10));
				}

				t10.end();
				System.out.println("Time taken to insert " + args[1] + " mil elements in SkipList: " + t10);

				System.out.println();

				Timer t11 = new Timer();
				t11.start();

				sl.contains(randomNumberGenerator(1, n*10));

				t11.end();
				System.out.println("Time taken to find a value in " + args[1] + " mil elements in SkipList: " + t11);

				System.out.println();

				Timer t12 = new Timer();
				t12.start();

				sl.remove(randomNumberGenerator(1, n*10));

				t12.end();
				System.out.println("Time taken to remove a value in " + args[1] + " mil elements in SkipList: " + t12);

			} else {
				System.out.println("Wrong command line argument input");
			}
		} else {
			System.out.println("USAGE: java Comparison <1 or 2> <1 or 5 or 10>");
			System.out.println("Example: java Comparison 1 1");
			System.out.println("1 = TreeMap (or 2 = SkipList)");
			System.out.println("1 (or 5 or 10) million entries");
		}
	}
}
