package com.fcjexample.demo.controller;

import com.fcjexample.demo.model.ApiResult;
import com.fcjexample.demo.service.DataViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    DataViewService dataViewService;

    @RequestMapping("/hhh")
    public Object helloha() {
        return "hello haha fcjdormi";
    }

    @RequestMapping("/{tenant}/publish")
    public Object publishDataView(@PathVariable("tenant") String tenant) {
        try {
            String res = null;
            res = dataViewService.publishDataView(tenant);
            return res;
        } catch (Exception e) {
            LOGGER.error("publish error is: {}", "haha", e);
        } finally {
            return "done";
        }
    }

    @RequestMapping("/{tenant}/publish2")
    public Object publishDataView2(@PathVariable("tenant") String tenant) {
        try {
            String res = null;
            res = dataViewService.publishDataView(tenant);
            return res;
        } finally {

        }
    }

    @RequestMapping(value = "/getViewType")
    public ApiResult<Integer> getViewType(@RequestParam(value = "appName") String appName) {
        try {
            Integer viewType = dataViewService.getViewType(appName);
            return new ApiResult<>(0, "success", viewType);
        } catch (Exception e) {
            LOGGER.error("getViewType failed for appName: {} ", appName, e);
            System.out.println("8888" + e.toString());
            //            System.out.println("211888" + e.getCause()); 这个不行
            System.out.println("08888" + e.getMessage());
            //            System.out.println("7888" + e.getStackTrace()); 这个不行
            return new ApiResult<>(1, "failed because " + e.toString(), -999);
        }
    }

}
