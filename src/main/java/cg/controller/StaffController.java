package cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staffs")
public class StaffController {

    @GetMapping({"", "/"})
    public String showList(){
        return "staff/list/table-data-staff";
    }

    @GetMapping("/create")
    public String showAddProduct(){
        return "staff/create/form-add-staff";
    }
}
