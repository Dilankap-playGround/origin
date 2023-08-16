import java.util.Scanner;

public class SmartBankingApp2 {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String COLOR_YELLO_BOLD = "\033[32;1m";
        final String RESET = "\033[0m";

        final String DASHBOARD = "Welcome to Smart Banking System🏦";
        final String CREATE_ACCOUNT = "Create New Acount";
        final String DEPOSIT = "Deposit";
        final String WITHDRAWAL = "Withdrawal";
        final String TRANSFER = "Transfer";
        final String PRINT_STATEMENT = "Check Account Balance";
        final String DELETE_ACCOUNT = "Delete Account";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String[][] customers = new String[0][];
        String screen = DASHBOARD;
        int i = 1;

        loop1: do {
            final String APP_TITLE = String.format("%s%s%s",
                    COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.printf("\t%s+%s+%s\n", COLOR_YELLO_BOLD, "-".repeat(APP_TITLE.length() - 11), RESET);
            System.out.print("\t|" + APP_TITLE + "|\n");
            System.out.printf("\t%s+%s+%s\n", COLOR_YELLO_BOLD, "-".repeat(APP_TITLE.length() - 11), RESET);
            switch (screen) {
                case DASHBOARD:
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
                            continue;
                    }
                    break;
                case CREATE_ACCOUNT:

                    String id = String.format("SDB-" + "%05d", i);
                    System.out.printf("\tID: %s\n", id);
                    i++;
                    String name;
                    boolean valid;
                    double initialAccountBalanceDouble;

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
                    String initialAccountBalance;
                    loop2: do {
                        valid = true;
                        System.out.printf("\tInitial Deposit: ");
                        initialAccountBalance = SCANNER.nextLine();
                        if (initialAccountBalance.length() > 4) {
                            for (int j = 0; j < initialAccountBalance.length() - 3; j++) {
                                if (!Character.isDigit(initialAccountBalance.charAt(j))) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                                    continue loop2;
                                }
                            }
                            if (!(initialAccountBalance.charAt((initialAccountBalance.length() - 3)) == '.' || Character
                                    .isDigit(initialAccountBalance.charAt(initialAccountBalance.length() - 3)))) {
                                valid = false;
                                System.out.printf(ERROR_MSG, "Enter a Valid Amount ");
                                continue loop2;

                            } else {
                                for (int j = initialAccountBalance.length() - 2; j < initialAccountBalance
                                        .length(); j++) {
                                    if (!Character.isDigit(initialAccountBalance.charAt(j))) {
                                        valid = false;
                                        System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                                        continue loop2;
                                    }
                                }

                            }
                        } else {
                            for (int j = 0; j < initialAccountBalance.length(); j++) {
                                if (!Character.isDigit(initialAccountBalance.charAt(j))) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                                    continue loop2;
                                }
                            }
                        }
                        if (valid) {
                            initialAccountBalanceDouble = Double.parseDouble(initialAccountBalance);
                            if (initialAccountBalanceDouble < 5000) {
                                System.out.printf(ERROR_MSG,
                                        "Insufficient Initial Deposit Minimum Rs.5000.00 Should be deposited");
                                System.out.print("\tDo you Want to Deposit more?(y/n)");
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                    valid = false;
                                    continue;
                                } else {
                                    valid = false;
                                    i--;
                                    continue loop1;
                                }
                            }
                        }

                    } while (!valid);
                    initialAccountBalanceDouble = Double.parseDouble(initialAccountBalance);
                    System.out.printf("\tDeposited: " + "%s%,.2f", "Rs.", initialAccountBalanceDouble);
                    String[][] newcustomers = new String[customers.length + 1][3];
                    for (int j = 0; j < customers.length; j++) {
                        newcustomers[j] = customers[j];
                    }
                    newcustomers[newcustomers.length - 1][0] = id;
                    newcustomers[newcustomers.length - 1][1] = name;
                    newcustomers[newcustomers.length - 1][2] = initialAccountBalance;
                    customers = newcustomers;

                    System.out.println();
                    System.out.printf(SUCCESS_MSG,
                            String.format("%s:%s has been Created successfully", id, name));
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;
                    screen = DASHBOARD;
                    break;
                case DEPOSIT:
                    String accountNumber;
                    String accountholdername = "";
                    String accountBalance = "";
                    String depositAmount = "";
                    double depositAmountDouble = 0;
                    int customerindex = -1;
                    loop3: do {
                        valid = true;
                        System.out.print("\tEnter A/C No: ");
                        accountNumber = SCANNER.nextLine().toUpperCase().strip();
                        if (customers.length == 0) {
                            System.out.printf(ERROR_MSG, "No Accounts has been found");
                            System.out.println("Do you want to create an Account?(y/n)");
                            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                valid = false;
                                screen = CREATE_ACCOUNT;
                                continue loop1;
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
                                continue loop1;
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
                                continue loop1;
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
                                continue loop1;
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
                                    continue loop1;
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
                                continue loop1;
                            }
                        }

                    } while (!valid);
                    System.out.printf("\tName: %s\n", accountholdername);
                    System.out.printf("\tCurrent Balance: Rs.%,.2f\n", Double.parseDouble(accountBalance));
                    loop4: do {
                        valid = true;
                        System.out.print("\tDeposit Amount: ");
                        depositAmount = SCANNER.nextLine().strip();
                        if (depositAmount.length() > 4) {
                            for (int j = 0; j < depositAmount.length() - 3; j++) {
                                if (!Character.isDigit(depositAmount.charAt(j))) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                                    continue loop4;
                                }
                            }
                            if (!(depositAmount.charAt((depositAmount.length() - 3)) == '.' || Character
                                    .isDigit(depositAmount.charAt(depositAmount.length() - 3)))) {
                                valid = false;
                                System.out.printf(ERROR_MSG, "Enter a Valid Amount ");
                                continue loop4;

                            } else {
                                for (int j = depositAmount.length() - 2; j < depositAmount
                                        .length(); j++) {
                                    if (!Character.isDigit(depositAmount.charAt(j))) {
                                        valid = false;
                                        System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                                        continue loop4;
                                    }
                                }

                            }
                        } else {
                            for (int j = 0; j < depositAmount.length(); j++) {
                                if (!Character.isDigit(depositAmount.charAt(j))) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Enter a Valid Amount");
                                    continue loop4;
                                }
                            }
                        }
                        if (valid) {
                            depositAmountDouble = Double.parseDouble(depositAmount);
                            if (depositAmountDouble < 500) {
                                System.out.printf(ERROR_MSG,
                                        "Insufficient Deposit Minimum Rs.500.00 Should be deposited");
                                System.out.print("\tDo you Want to Deposit more?(y/n)");
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                    valid = false;
                                    continue;
                                } else {
                                    valid = false;
                                    continue loop1;
                                }
                            }
                        }
                        customers[customerindex][2] = Double
                                .toString(Double.parseDouble(customers[customerindex][2]) + depositAmountDouble);
                        System.out.printf("\tNew Account Balance: Rs%,.2f\n",
                                Double.parseDouble(customers[customerindex][2]));
                        System.out.println();
                        System.out.printf(SUCCESS_MSG,
                                String.format("Amount has been Deposited successfully"));
                        System.out.print("\tDo you want to deposit again (Y/n)? ");
                        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                            screen=DEPOSIT;
                            continue;
                        }

                        screen = DASHBOARD;
                        break;

                    } while (!valid);

            }

        } while (true);

    }

}