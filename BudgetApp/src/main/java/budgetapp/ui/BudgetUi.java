package budgetapp.ui;

import budgetapp.domain.BudgetService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Sovelluksen tekstikäyttöliittymä; graafinen käyttöliittymä tehdään myöhemmin
 */

public class BudgetUi {
    private Scanner scanner;
    private TreeMap<String, String> commands;
    private BudgetService budgetService;
    
    public BudgetUi(Scanner scanner, BudgetService budgetService) {
        this.scanner = scanner;
        this.budgetService = budgetService;
        
        commands = new TreeMap<>();
        
        commands.put("1", "1 add an income");
        commands.put("2", "2 add an expense");
        commands.put("3", "3 edit a transaction");
        commands.put("4", "4 delete a transaction");
        commands.put("5", "5 show total income");
        commands.put("6", "6 show total expenditures");
        commands.put("7", "7 show balance");
        commands.put("p", "p print all transactions");
        commands.put("x", "x close");
    }
    
    public void start() throws Exception {
        System.out.println("BudgetApp - Create and master your finance!");       
        printInstructions();
        
        while (true) {
            System.out.println("");
            System.out.println("Command:");
            String command = scanner.nextLine();        
            if (!commands.keySet().contains(command)) {
                System.out.println("Command doesn't exist. Please choose a valid command.");
            }            
            if (command.equals("x")) {
                System.out.println("Goodbye!");
                break;
            } else if (command.equals("p")) {
                printRows();
            } else if (command.equals("1")) {
                addIncome();
            } else if (command.equals("2")) {
                addExpense();
            } else if (command.equals("3")) {
                editTransaction();
            } else if (command.equals("4")) {
                deleteTransaction();
            }
        }
    }
    
    private void printInstructions() {
        System.out.println("");
        System.out.println("Please choose the desired command and press enter. Only 1-4, p and x are in function, other commands are disabled.");
        for (String value : commands.values()) {
            System.out.println(value);   
        }
    }
    
    private void printRows() throws Exception {
        if (budgetService.listTransactions().isEmpty()) {
            System.out.println("No transactions");
        } else {
            for (Object o : budgetService.listTransactions()) {
            System.out.println(o);
            }
        }
    }
            
    private void addIncome() throws Exception {
        System.out.println("Please provide a short description and insert the amount and the date.");
        String description = askDescription();
        int amount = askAmount();
        LocalDate date = askDate();
        budgetService.addIncomeTransaction(description, amount, date);
    }
        
    private void addExpense() throws Exception {
        System.out.println("Please provide a short description and insert the amount and the date.");
        String categoryAsString = askCategory();
        String description = askDescription();
        int amount = askAmount();
        LocalDate date = askDate();
        
        budgetService.addExpenseTransaction(categoryAsString, description, amount, date);
    }
    
    private void editTransaction() throws Exception {
        System.out.println("Insert the ID number of the transaction to be edited:");
        int key = getIdInputAndCheckValidity();
        System.out.println("Current information: " + budgetService.getTransaction(key));
        System.out.println("Insert the new information.");
        String categoryAsString;
        if (budgetService.getTransaction(key).getCategory().isIncomeCategory()) {
            String description = askDescription();
            int amount = askAmount();
            LocalDate date = askDate();
            budgetService.editIncomeTransaction(key, description, amount, date);
        } else {
            categoryAsString = askCategory();
            String description = askDescription();
            int amount = askAmount();
            LocalDate date = askDate();
            budgetService.editExpenseTransaction(key, categoryAsString, description, amount, date);
        }
    }
    
    private int getIdInputAndCheckValidity() throws Exception {
        int key;
        while (true) {
            try {
                key = Integer.valueOf(scanner.nextLine());
                if (budgetService.getTransaction(key) != null ) {
                    break;
                } else {
                    throw new NullPointerException();
                }
            } catch (NumberFormatException nfException) {
                System.out.println("Please insert a number. ID number:");
            } catch (NullPointerException e) {
                System.out.println("Please insert a valid ID number. ID number:");
            }
        }
        return key;
    }
    
    private String askCategory() throws Exception {
        System.out.println("Choose category from the list:");
        showCategories();       
        System.out.println("Category:");
        String categoryAsString = scanner.nextLine();
        return categoryAsString;
    }
    
    private void showCategories() throws Exception {
        if (budgetService.listExpenseCategories().isEmpty()) {
            System.out.println("No categories");
        } else {
            for (Object o : budgetService.listExpenseCategories()) {
                System.out.println(o);
            }
        }
        System.out.println("");
    }
    
    private String askDescription() {
        System.out.println("Description:");
        String description = scanner.nextLine();
        return description;
    }    
    
    private int askAmount() {
        System.out.println("Amount:");
        int amount;
        while (true) {
            try {
                amount = Integer.valueOf(scanner.nextLine());  
                break;
            } catch (NumberFormatException nfException) {
                System.out.println("Please insert a whole number. Value:");
            }
        }
        return amount;
    }
        
    private LocalDate askDate() {
        System.out.println("Date (yyyy-mm-dd):");
        LocalDate date;
        while (true) {
            try {
                String inputDate = scanner.nextLine();
                date = LocalDate.parse(inputDate);
                break;
            } catch (DateTimeParseException dtpException) {
                System.out.println("Please insert the date in the correct format. Date (yyyy-mm-dd):");
            }
        }
        return date;
    }    
    
    private void deleteTransaction() throws Exception {
        System.out.println("Insert the ID number of the transaction to be deleted:");
        int key = getIdInputAndCheckValidity();
        System.out.println("You are about to delete: " + budgetService.getTransaction(key));
        System.out.println("Please confirm deletion by inserting 'y' or 'yes' or abort by inserting anything else.");
        String confirmation = scanner.nextLine();
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            budgetService.deleteTransaction(key);
        }
        else {
            System.out.println("Deletion aborted.");
        }
    }
    
}
