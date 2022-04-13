import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        User Bob = new User("Bob", 1000);
        User Nick = new User("Nick", 1000);

        System.out.println("Bob: " + Bob + "\nBob list: \n" + Bob.getList());
        System.out.println("Nick: " + Nick + "\nNick list: \n" + Nick.getList());

        System.out.println("\nLET'S CREATE TRANSACTIONS:");
        createTransaction(Bob, Nick, 200);
        createTransaction(Nick, Bob, 100);
        createTransaction(Nick, Bob, 150);
        createTransaction(Nick, Bob, 50);

        System.out.println("Bob: " + Bob + "\nBob list: \n" + Bob.getList());
        System.out.println("Nick: " + Nick + "\nNick list: \n" + Nick.getList());

        System.out.println("\nREMOVE TRANSACTION:");
        Transaction[] transactions = Bob.getList().toArray();
        Transaction toRemove = transactions[1];
        Bob.getList().remove(toRemove.getUUID());
        Nick.getList().remove(toRemove.getUUID());
        System.out.println("Bob: " + Bob + "\nBob list: \n" + Bob.getList());
        System.out.println("Nick: " + Nick + "\nNick list: \n" + Nick.getList());

        try {
            System.out.println("\nTRY TO REMOVE TRANSACTION WITH WRONG UUID:");
            Bob.getList().remove(toRemove.getUUID());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("\nTRY TO REMOVE TRANSACTION WITH null UUID:");
            Nick.getList().remove(null);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTransaction(User recipient, User sender, int amount) {
        Transaction.TransferCategory debit = Transaction.TransferCategory.DEBIT;
        Transaction.TransferCategory credit = Transaction.TransferCategory.CREDIT;

        if (sender.getBalance() >= amount) {
            UUID id = UUID.randomUUID();
            Transaction tr1 = new Transaction(id, recipient, sender, amount, debit);
            Transaction tr2 = new Transaction(id, recipient, sender, -1 * amount, credit);

            recipient.setBalance(recipient.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);

            recipient.getList().add(tr1);
            sender.getList().add(tr2);
        } else {
            System.err.println("Insufficient balance");
        }
    }
}