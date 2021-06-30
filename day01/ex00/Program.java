import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        int user_id = 0;
        int amount = 100;
        User Bob = new User("Bob", 1000, user_id++);
        User Nick = new User("Nick", 1000, user_id++);
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        UUID id4 = UUID.randomUUID();
        Transaction.TransferCategory debit = Transaction.TransferCategory.debit;
        Transaction.TransferCategory credit = Transaction.TransferCategory.credit;

        Transaction Tr1 = new Transaction(id1, Bob, Nick, amount, debit);
        Transaction Tr2 = new Transaction(id1, Bob, Nick, -amount, credit);
        Bob.setBalance(Bob.getBalance() + amount);
        Nick.setBalance(Nick.getBalance() - amount);
        Bob.userInfo();
        Nick.userInfo();

        Transaction Tr3 = new Transaction(id2, Nick, Bob, -500, credit);
        Transaction Tr4 = new Transaction(id2, Nick, Bob, 500, debit);
        Bob.setBalance(Bob.getBalance() - 500);
        Nick.setBalance(Nick.getBalance() + 500);
        Bob.userInfo();
        Nick.userInfo();

        User fault = new User("Tom", -100, user_id++);
        Transaction TrFault1 = new Transaction(id3, Bob, Nick, amount, credit);
        Transaction TrFault3 = new Transaction(id4, Bob, Nick, 2000, debit);
        Nick.setBalance(Nick.getBalance() - 2000);
    }
}