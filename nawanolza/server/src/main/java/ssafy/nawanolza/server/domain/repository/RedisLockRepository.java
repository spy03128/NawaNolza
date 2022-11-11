package ssafy.nawanolza.server.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(Long key) {
        return redisTemplate.opsForValue().setIfAbsent(generateKey(key), "lock");
    }

    public Boolean unLock(Long key) {
        return redisTemplate.delete(generateKey(key));
    }

    private String generateKey(Long key) {
        return key.toString();
    }

    public String checkAllLock() {
        return redisTemplate.opsForValue().get("ALL_MARKER");
    }
    public Boolean allLock() {
        return redisTemplate.opsForValue().setIfAbsent("ALL_MARKER", "lock");
    }

    public Boolean allUnLock() {
        return redisTemplate.delete("ALL_MARKER");
    }
}
