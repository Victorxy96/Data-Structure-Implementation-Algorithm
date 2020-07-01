/**
 * The Queue is a first in first out data structure. This Queue holds Java Object references.
 * It grows dynamically as long as memory is available. Note: Many of these Javadoc comments
 * describe implementation details. For non-classroom use, these implementation details would
 * not be exposed.
 *
 * @author: Yan Xing
 * @create: 2020-02-10 20:32
 **/
public class Queue {

    private static int size;
    private static final int DEFAULT_CAPACITY = 5;
    private static int capacity;
    private static int head;
    private static int tail;
    private static Object[] array;

    /**
     * Create an empty queue that lives in a small array.
     */
    public Queue() {
        array = new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        head = tail = 0;
        size = 0;
    }

    /**
     * Time: O(1)
     * Object method removes and returns reference in front of queue. Pre-condition: queue not empty
     * @return deque Object
     */
    public Object deQueue() {
        if(size == 0) {
            return null;
        }

        Object res = array[head];
        head = head + 1 == array.length ? 0: head+1;
        size--;
        return res;
    }

    /**
     * Time:
     * Best: O(1)
     * Worst: O(n)
     * Add an object reference to the rear of the queue.
     * @param x object reference
     */
    public void  enQueue(Object x) {
        if(size == array.length) {
            Object[] newArray = new Object[capacity*2];
            for(int i=0;i<array.length;i++) {
                newArray[i] = array[i];
            }
            array = newArray;
            capacity *= 2;
        }
        array[tail] = x;
        tail = tail + 1 == array.length ? 0: tail+1;

        size++;
    }

    /**
     * Time: O(1)
     * Method getFront returns the front of the queue without removing it.
     * @return return Object
     */
    public Object getFront() {
        if(size == 0) {
            return null;
        }
        return array[head];
    }

    /**
     * Time: O(1)
     * Boolean method returns true on empty queue, false otherwise.
     * @return whether is empty
     */
    public  boolean isEmpty() {
        return  size == 0;
    }

    /**
     * Time: O(1)
     * Boolean method returns true if queue is currently at capacity, false otherwise.
     * @return whether is full
     */
    public boolean isFull() {
        return size == array.length;
    }

    /**
     * Time: O(n)
     * The toString method returns a String representation of the current queue contents.
     * @return to String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<size;i++) {
            sb.append(array[(i+head)%capacity]).append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * Testing the queue route.
     * @param args input args
     */
    public static void main(String[] args) {
        Queue queue = new Queue();
        System.out.println("Queue is empty: "+ queue.isEmpty());
        System.out.println("Offering 0 to 4 to queue");
        for(int i=0;i<5;i++) {
            queue.enQueue(i);
        }
        System.out.println("Queue is full: "+queue.isFull());
        System.out.println("Content of queue:"+queue.toString());
        System.out.println("Offering 5 & 6 to queue");
        queue.enQueue(5);
        queue.enQueue(6);
        System.out.println("Performing deque");
        queue.deQueue();
        System.out.println("After expand the array, Queue is full: "+queue.isFull());

    }
}
