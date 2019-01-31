// Java Program to illustrate reading from Text File
// using Scanner Class
public static void main(String[] args)
    throws Exception {
    File file = new File("/Users/ludim.castillo/Downloads/input.txt");
    Scanner sc = new Scanner(file);

    ArrayList<String> boxIds = new ArrayList<>();
    while (sc.hasNextLine()) {
	String boxId = sc.nextLine();
	boxIds.add(boxId);
    }

    for(int i = 0; i < boxIds.size(); i++) {
	for(int j = i+1; j < boxIds.size(); j++) {
	    if (minDistance(boxIds.get(i), boxIds.get(j)) == 1) {
		System.out.println("Correct Box IDs => " + boxIds.get(i) + "," + boxIds.get(j));
	    }
	}
    }
}

private static int minDistance(String word1, String word2) {
    int[][] matrix = new int[word1.length() + 1][word2.length() + 1];
    for (int i = 0; i <= word1.length(); i++) {
	matrix[i][0] = i;
    }

    for (int j = 0; j <= word2.length(); j++) {
	matrix[0][j] = j;
    }

    for (int i = 1; i <= word1.length(); i++) {
	for (int j = 1; j <= word2.length(); j++) {
	    if (word1.charAt(i-1) == word2.charAt(j-1)) {
		matrix[i][j] = matrix[i-1][j-1];
	    }
	    else {
		matrix[i][j] = Math.min(matrix[i-1][j-1], Math.min(matrix[i-1][j], matrix[i][j-1])) + 1;
	    }
	}
    }
    return matrix[word1.length()][word2.length()];
}