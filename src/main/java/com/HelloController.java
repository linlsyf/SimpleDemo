

package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
 public class HelloController {
//test merge123a
    @Autowired
//    public DiscoveryClient client;
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping("/hello")
    public String index() {
//        java.util.List<ServiceInstance> instances = client.getInstances("hello-service");
        return "main jenkin test prohello33";
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

