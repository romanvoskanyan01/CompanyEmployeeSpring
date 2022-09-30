package com.example.companyemployeespring.controller;
import com.example.companyemployeespring.entity.Companis;
import com.example.companyemployeespring.repository.CompanisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompanisController {
@Autowired
    private CompanisRepository companisRepository;

    @GetMapping("/companis")
    public String companis(ModelMap modelMap){
        List<Companis> all = companisRepository.findAll();
        modelMap.addAttribute("companis", all);
        return "companis";
    }

    @GetMapping("/companis/add")
    public String addCompanisPage(){
        return "addCompanis";
    }


    @PostMapping("/companis/add")
    public String addCompanis(@ModelAttribute Companis companis){
        companisRepository.save(companis);
        return "redirect:/companis";
    }
    @GetMapping("/companis/delete")
    public String delete(@RequestParam("id") int id){
        companisRepository.deleteById(id);
        return "redirect:/companis";

    }
}
