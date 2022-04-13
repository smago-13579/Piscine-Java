import java.util.UUID;

public class Transaction {

    public enum TransferCategory {
        DEBIT,
        CREDIT
    }

    private UUID id;
    private User recipient;
    private User sender;
    private Integer amount;
    private TransferCategory category;

    public Transaction(UUID id, User recipient, User sender, Integer amount, TransferCategory category) {
        if ((amount > 0 && category == TransferCategory.CREDIT) ||
                (amount < 0 && category == TransferCategory.DEBIT)) {
            System.err.println("Can't create Transaction");
            System.err.println("Wrong Transfer Category");
        } else if ((category == TransferCategory.DEBIT && sender.getBalance() < amount) ||
                (category == TransferCategory.CREDIT && sender.getBalance() < -amount)) {
            System.err.println("Can't create Transaction");
            System.err.println("Insufficient balance");
        } else {
            this.id = id;
            this.recipient = recipient;
            this.sender = sender;
            this.amount = amount;
            this.category = category;
        }
    }

    @Override
    public String toString() {
        String str = "Transaction{ ";

        if (this.category == TransferCategory.DEBIT) {
            str += this.recipient.getName() + " -> " + this.sender.getName() + ", +"
                    + this.amount + ", INCOME, " + this.id + " }";
        } else {
            str += this.sender.getName() + " -> " + this.recipient.getName() + ", "
                    + this.amount + ", OUTCOME, " + this.id + " }";
        }

        return str;
    }

    public UUID getUUID() {
        return this.id;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public User getSender() {
        return this.sender;
    }

    public int getAmount() {
        return this.amount;
    }

    public TransferCategory getTransferCategory() {
        return this.category;
    }
}