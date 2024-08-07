package com.cunoc.edu.gt.ports.output;

import java.util.UUID;

public class TransactionId {

    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}