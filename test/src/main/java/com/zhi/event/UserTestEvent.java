package com.zhi.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by xukezhi on 17/10/11.
 */
public class UserTestEvent extends ApplicationEvent{
    public UserTestEvent(Object source) {
        super(source);
    }
}
