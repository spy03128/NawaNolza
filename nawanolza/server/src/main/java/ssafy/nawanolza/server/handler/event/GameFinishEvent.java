package ssafy.nawanolza.server.handler.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GameFinishEvent {
    private String entryCode;
    private boolean isWinTagger;
    private List<Winner> winnerList;

    public GameFinishEvent(String entryCode, boolean isWinTagger) {
        this.entryCode = entryCode;
        this.isWinTagger = isWinTagger;
    }

    @AllArgsConstructor
    private class Winner {
        private String name;
        private String image;
    }

    public void make(String name, String image) {
        if (winnerList == null) winnerList = new ArrayList<>();
        winnerList.add(new Winner(name, image));
    }
}
