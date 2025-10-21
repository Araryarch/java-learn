package mytree;

// AVL Tree = Height-Balanced (HB) Tree

public class DAA2 extends DAA1 {

	// 4. isHeightBalanced() [10 points]
	public static boolean isHeightBalanced(MyTree t) {
		// Write your codes in here
        if (t.getEmpty()) return true;
        int leftH = height(t.getLeft());
        int rightH = height(t.getRight());
        if (Math.abs(leftH - rightH) > 1) return false;
        return isHeightBalanced(t.getLeft()) && isHeightBalanced(t.getRight());
        // Write your codes in here
	}

    // helper untuk tinggi pohon
    private static int height(MyTree t) {
        if (t.getEmpty()) return 0;
        return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
    }

	// 5. insertHB() [10 points]
	public static MyTree insertHB(int n, MyTree t) {
		// Write your codes in here
        if (t.getEmpty()) return new MyTree(n, new MyTree(), new MyTree());
        if (n < t.getValue()) {
            t = new MyTree(t.getValue(), insertHB(n, t.getLeft()), t.getRight());
            t = rebalanceForLeft(t);
        } else if (n > t.getValue()) {
            t = new MyTree(t.getValue(), t.getLeft(), insertHB(n, t.getRight()));
            t = rebalanceForRight(t);
        }
        return t;
        // Write your codes in here
	}

	// rebalanceForLeft is called when the left subtree of t may have
	// grown taller by one notch.
	// If it is indeed taller than the right subtree by two notches,
	// return a height-balanced version of t using single or double rotations.
	// The subtrees of t are assumed to be already height-balanced and
	// no effort is made to rebalance them.
	//
	// Likewise, for the case of the right subtree -> rebalanceForRight
	// Both rebalanceForLeft & rebalanceForRight will be used by insertHB() and deleteHB()
	// 6. rebalanceForLeft() [15 points]
	private static MyTree rebalanceForLeft(MyTree t) {
		// Write your codes in here
        if (t.getEmpty()) return t;
        int lh = height(t.getLeft());
        int rh = height(t.getRight());
        if (lh - rh == 2) {
            MyTree left = t.getLeft();
            if (height(left.getLeft()) >= height(left.getRight())) {
                // single rotation (LL)
                t = rotateRight(t);
            } else {
                // double rotation (LR)
                t = rotateLeftRight(t);
            }
        }
        return t;
        // Write your codes in here
	}
	
	// 7. rebalanceForRight() [15 points]
	private static MyTree rebalanceForRight(MyTree t) {
		// Write your codes in here
        if (t.getEmpty()) return t;
        int lh = height(t.getLeft());
        int rh = height(t.getRight());
        if (rh - lh == 2) {
            MyTree right = t.getRight();
            if (height(right.getRight()) >= height(right.getLeft())) {
                // single rotation (RR)
                t = rotateLeft(t);
            } else {
                // double rotation (RL)
                t = rotateRightLeft(t);
            }
        }
        return t;
        // Write your codes in here
	}
	
	// 8. deleteHB() [10 points]
	/**
	 * Deletes the value 'x' from the given tree, if it exists, and returns the new
	 * Tree.
	 *
	 * Otherwise, the original tree will be returned.
	 */
	public static MyTree deleteHB(MyTree t, int x) {
		// Write your codes in here
        if (t.getEmpty()) return t;

        int val = t.getValue();
        if (x < val) {
            t = new MyTree(val, deleteHB(t.getLeft(), x), t.getRight());
            t = rebalanceForRight(t);
        } else if (x > val) {
            t = new MyTree(val, t.getLeft(), deleteHB(t.getRight(), x));
            t = rebalanceForLeft(t);
        } else {
            // node found
            if (t.getLeft().getEmpty() && t.getRight().getEmpty()) {
                return new MyTree();
            } else if (t.getLeft().getEmpty()) {
                return t.getRight();
            } else if (t.getRight().getEmpty()) {
                return t.getLeft();
            } else {
                int successor = minValue(t.getRight());
                t = new MyTree(successor, t.getLeft(), deleteHB(t.getRight(), successor));
                t = rebalanceForLeft(t);
            }
        }
        return t;
        // Write your codes in here
	}

    // helper rotation functions
    private static MyTree rotateRight(MyTree t) {
        MyTree newRoot = t.getLeft();
        MyTree movedSub = newRoot.getRight();
        return new MyTree(newRoot.getValue(), newRoot.getLeft(),
                new MyTree(t.getValue(), movedSub, t.getRight()));
    }

    private static MyTree rotateLeft(MyTree t) {
        MyTree newRoot = t.getRight();
        MyTree movedSub = newRoot.getLeft();
        return new MyTree(newRoot.getValue(),
                new MyTree(t.getValue(), t.getLeft(), movedSub), newRoot.getRight());
    }

    private static MyTree rotateLeftRight(MyTree t) {
        return rotateRight(new MyTree(t.getValue(),
                rotateLeft(t.getLeft()), t.getRight()));
    }

    private static MyTree rotateRightLeft(MyTree t) {
        return rotateLeft(new MyTree(t.getValue(),
                t.getLeft(), rotateRight(t.getRight())));
    }

    private static int minValue(MyTree t) {
        MyTree current = t;
        while (!current.getLeft().getEmpty()) {
            current = current.getLeft();
        }
        return current.getValue();
    }
}
