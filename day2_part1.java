// Java Program to illustrate reading from Text File
// using Scanner Class
public static void main(String[] args)
    throws Exception {
    File file = new File("/Users/ludim.castillo/Downloads/input.txt");
    Scanner sc = new Scanner(file);

    int countOfExactly2 = 0;
    int countOfExactly3 = 0;
    while (sc.hasNextLine()) {
	String frequencyStr = sc.nextLine();
	System.out.println(frequencyStr);

	HashMap<Character, Integer> boxIdChars = new HashMap<>();
	for (int i = 0; i < frequencyStr.length(); i++) {
	    if (boxIdChars.containsKey(frequencyStr.charAt(i))) {
		boxIdChars.put(frequencyStr.charAt(i), boxIdChars.get(frequencyStr.charAt(i)) + 1);
	    }
	    else {
		boxIdChars.put(frequencyStr.charAt(i), 1);
	    }
	}

	if (boxIdChars.containsValue(2)) {
	    countOfExactly2++;
	}
	if (boxIdChars.containsValue(3)) {
	    countOfExactly3++;
	}
    }
    System.out.println("CHECKSUM => " + countOfExactly2 * countOfExactly3);
}