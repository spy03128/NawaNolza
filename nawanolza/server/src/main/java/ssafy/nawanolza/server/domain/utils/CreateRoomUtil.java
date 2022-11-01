package ssafy.nawanolza.server.domain.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CreateRoomUtil {

    private static final Map<String, Boolean> entryCodes = new ConcurrentHashMap<>();
    private static final Random rand = new Random();
    private final int AVAILABLE_ENTRYCODE_COUNT = 10000;

    @PostConstruct
    private void init() {
        for(int i = 0; i < 10000; i++) {
            entryCodes.put(makeCode(i), Boolean.TRUE);
        }
    }

    // entryCodes 필드에 키들 초기화
    private String makeCode(int num) {
        String numStr = String.valueOf(num);
        for(int i = 0; i < 4 - numStr.length(); i++)
            numStr = "0" + numStr;
        return numStr;
    }

    // 입장코드 랜덤 생성
    private String makeCode() {
        StringBuilder entryCode = new StringBuilder();
        for(int i = 0; i < 4; i++) entryCode.append(rand.nextInt(10));
        return entryCode.toString();
    }

    // 방코드 발행
    public String issueEntryCode() {
        String tempCode = makeEntryCode();
        return tempCode;
    }
    
    // 입장코드 생성
    private String makeEntryCode() {
        String tempCode = makeCode();
        while(isAvailableEntryCode(tempCode))
            tempCode = makeCode();
        entryCodes.remove(tempCode);
        return tempCode;
    }

    // 방코드 회수
    public void retrieveEntryCode(String entryCode) {
        entryCodes.put(entryCode, null);
    }

    // 생성한 방코드가 사용가능한지 확인하는 메서드
    private boolean isAvailableEntryCode(String tempCode) {
        return entryCodes.containsKey(tempCode);
    }
}
