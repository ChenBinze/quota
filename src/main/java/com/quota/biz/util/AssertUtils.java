package com.quota.biz.util;

import com.quota.biz.exception.QuotaException;

public class AssertUtils {
    public static void isTrue(boolean match, String errorCode, String errorMessage) {
        if(match) {
            throw new QuotaException(errorCode, errorMessage);
        }
    }
}
