/**
 * Class implementing a generic node for SkipList data structure.
 *
 * @author Rammurthy Mudimadugula
 * @version 1.0
 * 
 */

public class Node<T> {
    T data;
    int gap;
    int level;
    Node<T>[] next;

    public Node(T x, int lev) {
        this.data  = x;
        this.level = lev;
        this.next  = new Node[lev];
    }
}
