package mytree;

// AVL Tree = Height-Balanced (HB) Tree

public class DAA2 extends DAA1 {

	// 4. isHeightBalanced() [10 points]
	public static boolean isHeightBalanced(MyTree t) {
		// Write your codes in here
		if (t.getEmpty()) return true;

		int leftHeight = getHeight(t.getLeft());
		int rightHeight = getHeight(t.getRight());

		if (Math.abs(leftHeight - rightHeight) > 1) return false;

		return isHeightBalanced(t.getLeft()) && isHeightBalanced(t.getRight());
	}

	private static int getHeight(MyTree t) {
		if (t.getEmpty()) return 0;
		return 1 + Math.max(getHeight(t.getLeft()), getHeight(t.getRight()));
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

		int leftHeight = getHeight(t.getLeft());
		int rightHeight = getHeight(t.getRight());

		if (leftHeight - rightHeight == 2) {
			MyTree left = t.getLeft();
			int ll = getHeight(left.getLeft());
			int lr = getHeight(left.getRight());

			if (ll >= lr) {
				MyTree newRoot = left;
				MyTree moved = newRoot.getRight();
				t = new MyTree(newRoot.getValue(),
						newRoot.getLeft(),
						new MyTree(t.getValue(), moved, t.getRight()));
			} else {
				MyTree child = left.getRight();
				MyTree a = left.getLeft();
				MyTree b = child.getLeft();
				MyTree c = child.getRight();
				MyTree d = t.getRight();
				t = new MyTree(child.getValue(),
						new MyTree(left.getValue(), a, b),
						new MyTree(t.getValue(), c, d));
			}
		}
		return t;
	}

	// 7. rebalanceForRight() [15 points]
	private static MyTree rebalanceForRight(MyTree t) {
		// Write your codes in here
		if (t.getEmpty()) return t;

		int leftHeight = getHeight(t.getLeft());
		int rightHeight = getHeight(t.getRight());

		if (rightHeight - leftHeight == 2) {
			MyTree right = t.getRight();
			int rr = getHeight(right.getRight());
			int rl = getHeight(right.getLeft());

			if (rr >= rl) {
				MyTree newRoot = right;
				MyTree moved = newRoot.getLeft();
				t = new MyTree(newRoot.getValue(),
						new MyTree(t.getValue(), t.getLeft(), moved),
						newRoot.getRight());
			} else {
				MyTree child = right.getLeft();
				MyTree a = t.getLeft();
				MyTree b = child.getLeft();
				MyTree c = child.getRight();
				MyTree d = right.getRight();
				t = new MyTree(child.getValue(),
						new MyTree(t.getValue(), a, b),
						new MyTree(right.getValue(), c, d));
			}
		}
		return t;
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
			if (t.getLeft().getEmpty() && t.getRight().getEmpty()) {
				return new MyTree();
			} else if (t.getLeft().getEmpty()) {
				return t.getRight();
			} else if (t.getRight().getEmpty()) {
				return t.getLeft();
			} else {
				int successor = findMin(t.getRight());
				t = new MyTree(successor, t.getLeft(), deleteHB(t.getRight(), successor));
				t = rebalanceForLeft(t);
			}
		}
		return t;
	}

	private static int findMin(MyTree t) {
		if (t.getLeft().getEmpty()) return t.getValue();
		return findMin(t.getLeft());
	}
}
