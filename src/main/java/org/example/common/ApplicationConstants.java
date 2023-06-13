package org.example.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {

    @UtilityClass
    public class Transaction {
        public static final double MAX_VALUE_FOR_TRANSACTION = 1500.0;
        public static final double MAX_VALUE_FOR_TRANSACTION_BY_DAY = 10000.0;
        public static final double MAX_VALUE_FOR_TRANSACTION_BY_MONTH = 25000.0;
        public static final double COURSE_FOR_UAH = 38.2;
        public static final double COURSE_FOR_EUR = 1.05;
    }
}
