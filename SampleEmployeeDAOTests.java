package edu.njit.cs602.s2018.assignments;

import edu.njit.cs602.s2018.assignments.Department;
import edu.njit.cs602.s2018.assignments.Employee;
import edu.njit.cs602.s2018.assignments.EmployeeDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ravi Varadarajan on 4/9/2018.
 * psql -U postgres
 * \list
 * \c justinbullock
 * SELECT * FROM Employee;
 *
 */
public class SampleEmployeeDAOTests {

    private static Date toDate(String dateStr) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String [] args) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Connection conn = DriverManager.getConnection(url, "postgres", "jb122795");
            EmployeeDAO dao = new EmployeeDAO(conn);
            List<Employee> empList = new ArrayList<>();
            Employee corpMgr = new Employee(100, "Aswin", "Chandran", toDate("2012-01-01"), 275000);
            Employee regionalOfficeMgr1 = new Employee(200, "John", "Liu", toDate("2014-02-01"), 250000);
            Employee regionalOfficeMgr2 = new Employee(300, "Prem", "Kumar", toDate("2015-03-15"), 300000);
            Employee mgr1 = new Employee(202, "Jane", "Doe", toDate("2012-05-01"), 200000);
            Employee mgr2 = new Employee(203, "John", "Doe", toDate("2014-01-01"), 150000);
            Employee mgr3 = new Employee(305, "Carlos", "Santana", toDate("2016-01-01"), 180000);
            empList.add(corpMgr);
            empList.add(regionalOfficeMgr1);
            empList.add(regionalOfficeMgr2);
            empList.add(mgr1);
            empList.add(mgr2);
            empList.add(mgr3);
            dao.addEmployees(empList, null);
            empList.clear();
            Department d1 = new Department(501, "Corporate Office", 100);
            Department d2 = new Department(502, "Regional Office1", 200);
            Department d3 = new Department(503, "Regional Office2", 300);
            Department d4 = new Department(504, "Engineering", 202);
            Department d5 = new Department(505, "Sales", 203);
            dao.addDepartment(d1);
            dao.addDepartment(d2);
            dao.addDepartment(d3);
            dao.addDepartment(d4);
            dao.addDepartment(d5);
            corpMgr.setDept(d1);
            regionalOfficeMgr1.setDept(d1);
            regionalOfficeMgr2.setDept(d1);
            mgr1.setDept(d2);
            mgr2.setDept(d2);
            mgr3.setDept(d3);
            dao.updateEmployee(corpMgr);
            dao.updateEmployee(regionalOfficeMgr1);
            dao.updateEmployee(regionalOfficeMgr2);
            dao.updateEmployee(mgr1);
            dao.updateEmployee(mgr2);
            dao.updateEmployee(mgr3);
            Employee emp1 = new Employee(2011, "Dirk", "Gentley", toDate("2011-12-01"), 50000);
            Employee emp2 = new Employee(2012, "John", "Derek", toDate("2010-02-25"), 80000);
            emp1.setDept(d4);
            emp2.setDept(d4);
            empList.add(emp1);
            empList.add(emp2);
            dao.addEmployees(empList, d4.getDeptId());
            empList.clear();
            Employee emp3 = new Employee(3022, "Alan", "Alda", toDate("2000-01-01"), 80000);
            emp3.setDept(d5);
            empList.add(emp3);
            dao.addEmployees(empList, d5.getDeptId());
            empList.clear();
            Employee emp4 = new Employee(900, "Han", "Solo", toDate("2014-01-01"), 90000);
            empList.add(emp4);
            dao.addEmployees(empList, null);
            empList.clear();
            Set<Integer> empIds = new HashSet<>();
            empIds.add(100);
            empIds.add(3022);
            empIds.add(305);
            empIds.add(900);
            List<Employee> employees = dao.getEmployees(empIds);
            for (Employee emp: employees) {
                System.out.println(emp);
            }
            List<Department> depts = new ArrayList<>();
            depts.add(d1);
            depts.add(d2);
            depts.add(d3);
            depts.add(d4);
            depts.add(d5);
            dao.setAverageSalaries(depts);
            for (Department dept : depts) {
                System.out.println(dept);
            }
            Employee anEmp = employees.get(1);
            List<Employee> sups = dao.getSuperiors(anEmp);
            System.out.println("Superiors of "+ anEmp.getFirstName() + " " +anEmp.getLastName() + "...");
            for (Employee emp : sups) {
                System.out.println(emp);
            }
            dao.deleteEmployee(emp4);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main1(String [] args) {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("wut");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Connection conn = DriverManager.getConnection(url, "postgres", "jb122795");
            EmployeeDAO dao = new EmployeeDAO(conn);



            //Department d1 = new Department(501, "Corporate Office", 100);
            //Department d2 = new Department(502, "Regional Office1", 200);
            //Department d3 = new Department(503, "Regional Office2", 300);
            //Department d4 = new Department(504, "Engineering", 202);
            //Department d5 = new Department(505, "Sales", 203);
            List<Employee> empList = new ArrayList<>();
            Employee corpMgr = new Employee(100, "Aswin", "Chandran", toDate("2012-01-01"), 275000);
            Employee regionalOfficeMgr1 = new Employee(200, "John", "Liu", toDate("2014-02-01"), 250000);
            Employee regionalOfficeMgr2 = new Employee(300, "Prem", "Kumar", toDate("2015-03-15"), 300000);
            Employee mgr1 = new Employee(202, "Jane", "Doe", toDate("2012-05-01"), 200000);
            Employee mgr2 = new Employee(203, "John", "Doe", toDate("2014-01-01"), 150000);
            Employee mgr3 = new Employee(305, "Carlos", "Santana", toDate("2016-01-01"), 180000);
            empList.add(corpMgr);
            empList.add(regionalOfficeMgr1);
            empList.add(regionalOfficeMgr2);
            empList.add(mgr1);
            empList.add(mgr2);
            empList.add(mgr3);
            //add employees to Employee table without employee_ids
            //dao.addEmployees(empList, null);
            //empList.clear();
            Department d1 = new Department(501, "Corporate Office", 100);
            Department d2 = new Department(502, "Regional Office1", 200);
            Department d3 = new Department(503, "Regional Office2", 300);
            Department d4 = new Department(504, "Engineering", 202);
            Department d5 = new Department(505, "Sales", 203);
            //add departments to department table
            /*dao.addDepartment(d1);
            dao.addDepartment(d2);
            dao.addDepartment(d3);
            dao.addDepartment(d4);
            dao.addDepartment(d5);
            */
            corpMgr.setDept(d1);
            regionalOfficeMgr1.setDept(d1);
            regionalOfficeMgr2.setDept(d1);
            mgr1.setDept(d2);
            mgr2.setDept(d2);
            mgr3.setDept(d3);
            Employee emp1 = new Employee(2011, "Dirk", "Gentley", toDate("2011-12-01"), 50000);
            Employee emp2 = new Employee(2012, "John", "Derek", toDate("2010-02-25"), 80000);
            emp1.setDept(d4);
            emp2.setDept(d4);
            empList.add(emp1);
            empList.add(emp2);
            //add employees to Employee table with employee_ids
            //dao.addEmployees(empList, d4.getDeptId());
            //empList.clear();

            //update employee
            /*dao.updateEmployee(corpMgr);
            dao.updateEmployee(regionalOfficeMgr1);
            dao.updateEmployee(regionalOfficeMgr2);
            dao.updateEmployee(mgr1);
            dao.updateEmployee(mgr2);
            dao.updateEmployee(mgr3);
            */


            Set<Integer> empIds = new HashSet<>();

            empIds.add(100);
            empIds.add(3022);
            empIds.add(305);
            empIds.add(202);
            //gets list of employees from Employee table based on employee_ids
            List<Employee> employees = dao.getEmployees(empIds);
            for (Employee emp: employees) {
                System.out.println(emp);
            }

            List<Department> depts = new ArrayList<>();
            depts.add(d1);
            depts.add(d2);
            depts.add(d3);
            depts.add(d4);
            depts.add(d5);
            dao.setAverageSalaries(depts);
            //for (Department dept : depts) {
                //System.out.println(dept);
            //}


            //dao.deleteEmployee(corpMgr);
            //dao.addEmployees(empList, null);
            //dao.addDepartment(d1);
            Employee anEmp = empList.get(2);
            List<Employee> sups = dao.getSuperiors(anEmp);
            System.out.println("Superiors of "+ anEmp.getFirstName() + " " +anEmp.getLastName() + "...");
            for (Employee emp : sups) {
                System.out.println(emp);
            }

            dao.deleteEmployee(emp2);


            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


