package mytree;

public class DAA1 extends MyTree {

    // Binary Search Tree (BST)
    // 1. isBST() [20 points]
    public static boolean isBST(MyTree t) {
    	// Write your codes in here
        if (t.getEmpty()) return true;
        return isBST(t, Integer.MIN_VALUE, Integer.MAX_VALUE);
        // Write your codes in here
    }

    // Helper function for isBST
    // Get a boolean value to know whether 't' is BST (Binary Search Tree)
    // whose values are within the range between lowerBound and upperBound
    private static boolean isBST(MyTree t, 
					 int lowerBound, int upperBound) {
    	// Write your codes in here
        if (t.getEmpty()) return true;
        int val = t.getValue();
        if (val < lowerBound || val > upperBound) return false;
        return isBST(t.getLeft(), lowerBound, val) &&
               isBST(t.getRight(), val, upperBound);
        // Write your codes in here    
    }

    // 2. printDescending() [10 points]
    public static void printDescending(MyTree t) {
    	// Write your codes in here
        if (t.getEmpty()) return;
        printDescending(t.getRight());
        System.out.println(t.getValue());
        printDescending(t.getLeft());
        // Write your codes in here
    }

    // 3. max() [10 points]   
    /**
     * You have to:
     * - handle empty trees
     * - never look at left
     * - never compares values, i.e., the value of t and the right,
     *   because it's not necessary if it's BST.
     * - returns the right value as soon as found
     *
     * @param t is the tree being searched for the max value
     * @return the max value of tree t
     */
    
    public static int max(MyTree t) {
    	// Write your codes in here
        if (t.getEmpty()) return Integer.MIN_VALUE;
        MyTree current = t;
        while (!current.getRight().getEmpty()) {
            current = current.getRight();
        }
        return current.getValue();
        // Write your codes in here
    }

}
