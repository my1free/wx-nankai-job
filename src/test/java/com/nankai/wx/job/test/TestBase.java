package com.nankai.wx.job.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBase {
    protected Logger logger = LoggerFactory.getLogger("TEST");
    @Test
    public void doNothing() {
        //
    }
}
