package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.rest.model.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO create(Integer id, String name, String department, String position);
    List<EmployeeDTO> readAll();
    EmployeeDTO read(Integer id);
    boolean delete(Integer id);
}
