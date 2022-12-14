package ee.andres.cafeteria.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(WebRequest request){
        return "index.html";
    }

    @RequestMapping("/cafeteria-event-swagger.json")
    @CrossOrigin(origins = {"*"})
    public ResponseEntity<String> writeConf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        return ResponseEntity.status(HttpStatus.OK).body(new String(getConfFile()));
    }
    private byte[] getConfFile() throws IOException{
        Resource resource = new ClassPathResource("cafeteria-event-swagger.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while((b = reader.read()) != -1){
            bos.write(b);
        }
        return  bos.toByteArray();
    }
}
