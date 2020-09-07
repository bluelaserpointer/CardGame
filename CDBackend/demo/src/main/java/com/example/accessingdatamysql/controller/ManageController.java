package com.example.accessingdatamysql.controller;


import com.example.accessingdatamysql.GlobalConstants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/manage") // This means URL's start with /demo (after Application path)
public class ManageController {

    @RequestMapping(value = "/doDebugOutput")
    public String doDebugOutput() {
        GlobalConstants.doDebug = true;
        System.out.println("------------debug started------------");
        return "ok.";
    }
    @RequestMapping(value = "/stopDebugOutput")
    public String stopDebugOutput() {
        GlobalConstants.doDebug = false;
        System.out.println("============debug stopped============");
        return "ok.";
    }
    @RequestMapping(value = "/getHostName")
    public String getHostName() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostname = "UnknownHost";
        }
        return hostname;
    }
}
