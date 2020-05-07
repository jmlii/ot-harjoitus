package budgetapp.ui;

import budgetapp.dao.SQLCategoryDao;
import budgetapp.dao.SQLTransactionDao;
import budgetapp.domain.BudgetService;
import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Graphical user interface.
 */
public class BudgetGui extends Application {
    
    private BudgetService budgetService;
    private Connection connection;
    
    private Scene primaryScene;
    private Scene newIncomeScene;
    private Scene newExpenseScene;
    private Scene editTransactionScene;
    private Scene exitScene;
    
    private BorderPane layout;
    private ScrollPane homePageScrollPane;
    private VBox transactionListComponents;
    private VBox categoryNodes;
    private VBox transactionNodes;
    private GridPane transactionForm;
    
    private Label balanceSum;
    private Label balanceLabel;
    private Label incomeSum;
    private Label expenseSum;
    private PieChart categoryPieChart;
    
    private String previousView;
    
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String database = properties.getProperty("database"); 
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        
        connection = DriverManager.getConnection(database, username, password);
        
        SQLCategoryDao categoryDao = new SQLCategoryDao(connection);
        SQLTransactionDao transactionDao = new SQLTransactionDao(connection, categoryDao);
        
        budgetService = new BudgetService(categoryDao, transactionDao);   
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Create app basic layout and components for the layout (top menu, home page and transaction lists):
        layout = new BorderPane();        
        createTopMenu(primaryStage);
        createHomePage(primaryStage);        
        createTransactionsList();        
        primaryScene = new Scene(layout);
                
        // Create individual scenes:
        createNewIncomeScene(primaryStage);        
        createNewExpenseScene(primaryStage);         
        createEditTransactionScene();         
        createExitScene();
        
        // Setup of primary stage and primary scene:
        primaryStage.setTitle("BudgetApp");
        primaryStage.setWidth(760);
        primaryStage.setHeight(460);
        primaryStage.setScene(primaryScene); 
        primaryStage.show();
        
    }


    // Top menu components for home page and transaction lists:
    public void createTopMenu(Stage primaryStage) {
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(15, 10, 15, 10));
        topMenu.setSpacing(10);
        topMenu.setStyle("-fx-background-color: DARKKHAKI");
        Button homeButton = new Button("Home");
        homeButton.setPrefWidth(110);
        Button newIncomeButton = new Button("New income");
        newIncomeButton.setPrefWidth(110);
        Button newExpenseButton = new Button("New expense");
        newExpenseButton.setPrefWidth(110);
        Button transactionsButton = new Button("Transactions");
        transactionsButton.setPrefWidth(110);
        
        HBox exitBox = new HBox();
        exitBox.setAlignment(Pos.CENTER_RIGHT);
        Button exitButton = new Button("Exit");
        exitBox.getChildren().add(exitButton);
        
        homeButton.setOnAction((event) -> {
            try {
                updateSituation();
            } catch (Exception ex) {
                Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            layout.setCenter(homePageScrollPane);
        });
        newIncomeButton.setOnAction((event) -> {
            primaryStage.setScene(newIncomeScene);
        });
        newExpenseButton.setOnAction((event) -> {
            primaryStage.setScene(newExpenseScene);
        });
        transactionsButton.setOnAction((event) -> {
            try {
                listTransactions(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            layout.setCenter(transactionListComponents);
        });
        
        exitButton.setOnAction((event) -> {
            try {
                stop();
                primaryStage.setScene(exitScene);
            } catch (SQLException ex) {
                Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        topMenu.getChildren().addAll(homeButton, newIncomeButton, newExpenseButton, transactionsButton, exitBox);
        HBox.setHgrow(exitBox, Priority.ALWAYS);
        layout.setTop(topMenu);
    }
    
    // Home page components:
    public void createHomePage(Stage primaryStage) throws FileNotFoundException, Exception {
        HBox homePageComponents = new HBox();
        homePageScrollPane = new ScrollPane();
        
        GridPane situationView = new GridPane();
        situationView.setPadding(new Insets(15, 10, 15, 10));
        situationView.setHgap(10);
        situationView.setVgap(10);
        
        balanceLabel = new Label("Balance:");
        balanceLabel.setStyle("-fx-font-weight: bold;");
        situationView.add(balanceLabel, 0, 0);
        balanceSum = new Label();
        balanceSum.setStyle("-fx-font-weight: bold;");        
        situationView.add(balanceSum, 1, 0);
        GridPane.setHalignment(balanceSum, HPos.RIGHT);
        Label incomeLabel = new Label("Income:");
        situationView.add(incomeLabel, 0, 1);
        incomeSum = new Label();
        situationView.add(incomeSum, 1, 1);
        GridPane.setHalignment(incomeSum, HPos.RIGHT);
        Label expenseLabel = new Label("Expenses:");
        situationView.add(expenseLabel, 0, 2);
        expenseSum = new Label();
        situationView.add(expenseSum, 1, 2);
        GridPane.setHalignment(expenseSum, HPos.RIGHT);
        Label categoryPieChartLabel = new Label("My money is spent on...");
        categoryPieChartLabel.setStyle("-fx-font-style: italic;");
        situationView.add(categoryPieChartLabel, 0, 4, 2, 1); 
                
        categoryPieChart = new PieChart();
        categoryPieChart.setStartAngle(90);
        categoryPieChart.setLabelsVisible(false);
        categoryPieChart.setLegendVisible(false);
        categoryPieChart.setPrefWidth(200);
        categoryPieChart.setPrefHeight(200);      
        
        situationView.add(categoryPieChart, 0, 5, 2, 1);
        
        updateSituation();
        
        categoryNodes = new VBox();
        categoryNodes.setPadding(new Insets(15, 10, 15, 10));
        listCategories(primaryStage);
        
        homePageComponents.getChildren().addAll(situationView, categoryNodes);        
        homePageScrollPane.setContent(homePageComponents);
        layout.setCenter(homePageScrollPane);
    }
    
    // Transactions list components (all transactions and transactions from a single category)
    public void createTransactionsList() {
                        
        Label transactionListHeading = new Label("Transactions");
        transactionListHeading.setStyle("-fx-font-weight: bold;");
        transactionListHeading.setPadding(new Insets(15, 10, 15, 10));

        ScrollPane transactionPane = new ScrollPane();
        transactionPane.setPadding(new Insets(15, 10, 15, 10));
        transactionNodes = new VBox();
        transactionPane.setContent(transactionNodes);

        transactionListComponents = new VBox();
        transactionListComponents.getChildren().addAll(transactionListHeading, transactionPane);
        previousView = "";
    }
    
    // New income form components (scene)
    public void createNewIncomeScene(Stage primaryStage) throws Exception {
        Label incomeIntroLabel = new Label("Add a new income");
        incomeIntroLabel.setPadding(new Insets(15, 0, 20, 0));
        
        transactionForm = new GridPane();
        getTransactionForm(primaryStage, "newIncome", null);
        
        VBox newIncomeComponents = new VBox();     
        newIncomeComponents.setPadding(new Insets(20, 20, 20, 20)); 
        newIncomeComponents.getChildren().addAll(incomeIntroLabel, transactionForm);
        
        newIncomeScene = new Scene(newIncomeComponents);   
    }
    
    // New expense form components (scene)
    public void createNewExpenseScene(Stage primaryStage) throws Exception {
        Label expenseIntroLabel = new Label("Add a new expense");
        expenseIntroLabel.setPadding(new Insets(15, 0, 20, 0));
            
        transactionForm = new GridPane();
        getTransactionForm(primaryStage, "newExpense", null);

        VBox newExpenseComponents = new VBox();
        newExpenseComponents.setPadding(new Insets(20, 20, 20, 20));
        newExpenseComponents.getChildren().addAll(expenseIntroLabel, transactionForm);
        
        newExpenseScene = new Scene(newExpenseComponents);
    }
    
    // Edit transaction form components (scene)
    public void createEditTransactionScene() {
        Label editTransactionIntroLabel = new Label("Edit transaction");
        editTransactionIntroLabel.setPadding(new Insets(15, 0, 20, 0));
            
        transactionForm = new GridPane();

        VBox editTransactionComponents = new VBox();
        editTransactionComponents.setPadding(new Insets(20, 20, 20, 20));
        editTransactionComponents.getChildren().addAll(editTransactionIntroLabel, transactionForm);
        
        editTransactionScene = new Scene(editTransactionComponents);
    }
    
    // Exit scene components:
    public void createExitScene() {
        Label exitLabel = new Label("The connection to the app data storage has been closed. You can now close the window.");
        VBox exitComponents = new VBox();
        exitComponents.getChildren().add(exitLabel);
        exitScene = new Scene(exitComponents);
    }
 
    
    // Components for category list row:
    public Node categoryNode(Category category, Stage primaryStage, BorderPane layout) throws Exception {
        HBox cRow = new HBox();
        Label categoryLabel = new Label(category.getName());
        Label sumLabel = new Label(String.valueOf(budgetService.getCategorySum(category)));
        Button viewButton = new Button("View");
        
        categoryLabel.setPrefWidth(180);
        sumLabel.setPrefWidth(90);
        sumLabel.setAlignment(Pos.BASELINE_RIGHT);
        
        cRow.getChildren().addAll(categoryLabel, sumLabel, viewButton);
        cRow.setSpacing(10); 
        
        viewButton.setOnAction((event) -> {
            try {
                listTransactionsFromCategory(category, primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            layout.setCenter(transactionListComponents);
        });
        
        return cRow;
    }
    
    // Compilation and updating of category list rows
    public void listCategories(Stage primaryStage) throws Exception {
        categoryNodes.getChildren().clear();
        for (Category c : budgetService.listAllCategories()) {
            categoryNodes.getChildren().add(categoryNode(c, primaryStage, layout));
        }
    }
    
    // Components for transaction list row: 
    public Node transactionNode(Transaction transaction, Stage primaryStage) {
        HBox tRow = new HBox();
        Label categoryLabel = new Label(transaction.getCategory().getName());
        Label descriptionLabel = new Label(transaction.getDescription());
        Label amountLabel = new Label(String.valueOf(transaction.getAmount()));
        Label dateLabel = new Label(transaction.getDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        
        tRow.getChildren().addAll(categoryLabel, descriptionLabel, amountLabel, dateLabel, editButton, deleteButton);
        
        tRow.setSpacing(10);
        categoryLabel.setPrefWidth(180);
        descriptionLabel.setPrefWidth(180);
        amountLabel.setPrefWidth(80);
        amountLabel.setAlignment(Pos.BASELINE_RIGHT);
        dateLabel.setPrefWidth(90);
        dateLabel.setAlignment(Pos.BASELINE_RIGHT);
        editButton.setPrefWidth(70);
        deleteButton.setPrefWidth(70);
        
        deleteButton.setOnAction((event) -> {
            try { 
                budgetService.deleteTransaction(transaction.getId());
                listCategories(primaryStage);
                updateSituation();
                if (previousView.equals("listTransactionsFromCategory")) {
                    listTransactionsFromCategory(transaction.getCategory(), primaryStage);
                } else {
                    listTransactions(primaryStage);
                }
            } catch (Exception ex) {
                Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        editButton.setOnAction((event) -> {
            try {
                getTransactionForm(primaryStage, "editTransaction", transaction);
            } catch (Exception ex) {
                Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            primaryStage.setScene(editTransactionScene);
        });
        
        return tRow;
    }
    
    // Compilation and updating of transaction list rows:
    public void listTransactions(Stage primaryStage) throws Exception {
        transactionNodes.getChildren().clear();
        for (Transaction t : budgetService.listTransactions()) {
            transactionNodes.getChildren().add(transactionNode(t, primaryStage));
        }
        previousView = "listTransactions";
    }
    
    // Compilation and updating of transaction list rows for single category:
    public void listTransactionsFromCategory(Category category, Stage primaryStage) throws Exception {
        transactionNodes.getChildren().clear();
        for (Transaction t : budgetService.listTransactionsFromCategory(category)) {
            transactionNodes.getChildren().add(transactionNode(t, primaryStage));
        }
        previousView = "listTransactionsFromCategory";
    }
    
    // Components for different transaction forms
    public void getTransactionForm(Stage primaryStage, String form, Transaction transaction) throws Exception {
        transactionForm.getChildren().clear();
        
        Label categoryLabel = new Label("Category");
        Label categoryIncome = new Label("Income");
        ObservableList<Category> categoryList = FXCollections.observableArrayList(); 
        for (Category c : budgetService.listExpenseCategories()) {
            categoryList.add(c);
        }
        ComboBox categoryComboBox = new ComboBox(categoryList);
        Text categoryErrorMessage = new Text();
        Label descriptionLabel = new Label("Description");
        TextField descriptionField = new TextField();
        Text descriptionFieldErrorMessage = new Text();
        Label amountLabel = new Label("Amount");
        TextField amountField = new TextField();
        Text amountFieldErrorMessage = new Text();
        Label dateLabel = new Label("Date");
        DatePicker datePicker = new DatePicker();
        Text datePickerErrorMessage = new Text();
        Button saveButton = new Button("Save");
        saveButton.setDefaultButton(true);
        saveButton.setPrefWidth(70);
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setPrefWidth(70);
                
        if (transaction != null) {
            descriptionField.setText(transaction.getDescription());
            if (!transaction.getCategory().isIncomeCategory()) {
                categoryComboBox.setValue(transaction.getCategory());
                amountField.setText(String.valueOf(-1 * transaction.getAmount()));
            } else  {
                amountField.setText(String.valueOf(transaction.getAmount()));
            }
            datePicker.setValue(transaction.getDate());            
        }
        transactionForm.add(categoryLabel, 0, 0);
        if (form.equals("newExpense") || (form.equals("editTransaction") && transaction != null && !transaction.getCategory().isIncomeCategory())) {
            transactionForm.add(categoryComboBox, 1, 0);
            transactionForm.add(categoryErrorMessage, 2, 0);
        }
        if (form.equals("newIncome") || form.equals("editTransaction") && transaction != null && transaction.getCategory().isIncomeCategory()) {
            transactionForm.add(categoryIncome, 1, 0);
        }
        transactionForm.add(descriptionLabel, 0, 1);
        transactionForm.add(descriptionField, 1, 1);
        transactionForm.add(descriptionFieldErrorMessage, 2, 1);
        transactionForm.add(amountLabel, 0, 2);
        transactionForm.add(amountField, 1, 2);
        transactionForm.add(amountFieldErrorMessage, 2, 2);
        transactionForm.add(dateLabel, 0, 3);
        transactionForm.add(datePicker, 1, 3);
        transactionForm.add(datePickerErrorMessage, 2, 3);
        transactionForm.add(saveButton, 0, 4);
        transactionForm.add(cancelButton, 1, 4);

        transactionForm.setVgap(10);
        transactionForm.setHgap(10);
        
        saveButton.setOnAction((event) -> {
            Category category = null;
            String description = "";
            int amount = 0;
            LocalDate date = null;                             
            boolean errorsOnForm = false;
            
            if (form.equals("newExpense") || (form.equals("editTransaction") && transaction != null && !transaction.getCategory().isIncomeCategory())) {
                if (categoryComboBox.getValue() == null) {
                    categoryErrorMessage.setText("Please choose a category.");
                    errorsOnForm = true;
                } else {
                    category = (Category) categoryComboBox.getValue();
                    categoryErrorMessage.setText("");
                }
            } else if (form.equals("newIncome") || (form.equals("editTransaction") && transaction != null && transaction.getCategory().isIncomeCategory())) {
                try {
                    category = budgetService.getIncomeCategory();
                } catch (Exception ex) {
                    Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (descriptionField.getText().length() > 64 || descriptionField.getText().length() < 1) {
                descriptionFieldErrorMessage.setText("Please insert 1-64 characters.");
                errorsOnForm = true;
            } else {
                description = descriptionField.getText();
                descriptionFieldErrorMessage.setText("");
            }
            try {
                amount = Integer.parseUnsignedInt(amountField.getText()); 
                amountFieldErrorMessage.setText("");
            } catch (NumberFormatException nfException) {
                amountFieldErrorMessage.setText("Please insert a positive whole number.");
                errorsOnForm = true;
            } 
            if (datePicker.getValue() == null) {
                datePickerErrorMessage.setText("Please pick a date.");
                errorsOnForm = true;
            } else {
                date = datePicker.getValue();
                datePickerErrorMessage.setText("");
            }
            if (!errorsOnForm) { 
                if (form.equals("newIncome")) {
                    try {
                        budgetService.addIncomeTransaction(description, amount, date);
                    } catch (Exception ex) {
                        Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (form.equals("newExpense")) {
                    try {
                        budgetService.addExpenseTransaction(category, description, amount, date);
                    } catch (Exception ex) {
                        Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (form.equals("editTransaction") && (transaction != null && !transaction.getCategory().isIncomeCategory())) {
                    try {
                        budgetService.editExpenseTransaction(transaction, category, description, amount, date);
                    } catch (Exception ex) {
                        Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (form.equals("editTransaction") && (transaction != null && transaction.getCategory().isIncomeCategory())) {
                    try {
                        budgetService.editIncomeTransaction(transaction, description, amount, date);
                    } catch (Exception ex) {
                        Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                categoryComboBox.setValue(null);
                descriptionField.clear();
                amountField.clear();
                datePicker.setValue(null);
                try {
                    listCategories(primaryStage);
                    updateSituation();
                    if (previousView.equals("listTransactionsFromCategory")) {
                        listTransactionsFromCategory(category, primaryStage);
                    } else {
                        listTransactions(primaryStage);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(BudgetGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                primaryStage.setScene(primaryScene);
            }
        });
        
        cancelButton.setOnAction((event) -> {
            categoryComboBox.setValue(null);
            categoryErrorMessage.setText("");
            descriptionField.clear();
            descriptionFieldErrorMessage.setText("");
            amountField.clear();
            amountFieldErrorMessage.setText("");
            datePicker.setValue(null);
            datePickerErrorMessage.setText("");
            primaryStage.setScene(primaryScene);
        });
    }
 
    // Updating of the amounts and pie chart in the situation view on home page
    public void updateSituation() throws Exception {
        balanceSum.setText("" + budgetService.getBalance());
        if (budgetService.getBalance() < 0) {
            balanceLabel.setTextFill(Color.RED); 
            balanceSum.setTextFill(Color.RED); 
        } else if (budgetService.getBalance() > 0) {
            balanceLabel.setTextFill(Color.LIMEGREEN);
            balanceSum.setTextFill(Color.LIMEGREEN);

        } else {
            balanceLabel.setTextFill(incomeSum.getTextFill());
            balanceSum.setTextFill(incomeSum.getTextFill()); 
        }
        incomeSum.setText("" + budgetService.getIncomeSum());                   
        expenseSum.setText("" + budgetService.getExpensesSum());
        
        categoryPieChart.setData(budgetService.listCategoryPieChartData());
        categoryPieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue()));
            String categoryToolTip = data.getName() + " " + percentage;
            Tooltip toolTip = new Tooltip(categoryToolTip);
            Tooltip.install(data.getNode(), toolTip);
        });
        if (categoryPieChart.getData().get(0).getName().equals("Nothing")) {
            categoryPieChart.getData().get(0).getNode().setStyle("-fx-pie-color: GREY;");
        }
    }


    @Override
    public void stop() throws SQLException {
        connection.close();
    }
    
    public static void main(String[] args) {
        launch(BudgetGui.class);
    }
}
