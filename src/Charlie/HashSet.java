package Charlie;

public class HashSet {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node[] buckets;
    private int size;

    private static class Node {
        String item;
        Node next;

        Node(String item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public HashSet() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    public boolean add(String item) {
        if (contains(item)) {
            return false; // Item already exists in the set
        }

        if (size + 1 > buckets.length * DEFAULT_LOAD_FACTOR) {
            resize();
        }

        int index = getBucketIndex(item);
        Node newNode = new Node(item, buckets[index]);
        buckets[index] = newNode;
        size++;

        return true;
    }

    public boolean contains(String item) {
        int index = getBucketIndex(item);
        Node current = buckets[index];
        while (current != null) {
            if (current.item.equals(item)) {
                return true; // Item found in the set
            }
            current = current.next;
        }
        return false; // Item not found in the set
    }

    public boolean remove(String item) {
        int index = getBucketIndex(item);
        Node current = buckets[index];
        Node previous = null;

        while (current != null) {
            if (current.item.equals(item)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true; // Item removed from the set
            }
            previous = current;
            current = current.next;
        }

        return false; // Item not found in the set
    }

    public int size() {
        return size;
    }

    private int getBucketIndex(String item) {
        int hashCode = item.hashCode();
        return (hashCode & 0x7fffffff) % buckets.length;
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        Node[] newBuckets = new Node[newCapacity];

        for (Node node : buckets) {
            while (node != null) {
                int newIndex = getBucketIndex(node.item);
                Node newNode = new Node(node.item, newBuckets[newIndex]);
                newBuckets[newIndex] = newNode;
                node = node.next;
            }
        }

        buckets = newBuckets;
    }
}
