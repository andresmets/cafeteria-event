package ee.andres.cafeteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(WebRequest request){
        return "index.html";
    }

}
