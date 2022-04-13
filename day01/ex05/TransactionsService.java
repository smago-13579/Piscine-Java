import java.util.UUID;

public class TransactionsService {
    private UsersList usersList;

    public TransactionsService() {
        usersList = new UsersArrayList();
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getBalanceByUserId(int id) {
        User user = usersList.retrieveByID(id);
        return user.getBalance();
    }

    public void createTransaction(int recipientId, int senderId, int amount) {
        if (amount <= 0) {
            throw new IllegalTransactionException("Amount can't be negative or equal to zero");
        }

        if (recipientId == senderId) {
            throw new IllegalTransactionException("Recipient and Sender must be different users");
        }

        User recipient = usersList.retrieveByID(recipientId);
        User sender = usersList.retrieveByID(senderId);
        performTransfer(recipient, sender, amount);
    }

    private void performTransfer(User recipient, User sender, int amount) {
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
            throw new IllegalTransactionException("Insufficient balance for user: " + sender.getName()
                    + ", balance: " + sender.getBalance());
        }
    }

    public Transaction[] getTransactionsByUserId(int userId) {
        return usersList.retrieveByID(userId).getList().toArray();
    }

    public void removeTransaction(int userId, UUID id) {
        usersList.retrieveByID(userId).getList().remove(id);
    }

    public Transaction[] unpairedTransactions() {
        int start = 0;
        int max = 10;
        Transaction[] result = new Transaction[max];
        int size = usersList.numberOfUsers();

        for (int index = 0; index < size; index++) {
            User user = usersList.retrieveByIndex(index);
            Transaction[] transactions = user.getList().toArray();

            for (int j = 0; j < transactions.length; j++) {
                if (isUnpaired(index, transactions[j].getUUID(), size)) {
                    if (start == max) {
                        max *= 2;
                        Transaction[] tmp = result;
                        result = new Transaction[max];

                        for (int l = 0; l < tmp.length; l++) {
                            result[l] = tmp[l];
                        }
                    }
                    result[start++] = transactions[j];
                }
            }
        }
        return modifyArray(start, result);
    }

    private boolean isUnpaired(int index, UUID id, int size) {
        for (int i = 0; i < size; i++) {
            if (index == i) {
                continue;
            }
            User user = usersList.retrieveByIndex(i);
            Transaction[] transactions = user.getList().toArray();

            for (int j = 0; j < transactions.length; j++) {
                if (id.equals(transactions[j].getUUID())) {
                    return false;
                }
            }
        }
        return true;
    }

    private Transaction[] modifyArray(int max, Transaction[] tmp) {
        Transaction[] result = new Transaction[max];

        for (int i = 0; i < max; i++) {
            result[i] = tmp[i];
        }
        return result;
    }

    public UsersList getUsersList() {
        return usersList;
    }
}
