package edu.njit.cs602.s2018.assignments;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ravi Varadarajan on 4/9/2018.
 */
public class Employee {

    private static final ThreadLocal<SimpleDateFormat> df = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private final int employeeId;
    private final String firstName;
    private final String lastName;
    private final Date employmentDate;
    private double annualSalary;
    private Department dept;

    public Employee(int employeeId,
                    String firstName,
                    String lastName,
                    Date employmentDate,
                    double annualSalary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employmentDate = employmentDate;
        this.annualSalary = annualSalary;
        this.dept = dept;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public Department getDept() {
        return dept;
    }

    public Employee setDept(Department dept) {
        this.dept = dept;
        return this;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = df.get();
        return String.format("Employee with id=%d, firstName=%s, lastName=%s, employment date=%s, deptInfo:%s, annualSalary=%f",
                employeeId, firstName, lastName, fmt.format(employmentDate), (dept == null ? "" : dept.toString()), annualSalary);
    }

    private boolean doubleEquals(double d1, double d2) {
        return (Math.abs(d1-d2) <= 0.001);
    }

    public Employee setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) obj;
        SimpleDateFormat fmt = df.get();
        if (this.employeeId != other.employeeId) {
            return false;
        }
        if (!this.firstName.equals(other.firstName)) {
            return false;
        }
        if (!this.lastName.equals(other.lastName)) {
            return false;
        }
        if (!fmt.format(this.employmentDate).equals(fmt.format(other.employmentDate))) {
            return false;
        }
        if (!doubleEquals(this.annualSalary, other.annualSalary)) {
            return false;
        }
        if (this.dept == null && other.dept != null) {
            return false;
        }
        if (this.dept != null && other.dept == null) {
            return false;
        }
        if (this.dept != null && !this.dept.equals(other.dept)) {
            return false;
        }
        return true;
    }

}
