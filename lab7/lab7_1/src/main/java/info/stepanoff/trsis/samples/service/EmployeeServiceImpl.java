package info.stepanoff.trsis.samples.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.stepanoff.trsis.samples.db.dao.EmployeeRepository;
import info.stepanoff.trsis.samples.db.model.Employee;
import info.stepanoff.trsis.samples.rest.model.EmployeeDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    @Override
    public EmployeeDTO create(Integer id, String name, String department, String position){
        return objectMapper.convertValue(employeeRepository.save(new Employee(id, name, department, position)), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> readAll(){
        return employeeRepository.findAll().stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO read(Integer id){
        return objectMapper.convertValue(employeeRepository.findById(id), EmployeeDTO.class);
    }

    @Override
    public boolean delete(Integer id){
        if (!employeeRepository.existsById(id))
            return false;
        employeeRepository.deleteById(id);
        return true;
    }
}
