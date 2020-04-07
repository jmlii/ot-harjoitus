package budgetapp.ui;

import budgetapp.dao.SQLCategoryDao;
import budgetapp.dao.SQLTransactionDao;
import budgetapp.domain.BudgetService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        
        SQLCategoryDao sqlCategoryDao = new SQLCategoryDao();
        SQLTransactionDao sqlTransactionDao = new SQLTransactionDao();
        
        Scanner scanner = new Scanner(System.in);
        BudgetService budgetService = new BudgetService(sqlCategoryDao, sqlTransactionDao);

        BudgetUi budgetUi = new BudgetUi(scanner, budgetService);
        budgetUi.start();
    }

}
