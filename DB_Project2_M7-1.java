import java.sql.*;
import java.util.Scanner;

public class DB_Project2_M7 {

    boolean exit;
    int choice = 0;
    static Connection conn = null;

    public static Connection createConnection()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/project_2","appcontroller",
                    "<Password>");
        } catch(SQLException e){
            System.err.println(e.getMessage());
        } catch(ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        //main
        conn = createConnection();
        DB_Project2_M7 menu = new DB_Project2_M7();
        menu.runMainMenu();
    } //end main
    private void printHeader()
    {
        System.out.println("===============================");
        System.out.println("|       Interior Design       |");
        System.out.println("|     Database Management     |");
        System.out.println("===============================");
    }
    // Main menu functionality
    private void printMainMenu()
    {
        //print main menu
        System.out.println();
        System.out.println("[1] Customer Options");
        System.out.println("[2] Designer Options");
        System.out.println("[3] Project Management");
        System.out.println("[0] Exit Program");
    } // End printMainMenu
    public void runMainMenu() throws SQLException {
        printHeader();
        while (!exit)
        {
            printMainMenu();
            choice = getInput();
            performMainAction(choice);
        }
    } // End runMainMenu
    private void performMainAction(int choice) throws SQLException {
        switch(choice)
        {
            case 0:
                exit = true;
                System.out.println("Closing program. Thank you!");
                break;
            case 1: performCustomerAction();
                break;
            case 2: performDesignerAction();
                break;
            case 3: performProjectAction();
                break;
            default: System.out.println("An unknown error has occurred!");
        }
    } // End performMainAction
    // End main menu functionality
    public void printCustomerMenu()
    {
        //print customer menu
        System.out.println();
        System.out.println("[1] Add a customer");
        System.out.println("[0] Return to main menu");
    }
    public void performCustomerAction() throws SQLException {
        boolean back = false;

        while(!back)
        {
            printCustomerMenu();
            choice = getInput();

            switch(choice) {
                case 0:
                    back = true;
                    runMainMenu();
                    break;
                case 1:
                    addCustomer(conn);
                    break;
                default:
                    System.out.println("An unknown error has occurred!");
            }
        }
    }
    public void printDesignerMenu()
    {
        //print designer menu
        System.out.println();
        System.out.println("[1] Update designer demand");
        System.out.println("[2] Search designers by manager");
        System.out.println("[3] Delete a designer");
        System.out.println("[4] Add bonus eligibility");
        System.out.println("[5] Show budget per designer");
        System.out.println("[0] Return to main menu");
    }
    public void performDesignerAction() throws SQLException {
        boolean back = false;

        while(!back)
        {
            printDesignerMenu();
            choice = getInput();

            switch(choice) {
                case 0:
                    back = true;
                    runMainMenu();
                    break;
                case 1:
                    updateDemand(conn);
                    break;
                case 2:
                    designerByManager(conn);
                    break;
                case 3:
                    deleteDesigner(conn);
                    break;
                case 4:
                    calcBonus(conn);
                    break;
                case 5:
                    budgetPerDesigner(conn);
                    break;
                default:
                    System.out.println("An unknown error has occurred!");
            }
        }
    }
    public void printProjectMenu()
    {
        //print project menu
        System.out.println();
        System.out.println("[1] Update project deadline");
        System.out.println("[2] Display project by customer");
        System.out.println("[0] Return to main menu");
    }
    public void performProjectAction() throws SQLException {
        boolean back = false;

        while(!back)
        {
            printProjectMenu();
            choice = getInput();

            switch(choice) {
                case 0:
                    back = true;
                    runMainMenu();
                    break;
                case 1:
                    updateProjectDeadline(conn);
                    break;
                case 2:
                    projectByCustomer(conn);
                    break;
                default:
                    System.out.println("An unknown error has occurred!");
            }
        }
    }
    private int getInput(){
        Scanner cMenuIn = new Scanner(System.in);
        int choice = -1;
        while(choice < 0 || choice > 5){
            try{
                System.out.print("\nMake your selection: ");
                choice = Integer.parseInt(cMenuIn.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid selection. Please try again.");
            }
        }

        return choice;
    }
    public void sqlPlaceHolder()
    {
        System.out.println("\nComing soon!");
    }

    public void designerByManager(Connection conn)
    {
        Scanner in = new Scanner(System.in);
        String managerId = "";
        int count = 0;

        System.out.println("===================================");
        System.out.println("|         Search Designers        |");
        System.out.println("|           by Manager ID         |");
        System.out.println("===================================");

        System.out.print("Enter Manager ID: ");
        managerId = in.nextLine();

        String SELECT_DESIGNER = "SELECT DesignerID, FirstName, LastName, Email, PhoneNumber,PreferredStyle, Demand FROM Designer WHERE ManagerID = ?";
        ResultSet designer = null;
        PreparedStatement selectPrep = null;
        try {
            selectPrep = conn.prepareStatement(SELECT_DESIGNER);
            selectPrep.setString(1, managerId);

            designer = selectPrep.executeQuery();

            System.out.println("\nAll designers managed by " + managerId);

            //if (designer != null) {
                while (designer.next()) {
                    ++count;
                    String id = designer.getString("DesignerID");
                    String fName = designer.getString("FirstName");
                    String lName = designer.getString("LastName");
                    String email = designer.getString("Email");
                    String pNumber = designer.getString("PhoneNumber");
                    String prefStyle = designer.getString("PreferredStyle");
                    String demand = designer.getString("Demand");
                    System.out.println(count + ") " + id + ", " + fName + ", " + lName + ", " + email + ", " + pNumber + ", " + prefStyle + ", " + demand);
                }
            //}
        } catch(SQLException e){
            System.err.println(e.getMessage());
        } finally {
            if(designer != null){
                try {
                    designer.close();
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
            if(selectPrep != null){
                try {
                    selectPrep.close();
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }
        count = 0;

    }
    public void deleteDesigner(Connection conn){
        Scanner in = new Scanner(System.in);
        String designerId = "";

        System.out.println("===================================");
        System.out.println("|          Delete Designer        |");
        System.out.println("===================================");

        System.out.print("Enter Designer ID: ");
        designerId = in.nextLine();

        String DELETE_DESIGNER = "DELETE FROM Designer WHERE DesignerID = ?";
        PreparedStatement deletePrep = null;

        try{
            deletePrep = conn.prepareStatement(DELETE_DESIGNER);
            deletePrep.setString(1, designerId);

            deletePrep.execute();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        } finally {
            if (deletePrep != null) {
                try{
                    deletePrep.close();
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }
        System.out.println("\nDesigner (" + designerId + ") removed!");
    }

    public void calcBonus(Connection conn) throws SQLException {
        Scanner in = new Scanner(System.in);
        String designerId = "";

        System.out.println("==========================================");
        System.out.println("|           Add Bonus Eligibility        |");
        System.out.println("==========================================");


            Statement bonusStmt = conn.createStatement ();
            bonusStmt.executeUpdate ("CALL AddDesignerBonus();");


        System.out.println("\nAdded high demand designers to bonus list!");
    }

    public void updateDemand(Connection conn){
        Scanner in = new Scanner(System.in);
        String designerId = "";

        System.out.println("==========================================");
        System.out.println("|           Update Designer Demand        |");
        System.out.println("==========================================");

        System.out.print("Enter designer ID: ");
        designerId = in.nextLine();


        String UPDATE_DESIGNER_DEMAND = "UPDATE Designer " +
                "SET Demand = DemandCalc(?) " +
                "WHERE DesignerID = ?";
        PreparedStatement updateDemandPrep = null;

        try{
            updateDemandPrep = conn.prepareStatement(UPDATE_DESIGNER_DEMAND);
            updateDemandPrep.setString(1, designerId);
            updateDemandPrep.setString(2, designerId);

            updateDemandPrep.execute();

        } catch(SQLException e){
            System.err.println(e.getMessage());
        } finally {
            if (updateDemandPrep != null) {
                try{
                    updateDemandPrep.close();
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }

        System.out.println("\nUpdate designer (" + designerId + ") demand!");
    }


    public void addCustomer(Connection conn) {

        Scanner custIn = new Scanner(System.in);
        String id ="";
        String custFirst = "";
        String custLast = "";
        String custEmail = "";
        String custStreet = "";
        String custZip = "";
        String custCity = "";

        System.out.println("===============================");
        System.out.println("|         Add Customer        |");
        System.out.println("===============================");

        System.out.print("Customer first name: ");
            custFirst = custIn.nextLine();
        System.out.print("Customer last name: ");
            custLast = custIn.nextLine();
        System.out.print("Customer email address: ");
            custEmail = custIn.nextLine();
        System.out.print("Customer street address: ");
            custStreet = custIn.nextLine();
        System.out.print("Customer zip code: ");
            custZip = custIn.nextLine();
        System.out.print("Customer city: ");
            custCity = custIn.nextLine();
        System.out.print("Assign customer ID: ");
            id = custIn.nextLine();

        String INSERT_CUSTOMER = "INSERT INTO Customer" +
        "(CustomerID, FirstName, LastName, Email, Address, Zip_Code, City) " +
        "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement insertPrep = null;

        try{
            insertPrep = conn.prepareStatement(INSERT_CUSTOMER);
            insertPrep.setString(1, id);
            insertPrep.setString(2, custFirst);
            insertPrep.setString(3, custLast);
            insertPrep.setString(4, custEmail);
            insertPrep.setString(5, custStreet);
            insertPrep.setString(6, custZip);
            insertPrep.setString(7, custCity);

            insertPrep.execute();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        } finally {
            if (insertPrep != null) {
                try{
                    insertPrep.close();
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }

        System.out.println("\nCustomer (" + id + ") added!");
    }
    public void updateProjectDeadline(Connection conn){
        Scanner in = new Scanner(System.in);
        String deadline = "";
        String projectId = "";

        System.out.println("==========================================");
        System.out.println("|         Update Project Deadline        |");
        System.out.println("==========================================");

        System.out.print("Enter project ID: ");
        projectId = in.nextLine();
        System.out.print("Enter new deadline YYYY-MM-DD: ");
        deadline = in.nextLine();

        String UPDATE_DEADLINE = "UPDATE Project " +
                "SET Deadline = ? " +
                "WHERE ProjectID = ?";
        PreparedStatement updatePrep = null;

        try{
            updatePrep = conn.prepareStatement(UPDATE_DEADLINE);
            updatePrep.setString(1, deadline);
            updatePrep.setString(2, projectId);

            updatePrep.execute();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        } finally {
            if (updatePrep != null) {
                try{
                    updatePrep.close();
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }
        System.out.println("\nProject (" + projectId + ") deadline updated!");
    }

    public void projectByCustomer(Connection conn) throws SQLException {
        Scanner in = new Scanner(System.in);
        int count = 0;

        System.out.println("====================================");
        System.out.println("|          Display Projects        |");
        System.out.println("|            by Customer           |");
        System.out.println("====================================");

        Statement selectStmt = conn.createStatement ();
        ResultSet selectRset = selectStmt.executeQuery ("SELECT ProjectID, CONCAT(FirstName, ' '," +
                "LastName) AS CustomerName, CONCAT(Address,', ', Zip_Code,', ', City) AS Customer_Address, Deadline," +
                "Scope, Budget, OtherConsiderations " +
                "FROM Project JOIN Customer " +
                "ON Project_CustomerID = CustomerID;");

            System.out.println("\nProjects by Customer");

            System.out.println("0) Project ID, CustomerName, Customer_Address, Deadline, Scope, Budget, OtherConsiderations");
            while (selectRset.next()) {
                ++count;
                String projId = selectRset.getString("ProjectID");
                String custName = selectRset.getString("CustomerName");
                String custAddress = selectRset.getString("Customer_Address");
                String deadline = selectRset.getString("Deadline");
                String scope = selectRset.getString("Scope");
                String budget = selectRset.getString("Budget");
                String otherCon = selectRset.getString("OtherConsiderations");
                System.out.println(count + ") " + projId + ", " + custName + ", " + custAddress + ", " + deadline + ", " + scope + ", " + budget + ", " + otherCon);
            }

        count = 0;
    }

    public void budgetPerDesigner(Connection conn) throws SQLException {
        int count = 0;

        System.out.println("====================================");
        System.out.println("|          Display Projects        |");
        System.out.println("|            by Customer           |");
        System.out.println("====================================");

        Statement selectStmt = conn.createStatement ();
        ResultSet selectRset = selectStmt.executeQuery ("SELECT de.DesignerID, CONCAT(de.FirstName,' ', de.LastName) AS DesignerName, ROUND(AVG(pr.Budget), 2) AS AverageBudget, SUM(pr.Budget) AS TotalBudget " +
                "FROM Designer de " +
                "JOIN Document " +
                "ON Document_DesignerID = DesignerID " +
                "JOIN Project pr " +
                "ON Document_ProjectID = ProjectID " +
                "GROUP BY de.DesignerID " +
                "ORDER BY AVG(pr.Budget) DESC;");

        System.out.println("\nTotal and Average Budget per Designer");

        System.out.println("0) Designer ID, Designer Name, Average Budget, Total Budget");
        while (selectRset.next()) {
            ++count;
            String designerId = selectRset.getString("DesignerID");
            String desName = selectRset.getString("DesignerName");
            double avgBudget = selectRset.getDouble("AverageBudget");
            double totBudget = selectRset.getDouble("TotalBudget");
            System.out.println(count + ") " + designerId + ", " + desName + ", " + avgBudget + ", " + totBudget);
        }

        count = 0;
    }

    } // end class
