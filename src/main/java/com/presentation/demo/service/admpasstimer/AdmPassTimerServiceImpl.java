package com.presentation.demo.service.admpasstimer;

import com.presentation.demo.constance.Constant;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class AdmPassTimerServiceImpl {
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String adminPassword;

    private int lifetime;

    public int getLifetime(){
        return lifetime;
    }

    public String getAdminPassword(){
        return adminPassword;
    }

    @Async
    @Scheduled(fixedRate = Constant.oneSec)
    public void decrementLifetime(){
        readWriteLock.writeLock().lock();
        try {
            lifetime--;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Async
    @Scheduled(fixedRate = Constant.adminPassGenDelay)
    public void generationTimer(){
        readWriteLock.writeLock().lock();
        try {
            adminPassword = getAdminPass();
            lifetime = (int)(Constant.adminPassGenDelay / 1000);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private String getAdminPass(){
        readWriteLock.readLock().lock();
        int length = 6;
        try {
            RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator(length);
            return randomValueStringGenerator.generate();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
