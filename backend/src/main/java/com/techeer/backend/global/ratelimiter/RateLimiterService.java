package com.techeer.backend.global.ratelimiter;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateLimiterService {

    private final RedissonClient redissonClient;

    /**
     * 특정 키에 대해 카운터를 증가시키고, 제한을 초과했는지 확인합니다.
     *
     * @param counterKey 카운터 키
     * @param limit      허용할 최대 카운트
     * @param period     제한 기간
     * @param unit       제한 기간의 시간 단위
     * @return 현재 카운트
     */
    public Long incrementAndCheckLimit(String counterKey, long limit, long period, TimeUnit unit) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(counterKey);
        long currentCount = atomicLong.incrementAndGet();
        if (currentCount == 1) {
            // 첫 번째 호출 시 TTL 설정
            atomicLong.expire(period, unit);
        }
        log.info("현재 카운트 [{}]: {}", counterKey, currentCount);
        return currentCount;
    }
}
