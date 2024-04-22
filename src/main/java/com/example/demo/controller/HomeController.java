package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.repository.IOfficeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class HomeController {
    @Autowired
    private IEmployeeRepository iEmployeeRepository;

    @Autowired
    private IOfficeRepository iOfficeRepository;

    @GetMapping("/showAll")
    public ModelAndView showAllEmployee(@PageableDefault(3)Pageable pageable, @RequestParam(value = "keyword",required = false) String keyword) {
        ModelAndView modelAndView = new ModelAndView("home");
        if (keyword!= null){
            modelAndView.addObject("keyword",keyword);
            modelAndView.addObject("employeeList", iEmployeeRepository.findAllByNameContains(keyword,pageable));
        }else {
            modelAndView.addObject("employeeList", iEmployeeRepository.findAll(pageable));

        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("createForm");
        modelAndView.addObject("listOffice", iOfficeRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("createForm");
            modelAndView.addObject("listOffice", iOfficeRepository.findAll());
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/employees/showAll");
        iEmployeeRepository.save(employee);
        List<Employee> employeeList = iEmployeeRepository.findAll();
        modelAndView.addObject("employeeList", employeeList);
        return modelAndView;
    }


    @ModelAttribute("employee")
    public Employee getNewEmployee(){
        return new Employee();
    }


    @GetMapping("/{id}/delete")
    public ModelAndView showFormDelete(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("employee", iEmployeeRepository.findById(id).get());
        return modelAndView;
    }
    @PostMapping("/delete")
    public ModelAndView deleteEmployee(Employee employee) {
        ModelAndView modelAndView = new ModelAndView("redirect:/employees/showAll");
        iEmployeeRepository.deleteById(employee.getId());
        return modelAndView;
    }
    @GetMapping("/{id}/update")
    public ModelAndView showFormUpdate(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("/update");
        modelAndView.addObject("employee", iEmployeeRepository.findById(id).get());
        modelAndView.addObject("listOffice", iOfficeRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateBooking(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        iEmployeeRepository.save(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully");
        return "redirect:/employees/showAll";
    }

    @GetMapping("/{id}/view")
    public ModelAndView showInfo(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/view");
        modelAndView.addObject("employee",iEmployeeRepository.findById(id).get());
        return modelAndView;
    }



}
