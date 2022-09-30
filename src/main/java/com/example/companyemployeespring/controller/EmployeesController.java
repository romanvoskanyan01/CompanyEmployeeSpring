package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Companis;
import com.example.companyemployeespring.entity.Employees;
import com.example.companyemployeespring.repository.CompanisRepository;
import com.example.companyemployeespring.repository.EmployeesRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeesController {
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private CompanisRepository companisRepository;

    @Value("${employees.images.folder}")
    private String folderPath;

    @GetMapping("/employees")
    public String employees(ModelMap modelMap) {
        List<Employees> all = employeesRepository.findAll();
        modelMap.addAttribute("employees", all);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeesPage(ModelMap modelMap) {
        List<Companis> all = companisRepository.findAll();
        modelMap.addAttribute("companies", all);
        return "addEmployees";
    }

    @PostMapping("/employees/add")
    public String addEmployees(@ModelAttribute Employees employees,
                               @RequestParam("employeesImage") MultipartFile file) throws IOException {
        Companis company = companisRepository.getReferenceById(employees.getCompany().getId());
        company.setSize(company.getSize() + 1);
        companisRepository.save(company);
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newfile = new File(folderPath + File.separator + fileName);
            file.transferTo(newfile);
            employees.setProfilePic(fileName);
            employeesRepository.save(employees);
        }


        return "redirect:/employees";
    }


    @GetMapping("/employees/delete")
    public String delete(@RequestParam("id") int id) {
        Optional<Employees> employees = employeesRepository.findById(id);
        if (employees.isPresent()) {
            Companis companis = employees.get().getCompany();
            if (companis != null) {
                companis.setSize(companis.getSize() - 1);
                companisRepository.save(companis);

            }
        }
        employeesRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }
}
