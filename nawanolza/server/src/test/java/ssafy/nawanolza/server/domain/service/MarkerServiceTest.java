package ssafy.nawanolza.server.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.repository.MapCharacterRedisRepository;
import ssafy.nawanolza.server.domain.repository.RedisLockRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ActiveProfiles("local")
class MarkerServiceTest {

    @Autowired
    RedisLockRepository redisLockRepository;

    @Autowired
    MarkerService markerService;

    @Autowired
    MapCharacterRedisRepository mapCharacterRedisRepository;

    @BeforeEach
    public void insert() {
        Marker marker = Marker.builder().markerId(1L).build();
        mapCharacterRedisRepository.save(marker);
    }

    @AfterEach
    public void delete() {
        mapCharacterRedisRepository.deleteAll();
    }

    @Test
    void 마커_접근_동시에_100개() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    markerService.questStart(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
            
        }

        latch.await();
        Marker marker = mapCharacterRedisRepository.findById(1L).orElseThrow();
        Assertions.assertThat(marker.getIsPlayGame()).isEqualTo(100);
    }
}