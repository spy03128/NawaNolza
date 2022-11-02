package ssafy.nawanolza.server.domain.utils;

import lombok.extern.slf4j.Slf4j;
import ssafy.nawanolza.server.domain.repository.HideAndSeekGameRoomRepository;

import java.util.Random;

@Slf4j
public class CreateRoomUtil {
    private static final Random rand = new Random();

    // 입장코드 랜덤 생성
    private static final String makeCode() {
        StringBuilder entryCode = new StringBuilder();
        for(int i = 0; i < 4; i++) entryCode.append(rand.nextInt(10));
        return entryCode.toString();
    }

    // 방코드 발행
    public static final String issueEntryCode(HideAndSeekGameRoomRepository hideAndSeekGameRoomRepository) {
        String makeEntryCode = CreateRoomUtil.makeCode();
        while(hideAndSeekGameRoomRepository.findByEntryCode(makeEntryCode) != null)
            log.info("makeEntryCode = ", makeEntryCode);
            makeEntryCode = CreateRoomUtil.makeCode();
        return makeEntryCode;
    }
}