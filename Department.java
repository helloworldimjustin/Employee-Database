package edu.njit.cs602.s2018.assignments;

/**
 * Created by Ravi Varadarajan on 4/9/2018.
 */
public class Department {

    private final int deptId;
    private final String deptName;
    private final int managerId;
    private double averageSalary;

    public Department(int deptId,
                      String deptName,
                      int managerId) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.managerId = managerId;
    }

    public int getDeptId() {
        return deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public int getManagerId() {
        return managerId;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return String.format("Dept with id %d, name = %s, manager id =%d, avg salary=%f",
                deptId, deptName, managerId, averageSalary);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Department)) {
            return false;
        }
        Department other = (Department) obj;
        return this.deptId == other.deptId && this.deptName.equals(other.deptName)
                && this.deptId == other.deptId;
    }
}

