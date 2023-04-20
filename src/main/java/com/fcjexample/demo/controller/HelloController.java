package com.fcjexample.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcjexample.demo.entity.PreviewResponse;
import com.fcjexample.demo.entity.ReplayPreviewEntry;
import com.fcjexample.demo.model.ApiResult;
import com.fcjexample.demo.model.TestEntity;
import com.fcjexample.demo.service.DataViewService;
import com.fcjexample.demo.service.HelloService;
import com.fcjexample.demo.util.exception.DataViewException;
import com.swrve.ratelimitedlogger.RateLimitedLog;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    private static final RateLimitedLog rateLimitedLog = RateLimitedLog
            .withRateLimit(LOGGER)
            .maxRate(5).every(Duration.ofSeconds(30))
            .build();
    private static AtomicLong count = new AtomicLong();

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++)
            //            rateLimitedLog.info(count.getAndIncrement() + "Testing: {}", i);
            rateLimitedLog.info("Testing: {}, {}", count.getAndIncrement(), i);
        rateLimitedLog.info("End");
    }

    @Autowired
    DataViewService dataViewService;

    @Autowired
    private HelloService hello;

    @Autowired
    ApplicationContext context;

    @RequestMapping("/hhh")
    public Object helloha() {
        return "hello haha fcjdormi";
    }

    @RequestMapping("/hhh02")
    public Object helloha02(@RequestBody String rawExternal) {
        return rawExternal + "hello haha fcjdormi";
    }

    @RequestMapping("/hhh03")
    public Object helloha03(@RequestBody TestEntity entity) {
        return entity.getName() + "hello haha fcjdormi";
    }

    @RequestMapping("/hhh04/{id}")
    public void helloha04(@PathVariable("id") Integer id) {
        rateLimitedLog.info("{} + hhh04: {}", count.getAndIncrement(), id);
    }

    @GetMapping("/test01")
    public String test01Controller() {
        LOGGER.info(Thread.currentThread().getName() + " 进入test01Controller方法");
        String result = hello.sayHello();
        LOGGER.info(Thread.currentThread().getName() + " 从test01Controller方法返回");
        return result;
    }

    @GetMapping("/test02")
    public String test02Controller(
            @RequestParam(value = "cpName", defaultValue = "") String cpName) {
        LOGGER.info(Thread.currentThread().getName() + " 进入test01Controller方法");
        String result = "ha " + cpName;
        LOGGER.info(Thread.currentThread().getName() + " 从test01Controller方法返回");
        return result;
    }

    @GetMapping("/helloha")
    public Callable<String> helloController() {
        LOGGER.info(Thread.currentThread().getName() + " 进入helloController方法");
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                LOGGER.info(Thread.currentThread().getName() + " 进入call方法");
                String say = hello.sayHello();
                LOGGER.info(Thread.currentThread().getName() + " 从helloService方法返回");
                return say;
            }
        };
        LOGGER.info(Thread.currentThread().getName() + " 从helloController方法返回");
        return callable;
    }

    /**
     * 带超时时间的异步请求 通过WebAsyncTask自定义客户端超时间
     *
     * @return
     */
    @GetMapping("/world01")
    public WebAsyncTask<String> worldController() {
        LOGGER.info(Thread.currentThread().getName() + " 进入helloController方法");

        // 5s钟没返回，则认为超时
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(5000, new Callable<String>() {

            @Override
            public String call() throws Exception {
                LOGGER.info(Thread.currentThread().getName() + " 进入call方法");
                String say = hello.sayHello();
                LOGGER.info(Thread.currentThread().getName() + " 从helloService方法返回");
                return say;
            }
        });

        LOGGER.info(Thread.currentThread().getName() + " 从helloController方法返回");

        webAsyncTask.onCompletion(new Runnable() {

            @Override
            public void run() {
                LOGGER.info(Thread.currentThread().getName() + " 执行完毕");
            }
        });

        webAsyncTask.onTimeout(new Callable<String>() {

            @Override
            public String call() throws Exception {
                LOGGER.info(Thread.currentThread().getName() + " onTimeout");
                // 超时的时候，直接抛异常，让外层统一处理超时异常
                throw new TimeoutException("调用超时");
            }
        });
        return webAsyncTask;
    }

    @RequestMapping(value = "/test")
    public String handleRequest() {
        Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                context, HandlerMapping.class, true, false);

        matchingBeans.forEach((k, v) -> System.out.printf("order:%s %s=%s%n",
                ((Ordered) v).getOrder(),
                k, v.getClass().getSimpleName()));
        return "response from /test";
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

            return s;
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
    public ApiResult<Integer> getViewType(
            @RequestParam(value = "appName") String appName,
            @RequestHeader(value = "name", required = false) String name) {
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

    @RequestMapping("/testStream")
    public StreamingResponseBody testStream() throws Exception {
        PreviewResponse previewResponse = new PreviewResponse();
        Map<String, ReplayPreviewEntry> finalPreviewEntryMap = new HashMap<>();
        finalPreviewEntryMap.put("01", generate("01"));
        finalPreviewEntryMap.put("02", generate("02"));

        previewResponse.setErrorLines(10);
        previewResponse.setTotalLines(30);
        previewResponse.setExamples(finalPreviewEntryMap);
        StreamingResponseBody responseBody = wrapResponse(previewResponse);
        return responseBody;
    }

    //    @RequestMapping("/testStream")
    //    public ResponseEntity<StreamingResponseBody> testStream() throws Exception {
    //        PreviewResponse previewResponse = new PreviewResponse();
    //        Map<String, ReplayPreviewEntry> finalPreviewEntryMap = new HashMap<>();
    //        finalPreviewEntryMap.put("01", generate("01"));
    //        finalPreviewEntryMap.put("02", generate("02"));
    //
    //        previewResponse.setErrorLines(10);
    //        previewResponse.setTotalLines(30);
    //        previewResponse.setExamples(finalPreviewEntryMap);
    //        StreamingResponseBody responseBody = wrapResponse(previewResponse);
    //        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    //    }

    @RequestMapping("/testStreamObject")
    public ResponseEntity<StreamingResponseBody> testStreamObject() throws Exception {
        PreviewResponse previewResponse = new PreviewResponse();
        Map<String, ReplayPreviewEntry> finalPreviewEntryMap = new HashMap<>();
        finalPreviewEntryMap.put("01", generate("01"));
        finalPreviewEntryMap.put("02", generate("02"));

        previewResponse.setErrorLines(10);
        previewResponse.setTotalLines(30);
        previewResponse.setExamples(finalPreviewEntryMap);
        StreamingResponseBody responseBody = wrapResponseObject(previewResponse);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private ReplayPreviewEntry generate(String name) {
        ReplayPreviewEntry replayPreviewEntry01 = new ReplayPreviewEntry();
        replayPreviewEntry01.setColumnName(name);
        replayPreviewEntry01.setExamples(Arrays.asList("foo", name));

        return replayPreviewEntry01;
    }

    private StreamingResponseBody wrapResponse(PreviewResponse response)
            throws Exception {

        LOGGER.info("first is: " + response.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(response);
        LOGGER.info("second is: " + str);

        InputStream inputStream = IOUtils.toInputStream(str);
        FileOutputStream fos6 = new FileOutputStream("ha6.ser");
        IOUtils.copy(inputStream, fos6);
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        FileInputStream is = new FileInputStream("ha6.ser");
        int num = is.read();

        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                //                BufferedOutputStream bo = new BufferedOutputStream(outputStream);
                //                bo.write(str.getBytes(), 0, 20);
                //                bo.write(str.getBytes());

                //
                //                outputStream.write(str.getBytes());
                //                IOUtils.write("data", outputStream, "UTF-8");

                IOUtils.copy(IOUtils.toInputStream(str, Charset.defaultCharset()), outputStream);

                //                Reader reader = new StringReader(str);
                //                IOUtils.copy(reader, outputStream);

            }
        };

        return responseBody;
    }

    private StreamingResponseBody wrapResponseObject(PreviewResponse response) {

        LOGGER.info("first is: " + response);
        try {
            FileOutputStream fos = new FileOutputStream("haha.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(response);
            System.out.println("Done");
            // closing resources
            oos.close();
            fos.close();

            FileInputStream is = new FileInputStream("haha.ser");
            ObjectInputStream ois = new ObjectInputStream(is);
            PreviewResponse emp = (PreviewResponse) ois.readObject();

            ois.close();
            is.close();
            System.out.println(emp.toString());
        } catch (Exception e) {
            LOGGER.error("errors. ", e);
        }

        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                // write object to file
                oos.writeObject(response);
                oos.close();
            }
        };

        return responseBody;
    }

}
