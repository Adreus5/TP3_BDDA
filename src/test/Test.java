package test;

import model.DataAccess;
import model.EmployeeInfo;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jean-Michel Busca
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        // work around Netbeans bug
        if (args.length != 3) {
            args = Arrays.copyOf(args, 3);
            args[0] = "jdbc:mysql://localhost:3306/company?useSSL=false&serverTimezone=UTC";
            args[1] = "User1Tp3";
            args[2] = "root";
        }

        // create a data access object
        DataAccess data = new DataAccess(args[0], args[1], args[2]);
        List<EmployeeInfo> employees = data.getEmployees();
        for (EmployeeInfo employee : employees) {
            System.out.println(employee);
        }

        // Specify the job and the salary raise amount
        String job = "SALESMAN";
        float amount = 150;

        // Call the raiseSalary method to raise the salary for employees with the specified job
        boolean raise = data.raiseSalary(job, amount);

        if (raise) {
            System.out.println("-----------------------------------------------------------");
            System.out.println("\n\n\nSalary raise successful for:"+job+".\n\n");
            for (EmployeeInfo employee : employees) {
                System.out.println(employee);
            }

        } else {
            System.out.println("Salary raise failed or no employees with the specified job.");
        }
    }
}
