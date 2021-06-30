import java.util.UUID;

public interface TransactionsList {
    public void             addTransaction(Transaction tr);
    public void             removeTransaction(UUID ID) throws TransactionNotFoundException;
    public Transaction[]    toArray();
}