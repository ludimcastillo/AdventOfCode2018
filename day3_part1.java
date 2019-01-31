private static String claimID;
private static String x;
private static String y;
private static String width;
private static String height;

private static int maxWidth = 0;
private static int maxHeight = 0;

private static int maxX = 0;
private static int maxY = 0;

private static String[][] fabric = new String[1000][1000];

// Java Program to illustrate reading from Text File
// using Scanner Class
public static void main(String[] args)
    throws Exception {
    File file = new File("/Users/ludim.castillo/Downloads/input.txt");
    Scanner sc = new Scanner(file);

    initFabric();

    while (sc.hasNextLine()) {
	String claim = sc.nextLine();

	parseClaim(claim);
	draw(claimID, x, y, width, height);

    }
    System.out.println("maxWidth: " + getMaxWidth() + " - maxHeight: " + getMaxHeight());
    System.out.println("maxX: " + getMaxX() + " - maxY: " + getMaxY());

    printFabric();

    System.out.println("Inches of fabric within two or more claims ==> " + getSqrFtClaimed());
}

private static void parseClaim(String claim) {
    claim = claim.replaceAll("\\s+", "");
    claimID = claim.substring(1, claim.indexOf("@"));
    x = claim.substring(claim.indexOf("@") + 1, claim.indexOf(","));
    y = claim.substring(claim.indexOf(",") + 1, claim.indexOf(":"));
    width = claim.substring(claim.indexOf(":") + 1, claim.indexOf("x"));
    height = claim.substring(claim.indexOf("x") + 1);

    maxX = Math.max(Integer.valueOf(x), maxX);
    maxY = Math.max(Integer.valueOf(y), maxY);

    maxWidth = Math.max(Integer.valueOf(width), maxWidth);
    maxHeight = Math.max(Integer.valueOf(width), maxHeight);

    System.out.printf("claimID: %s, x: %s, y: %s, width: %s, height: %s%n", claimID, x, y, width, height);
}

static void draw(String claimID, String X, String Y, String width, String height) {
    for (int x = Integer.valueOf(X); x < Integer.valueOf(X) + Integer.valueOf(width); x++) {
	for (int y = Integer.valueOf(Y); y < Integer.valueOf(Y) + Integer.valueOf(height); y++) {
	    if (!fabric[x][y].equals(".")) {
		fabric[x][y] = "X";
	    }
	    else {
		fabric[x][y] = claimID;
	    }
	}
    }
}

static void initFabric() {
    for (int i = 0; i < 1000; i++) {
	for (int j = 0; j < 1000; j++) {
	    fabric[i][j] = ".";
	}
    }
}

static void printFabric() {
    for (int i = 0; i < 1000; i++) {
	for (int j = 0; j < 1000; j++) {
	    System.out.print(fabric[j][i]);
	}
	System.out.println();
    }
}

static int getSqrFtClaimed() {
    int sqrFt = 0;
    for (int i = 0; i < 1000; i++) {
	for (int j = 0; j < 1000; j++) {
	    if (fabric[i][j].equals("X")) {
		sqrFt++;
	    }
	}
    }
    return sqrFt;
}

private static int getMaxWidth() {
    return maxWidth;
}

private static int getMaxHeight() {
    return maxHeight;
}

private static int getMaxX() {
    return maxX;
}

private static int getMaxY() {
    return maxY;
}
