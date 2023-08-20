import java.util.Scanner;

public class SmartBankingApp2 {

    private static final Scanner SCANNER = new Scanner(System.in);
    public static String name;
    public static boolean valid;
    public static double initialAccountBalanceDouble;
    final static String CLEAR = "\033[H\033[2J";
    final static String COLOR_BLUE_BOLD = "\033[34;1m";
    final static String COLOR_RED_BOLD = "\033[31;1m";
    final static String COLOR_GREEN_BOLD = "\033[33;1m";
    final static String COLOR_YELLO_BOLD = "\033[32;1m";
    final static String RESET = "\033[0m";

    final static String DASHBOARD = "Welcome to Smart Banking System🏦";
    final static String CREATE_ACCOUNT = "Create New Acount";
    final static String DEPOSIT = "Deposit";
    final static String WITHDRAWAL = "Withdrawal";
    final static String TRANSFER = "Transfer";
    final static String PRINT_STATEMENT = "Check Account Balance";
    final static String DELETE_ACCOUNT = "Delete Account";

    final static String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
    final static String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

    public static String[][] customers = new String[0][];
    public static String screen = DASHBOARD;
    public static String accountNumber;
    public static String accountholdername = "";
    public static String accountBalance = "";
    public static String depositAmount = "";
    public static double depositAmountDouble = 0;
    public static String withdrawAmount = "";
    public static double withdrawAmountDouble = 0;
    public static int customerindex = -1;
    public static int i = 1;

    public static void main(String[] args) {
        smartApp();
    }

    public static void smartApp() {
        do {
            final String APP_TITLE = String.format("%s%s%s",
                    COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.printf("\t%s+%s+%s\n", COLOR_YELLO_BOLD, "-".repeat(APP_TITLE.length() - 11), RESET);
            System.out.print("\t|" + APP_TITLE + "|\n");
            System.out.printf("\t%s+%s+%s\n", COLOR_YELLO_BOLD, "-".repeat(APP_TITLE.length() - 11), RESET);
            switch (screen) {
                case DASHBOARD:
                    mainManu();
                    break;
                case CREATE_ACCOUNT:
                    createAccount();
                    break;
                case DEPOSIT:
                    deposit();
                    break;
                case WITHDRAWAL:
                    withdrawal();
                    break;

                case TRANSFER:
                    transfer();
                    break;
                case PRINT_STATEMENT:
                    printStatement();
                    break;
                case DELETE_ACCOUNT:
                    deleteAccount();
                    break;
            }

        } while (true);
    }

    private static void deleteAccount() {
        int accountHolderIndex = accountNumberValidation("");
        System.out.printf("\tName: %s\n", accountholdername);
        System.out.printf("\tCurrent Account Balance: Rs.%.2f\n", Double.parseDouble(accountBalance));
        System.out.print("\tAre you sure to Delete this account (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            String[][] newcustomers = new String[customers.length - 1][3];
            for (int i = 0; i < customers.length; i++) {
                if (i < accountHolderIndex) {
                    newcustomers[i] = customers[i];
                } else if (i > accountHolderIndex) {
                    newcustomers[i-1] = customers[i];
                }
            }
            customers = newcustomers;
            String massege=String.format("Account Holder %s has been Succesfully deleted.\n", accountholdername);
            System.out.printf(SUCCESS_MSG,massege);
            System.out.print("\tDo you want to confirm (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                screen = DASHBOARD;// No instruction given for this
                smartApp();
            } else {
                screen = DASHBOARD;
                smartApp();
            }
        } else {
            screen = DASHBOARD;
            smartApp();
        }

    }

    private static void printStatement() {
         accountNumberValidation("");
        System.out.printf("\tName: %s\n", accountholdername);
        System.out.printf("\tCurrent Account Balance: Rs.%.2f\n", Double.parseDouble(accountBalance));
        System.out.print("\tDo you want to confirm (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            screen = DASHBOARD;// No instruction given for this
            smartApp();
        } else {
            screen = DASHBOARD;
            smartApp();
        }

    }

    private static void transfer() {
        int fromAccountIndex = accountNumberValidation("from");
        int toAccountIndex = accountNumberValidation("to");
        double fromAccountBalance = Double.parseDouble(customers[fromAccountIndex][2]);
        double toAccountBalance = Double.parseDouble(customers[toAccountIndex][2]);
        System.out.printf("\tFrom A/C Balance: Rs.%.2f\n", fromAccountBalance);
        System.out.printf("\tTo A/C Balance: Rs.%.2f\n", toAccountBalance);

        do {
            withdrawValidation("Transfer", 500);
            if ((withdrawAmountDouble + (withdrawAmountDouble * 0.02)) > fromAccountBalance) {
                System.out.printf(ERROR_MSG,
                        "Insufficient account balance balance in from A/C");
                System.out.printf("\tDo you Want to Tranfer a less amount?(y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    valid = false;
                } else {
                    valid = false;
                    screen = DASHBOARD;
                    smartApp();
                }
            }

        } while (!valid);

        fromAccountBalance = fromAccountBalance - (withdrawAmountDouble + withdrawAmountDouble * 0.02);
        toAccountBalance = toAccountBalance + withdrawAmountDouble;

        customers[fromAccountIndex][2] = Double.toString(fromAccountBalance);
        customers[toAccountIndex][2] = Double.toString(toAccountBalance);

        System.out.printf("\tNew from Accout Balnce : Rs.%.2f\n", fromAccountBalance);
        System.out.printf("\tNew to Accout Balnce : Rs.%.2f\n", toAccountBalance);

        System.out.printf(SUCCESS_MSG,
                String.format("Transfer is  successfully"));
        System.out.print("\tDo you want to Tranfer again (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            screen = TRANSFER;
            smartApp();
        } else {
            screen = DASHBOARD;
            smartApp();
        }
    }

    private static void withdrawal() {
        accountNumberValidation("");
        System.out.printf("\tName: %s\n", accountholdername);
        System.out.printf("\tCurrent Balance: Rs.%,.2f\n", Double.parseDouble(accountBalance));

        withdrawValidation("Withdrawal", 100);
        if (withdrawAmountDouble > Double.parseDouble(accountBalance)) {
            System.out.printf(ERROR_MSG,
                    "Insufficient account balance");
            System.out.printf("\tDo you Want to Withdraw a less amount?(y/n)");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                valid = false;
                withdrawal();
            } else {
                valid = false;
                screen = DASHBOARD;
                smartApp();
            }

        }
        customers[customerindex][2] = Double
                .toString(Double.parseDouble(customers[customerindex][2]) - withdrawAmountDouble);
        System.out.printf("\tNew Account Balance: Rs%,.2f\n",
                Double.parseDouble(customers[customerindex][2]));
        System.out.println();
        System.out.printf(SUCCESS_MSG,
                String.format("Withdrawal is  successfully"));
        System.out.print("\tDo you want to withdraw again (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            screen = WITHDRAWAL;
            smartApp();
        } else {
            screen = DASHBOARD;
        }

        screen = DASHBOARD;
    }

    public static boolean nameValidation() {
        do {
            valid = true;
            System.out.print("\tName: ");
            name = SCANNER.nextLine().strip();
            if (name.isBlank()) {
                System.out.printf(ERROR_MSG, "Name can't be empty");
                valid = false;
                continue;
            }
            for (int j = 0; j < name.length(); j++) {
                if (!(Character.isLetter(name.charAt(j)) ||
                        Character.isSpaceChar(name.charAt(j)))) {
                    System.out.printf(ERROR_MSG, "Invalid name");
                    valid = false;
                    break;
                }
            }
        } while (!valid);
        return valid;
    }

    public static boolean depositvalidation(String depositType, double minimumamount) {
        do {
            valid = true;
            System.out.printf("\t%s Amount: ", depositType);
            depositAmount = SCANNER.nextLine().strip();
            if (depositAmount.length() > 4) {
                for (int j = 0; j < depositAmount.length() - 3; j++) {
                    if (!Character.isDigit(depositAmount.charAt(j))) {
                        valid = false;
                        System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                        depositvalidation("Deposit", 500);
                    }
                }
                if (!(depositAmount.charAt((depositAmount.length() - 3)) == '.' || Character
                        .isDigit(depositAmount.charAt(depositAmount.length() - 3)))) {
                    valid = false;
                    System.out.printf(ERROR_MSG, "Enter a Valid Amount ");
                    depositvalidation(depositType, minimumamount);

                } else {
                    for (int j = depositAmount.length() - 2; j < depositAmount
                            .length(); j++) {
                        if (!Character.isDigit(depositAmount.charAt(j))) {
                            valid = false;
                            System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                            depositvalidation(depositType, minimumamount);
                        }
                    }

                }
            } else {
                for (int j = 0; j < depositAmount.length(); j++) {
                    if (!Character.isDigit(depositAmount.charAt(j))) {
                        valid = false;
                        System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                        depositvalidation("Deposit", 500);
                    }
                }
            }
            if (valid) {
                depositAmountDouble = Double.parseDouble(depositAmount);
                if (depositAmountDouble < minimumamount) {
                    String massege = String.format("Insufficient %s Minimum Rs.%.2f Should be deposited", depositType,
                            minimumamount);
                    System.out.printf(ERROR_MSG,
                            massege);
                    System.out.print("\tDo you Want to Deposit more?(y/n)");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                        valid = false;
                        continue;
                    } else {
                        valid = false;
                        if (depositType.equals("Initial Deposit")) {
                            i--;
                            smartApp();
                        } else {
                            smartApp();
                        }

                    }
                }
            }
        } while (!valid);
        return valid;
    }

    public static void createAccount() {
        String id = String.format("SDB-" + "%05d", i);
        System.out.printf("\tID: %s\n", id);
        i++;

        nameValidation();
        depositvalidation("Initial Deposit", 5000);
        initialAccountBalanceDouble = Double.parseDouble(depositAmount);
        System.out.printf("\tDeposited: " + "%s%,.2f", "Rs.", initialAccountBalanceDouble);
        String[][] newcustomers = new String[customers.length + 1][3];
        for (int j = 0; j < customers.length; j++) {
            newcustomers[j] = customers[j];
        }
        newcustomers[newcustomers.length - 1][0] = id;
        newcustomers[newcustomers.length - 1][1] = name;
        newcustomers[newcustomers.length - 1][2] = depositAmount;
        customers = newcustomers;

        System.out.println();
        System.out.printf(SUCCESS_MSG,
                String.format("%s:%s has been Created successfully", id, name));
        System.out.print("\tDo you want to continue adding (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
            smartApp();
        screen = DASHBOARD;
    }

    public static void deposit() {
        accountNumberValidation("");
        System.out.printf("\tName: %s\n", accountholdername);
        System.out.printf("\tCurrent Balance: Rs.%,.2f\n", Double.parseDouble(accountBalance));
        depositvalidation("Deposit", 500);

        customers[customerindex][2] = Double
                .toString(Double.parseDouble(customers[customerindex][2]) + depositAmountDouble);
        System.out.printf("\tNew Account Balance: Rs%,.2f\n",
                Double.parseDouble(customers[customerindex][2]));
        System.out.println();
        System.out.printf(SUCCESS_MSG,
                String.format("Amount has been Deposited successfully"));
        System.out.print("\tDo you want to deposit again (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            screen = DEPOSIT;
            smartApp();
        }

        screen = DASHBOARD;
    }

    public static int accountNumberValidation(String from) {
        do {
            valid = true;
            System.out.printf("\tEnter %s A/C No: ", from);
            accountNumber = SCANNER.nextLine().toUpperCase().strip();
            if (customers.length == 0) {
                System.out.printf(ERROR_MSG, "No Accounts has been found");
                System.out.println("Do you want to create an Account?(y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    valid = false;
                    screen = CREATE_ACCOUNT;
                    smartApp();
                }
                valid = false;
                continue;
            }
            if (accountNumber.isBlank()) {
                System.out.printf(ERROR_MSG, "Account Number can't be empty");
                System.out.print("\tDo you Want to try agin?(y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    valid = false;
                    continue;
                } else {
                    valid = false;
                    smartApp();
                }

            }
            if (accountNumber.length() != 9) {
                System.out.printf(ERROR_MSG, "Invalid Account Number");
                System.out.print("\tDo you Want to try agin?(y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    valid = false;
                    continue;
                } else {
                    valid = false;
                    screen = DASHBOARD;
                    smartApp();
                }

            }
            if (!accountNumber.startsWith("SDB-")) {
                System.out.printf(ERROR_MSG, "Invalid Account Number");
                System.out.print("\tDo you Want to try agin?(y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    valid = false;
                    continue;
                } else {
                    valid = false;
                    screen = DASHBOARD;
                    smartApp();
                }

            }
            for (int j = 4; j < accountNumber.length(); j++) {
                if (!Character.isDigit(accountNumber.charAt(j))) {
                    System.out.printf(ERROR_MSG, "Invalid Account Number");
                    System.out.print("\tDo you Want to try agin?(y/n)");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                        valid = false;
                        continue;
                    } else {
                        valid = false;
                        smartApp();
                    }

                }
            }
            for (int j = 0; j < customers.length; j++) {
                if (accountNumber.equals(customers[j][0])) {
                    accountholdername = customers[j][1];
                    accountBalance = customers[j][2];
                    customerindex = j;
                    valid = true;
                    break;
                } else {
                    valid = false;
                }
            }
            if (!valid) {
                System.out.printf(ERROR_MSG, "You have no account in this account number");
                System.out.print("\tDo you Want to try agin?(y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    valid = false;
                    continue;
                } else {
                    valid = false;
                    smartApp();
                }
            }

        } while (!valid);
        if (valid) {
            return customerindex;
        } else
            return customerindex;
    }

    public static void mainManu() {
        System.out.printf("\t[1]. %s\n", CREATE_ACCOUNT);
        System.out.printf("\t[2]. %s\n", DEPOSIT);
        System.out.printf("\t[3]. %s\n", WITHDRAWAL);
        System.out.printf("\t[4]. %s\n", TRANSFER);
        System.out.printf("\t[5]. %s\n", PRINT_STATEMENT);
        System.out.printf("\t[6]. %s\n", DELETE_ACCOUNT);
        System.out.printf("\t[7]. %s\n", "Exsit");
        System.out.print("\tEnter an option to continue: ");
        int option = SCANNER.nextInt();
        SCANNER.nextLine();

        switch (option) {
            case 1:
                screen = CREATE_ACCOUNT;
                break;
            case 2:
                screen = DEPOSIT;
                break;
            case 3:
                screen = WITHDRAWAL;
                break;
            case 4:
                screen = TRANSFER;
                break;
            case 5:
                screen = PRINT_STATEMENT;
                break;
            case 6:
                screen = DELETE_ACCOUNT;
                break;

            case 7:
                System.out.println(CLEAR);
                System.exit(0);
            default:
                smartApp();
        }
    }

    public static void withdrawValidation(String withdrawtype, double minimumamount) {

        do {
            valid = true;
            System.out.printf("\t%s Amount: ", withdrawtype);
            withdrawAmount = SCANNER.nextLine().strip();
            if (withdrawAmount.length() > 4) {
                for (int j = 0; j < withdrawAmount.length() - 3; j++) {
                    if (!Character.isDigit(withdrawAmount.charAt(j))) {
                        valid = false;
                        System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                        withdrawValidation(withdrawtype, minimumamount);
                    }
                }
                if (!(withdrawAmount.charAt((withdrawAmount.length() - 3)) == '.' || Character
                        .isDigit(withdrawAmount.charAt(withdrawAmount.length() - 3)))) {
                    valid = false;
                    System.out.printf(ERROR_MSG, "Enter a Valid Amount ");
                    withdrawValidation(withdrawtype, minimumamount);

                } else {
                    for (int j = withdrawAmount.length() - 2; j < withdrawAmount
                            .length(); j++) {
                        if (!Character.isDigit(withdrawAmount.charAt(j))) {
                            valid = false;
                            System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                            withdrawValidation(withdrawtype, minimumamount);
                        }
                    }

                }
            } else {
                for (int j = 0; j < withdrawAmount.length(); j++) {
                    if (!Character.isDigit(withdrawAmount.charAt(j))) {
                        valid = false;
                        System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                        withdrawValidation(withdrawtype, minimumamount);
                    }
                }
            }
            if (valid) {
                withdrawAmountDouble = Double.parseDouble(withdrawAmount);
                if (withdrawAmountDouble < minimumamount) {
                    String massege = String.format("Insufficient %s Minimum Rs.%.2f Should be %s", withdrawtype,
                            minimumamount, withdrawtype == "Withdrawal" ? "withdrawn" : "Transfered");
                    System.out.printf(ERROR_MSG,
                            massege);
                    System.out.printf("\tDo you Want to %s more?(y/n)", withdrawtype);
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                        valid = false;
                        continue;
                    } else {
                        valid = false;
                        smartApp();
                    }
                }
            }

        } while (!valid);

    }
}
