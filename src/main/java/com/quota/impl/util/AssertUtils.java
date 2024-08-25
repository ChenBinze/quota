package com.quota.impl.util;

import com.quota.impl.exception.QuotaException;

public class AssertUtils {
    public static void isTrue(boolean match, String errorCode, String errorMessage) {
        if(match) {
            throw new QuotaException(errorCode, errorMessage);
        }
    }
}
