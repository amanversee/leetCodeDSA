import java.util.*;

class Solution {
    private int preorderIndex = 0;
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        inorderMap.clear();

        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return build(preorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        int rootValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootValue);

        int rootIndex = inorderMap.get(rootValue);

        root.left = build(preorder, left, rootIndex - 1);
        root.right = build(preorder, rootIndex + 1, right);

        return root;
    }
}