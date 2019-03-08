package edu.njit.cs602.s2018.assignments;

/**
 * Created by Ravi Varadarajan on 4/9/2018.
 */
public class DepartmentException extends Exception {

    public DepartmentException(String msg) {
        super(msg);
    }

    public DepartmentException(String msg, Throwable t) {
        super(msg, t);
    }

}
