package com.zhi.service;

import com.zhi.event.UserTestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Created by xukezhi on 17/10/11.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public String test() {

        System.out.println("user is testing ");
        applicationEventPublisher.publishEvent(new UserTestEvent("handler is  testing"));
        return null;
    }

}
