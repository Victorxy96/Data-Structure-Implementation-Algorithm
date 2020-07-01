/**
 * Stack is implemented in an array with a top index initially set to -1.
 * Each push operation will add one to the top index and then add a new
 * element at that location. Each pop operation will return the value pointed
 * to by the top pointer and it will decrease the top pointer by 1. The stack
 * will hold Java objects. Thus, it will be able to contain such Java objects
 * as Strings and BigIntegers. You should also provide a method called isEmpty.
 *  This method returns true if and only if the top index is -1.
 *
 * @author: Yan Xing
 * @create: 2020-02-13 16:04
 **/
public class DynamicStack {
    private final int DEFAULT_CAPACITY = 6;
    private int top;
    private Object[] array;
    private int size;
    private int capacity;

    /**
     * Time: O(1)
     * Constructor.
     */
    public DynamicStack() {
        this.top = -1;
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Time: O(1)
     * Whether the stack is empty.
     * @return Whether the stack is empty
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Time:
     * Best: O(1)
     * Worst: O(n)
     * Push the element into stack, expand the array to a new array if the array is full.
     * @param e element
     */
    public void push(Object e) {
        if (isFull()) {
            expand();
        }
        top += 1;
        size++;
        array[top] = e;
    }

    /**
     * Time: O(1)
     * Pop the element of stack
     * @return element
     */
    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        Object res = array[top];
        top -= 1;
        size--;
        return res;
    }

    /**
     * Time: O(1)
     * Check whether the stack is full
     * @return Whether the stack is full
     */
    private boolean isFull() {
        return size == capacity;
    }

    /**
     *  Time: O(n)
     *  If the array is full and a push operation is executed, create a new
     *  array of twice the size as the old and copy the elements within the
     *  old array over to the new array.
     */
    private void expand() {
        Object[] newArray = new Object[2 * array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
        capacity = 2 * capacity;
    }

    /**
     * Time: O(1)
     * Get the size of element in the stack.
     * @return number of elements in the stack
     */
    public int getSize() {
        return size;
    }

    /**
     * Main method for test the stack.
     * @param args args
     */
    public static void main(String[] args) {
        DynamicStack stack = new DynamicStack();
        System.out.println("Pushing...");
        for (int i = 0; i <= 1000; i++) {
            stack.push(i);
        }

        System.out.println("Poping result:");
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
