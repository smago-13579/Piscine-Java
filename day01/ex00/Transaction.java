import java.util.UUID;

public class Transaction {

    public enum TransferCategory {
        debit,
        credit
    }

    private UUID ID;
    private User Recipient;
    private User Sender;
    private int TransferAmount;
    private TransferCategory category;

    Transaction(UUID ID, User Recipient, User Sender, int amount, TransferCategory category) {
        if ((amount > 0 && category == TransferCategory.credit) ||
                (amount < 0 && category == TransferCategory.debit)) {
            System.err.println("Can't create Transaction");
            System.err.println("Wrong Transfer Category");
        }
        else if (Sender.getBalance() < amount) {
            System.err.println("Can't create Transaction");
            System.err.println("Insufficient balance");
        }
        else {
            this.ID = ID;
            this.Recipient = Recipient;
            this.Sender = Sender;
            this.TransferAmount = amount;
            this.category = category;

            TransactionInfo();
        }
    }

    void    TransactionInfo() {
        if (this.category == TransferCategory.debit) {
            System.out.println(this.Recipient.getName() + " -> " + this.Sender.getName() + ", +"
                    + this.TransferAmount + ", INCOME, " + this.ID);
        }
        else {
            System.out.println(this.Sender.getName() + " -> " + this.Recipient.getName() + ", "
                    + this.TransferAmount + ", OUTCOME, " + this.ID);
        }
    }

    UUID    getUUID() {
        return this.ID;
    }

    User    getRecipient() {
        return this.Recipient;
    }

    User    getSender() {
        return this.Sender;
    }

    int     getTransferAmount() {
        return this.TransferAmount;
    }

    TransferCategory    getTransferCategory() {
        return this.category;
    }
}