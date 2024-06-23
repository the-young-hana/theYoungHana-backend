package hana.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Admin", description = "관리자 컨트롤러")
public class AdminController {
    @GetMapping(value = "/")
    public String homeGET() {
        return "index";
    }
}
