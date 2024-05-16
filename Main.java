import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Employee {
    private String name;
    private int id;
    private String employmentStatus;

    public Employee(String name, int id, String employmentStatus) {
        this.name = name;
        this.id = id;
        this.employmentStatus = employmentStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format("| %-10s | %-10d | %-10.2f | %-20s |", getName(), getId(), calculateSalary(),
                getEmploymentStatus());
    }
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary, String employmentStatus) {
        super(name, id, employmentStatus);
        this.monthlySalary = monthlySalary;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int id, double hourlyRate, int hoursWorked, String employmentStatus) {
        super(name, id, employmentStatus);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

class PayrollSystem {
    private static final String FILE_PATH = "employees.txt";
    public ArrayList<Employee> employeeList;

    public PayrollSystem() {
        employeeList = new ArrayList<>();
        loadEmployeesFromFile();
    }

    // Load employee data from file
    private void loadEmployeesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    if (data[0].equals("FullTime")) {
                        employeeList.add(new FullTimeEmployee(data[1], Integer.parseInt(data[2]),
                                Double.parseDouble(data[3]), data[4]));
                    } else if (data[0].equals("PartTime")) {
                        employeeList.add(new PartTimeEmployee(data[1], Integer.parseInt(data[2]),
                                Double.parseDouble(data[3]), Integer.parseInt(data[4]), data[5]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save employees to file
    private void saveEmployeesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employeeList) {
                if (employee instanceof FullTimeEmployee) {
                    FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                    bw.write(String.format("FullTime,%s,%d,%.2f,%s\n", fullTimeEmployee.getName(),
                            fullTimeEmployee.getId(), fullTimeEmployee.calculateSalary(),
                            fullTimeEmployee.getEmploymentStatus()));
                } else if (employee instanceof PartTimeEmployee) {
                    PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                    bw.write(String.format("PartTime,%s,%d,%.2f,%d,%s\n", partTimeEmployee.getName(),
                            partTimeEmployee.getId(), partTimeEmployee.calculateSalary(),
                            partTimeEmployee.getHoursWorked(), partTimeEmployee.getEmploymentStatus()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        saveEmployeesToFile();
        System.out.println("Employee added successfully.");
    }

    public void removeEmployee(int id) {
        Employee employeeToRemove = null;
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                employeeToRemove = employee;
                break;
            }
        }

        if (employeeToRemove != null) {
            employeeList.remove(employeeToRemove);
            saveEmployeesToFile();
            System.out.println("Employee removed successfully.");
        }
    }

    public void updateEmployee(int id, Scanner scanner) {
        Employee employeeToUpdate = null;
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                employeeToUpdate = employee;
                break;
            }
        }
        if (employeeToUpdate != null) {
            System.out.println("Current information for employee: ");
            System.out.println(employeeToUpdate.toString());

            System.out.println("Enter updated information: ");
            scanner.nextLine();
            System.out.println("Enter new name: ");
            String newName = scanner.nextLine();

            employeeToUpdate.setName(newName);

            System.out.println("Enter new employment status: ");
            String newEmploymentStatus = scanner.nextLine();
            employeeToUpdate.setEmploymentStatus(newEmploymentStatus);
            if (employeeToUpdate instanceof FullTimeEmployee) {
                System.out.println("Enter new monthly salary: ");
                double newMonthlySalary = scanner.nextDouble();
                ((FullTimeEmployee) employeeToUpdate).setMonthlySalary(newMonthlySalary);
            } else if (employeeToUpdate instanceof PartTimeEmployee) {
                System.out.println("Enter new hours worked: ");
                int newHoursWorked = scanner.nextInt();
                ((PartTimeEmployee) employeeToUpdate).setHoursWorked(newHoursWorked);

                System.out.println("Enter new hourly rate: ");
                double newHourlyRate = scanner.nextDouble();
                ((PartTimeEmployee) employeeToUpdate).setHourlyRate(newHourlyRate);
            }

            saveEmployeesToFile();

            System.out.println("Employee information updated successfully.");
        } else {
            System.out.println("Employee with ID: " + id + " not found.");
        }

    }

    public void displayEmployees() {
        int nameWidth = 10; // Minimum width for name column
        int idWidth = 10; // Minimum width for ID column
        int salaryWidth = 10; // Minimum width for salary column
        int statusWidth = 20; // Minimum width for status column

        // Calculate maximum width for each column based on employee data
        for (Employee employee : employeeList) {
            nameWidth = Math.max(nameWidth, employee.getName().length());
            idWidth = Math.max(idWidth, String.valueOf(employee.getId()).length());
            salaryWidth = Math.max(salaryWidth, String.valueOf(employee.calculateSalary()).length());
            statusWidth = Math.max(statusWidth, employee.getEmploymentStatus().length());
        }

        // Print table header
        System.out.println("+-" + "-".repeat(nameWidth) + "-+-" + "-".repeat(idWidth) + "-+-"
                + "-".repeat(salaryWidth) + "-+-" + "-".repeat(statusWidth) + "-+");
        System.out.printf("| %-"+nameWidth+"s | %-"+idWidth+"s | %-"+salaryWidth+"s | %-"+statusWidth+"s |\n", "Name", "ID", "Salary", "Employment Status");
        System.out.println("+-" + "-".repeat(nameWidth) + "-+-" + "-".repeat(idWidth) + "-+-"
                + "-".repeat(salaryWidth) + "-+-" + "-".repeat(statusWidth) + "-+");

        // Print employee data
        for (Employee employee : employeeList) {
            System.out.printf("| %-"+nameWidth+"s | %-"+idWidth+"d | %-"+salaryWidth+".2f | %-"+statusWidth+"s |\n", employee.getName(), employee.getId(), employee.calculateSalary(), employee.getEmploymentStatus());
        }

        // Print bottom border
        System.out.println("+-" + "-".repeat(nameWidth) + "-+-" + "-".repeat(idWidth) + "-+-"
                + "-".repeat(salaryWidth) + "-+-" + "-".repeat(statusWidth) + "-+");
    }
}

public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println(
                    "Enter your choice:\n1. Add full time employee. \n2. Add part time Employee \n3. Remove employee \n4. Update Employee \n5. Display Employee \n6. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    FullTimeEmployee FTemployee = getFullTimeEmployee(scanner, payrollSystem);
                    payrollSystem.addEmployee(FTemployee);
                    break;

                case 2:
                    PartTimeEmployee PTemployee = getPartTimeEmployee(scanner, payrollSystem);
                    payrollSystem.addEmployee(PTemployee);
                    break;

                case 3:
                    System.out.println("Enter employee id to remove: ");
                    int idToRemove = scanner.nextInt();
                    payrollSystem.removeEmployee(idToRemove);
                    break;

                case 4:
                    System.out.println("Enter employee id to update the information: ");
                    int idToUpdate = scanner.nextInt();
                    payrollSystem.updateEmployee(idToUpdate, scanner);
                    break;

                case 5:
                    System.out.println("Employees: ");
                    payrollSystem.displayEmployees();
                    break;

                case 6:
                    System.out.println("Exiting....");
                    break;

                default:
                    System.out.println("Invalid choice.\nPlease enter a valid option.");

            }
        } while (choice != 6);
    }

    public static FullTimeEmployee getFullTimeEmployee(Scanner scanner, PayrollSystem payrollSystem) {
        String empStatus;
        String empName;
        // Validate employee name
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
        do {
            idExists = false;
            while (true) {
                System.out.println("Enter employee id (must be an integer): ");
                try {
                    empId = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!! Please enter an integer value for employee ID,");
                }
            }
            for (Employee emp : payrollSystem.employeeList) {
                if (emp.getId() == empId) {
                    idExists = true;
                    System.out.println("Employee with ID " + empId + " already exists. \nPlease enter different ID.");
                    break;
                }
            }
        } while (idExists);

        System.out.println("Enter employee monthly salary: ");
        double monthlySalary = scanner.nextDouble();
        scanner.nextLine();
        while (true) {
            System.out.println("Enter employment status of employee: ");
            empStatus = scanner.nextLine();
            if (empStatus.matches("(?i)full[-\\s]?time")) {
                break;
            } else {
                System.out.println("Invalid input!!\nEmployment status does not match with the option chosen.");
            }
        }
        return new FullTimeEmployee(empName, empId, monthlySalary, empStatus);
    }

    public static PartTimeEmployee getPartTimeEmployee(Scanner scanner, PayrollSystem payrollSystem) {
        String empName;
        String empStatus;
        // Validate employee name
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
        do {
            idExists = false;
            while (true) {
                System.out.println("Enter employee id (must be an integer): ");
                try {
                    empId = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!! Please enter an integer value for employee ID,");
                }
            }
            for (Employee emp : payrollSystem.employeeList) {
                if (emp.getId() == empId) {
                    idExists = true;
                    System.out.println("Employee with ID " + empId + " already exists. \nPlease enter different ID.");
                    break;
                }
            }
        } while (idExists);

        System.out.println("Enter hours worked: ");
        int hoursWorked = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter hourly rate: ");
        double hourlyRate = Double.parseDouble(scanner.nextLine());

        // Validating employment status
        while (true) {
            System.out.println("Enter employment status of employee: ");
            empStatus = scanner.nextLine();
            if (empStatus.matches("(?i)part[-\\s]?time")) {
                break;
            } else {
                System.out.println("Invalid Input!!\nEmployment status does not match with the option chosen.");
            }
        }

        return new PartTimeEmployee(empName, empId, hourlyRate, hoursWorked, empStatus);
    }
}
