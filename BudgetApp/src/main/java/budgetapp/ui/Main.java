package budgetapp.ui;

import budgetapp.domain.BudgetService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetService budgetService = new BudgetService();

        BudgetUi budgetUi = new BudgetUi(scanner, budgetService);
        budgetUi.start();
    }

}
