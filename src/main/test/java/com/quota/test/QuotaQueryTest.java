package com.quota.test;

import com.quota.QuotaApplication;
import com.quota.api.service.QuotaQueryService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuotaApplication.class)
public class QuotaQueryTest {

    @Autowired
    private QuotaQueryService quotaQueryService;


}
