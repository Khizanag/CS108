package metropolises;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MetropolisesTable extends AbstractTableModel
    implements MetropolisesConstants {

    private List<Object[]> metropolisesList;
    private final String[] columnNames = { "Metropolis", "Continent", "Population"};

    /**
     *  Used to draw table and update changes on JFrame
     */
    public MetropolisesTable(){
        metropolisesList = new ArrayList<>();
    }

    public void addRow(String metropolis, String continent, long population){
        Object[] newRow = { metropolis, continent, population };
        metropolisesList.add(newRow);
    }

    public void clearTable(){
        metropolisesList.clear();
    }

    @Override
    public int getRowCount() {
        return metropolisesList.size();
    }

    @Override
    public int getColumnCount() {
        return MetropolisesConstants.NUM_COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return metropolisesList.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }
}
