import java.util.ArrayList;
import java.util.List;

class Rules {
    private String playerMove;
    private String computerMove;
    private String[] availableMoves;

    Rules(String playerMove, String computerMove, String[] availableMoves) {
        this.playerMove = playerMove;
        this.computerMove = computerMove;
        this.availableMoves = availableMoves;
    }

    String getResult() {
        String result;
        if (this.playerMove.equals(this.computerMove)) {
            result = "DRAW";
            return result;
        }
        List<String> winners = new ArrayList<String>();
        result = defineWinOrLose(winners);
        return result;
    }

    String defineWinOrLose(List<String> winners) {
        String result;
        for (int i = 0; i < this.availableMoves.length; i++) {
            if (this.availableMoves[i].equals(this.playerMove)) {
                int currentIndex = i;
                for (int j = 0; j < (this.availableMoves.length - 1) / 2; j++) {
                    currentIndex++;
                    if (currentIndex > this.availableMoves.length - 1) {
                        currentIndex = 0;
                    }
                    winners.add(this.availableMoves[currentIndex]);
                }
                break;
            }
        }
        result = winners.contains(this.computerMove) ? "LOSE" : "WIN";
        return result;
    }
}

