package com.zhi.service;

import com.zhi.event.UserTestEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Created by xukezhi on 17/10/11.
 */
@Service
public class UserTestEventHandler implements ApplicationListener<UserTestEvent>{

    @Override
    public void onApplicationEvent(UserTestEvent userTestEvent) {
        System.out.println("hander recevied"+userTestEvent.getSource());
    }
}
