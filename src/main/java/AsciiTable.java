import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

import java.util.ArrayList;
import java.util.List;

public class AsciiTable {
    private String[] args;
    private String[][] table;

    AsciiTable(String[] args) {
        this.args = args;
        this.table = new String[args.length][args.length + 1];
    }

    private void initTable() {
        List<String> winners = new ArrayList<String>();
        for (int i = 0; i < this.table.length; i++) {
            String compMove = this.args[i];
            for (int j = 0; j < this.table.length + 1; j++) {
                if (j == 0) {
                    table[i][j] = args[i];
                } else {
                    String userMove = this.args[j - 1];
                    if (compMove.equals(userMove)) {
                        this.table[i][j] = "DRAW";
                    } else {
                        Rules rules = new Rules(userMove, compMove, args);
                        this.table[i][j] = rules.defineWinOrLose(winners);
                        winners.clear();
                    }
                }
            }
        }
    }

    private String[][] getData() {
        initTable();
        return this.table;
    }

    private Column[] getColumns() {
        Column[] columns = new Column[this.args.length + 1];
        for (int i = 0; i < this.args.length + 1; i++) {
            if (i == 0) {
                columns[0] = new Column().header("  \\User \nPC \\").headerAlign(HorizontalAlign.LEFT)
                        .dataAlign(HorizontalAlign.LEFT);
            } else {
                columns[i] = new Column().header(this.args[i - 1]).headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT);
            }
        }
        return columns;
    }

    @Override
    public String toString() {
        return com.github.freva.asciitable.AsciiTable.getTable(getColumns(), getData());
    }
}
