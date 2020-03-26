package com.fcjexample.demo.controller;

import com.fcjexample.demo.model.ApiResult;
import com.fcjexample.demo.model.TestEntity;
import com.fcjexample.demo.service.DataViewService;
import com.fcjexample.demo.util.exception.DataViewException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public String publishDataView(@PathVariable("tenant") String tenant) throws Exception {
        try {
            String res = null;
            res = dataViewService.publishDataView(tenant);
            return res;
        } finally {
            // todo
        }
    }

    @RequestMapping("/{tenant}/feature/testException/internal")
    // 该方法里的testStringException方法里有显示地throw exception，
    // 所以testExceptionInternal也要显示地throws exception.
    public String testExceptionInternal(@PathVariable("tenant") String tenant) throws Exception {
        //    public String testExceptionInternal(@PathVariable("tenant") String tenant) {
        try {
            //                        String res = dataViewService.publishDataView(tenant);
            String s = dataViewService.testStringException(tenant);

            return "eee";
        } catch (Exception e) {
            LOGGER.error("testExceptionInternal failed for {}", tenant, e);
            throw e;
            //            return null;
        } finally {
        }
    }

    @RequestMapping("/{tenant}/publishEntity")
    public TestEntity publishEntity(@PathVariable("tenant") String tenant) throws Exception {
        try {
            TestEntity res = null;
            if (tenant == "test") {
                throw new RuntimeException();
            }
            res = dataViewService.publishEntity(tenant);

            return res;
        } catch (Exception e) {
            LOGGER.error("publishEntity failed for {}. ", tenant, e);
            throw e;
        } finally {
            // todo
        }
    }

    @RequestMapping("/{tenant}/publish2")
    // 该方法里的testStringException方法里没有显示地throw exception，
    // 所以testExceptionInternal不需要显示地throws exception.
    public String publishDataView2(@PathVariable("tenant") String tenant) {
        try {
            String res = null;
            res = dataViewService.publishDataView(tenant);
            return res;
        } catch (Exception e) {
            LOGGER.error("publish failed for {}. ", tenant, e);
            throw e;
        } finally {
            // todo
        }
    }

    @RequestMapping("/{tenant}/publishType")
    public Integer publishType(@PathVariable("tenant") String tenant) {
        try {
            Integer res = null;
            res = dataViewService.getViewType(tenant);
            return res;
        } catch (Exception e) {
            LOGGER.error("publish type failed for {}. ", tenant, e);
            throw e;
        } finally {
            // todo
        }
    }

    @RequestMapping("/{tenant}/customPublish")
    // responseEntity
    // errorHandler
    public String customPublish(@PathVariable("tenant") String tenant) {
        try {
            String customRes = dataViewService.publishDataView(tenant);
            return customRes;
        } catch (DataViewException exc) {
            LOGGER.error("customPublish failed for {}. ", tenant, exc);
            throw new ResponseStatusException(HttpStatus.LENGTH_REQUIRED, "custom message", exc);
        } finally {
            // todo
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
