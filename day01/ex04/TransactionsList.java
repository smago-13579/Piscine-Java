import java.util.UUID;

public interface TransactionsList {
    boolean             add(Transaction tr);
    boolean             remove(UUID ID);
    Transaction[]    toArray();
}