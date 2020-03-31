package budgetapp.ui;

import budgetapp.domain.BudgetService;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

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
        budgetService = new BudgetService();
        
        commands = new TreeMap<>();
        
        commands.put("0", "0 print all budget rows");
        commands.put("1", "1 add an income");
        commands.put("2", "2 add an expense");
        commands.put("3", "3 edit a row");
        commands.put("4", "4 delete a row");
        commands.put("5", "5 show total budget");
        commands.put("6", "6 show the allocated amount");
        commands.put("7", "7 show the unallocated amount");
        commands.put("8", "8 show the sum of the expenditures");
        commands.put("9", "9 show the sum of the unused budget");
        commands.put("x", "x close");
    }
    
    public void start() {
        System.out.println("BudgetApp - Create and master your own budget!");       
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
            } else if (command.equals("0")) {
                printRows();
            } else if (command.equals("1")) {
                addIncome();
            }
        }
    }
    
    public void printInstructions() {
        System.out.println("");
        System.out.println("Please choose the desired command and press enter. Commands 2-9 are disabled, only 0, 1 and x are in function.");
        for (String value : commands.values()) {
            System.out.println(value);   
        }
    }
    
    public void printRows() {
        budgetService.printRows();
    }
    
    public void addIncome() {
        System.out.println("Please insert the value of the income and provide a short description.");
        System.out.println("Value:");
        String input = "";
        int budget = 0;
        while (budget == 0) {
            try {
                input = scanner.nextLine();  
                if (input.equals("0")) {
                    break;
                }
                budget = Integer.valueOf(input);                
            } catch (NumberFormatException exception) {
                System.out.println("Please insert a whole number. Value:");
                
            }
        }       
        
        System.out.println("Description:");
        String description = scanner.nextLine();
        budgetService.addIncomeRow(description, budget);
    }
}
