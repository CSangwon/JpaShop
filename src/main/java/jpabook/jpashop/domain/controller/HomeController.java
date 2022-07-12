package jpabook.jpashop.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String Home(){
        log.info("Home Controller...");
        return "home"; // home.html로 찾아감
    }
}
