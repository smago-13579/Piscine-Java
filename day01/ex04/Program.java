import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        User Bob = new User("Bob", 1000);
        User Nick = new User("Nick", 1000);
        User Tom = new User("Tom", 1500);
        User Kate = new User("Kate", 2000);

        service.addUser(Bob);
        service.addUser(Nick);
        service.addUser(Tom);
        service.addUser(Kate);

        System.out.println("Bob balance: " + service.getBalanceByUserId(Bob.getId()));
        System.out.println("Nick balance: " + service.getBalanceByUserId(Nick.getId()));
        System.out.println("Tom balance: " + service.getBalanceByUserId(Tom.getId()));
        System.out.println("Kate balance: " + service.getBalanceByUserId(Kate.getId()));

        System.out.println("\nLET'S CREATE TRANSACTIONS:");
        service.createTransaction(Bob.getId(), Nick.getId(), 200);
        service.createTransaction(Nick.getId(), Bob.getId(), 100);
        service.createTransaction(Nick.getId(), Bob.getId(), 150);
        service.createTransaction(Nick.getId(), Bob.getId(), 50);
        service.createTransaction(Bob.getId(), Tom.getId(), 150);
        service.createTransaction(Bob.getId(), Kate.getId(), 500);
        service.createTransaction(Tom.getId(), Kate.getId(), 110);
        service.createTransaction(Nick.getId(), Kate.getId(), 120);
        service.createTransaction(Bob.getId(), Kate.getId(), 150);

        System.out.println("Bob: " + Bob + "\nBob list: \n" + Bob.getList());
        System.out.println("Nick: " + Nick + "\nNick list: \n" + Nick.getList());
        System.out.println("Tom: " + Tom + "\nTom list: \n" + Tom.getList());
        System.out.println("Kate: " + Kate + "\nKate list: \n" + Kate.getList());

        System.out.println("\nGET TRANSACTIONS BY USER ID:");
        Transaction[] transactions = service.getTransactionsByUserId(Bob.getId());

        for (Transaction tr : transactions) {
            System.out.println(tr);
        }

        System.out.println("\nREMOVE TRANSACTIONS:");
        service.removeTransaction(Bob.getId(), transactions[1].getUUID());
        service.removeTransaction(Bob.getId(), transactions[2].getUUID());
        service.removeTransaction(Bob.getId(), transactions[3].getUUID());
        System.out.println("Bob: " + Bob + "\nBob list: \n" + Bob.getList());

        System.out.println("\nCHECK UNPAIRED TRANSACTIONS:");
        transactions = service.unpairedTransactions();

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        try {
            System.out.println("\nWRONG CASE:");
            service.createTransaction(Tom.getId(), Nick.getId(), 5600);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}