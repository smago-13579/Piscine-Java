import java.util.UUID;

public class Transaction {

    public enum TransferCategory {
        debit,
        credit
    }

    private UUID id;
    private User recipient;
    private User sender;
    private Integer transferAmount;
    private TransferCategory category;

    Transaction(User recipient, User sender, Integer amount, TransferCategory category) {
        if ((amount > 0 && category == TransferCategory.credit) ||
                (amount < 0 && category == TransferCategory.debit)) {
            System.err.println("Wrong Transfer Category");
        } else if ((category == TransferCategory.debit && sender.getBalance() < amount) ||
                (category == TransferCategory.credit && sender.getBalance() < -amount)) {
            System.err.println("Insufficient balance");
        } else {
            this.id = UUID.randomUUID();
            this.recipient = recipient;
            this.sender = sender;
            this.transferAmount = amount;
            this.category = category;
        }
    }

    @Override
    public String toString() {
        String str = "Transaction{ ";

        if (this.category == TransferCategory.debit) {
            str += this.recipient.getName() + " -> " + this.sender.getName() + ", +"
                    + this.transferAmount + ", INCOME, " + this.id + " }";
        }
        else {
            str += this.sender.getName() + " -> " + this.recipient.getName() + ", "
                    + this.transferAmount + ", OUTCOME, " + this.id + " }";
        }

        return str;
    }

    UUID    getUUID() {
        return this.id;
    }

    User    getRecipient() {
        return this.recipient;
    }

    User    getSender() {
        return this.sender;
    }

    int     getTransferAmount() {
        return this.transferAmount;
    }

    TransferCategory    getTransferCategory() {
        return this.category;
    }
}