package com.kosta.bus_reserve.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/mypage/*")
public class MypageController {

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/mypage_info")
    public String mypageInfo(){
        return "mypage/mypage_info";
    }
}
