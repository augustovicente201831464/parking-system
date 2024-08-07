package com.cunoc.edu.gt.ports.output.event;

import com.cunoc.edu.gt.ports.output.Event;

import java.time.LocalDateTime;

/**
 * EnrollingEvent
 *
 * @author Augusto Vicente
 */
public class EnrollingEvent<USER, COMPONENT, PROCESS, TRANSACTION_ID, CRITERIA> extends Event<USER, COMPONENT, PROCESS, TRANSACTION_ID, CRITERIA> {

    public EnrollingEvent(String message, LocalDateTime date, USER user, COMPONENT component, PROCESS process, TRANSACTION_ID transactionId, CRITERIA criteria) {
        super(message, date, user, component, process, transactionId, criteria);
    }

}
