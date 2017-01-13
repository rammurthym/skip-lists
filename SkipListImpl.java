/**
 * Class implementing methods for SkipList data structure.
 *
 * @author Rammurthy Mudimadugula
 * @version 1.0
 * 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import java.util.ConcurrentModificationException;

public class SkipListImpl<T extends Comparable<? super T>> implements SkipList<T> {

    private int maxLevel = 16;
    private int size;
    private long version;
    private Node<T> head;
    private Node<T> tail;

    /**
     * Class method to initialize a skiplist.
     *
     * @return Nothing.
     */
    public SkipListImpl() {
        size = 0;
        version = 0;

        head = new Node<T>(null, maxLevel);
        tail = new Node<T>(null, maxLevel);

        for (int i = 0; i < maxLevel; i++) {
            head.next[i]=tail;
            head.next[i].gap = 0;
        }

        for(int i = 0; i < maxLevel; i++) {
            tail.next[i]=null;
        }
    }

    /**
     * Method to generate a random level for inserting a node.
     *
     * @return Nothing.
     */
    private int choice () {
        int l = 1;
        Random random = new Random();
        while (l < maxLevel) {
            boolean b = random.nextBoolean();
            if (b) {
                break;
            } else {
                l++;
            }
        }
        return l;
    }

    /**
     * Method to insert an element into SkipList.
     *
     * @param x element to be inserted.
     * @return boolean true/false.
     */
    @Override
    public boolean add(T x) {
        Node<T>[] prev = find (x);
       
        if (compare(prev[0].next[0].data, x) == 0) {
            prev[0].next[0].data = x;
            return false;
        } else {
            ++size;
            int currLvl = (int) (Math.log(size())/Math.log(2));
            if (maxLevel < currLvl) {
                maxLevel = currLvl;
                rebuild();
            }

            int l = choice();
            Node<T> n = new Node<T>(x, l);

            for (int i = 0; i < l; i++) {
                n.next[i] = prev[i].next[i];
                prev[i].next[i] = n;
            }
            version++;
            return true;
        }
    }

    /**
     * Method to compare two generic elements from a SkipList.
     *
     * @param x Generic element already existing in SkipList.
     * @param y Generic element passed by other methods.
     */
    private int compare (T x, T y) {
        if (x == null) {
            return 1;
        } else if (y == null) {
            return -1;
        } else {
            return x.compareTo(y);
        }
    }

    /**
     * Method to find whether an element in a SkipList.
     *
     * @param x Element to be searched for in SkipList.
     * @return array Array of nodes which led to the previous node of x.
     */
    public Node<T>[] find(T x) {
        Node<T> p = head; 
        Node<T>[] prev = new Node[maxLevel];
        for (int i = maxLevel-1; i >= 0; i--) {         
            while (compare(p.next[i].data, x) < 0) {
                p = p.next[i];
            }
            prev[i] = p;          
        }
        return prev;        
    }

    /**
     * Method to find least element that is >= x, or null if no such element exists.
     *
     * @param x Element for which ceiling to be found.
     * @return y Value to be returned if there exists such element or null.
     */
    @Override
    public T ceiling(T x) {
        Node<T>[] prev = find(x);

        if(compare(prev[0].next[0].data, x) == 0) {
            return x;
        } else if (prev[0].next[0].data == null) {
            return null;
        } else {
            return prev[0].next[0].data;
        }
    }

    /**
     * Method which searches for the existence of an element in the SkipList.
     *
     * @param x Element to be searched for.
     * @param boolean If x contains return true else false.
     */
    @Override
    public boolean contains(T x) {
        Node<T>[] prev = find(x);
        return (compare(prev[0].next[0].data, x) == 0) ? true : false;
    }

    /**
     * Method to return an element at the given index passed as an argument.
     *
     * @param n Index value
     * @return value Return value at index value n or null if n > size of SkipList.
     */
    @Override
    public T findIndex(int n) {

        if (n > (size()-1)) {
            return null;
        } else {
            Node<T> p = head;

            for (int i = 0; i <= n; i++) {
                p = p.next[0];
            }

            return p.data;
        }
    }

    /**
     * Method to return the first element in the SkipList.
     *
     * @return value Data in the first element if size > 0 else null.
     */
    @Override
    public T first() {
        if (size() == 0) {
            return null;
        } else {
            return head.next[0].data;
        }
    }

    /**
     * Method to return the greatest element that is <= x, or null if no such element exists.
     *
     * @param x Value for which floor as to be found.
     * @return value If exists, value which is floor of x else null. 
     */
    @Override
    public T floor(T x) {
        if (size() == 0) {
            return null;
        }

        Node<T>[] prev = find(x);

        if(compare(prev[0].next[0].data, x) == 0) {
            return x;
        }
        
        return prev[0].data;
    }

    /**
     * Method to know whether a SkipList is empty or not.
     *
     * @return boolean True idicates list is empty, false indicates otherwise.
     */
    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to return iterator for a SkipList.
     *
     * @return Node Head of the SkipList.
     */
    @Override
    public Iterator<T> iterator() { return new SLIterator<>(head); }

    /**
     * Private class which implements iterator for a SkipList.
     */
    private class SLIterator<E> implements Iterator<E> {
        Node<E> curr;
        Node<E> prev;
        long itVersion;

        /**
         * Method to initialize an iterator.
         */
        SLIterator(Node<E> head) {
            curr = head;
            prev = null;
            itVersion = version;
        }

        /**
         * Method to know whether next elemt exists or not.
         * 
         * @return boolean True indicates, next element exists false indicates otherwise.
         */
        public boolean hasNext() {
            return curr.next[0].data != null;
        }
        
        /**
         * Method to give next element of SkipList and increment the iterator position.
         *
         * @return value Node data at current position.
         */
        public E next() {
            prev = curr;
            curr = curr.next[0];
            return curr.data;
        }

        /**
         * Iterator method to remove an element in SkipList.
         *
         * @return Nothing.
         */
        public void remove() {
            if (itVersion < version) {                
                throw new ConcurrentModificationException();
            } else {
                for (int i = 0; i < maxLevel; i++) {
                    if(prev.next[i] == curr) {
                        prev.next[i] = curr.next[i];
                    } else {
                        break;
                    }
                }
                curr = prev.next[0];
                size--;
                version++;
                itVersion = version;
            }
        }
    }

    /**
     * Method to return last element of a SkipList
     *
     * @return value Node data of last element if size > 0 else null.
     */
    @Override
    public T last() {
        if (size() == 0) {
            return null;
        } else {
            return findIndex(size()-1);
        }
    }

    /**
     * Method to rebuild a SkipList into a perfect SkipList.
     * TO-DO: Just increasing the max level as of now. Have to implement actual rebuild.
     * 
     * @return Nothing.
     */
    @Override
    public void rebuild() {
        Node<T> temp = head;

        head = new Node<T>(null, maxLevel);
        tail = new Node<T>(null, maxLevel);
        
        int oldLevel = temp.level;

        for(int i = 0; i < maxLevel; i++) {
            if (i < oldLevel) {
                head.next[i]=temp.next[i];
            } else {
                head.next[i]=tail;
                tail.next[i] = null;    
            }
        }

    //     int s = size();
    //     Node<T>[] arr = new Node[s];
    //     rebuild(arr, 0, s-1, k);
    //     int mid = (s%2 == 0) ? (s/2)-1 : s/2;
    //     int i = 0;
    //     for (T x: this) {
    //         arr[i].data = x;
    //         if (i == 0) {
    //             head = new Node(null, maxLevel);
    //             tail = new Node(null, maxLevel);
    //             for (int j = 0; j < arr[i].level; j++) {
    //                 head.next[j] = arr[i];
    //             }
    //         } else {
    //             if (arr[i].level <= k) {

    //                 if (arr[i].level > arr[i-1].level) {
    //                     int j;
    //                     for (j = 0; j < arr[i-1].level; j++) {
    //                         arr[i-1].next[j] = arr[i];
    //                     }
                        
    //                     head.next[j] = arr[i];
    //                 } else if (arr[i].level == arr[i-1].level) {
    //                     for (int j = 0; j < arr[i].level; j++) {
    //                         arr[i-1].next[j] = arr[i];
    //                     }
    //                 } else {
    //                     int j;
    //                     for (j = 0; j < arr[i].level; j++) {
    //                         arr[i-1].next[j] = arr[i];
    //                     }
    //                     arr[i-1].next[j] = arr[mid];
    //                 }
    //             } else {
    //                 if (i == mid+1) {
    //                     for (int j = 0; i < arr[i].level; j++) {
    //                         arr[mid].next[j] = arr[i];
    //                     }
    //                 } else if (i > mid+1) {
    //                     if (arr[i-1].level < arr[i].level) {
    //                         int j;
    //                         for (j = 0; j < arr[i-1].level; j++) {
    //                             arr[i-1].next[j] = arr[i];
    //                         }
    //                         arr[mid].next[j] = arr[i];
    //                     } else if (arr[i].level == arr[i-1].level) {
    //                         for (int j = 0; j < arr[i].level; j++) {
    //                             arr[i-1].next[j] = arr[i];
    //                         }
    //                     } else {
    //                         int j;
    //                         for (j = 0; j < arr[i].level; j++) {
    //                             arr[i-1].next[j] = arr[i];
    //                         }
    //                         arr[i-1].next[j] = tail;
    //                     }
    //                 }
    //             }
    //         }
    //         i++;
    //     }
    //     arr[mid].next[k-1] = tail;
    }

    // private void rebuild(Node<T>[] arr, int p, int r, int k) {
    //     if (p <= r) {
    //         if (k == 0) {
    //             for (int i = p; i <= r; i++) {
    //                 arr[i] = new Node(null, 1);
    //             }
    //         } else {
    //             int q = (p+r)/2;
    //             arr[q] = new Node(null, k);
    //             rebuild(arr, p, q-1, k-1);
    //             rebuild(arr, q+1, r, k-1);
    //         }
    //     }
    // }


    /**
     * Method to remove an element from a SkipList.
     * 
     * @param x Element to be removed.
     * @return value Remove and return x if exists else return null.
     */
    @Override
    public T remove(T x) {
        Node<T>[] prev = find(x);

        if (compare(prev[0].next[0].data, x) == 0) {
            Node<T> n = prev[0].next[0];
            for (int i = 0; i < maxLevel; i++) {
                if(prev[i].next[i] == n) {
                    prev[i].next[i] = n.next[i];
                } else {
                    break;
                }
            }
            size--;
            version++;
            return n.data;
        } else {
            return null;
        }
    }

    /**
     * Method to return the size of the SkipList.
     *
     * @return int Integer indicating the size of the SkipList.
     */
    @Override
    public int size() {
	   return size;
    }

    /**
     * Method to display the elements of SkipList.
     *
     * @return Nothing.
     */
    public void display() {
        Node<T> curr = head;

        while (curr.next[0].data != null) {
            System.out.print(curr.next[0].data);
            if (curr.next[0].next[0].data != null) {
                System.out.print(" -> ");
            }
            curr = curr.next[0];
        }
        System.out.println();
    }
}
