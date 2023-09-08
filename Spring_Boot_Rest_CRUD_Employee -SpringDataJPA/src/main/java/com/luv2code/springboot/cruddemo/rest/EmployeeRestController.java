package com.luv2code.springboot.cruddemo.rest;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
   // private EmployeeDAO employeeDAO;
    private EmployeeService employeeService;

    //inject employee dao(use constructor injection)
    public EmployeeRestController(EmployeeService theEmployeeService){
        employeeService=theEmployeeService;
    }

    //expose"/employees" and return the list of employee
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    //add mapping for GET/employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee theEmployee=employeeService.findById(employeeId);
        if(theEmployee == null){
            throw new RuntimeException("Employee is not found"+employeeId);
        }
        return theEmployee;
    }
    //add mapping for POST/employees - add new employee
    @PostMapping("/employees")
    public Employee theEmployee(@RequestBody Employee theEmployee){
        //also just in case the pass an id in json .. set id to 0
        //this is to force a save of new item .. instance of update

        theEmployee.setId(0);

        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @PutMapping("/employees")
    public  Employee updateEmployee (@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add mapping for delete/employees/{employeeId}
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee (@PathVariable int employeeId){
        Employee tempEmployee = employeeService.findById(employeeId);

        //throw exception if null
        if(tempEmployee == null){
            throw new RuntimeException("Employee is not found " + employeeId);
        }

        employeeService.deleteById(employeeId);
        return "The deleted employee - " + employeeId;
    }
}
