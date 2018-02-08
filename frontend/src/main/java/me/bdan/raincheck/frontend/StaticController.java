package me.bdan.raincheck.frontend;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {
 
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
}