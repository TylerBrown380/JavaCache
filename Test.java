import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Test {

	public static void main(String[] args) {
		File file = null;
		try {
			int cacheType = Integer.parseInt(args[0]);
			int cacheSize1 = Integer.parseInt(args[1]);
			if (cacheSize1 <= 0) {
				System.out.println("cache size must be bigger than 0");
				System.out.println(
						"Usage: <cache size> <File name> OR <1st-level cache size> <2nd-level cache size> <File name>");
				System.exit(1);
			}
			if (cacheType == 2) {
				int cacheSize2 = Integer.parseInt(args[2]);
				if (cacheSize1 >= cacheSize2) {
					System.out.println("Cache 1 must be smaller than Cache 2");
					System.out.println(
							"Usage: <cache size> <File name> OR <1st-level cache size> <2nd-level cache size> <File name>");
					System.exit(1);
				}

				String fileName = args[3];
				file = new File(fileName);
				if (!file.exists()) {
					throw new FileNotFoundException();
				}
				Cache<String> twoLevel = new Cache<String>(cacheSize1, cacheSize2);
				System.out.println("First level cache with " + args[1] + " entries has been created");
				System.out.println("Second level cache with " + args[2] + " entries has been created");

				@SuppressWarnings("resource")
				Scanner fileScan = new Scanner(file).useDelimiter("/t/s");
				int count = 0;
				while (fileScan.hasNextLine()) {
					String line = fileScan.nextLine();
					StringTokenizer stk = new StringTokenizer(line);
					while (stk.hasMoreTokens()) {
						String word = stk.nextToken();
						if (count % 7000 == 0) {
							System.out.print(" . ");
						}
						count++;
						if (twoLevel.getObj(word) == null) {
							twoLevel.addObj(word);
						}
					}
				}
				fileScan.close();
				System.out.println("\nThe Number of Global References: " + twoLevel.getCache1Adds());
				System.out.println(
						"The Number of Global Hits: " + (twoLevel.getCache1Matches() + twoLevel.getCache2Matches()));
				System.out.println("The Global Hit Ratio: \t" + twoLevel.getGlobalHitRatio());
				System.out.println("\nThe Number of 1st Level References: " + twoLevel.getCache1Adds());
				System.out.println("The Number of 1st Level Hits: " + twoLevel.getCache1Matches());
				System.out.println("The 1st Level Cache Hit Ratio: \t" + twoLevel.getFirstLevelHitRatio());
				System.out.println("\nThe number of 2nd Level References: " + twoLevel.getCache2Adds());
				System.out.println("The number of 2nd Level Hits: " + twoLevel.getCache2Matches());
				System.out.println("The 2nd Level Cache Hit Ratio: " + twoLevel.getSecondLevelHitRatio());
			} else {
				if (cacheType != 1) {
					throw new Exception();
				}
				String file1Cache = args[2];
				file = new File(file1Cache);
				if (!file.exists()) {
					throw new FileNotFoundException();
				}
				Cache<String> oneLevel = new Cache<String>(cacheSize1);
				System.out.println("Single cache with " + args[1] + " entries has been created");
				@SuppressWarnings("resource")
				Scanner fileScan = new Scanner(file).useDelimiter("/t/s");
				int count = 0;
				while (fileScan.hasNextLine()) {
					String line = fileScan.nextLine();
					StringTokenizer stk = new StringTokenizer(line);
					while (stk.hasMoreTokens()) {
						String word = stk.nextToken();
						if (count % 5000 == 0) {
							System.out.print(" . ");
						}
						count++;
						if (oneLevel.getObj(word) == null) {
							oneLevel.addObj(word);
						}
					}

				}

				fileScan.close();
				System.out.println("\nThe Number of Global References: " + oneLevel.getCache1Adds());
				System.out.println("The Number of Global Hits: " + oneLevel.getCache1Matches());
				System.out.println("The Global Hit Ratio: \t" + oneLevel.getGlobalHitRatio());
			}
		} catch (FileNotFoundException fe) {
			System.out.println("File " + file + " vdoes not exist");
			System.out.println(
					"Usage: <1> <cache size> <File name> OR <2> <1st-level cache size> <2nd-level cache size> <File name>");
			System.exit(1);
		} catch (Exception e) {
			System.out.println(
					"Usage: <1> <cache size> <File name> OR <2> <1st-level cache size> <2nd-level cache size> <File name>");
			System.exit(1);
		}
	}

}