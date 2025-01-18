package com.techeer.backend.global.lock;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistributedLockService {

    private final RedissonClient redissonClient;

    public RLock getLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, unit);
            if (isLocked) {
                log.info("락을 획득했습니다. 키: {}", lockKey);
                return lock;
            } else {
                log.warn("락을 획득하지 못했습니다. 키: {}", lockKey);
                return null;
            }
        } catch (InterruptedException e) {
            log.error("락 획득 중 인터럽트 발생. 키: {}", lockKey, e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void releaseLock(RLock lock) {
        if (lock != null && lock.isHeldByCurrentThread()) {
            try {
                lock.unlock();
                log.info("락을 해제했습니다. 키: {}", lock.getName());
            } catch (IllegalMonitorStateException e) {
                log.error("락 해제 실패. 현재 스레드가 락을 보유하고 있지 않습니다. 키: {}", lock.getName(), e);
            }
        }
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }
}
