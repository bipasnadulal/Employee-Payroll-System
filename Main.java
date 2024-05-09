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
    private ArrayList<Employee> employee_list;

    public PayrollSystem(){
        employee_list=new ArrayList<>();
    }

    public void AddEmployee(Employee employee){
        employee_list.add(employee);
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
                    FullTimeEmployee FTemployee = geFullTimeEmployee(scanner);
                    payrollSystem.AddEmployee(FTemployee);
                    break;
                
                case 2:
                    PartTimeEmployee PTemployee=getPartTimeEmployee(scanner);
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

    public static FullTimeEmployee geFullTimeEmployee(Scanner scanner){
        System.out.println("Enter employee name: ");
        String empName=scanner.nextLine();
        int empId;
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
        System.out.println("Enter employee monthly salary: ");
        double monthlySalary=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter employement status of employee: ");
        String empStatus=scanner.nextLine();
    
        return new FullTimeEmployee(empName, empId, monthlySalary, empStatus);
    }
    
    public static PartTimeEmployee getPartTimeEmployee(Scanner scanner){
        System.out.println("Enter employee name: ");
        String empName=scanner.nextLine();
        int empId;
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
        System.out.println("Enter hours worked: ");
        int hoursWorked=scanner.nextInt();
        System.out.println("Enter hourly rate: ");
        double hourlyRate=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter employement status of employee: ");
        String empStatus=scanner.nextLine();
    
        return new PartTimeEmployee(empName, empId, hoursWorked, hourlyRate, empStatus);
    }
}



