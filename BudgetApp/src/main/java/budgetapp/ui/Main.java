package budgetapp.ui;

import budgetapp.dao.SQLCategoryDao;
import budgetapp.dao.SQLTransactionDao;
import budgetapp.domain.BudgetService;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        
        // Starting of the graphical user interface:
        BudgetGui.main(args);
        
//        Starting and initialization of the text-based user interface in this main method;
//        will be left out from the final application:

//        Properties properties = new Properties();
//        properties.load(new FileInputStream("config.properties"));
//        
//        String database = properties.getProperty("database"); 
//        String username = properties.getProperty("username");
//        String password = properties.getProperty("password");
//        
//        Connection connection = DriverManager.getConnection(database, username, password);
//        
//        SQLCategoryDao categoryDao = new SQLCategoryDao(connection);
//        SQLTransactionDao transactionDao = new SQLTransactionDao(connection, categoryDao);
//        
//        Scanner scanner = new Scanner(System.in);
//        BudgetService budgetService = new BudgetService(categoryDao, transactionDao);
//
//        BudgetUi budgetUi = new BudgetUi(scanner, budgetService);
//        budgetUi.start();
//        connection.close();
        
    }

}
