package com.cunoc.edu.gt.ports.output.event;

import com.cunoc.edu.gt.ports.output.Event;

import java.time.LocalDateTime;

/**
 * ModificationEvent
 *
 * @author balamkiche
 */

public class ModificationEvent<USER, COMPONENT, PROCESS, TRANSACTION_ID, CRITERIA> extends Event<USER, COMPONENT, PROCESS, TRANSACTION_ID, CRITERIA> {

    public ModificationEvent(String message, LocalDateTime date, USER user, COMPONENT component, PROCESS process, TRANSACTION_ID transactionId, CRITERIA criteria) {
        super(message, date, user, component, process, transactionId, criteria);
    }
}
