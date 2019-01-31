// Java Program to illustrate reading from Text File
// using Scanner Class
public static void main(String[] args)
    throws Exception {

    HashMap<Integer, Integer> countOfFrequencies = new HashMap<>();
    boolean found = false;
    int valueOfFrequency = 0;

    while (!found) {
	File file = new File("/Users/ludim.castillo/Downloads/input.txt");
	Scanner sc = new Scanner(file);

	while (sc.hasNextLine()) {
	    int currFrequency = valueOfFrequency;

	    if (countOfFrequencies.containsKey(valueOfFrequency)) {
		countOfFrequencies.put(valueOfFrequency, countOfFrequencies.get(valueOfFrequency) + 1);
	    }
	    else {
		countOfFrequencies.put(valueOfFrequency, 1);
	    }

	    if (countOfFrequencies.get(valueOfFrequency) == 2) {
		found = true;
		break;
	    }
	    String frequencyStr = sc.nextLine();
	    valueOfFrequency += Integer.valueOf(frequencyStr);
	    System.out.println("Current frequency " + currFrequency + ", change of " + frequencyStr
			       + "; resulting frequency " + valueOfFrequency);
	}
    }

    System.out.println();
    System.out.println("FOUND DUPLICATE FREQUENCY => " + valueOfFrequency);
}