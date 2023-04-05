package org.examples.collectionsAndStreamApiClass.forStreamApiClass;

import java.util.List;

public interface EmployeeAction {
    void employeeRemove(List<? extends Employee> employees, int index);
    void employeeAdd(List<? super Employee> employees);
}
