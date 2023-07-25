package cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {
    @GetMapping()
    public String test(){
        return "report/report";
    }

}
