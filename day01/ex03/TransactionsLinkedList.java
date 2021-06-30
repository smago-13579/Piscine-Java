import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node tail = new Node();
    private Node iter = null;
    private int size = 0;

    private class Node {
        public Node() {}
        public Node(Transaction tr) {
            this.data = tr;
        }

        Transaction data;
        Node        next;
        Node        prev;
    }

    public void addTransaction(Transaction tr) {
        Node tmp = new Node(tr);
        if (size == 0) {
            tmp.next = tail;
            tmp.prev = tail;
            tail.next = tmp;
            tail.prev = tmp;
        } else {
            tmp.prev = tail.prev;
            tmp.next = tail;
            tail.prev = tmp;
            tmp.prev.next = tmp;
        }
        size++;
    }

    public void removeTransaction(UUID ID) throws TransactionNotFoundException {
        if (this.size != 0) {
            this.iter = this.tail.next;
            while (iter != tail) {
                if (iter.data.getUUID() == ID) {
                    iter.prev.next = iter.next;
                    iter.next.prev = iter.prev;
                    iter.next = iter;
                    iter.prev = iter;
                    iter = null;
                    size--;
                    break;
                }
                else {
                    iter = iter.next;
                }
            }
            if (iter == tail) {
                throw new TransactionNotFoundException("Exception: Transaction Not Found");
            }
        }
        else {
            throw new TransactionNotFoundException("Exception: Transaction Not Found");
        }
    }
    public Transaction[]    toArray() {
        if (this.size == 0) {
            return null;
        }
        iter = tail.next;
        Transaction[] tmp = new Transaction[this.size];
        for (int i = 0; i < this.size; i++, iter = iter.next) {
            tmp[i] = iter.data;
        }
        return tmp;
    }
}