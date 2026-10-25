class Solution {
    public Node connect(Node root) {
        Node current = root;

        while (current != null) {
            Node dummy = new Node(0);
            Node tail = dummy;

            while (current != null) {
                if (current.left != null) {
                    tail.next = current.left;
                    tail = tail.next;
                }

                if (current.right != null) {
                    tail.next = current.right;
                    tail = tail.next;
                }

                current = current.next;
            }

            current = dummy.next;
        }

        return root;
    }
}