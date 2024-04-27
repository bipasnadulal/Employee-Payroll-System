import java.util.ArrayList;
import java.util.Scanner;


abstract class Employee{

    private String name;
    private int id;

    public Employee(String name, int id){
        this.name=name;
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public abstract double calculateSalary();

    @Override
    public String toString(){
        return "Employee[name="+name+", id="+id+",salary="+calculateSalary()+"]";
    }
}

class FullTimeEmployee extends Employee{
    private double monthlySalary;
    public FullTimeEmployee(String name, int id, double monthlySalary){
        super(name , id);
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
    public PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate){
        super(name, id);
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
        public void displayEmployees(){
            for(Employee employee : employee_list){
                System.out.println(employee);
            }
        }
}


public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("Enter your choice:\n 1. Add full time employee. \n2. Add part time Employee \n3. Remove employee \n4.Display Employee \n5.Exit");
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
        System.out.println("Enter employee id: ");
        int empId=scanner.nextInt();
        System.out.println("Enter employee monthly salary: ");
        double monthlySalary=scanner.nextDouble();
    
        return new FullTimeEmployee(empName, empId, monthlySalary);
    }
    
    public static PartTimeEmployee getPartTimeEmployee(Scanner scanner){
        System.out.println("Enter employee name: ");
        String empName=scanner.nextLine();
        System.out.println("Enter employee id: ");
        int empId=scanner.nextInt();
        System.out.println("Enter hours worked: ");
        int hoursWorked=scanner.nextInt();
        System.out.println("Enter hourly rate: ");
        double hourlyRate=scanner.nextDouble();
    
        return new PartTimeEmployee(empName, empId, hoursWorked, hourlyRate);
    }
}




