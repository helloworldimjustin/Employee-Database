package edu.njit.cs602.s2018.assignments;

import java.awt.*;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Date;


/*
* Created by Justin Bullock 4/21/18
*
*
* */

public class EmployeeDAO extends AbstractEmployeeDAO{

    private Statement stmt;
    public EmployeeDAO(Connection connection){
        super(connection);
        try{
            stmt = conn.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * Add department info to database
     * @param dept department info to add
     * @throws DepartmentException if department already exists or department info is invalid
     */
    public void addDepartment(Department dept) throws DepartmentException{
        try{
            int dept_id = dept.getDeptId();
            String dept_name = dept.getDeptName();
            int manager_id = dept.getManagerId();
            String addDepartmentSql =
                    "INSERT INTO Department VALUES ('"+dept_id+"', '"+dept_name+"', "+manager_id+")";
            System.out.println("addDepartment: "+stmt.executeUpdate(addDepartmentSql));
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    /**
     *
     * Add employees and if deptId is not null, add department id to employee info
     * @param employees
     * @param deptId
     * @throws EmployeeException if one or more employees cannot be added
     */
    public void addEmployees(List<Employee> employees, Integer deptId) throws EmployeeException {
        try{
            String employeeFname;
            String employeeLname;
            Date employeeEmploymentDate;
            double employeeAnnualSalary;

            for(Employee emp : employees){
                employeeFname = emp.getFirstName();
                employeeLname = emp.getLastName();
                employeeEmploymentDate = emp.getEmploymentDate();
                employeeAnnualSalary = emp.getAnnualSalary();
                String addEmployeeSql;

                if(deptId != null){
                    addEmployeeSql = "INSERT INTO Employee VALUES ('"+emp.getEmployeeId()+"', '"+employeeFname+"', '"+employeeLname+"', '"
                            +employeeEmploymentDate+"', '"+deptId+"', '"+employeeAnnualSalary+"')";
                    System.out.println("addEmployee (not null): "+stmt.executeUpdate(addEmployeeSql));
                }else{
                    addEmployeeSql = "INSERT INTO Employee VALUES ('"+emp.getEmployeeId()+"', '"+employeeFname+"', '"+employeeLname+"', '"
                            +employeeEmploymentDate+"',"+null+", '"+employeeAnnualSalary+"')";
                    System.out.println("addEmployee (null): "+stmt.executeUpdate(addEmployeeSql));
                }
            }
            conn.commit();
            //stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        //String addEmployeeSql =
        //        "INSERT INTO Employee VALUES ("+dept_id+","+dept_name+","+manager_id+")";
    }

    /**
     * DONE
     * update employee table
     * Update employee info
     * @param employee
     * @throws EmployeeException if employee is not valid
     */
    public void updateEmployee(Employee employee) throws EmployeeException{
        try{
            int employeeId = employee.getEmployeeId();
            String employeeFname = employee.getFirstName();
            String employeeLname = employee.getLastName();
            Date employmentDate = employee.getEmploymentDate();
            double employeeAnnualSalary = employee.getAnnualSalary();
            String updateEmployeeSql;

            if(employee.getDept() != null){
                int deptId = (employee.getDept()).getDeptId();
                updateEmployeeSql = "UPDATE Employee " +
                        "SET " +
                        "employee_id = '"+employeeId+"', " +
                        "first_name = '"+employeeFname+"', " +
                        "last_name = '"+employeeLname+"', " +
                        "employment_date = '"+employmentDate+"', " +
                        "dept_id = '"+deptId+"', " +
                        "annual_salary = '"+employeeAnnualSalary+"' " +
                        "WHERE first_name = '"+employeeFname+"' AND last_name = '"+employeeLname+"'";
                System.out.println("updateEmployee (not null)"+stmt.executeUpdate(updateEmployeeSql));
            }else{
                updateEmployeeSql = "UPDATE Employee " +
                        "SET " +
                        "employee_id = "+employeeId+"," +
                        "first_name = "+employeeFname+"," +
                        "last_name = "+employeeLname+"" +
                        "employment_date = "+employmentDate+"" +
                        "dept_id = "+null+", " +
                        "annual_salary = "+employeeAnnualSalary+"" +
                        "WHERE first_name = "+employeeFname+" AND last_name = "+employeeLname+"";

                System.out.println("updateEmployee (null): "+stmt.executeUpdate(updateEmployeeSql));
            }
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Delete employee info
     * @param employee
     * @throws EmployeeException if employee is not valid
     */
    public void deleteEmployee(Employee employee) throws EmployeeException{
        try{
            int employeeId = employee.getEmployeeId();
            String deleteEmployeeSql = "DELETE FROM Employee " +
                    "WHERE employee_id = '"+employeeId+"'";
            stmt.executeUpdate(deleteEmployeeSql);
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Get employees for given employee ids
     * @param employeeIds
     * @return list of employees for which employee ids exist
     */
    public List<Employee> getEmployees(Set<Integer> employeeIds){
        String getEmployeeSql;
        List<Employee> empList = new ArrayList<>();
        try{
            getEmployeeSql = "SELECT * FROM Employee WHERE " +
                    "employee_id = ";
            Object[] empIds = employeeIds.toArray();
            for(int i =0; i<empIds.length;i++){
                if(i == empIds.length-1){
                    getEmployeeSql += empIds[i];
                }else{
                    getEmployeeSql += empIds[i]+" OR employee_id = ";
                }
            }
            ResultSet rset = stmt.executeQuery(getEmployeeSql);
            while(rset.next()){
                int empId = rset.getInt(1);
                String empFname = rset.getString(2);
                String empLname = rset.getString(3);
                Date emptDate = rset.getDate(4);
                double annualSalary = rset.getDouble(6);
                Employee emp = new Employee(empId, empFname, empLname, emptDate, annualSalary);
                empList.add(emp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return empList;
    }


    /**
     * Set average salaries in the given departments
     * @param departments
     */
    public void setAverageSalaries(List<Department> departments){

        try{
            for(Department dept : departments){
                String sql = "SELECT annual_salary FROM Employee WHERE dept_id = "+dept.getDeptId();

                double averageSalary;
                double accumSalary = 0;
                int divisor = 0;
                ResultSet rset = stmt.executeQuery(sql);

                while(rset.next()){
                    accumSalary+= rset.getDouble(1);
                    divisor++;
                }
                averageSalary = accumSalary/divisor;
                dept.setAverageSalary(averageSalary);
                System.out.println("Average Salary for Dept: "+dept.getDeptId()+" = "+dept.getAverageSalary());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Get list of all superiors (manager, manager of manager etc)
     * @param employee employee for which dept info exists
     * @return list of superiors (first in list is immediate supervisor)
     */
    public List<Employee> getSuperiors(Employee employee){
        List<Employee> empList = new ArrayList<>();
            Employee superior;
            int mngrId;
            int empId = employee.getEmployeeId();
            int empDeptId = employee.getDept().getDeptId();
            try{
                String getSuperiorsSql = "SELECT Employee.employee_id, Department.manager_id " +
                        "FROM Employee " +
                        "INNER JOIN Department ON Employee.dept_id = Department.dept_id " +
                        "WHERE Employee.dept_id = '"+empDeptId+"'";
                ResultSet rset = stmt.executeQuery(getSuperiorsSql);
                while(!rset.isClosed() && rset.next()){
                    String getSuperiorSql = "SELECT * FROM Employee WHERE employee_id = '"+rset.getInt(2)+"'";
                    ResultSet rset1 = stmt.executeQuery(getSuperiorSql);
                    if(rset1.next()){
                        int employee_id = rset1.getInt(1);
                        String employeeFname = rset1.getString(2);
                        String employeeLname = rset1.getString(3);
                        Date emptDate = rset1.getDate(4);
                        double annual_salary = rset1.getDouble(5);
                        superior = new Employee(employee_id, employeeFname, employeeLname, emptDate, annual_salary);
                        if(employee.equals(superior)){
                            empList.add(employee);
                            return empList;
                        }else{
                            empList.add(superior);

                        }
                    }
                }
                return empList;
            }catch (Exception e){
                e.printStackTrace();
            }
        return empList;
    }

}