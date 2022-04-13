import java.util.Scanner;

public class Menu {
    private TransactionsService service;
    private Scanner scanner;
    private final boolean devMode;

    public Menu(boolean devMode) {
        service = new TransactionsService();
        scanner = new Scanner(System.in);
        this.devMode = devMode;
    }

    public void run() {
        while (true) {
            print();
            int action = action();

            if (action == 1) {
                addUser();
            } else if (action == 2) {
                getBalance();
            } else if (action == 3) {
                transfer();
            } else if (action == 4) {
                viewTransactions();
            } else if (action == 5) {
                devRemoveTransferById();
            } else if (action == 6) {
                checkTransfers();
            } else {
                System.exit(0);
            }
            System.out.println("-----------------------------------------");
        }
    }

    private void print() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");

        if (devMode) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
        }
        System.out.println("7. Finish execution");
    }

    private int action() {
        int action;

        try {
            String str = scanner.nextLine().trim();
            action = Integer.parseInt(str);

            if (!isValidAction(action)) {
                throw new WrongDataException();
            }
        } catch (RuntimeException e) {
            System.out.println("Invalid action. Enter valid number: ");
            action = action();
        }

        return action;
    }

    private boolean isValidAction(int action) {
        if (devMode && action > 0 && action <= 7) {
            return true;
        }

        if (!devMode && ((action > 0 && action <= 4) || action == 7)) {
            return true;
        }
        return false;
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String str = scanner.nextLine().trim();

        try {
            String[] data = str.split("\\s+");

            if (data.length != 2) {
                throw new WrongDataException("Invalid data");
            }
            String name = data[0];
            int balance = checkBalance(data[1]);
            service.addUser(new User(name, balance));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number in: " + str);
            addUser();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            addUser();
        }
    }

    private int checkBalance(String data) {
        int balance = Integer.parseInt(data);

        if (balance < 0) {
            throw new WrongDataException("Balance can't be negative");
        }
        return balance;
    }

    private void getBalance() {
        System.out.println("Enter a user ID");
        String str = scanner.nextLine().trim();

        try {
            int id = Integer.parseInt(str);
            User user = service.getUsersList().retrieveByID(id);
            System.out.println(user.getName() + " - " + user.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number in: " + str);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void transfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String str = scanner.nextLine().trim();

        try {
            String[] data = str.split("\\s+");

            if (data.length != 3) {
                throw new WrongDataException("Invalid data");
            }
            int senderId = Integer.parseInt(data[0]);
            int recipientId = Integer.parseInt(data[1]);
            int amount = Integer.parseInt(data[2]);
            service.createTransaction(recipientId, senderId, amount);
            System.out.println("The transfer is completed");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number in: " + str);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewTransactions() {
        System.out.println("Enter a user ID");
        String str = scanner.nextLine().trim();

        try {
            int userId = Integer.parseInt(str);
            Transaction[] transactions = service.getTransactionsByUserId(userId);
            printTransactions(userId, transactions);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number in: " + str);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printTransactions(int userId, Transaction[] transactions) {
        for (Transaction transaction : transactions) {
            User sender = transaction.getSender();
            User recipient = transaction.getRecipient();
            int amount = transaction.getAmount();

            if (userId == sender.getId()) {
                System.out.println("To " + recipient.getName() + "(id = " + recipient.getId() + ") "
                        + amount + " with id = " + transaction.getUUID());
            } else {
                System.out.println("From " + sender.getName() + "(id = " + sender.getId() + ") "
                        + amount + " with id = " + transaction.getUUID());
            }
        }
    }

    private void devRemoveTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        String str = scanner.nextLine().trim();

        try {
            String[] data = str.split("\\s+");
            Transaction transaction = null;

            if (data.length != 2) {
                throw new WrongDataException("Invalid data");
            }
            int userId = Integer.parseInt(data[0]);
            String uuid = data[1];
            Transaction[] transactions = service.getUsersList().retrieveByID(userId).getList().toArray();

            for (int i = 0; i < transactions.length; i++) {
                if (uuid.equals(transactions[i].getUUID().toString())) {
                    transaction = transactions[i];
                    break;
                }
            }

            if (transaction == null) {
                throw new WrongDataException("Transaction with id = "
                        + uuid + " not found from User with id = " + userId);
            }
            service.removeTransaction(userId, transaction.getUUID());

            if (userId == transaction.getSender().getId()) {
                User recipient = transaction.getRecipient();
                System.out.println("Transfer To " + recipient.getName()
                        + "(id = " + recipient.getId() + ") " + transaction.getAmount() + " removed");
            } else {
                User sender = transaction.getSender();
                System.out.println("Transfer From " + sender.getName()
                        + "(id = " + sender.getId() + ") " + transaction.getAmount() + " removed");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number in: " + str);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkTransfers() {
        System.out.println("Check results:");
        Transaction[] transactions = service.unpairedTransactions();

        for (Transaction transaction : transactions) {
            User recipient = transaction.getRecipient();
            User sender = transaction.getSender();

            if (transaction.getAmount() > 0) {
                System.out.println(recipient.getName() + "(id = " + recipient.getId()
                        + ") has an unacknowledged transfer id = " + transaction.getUUID()
                        + " from " + sender.getName() + "(id = " + sender.getId()
                        + ") for " + transaction.getAmount());
            } else {
                System.out.println(sender.getName() + "(id = " + sender.getId()
                        + ") has an unacknowledged transfer id = " + transaction.getUUID()
                        + " to " + recipient.getName() + "(id = " + recipient.getId()
                        + ") for " + transaction.getAmount());
            }
        }
    }
}
