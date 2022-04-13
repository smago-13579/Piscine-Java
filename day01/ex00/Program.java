import java.util.UUID;

public class Program {
    private static Integer userId = 0;

    public static void main(String[] args) {
        Integer amount = 100;
        User Bob = new User("Bob", 1000, userId++);
        User Nick = new User("Nick", 1000, userId++);
        Transaction.TransferCategory debit = Transaction.TransferCategory.DEBIT;
        Transaction.TransferCategory credit = Transaction.TransferCategory.CREDIT;
        UUID id = UUID.randomUUID();

        Transaction trDebit1 = new Transaction(id, Bob, Nick, amount, debit);
        Transaction trCredit1 = new Transaction(id, Bob, Nick, -amount, credit);

        if (isValid(trDebit1) && isValid(trCredit1)) {
            Bob.setBalance(Bob.getBalance() + amount);
            Nick.setBalance(Nick.getBalance() - amount);
        }
        System.out.println(Bob);
        System.out.println(Nick);
        System.out.println(trDebit1);
        System.out.println(trCredit1);

        id = UUID.randomUUID();
        Transaction trDebit2 = new Transaction(id, Nick, Bob, -500, credit);
        Transaction trCredit2 = new Transaction(id, Nick, Bob, 500, debit);

        if (isValid(trDebit2) && isValid(trCredit2)) {
            Bob.setBalance(Bob.getBalance() - 500);
            Nick.setBalance(Nick.getBalance() + 500);
        }
        System.out.println(Bob);
        System.out.println(Nick);
        System.out.println(trDebit2);
        System.out.println(trCredit2);

        System.out.println("\nINCORRECT CASES:");
        id = UUID.randomUUID();
        Transaction trFault1 = new Transaction(id, Bob, Nick, amount, credit);
        Transaction trFault2 = new Transaction(id, Bob, Nick, 2000, debit);
        Nick.setBalance(Nick.getBalance() - 2000);
    }

    public static boolean isValid(Transaction transaction) {
        if (transaction.getUUID() != null) {
            return true;
        }

        return false;
    }
}