package info.stepanoff.trsis.samples.db.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import info.stepanoff.trsis.samples.db.model.Employee;
import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    public List<Employee> findAll();
}
