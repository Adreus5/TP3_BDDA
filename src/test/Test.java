package test;

import model.DataAccess;
import model.DepartmentInfo;
import model.EmployeeInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jean-Michel Busca
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static <Int> void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrer un user:");
        String user = sc.nextLine();
        System.out.println("\nEntrer un mot de passe:");
        String pass = sc.nextLine();

        // work around Netbeans bug
        if (args.length != 3) {
            args = Arrays.copyOf(args, 3);
            args[0] = "jdbc:mysql://localhost:3306/company?useSSL=false&serverTimezone=UTC";
            args[1] = user;
            args[2] = pass;
        }

        System.out.println("Menu :\n1)getEmployee()\n2)raiseSalary()\n3)getDepartment()\n4)Quit");
        int choix = sc.nextInt();

        //Parametres à rentrer :
        DataAccess data = new DataAccess(args[0], args[1], args[2]);
        List<EmployeeInfo> employees = data.getEmployeesPS();
        List<DepartmentInfo> department = data.getDepartmentsPS(null,null,null);
        //raise amount for job :
        String job = "CLERK";
        float amount = 0;


        switch (choix) {
            case 1:
                //------- EXO 2 -------
        /*DataAccess data = new DataAccess(args[0], args[1], args[2]);
        List<EmployeeInfo> employees = data.getEmployees();
        for (EmployeeInfo employee : employees) {
            System.out.println(employee);
        }*/

                //------- EXO 4 -------
                for (EmployeeInfo employee : employees) {
                    System.out.println(employee);
                }
                break;
            case 2:
                //------ EXO 3 ------
        /*boolean raise = data.raiseSalary(job, amount);
        if (raise) {
            System.out.println("-----------------------------------------------------------");
            for (EmployeeInfo employee : employees) {
                System.out.println(employee);
            }
        */
                //EXO4
                boolean raise = data.raiseSalaryPS(job, amount);

                if (raise) {
                    System.out.println("\n\n\nSalary raise successful for:" + job + ".\n\n");
                    for (EmployeeInfo employee : employees) {
                        System.out.println(employee);
                    }
                } else {
                    System.out.println("Salary raise failed or no employees with the specified job.");
                }
                break;
            case 3:
                //EXO5
                for (DepartmentInfo deparment : department) {
                    System.out.println(deparment);
                }
                break;
            case 4:
                return;
            default:
                System.out.println("Please choose one of the options: ");
                choix = sc.nextInt();
        }
    }
}

