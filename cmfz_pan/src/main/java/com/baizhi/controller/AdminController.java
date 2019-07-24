package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(Admin admin, HttpSession session){
        Map<String, Object> map = adminService.queryAdmin(admin);
        if (map.get("code").equals("102")){
            session.setAttribute("adminSession", map.get("admin"));
            map.remove("admin");
        }
        return map;
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("adminSession");
        return "redirect:/jsp/login.jsp";
    }
}
