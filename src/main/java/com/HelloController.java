package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
 public class HelloController {

    @Autowired
//    public DiscoveryClient client;
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping("/prohello")
    public String index() {
//        java.util.List<ServiceInstance> instances = client.getInstances("hello-service");
        return "jenkin test prohello";
    }
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    public String index() {
////        java.util.List<ServiceInstance> instances = client.getInstances("hello-service");
//        return "Hello World eureka-product1";
//    }



//    @GetMapping("/hello")
//        @HystrixCommand(fallbackMethod = "queryByIdFallback")
//        public String hello() {
//
//            return "product-1";
//        }



    }
