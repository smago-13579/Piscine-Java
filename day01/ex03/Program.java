import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        User Bob = new User("Bob", 1000);
        User Nick = new User("Nick", 1000);
        User Tom = new User("Tom", 500);
        User Kate = new User("Kate", 670);
        System.out.println("Last generated Id: " + UserIdsGenerator.getInstance().getId());

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        UUID id4 = UUID.randomUUID();
        Transaction.TransferCategory debit = Transaction.TransferCategory.debit;
        Transaction.TransferCategory credit = Transaction.TransferCategory.credit;

        Transaction Tr1 = new Transaction(id1, Bob, Nick, 200, debit);
        Transaction Tr2 = new Transaction(id1, Bob, Nick, -200, credit);

        Transaction Tr3 = new Transaction(id2, Bob, Nick, 200, debit);
        Transaction Tr4 = new Transaction(id2, Bob, Nick, -200, credit);

        Transaction Tr5 = new Transaction(id3, Nick, Bob, -100, credit);
        Transaction Tr6 = new Transaction(id3, Nick, Bob, 100, debit);

        System.out.println("Bob list:");
        Transaction[] tmp = Bob.getList().toArray();
        for (int i = 0; i < tmp.length; i++ ) {
            tmp[i].TransactionInfo();
        }
        System.out.println("Bob list after remove:");
        Bob.getList().removeTransaction(id2);
        tmp = Bob.getList().toArray();
        for (int i = 0; i < tmp.length; i++ ) {
            tmp[i].TransactionInfo();
        }

        Bob.getList().removeTransaction(id4);
    }
}