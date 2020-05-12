package com.rh.project.retrydemo;
import com.rh.project.retrydemo.service.RetryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RetryServiceTest extends RetryDemoApplicationTests {

    @Autowired
    private RetryService retryService;

    @Test
    public void retry() {
        int count = retryService.retry(1);
        System.out.println("库存为 ：" + count);
    }
}