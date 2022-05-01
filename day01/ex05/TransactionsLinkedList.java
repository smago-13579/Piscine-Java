
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node first;
    private Node last;
    private int size;

    private static class Node {
        Transaction item;
        Node        next;
        Node        prev;

        Node(Node prev, Transaction element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public TransactionsLinkedList() {}

    public boolean add(Transaction e) {
        final Node lst = last;
        final Node newNode = new Node(lst, e, null);
        last = newNode;

        if (lst == null) {
            first = newNode;
        } else {
            lst.next = newNode;
        }
        size++;

        return true;
    }

    public boolean remove(UUID id) {
        if (id == null) {
            throw new TransactionNotFoundException("Transaction with 'null' UUID can't be removed");
        }

        for (Node tmp = first; tmp != null; tmp = tmp.next) {
            if (tmp.item != null && id.equals(tmp.item.getUUID())) {
                unlink(tmp);
                return true;
            }
        }
        throw new TransactionNotFoundException("Transaction with UUID: " + id + " not found.");
    }

    private void unlink(Node tmp) {
        final Node next = tmp.next;
        final Node prev = tmp.prev;

        if (prev == null) {
            this.first = next;
        } else {
            prev.next = next;
            tmp.prev = null;
        }

        if (next == null) {
            this.last = prev;
        } else {
            next.prev = prev;
            tmp.next = null;
        }

        tmp.item = null;
        size--;
    }

    public Transaction[]    toArray() {
        if (this.size == 0) {
            return new Transaction[0];
        }
        Transaction[] result = new Transaction[this.size];
        int i = 0;

        for (Node tmp = first; tmp != null; tmp = tmp.next) {
            result[i++] = tmp.item;
        }

        return result;
    }

    @Override
    public String toString() {
        String str = "TransactionsList { " +
                "size: " + this.size + "\n";

        for (Node tmp = first; tmp != null; tmp = tmp.next) {
            str += "\t" + tmp.item + "\n";
        }
        str += "}";

        return str;
    }
}