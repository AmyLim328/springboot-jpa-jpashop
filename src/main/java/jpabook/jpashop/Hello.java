package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Getter @Setter
public class Hello {
    private String data;

    @Controller
    public static class HelloController {

        @GetMapping("hello")
        public String hello(Model model) {
            model.addAttribute("data", "hello!");
            return "hello";
        }
    }
}
