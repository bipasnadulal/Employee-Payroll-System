import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


abstract class Employee{

    private String name;
    private int id;
    private String employementStatus;

    public Employee(String name, int id, String employementStatus){
        this.name=name;
        this.id=id;
        this.employementStatus=employementStatus;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public String getEmpStatus(){
        return employementStatus;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format("| %-10s | %-10d | %-10.2f | %-20s |", getName(), getId(), calculateSalary(), getEmpStatus());
}

    
}

class FullTimeEmployee extends Employee{
    private double monthlySalary;
    public FullTimeEmployee(String name, int id,  double monthlySalary, String employementStatus){
        super(name , id, employementStatus);
        this.monthlySalary=monthlySalary;
    }

    @Override
    public double calculateSalary(){
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee{
    private int hoursWorked;
    private double hourlyRate;
    public PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate, String employementStatus){
        super(name, id, employementStatus);
        this.hoursWorked=hoursWorked;
        this.hourlyRate=hourlyRate;
    }

    @Override
    public double calculateSalary(){
        return hoursWorked*hourlyRate;
    }
}

class PayrollSystem{
    private static final String FILE_PATH = "employees.txt";
    public ArrayList<Employee> employee_list;

    public PayrollSystem(){
        employee_list=new ArrayList<>();
        loadEmployeesFromFile();
    }

    //load employee data from file
    private void loadEmployeesFromFile(){
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while((line = br.readLine())!=null){
                String[] data=line.split(",");
                if(data.length==5){
                    if (data[0].equals("FullTime")){
                        employee_list.add(new FullTimeEmployee(data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]), data[4]));
                    }else if(data[0].equals("PartTime")){
                        employee_list.add(new PartTimeEmployee(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Double.parseDouble(data[4]), data[5]));
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //save employees to file
    private void saveEmployeesToFile(){
        try(BufferedWriter bw= new BufferedWriter(new FileWriter(FILE_PATH))){
            for(Employee employee : employee_list){
                if(employee instanceof FullTimeEmployee){
                    FullTimeEmployee fullTimeEmployee=(FullTimeEmployee) employee;
                    bw.write(String.format("FullTime: %s,%d,%.2f,%s\n", fullTimeEmployee.getName(), fullTimeEmployee.getId(), fullTimeEmployee.calculateSalary(), fullTimeEmployee.getEmpStatus()));
                }
                else if(employee instanceof PartTimeEmployee){
                    PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                    bw.write(String.format("PartTime: %s,%d,%d,%.2f,%s\n", partTimeEmployee.getName(), partTimeEmployee.getId(), partTimeEmployee.calculateSalary(), partTimeEmployee.getEmpStatus()));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void AddEmployee(Employee employee){
        employee_list.add(employee);
        saveEmployeesToFile();
        System.out.println("Employee added successfully.");
    }

    public void RemoveEmployee(int id){
        Employee employeeToRemove = null;
        for(Employee employee : employee_list){
            if (employee.getId()==id){
                employeeToRemove= employee;
                break;
            }
        }

        if (employeeToRemove != null){
            employee_list.remove(employeeToRemove);
            saveEmployeesToFile();
            System.out.println("Employee removed successfully.");
        }

        }
        
        public void displayEmployees() {
            int nameWidth = 10; // Minimum width for name column
            int idWidth = 10; // Minimum width for ID column
            int salaryWidth = 10; // Minimum width for salary column
            int statusWidth = 20; // Minimum width for status column
            
            // Calculate maximum width for each column based on employee data
            for (Employee employee : employee_list) {
                nameWidth = Math.max(nameWidth, employee.getName().length());
                idWidth = Math.max(idWidth, String.valueOf(employee.getId()).length());
                salaryWidth = Math.max(salaryWidth, String.valueOf(employee.calculateSalary()).length());
                statusWidth = Math.max(statusWidth, employee.getEmpStatus().length());
            }
        
            // Print table header
            System.out.println("+-" + "-".repeat(nameWidth) + "-+-" + "-".repeat(idWidth) + "-+-" + "-".repeat(salaryWidth) + "-+-" + "-".repeat(statusWidth) + "-+");
            System.out.printf("| %-"+nameWidth+"s | %-"+idWidth+"s | %-"+salaryWidth+"s | %-"+statusWidth+"s |\n", "Name", "ID", "Salary", "Employment Status");
            System.out.println("+-" + "-".repeat(nameWidth) + "-+-" + "-".repeat(idWidth) + "-+-" + "-".repeat(salaryWidth) + "-+-" + "-".repeat(statusWidth) + "-+");
        
            // Print employee data
            for (Employee employee : employee_list) {
                System.out.printf("| %-"+nameWidth+"s | %-"+idWidth+"d | %-"+salaryWidth+".2f | %-"+statusWidth+"s |\n", employee.getName(), employee.getId(), employee.calculateSalary(), employee.getEmpStatus());
            }
        
            // Print bottom border
            System.out.println("+-" + "-".repeat(nameWidth) + "-+-" + "-".repeat(idWidth) + "-+-" + "-".repeat(salaryWidth) + "-+-" + "-".repeat(statusWidth) + "-+");
        }
        
}
 

public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("Enter your choice:\n1. Add full time employee. \n2. Add part time Employee \n3. Remove employee \n4. Display Employee \n5. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    FullTimeEmployee FTemployee = geFullTimeEmployee(scanner, payrollSystem);
                    payrollSystem.AddEmployee(FTemployee);
                    break;
                
                case 2:
                    PartTimeEmployee PTemployee=getPartTimeEmployee(scanner, payrollSystem);
                    payrollSystem.AddEmployee(PTemployee);
                    break;
                
                case 3:
                    System.out.println("Enter employee id to remove: ");
                    int idToRemove=scanner.nextInt();
                    payrollSystem.RemoveEmployee(idToRemove);
                    break;

                case 4:
                    System.out.println("Employees: ");
                    payrollSystem.displayEmployees();
                    break;
                
                case 5:
                    System.out.println("Exiting....");
                    break;

                default:
                    System.out.println("Invalid choice.\nPlease enter a valid option.");
                
            }
        }
        while(choice!=5);
    }

    public static FullTimeEmployee geFullTimeEmployee(Scanner scanner, PayrollSystem payrollSystem){
        String empStatus;
        String empName;
        //validate employee name
        while (true) {
            System.out.println("Enter employee name: ");
            empName = scanner.nextLine();
            if (empName.matches("[a-zA-Z\\s]+")) {  
                break;
            } else {
                System.out.println("Invalid input!!\nEmployee name must be valid.");
            }
        }
        int empId;
        boolean idExists;
        do{
            idExists=false;
            while(true){
                System.out.println("Enter employee id (must be an integer): ");
                try{
                    empId=Integer.parseInt(scanner.nextLine());
                    break;
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid input!! Please enter an integer value for employee ID,");
                }
            }
            for(Employee emp:payrollSystem.employee_list){
                if(emp.getId()==empId){
                    idExists=true;
                    System.out.println("Employee with ID " + empId + " already exists. \nPlease enter different ID.");
                    break;
                }
            }
        }while(idExists);
       
        System.out.println("Enter employee monthly salary: ");
        double monthlySalary=scanner.nextDouble();
        scanner.nextLine();
        while (true) {
            System.out.println("Enter employment status of employee: ");
            empStatus = scanner.nextLine();
            if (empStatus.matches("(?i)full[-\\s]?time")) {  
                break;
            } else {
                System.out.println("Invalid input!!\nEmployment status doesnot match with the option chosen.");
            }
        }
        return new FullTimeEmployee(empName, empId, monthlySalary, empStatus);
    }
    
    public static PartTimeEmployee getPartTimeEmployee(Scanner scanner, PayrollSystem payrollSystem){
        String empName;
        String empStatus;
        //validate employee name
        while (true) {
            System.out.println("Enter employee name: ");
            empName = scanner.nextLine();
            if (empName.matches("[a-zA-Z\\s]+")) {  
                break;
            } else {
                System.out.println("Invalid input!!\nEmployee name must be valid.");
            }
        }
        
        int empId;
        boolean idExists;
        do{
            idExists=false;
            while(true){
                System.out.println("Enter employee id (must be an integer): ");
                try{
                    empId=Integer.parseInt(scanner.nextLine());
                    break;
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid input!! Please enter an integer value for employee ID,");
                }
            }
            for(Employee emp:payrollSystem.employee_list){
                if(emp.getId()==empId){
                    idExists=true;
                    System.out.println("Employee with ID " + empId + " already exists. \nPlease enter different ID.");
                    break;
                }
            }
        }while(idExists);
        System.out.println("Enter hours worked: ");
        int hoursWorked=scanner.nextInt();
        System.out.println("Enter hourly rate: ");
        double hourlyRate=scanner.nextDouble();
        scanner.nextLine();
        while(true){
            System.out.println("Enter employment status of employee: ");
            empStatus=scanner.nextLine();
            if(empStatus.matches("(?i)part[-\\s]?time")){
                break;
            }
            else{
                System.out.println("Invalid Input!!\nEmployment status doesnot match with the option chosen.");
            }
        }
    
        return new PartTimeEmployee(empName, empId, hoursWorked, hourlyRate, empStatus);
    }
}
