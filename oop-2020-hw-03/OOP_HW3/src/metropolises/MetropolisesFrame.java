package metropolises;

import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetropolisesFrame extends JFrame
        implements MetropolisesConstants{

    public int FRAME_WIDTH = 750;
    public int FRAME_HEIGHT = 600;

    /* private instance variables */
    private JTextField metropolisTF;
    private JTextField continentTF;
    private JTextField populationTF;

    private JComboBox populationCB;
    private JComboBox matchCB;

    private Metropolises metropolises;

    public MetropolisesFrame(Metropolises m, MetropolisesTable mt){
        metropolises = m;
        initNorthPanel();
        initEastPanel();
        initTable(mt);
        initDefaultUtilities();
    }

    private void initDefaultUtilities(){
        setTitle("Metropolis Viewer");
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void initTextFields(){
        metropolisTF = new JTextField(MetropolisesConstants.TEXT_FIELD_SIZE);
        continentTF = new JTextField(MetropolisesConstants.TEXT_FIELD_SIZE);
        populationTF = new JTextField(MetropolisesConstants.TEXT_FIELD_SIZE);
    }

    private void initNorthPanel(){
        initTextFields();
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Metropolis: "));
        northPanel.add(metropolisTF);
        northPanel.add(new JLabel(MetropolisesConstants.SPACE_BETWEEN_TOP_ITEMS));
        northPanel.add(new JLabel("Continent: "));
        northPanel.add(continentTF);
        northPanel.add(new JLabel(MetropolisesConstants.SPACE_BETWEEN_TOP_ITEMS));
        northPanel.add(new JLabel("Population: "));
        northPanel.add(populationTF);

        add(northPanel, BorderLayout.NORTH);
    }

    private void initEastPanel(){
        JPanel eastPanel = new JPanel(new GridLayout(6,1));
        eastPanel.add(getEastNorthBoxComponents());
        eastPanel.add(getSearchOptionComponents());

        add(eastPanel, BorderLayout.EAST);
    }

    private Box getEastNorthBoxComponents(){
        Box box = Box.createVerticalBox();
        box.add(new JLabel("     "));
        box.add(getAddButton());
        box.add(getSearchButton());
        return box;
    }

    private JButton getAddButton(){
        JButton addBT = new JButton("Add");
        addBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metropolis = metropolisTF.getText();
                String continent = continentTF.getText();
                String population = populationTF.getText();
                metropolises.add(metropolis, continent, population);
            }
        });
        return addBT;
    }

    private JButton getSearchButton(){
        JButton searchBT = new JButton("Search");
        searchBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metropolis = metropolisTF.getText();
                String continent = continentTF.getText();
                String population = populationTF.getText();
                String populationMode = (String)populationCB.getSelectedItem();
                String matchMode = (String)matchCB.getSelectedItem();
                metropolises.search(metropolis, continent, population, populationMode, matchMode);
            }
        });
        return searchBT;
    }

    private JComponent getSearchOptionComponents(){
        JPanel little = new JPanel(new GridLayout(2, 1));
        little.setBorder(new TitledBorder("Search Options"));
        initPopulationComboBox();
        initMatchComboBox();
        little.add(populationCB);
        little.add(matchCB);
        return little;
    }

    private void initPopulationComboBox() {
        populationCB = new JComboBox();
        populationCB.addItem(POPULATION_GRATER_THAN_TEXT);
        populationCB.addItem(POPULATION_LESS_THAT_TEXT);
    }

    private void initMatchComboBox() {
        matchCB = new JComboBox();
        matchCB.addItem(SEARCH_EXACT_LABEL);
        matchCB.addItem(SEARCH_PARTIAL_LABEL);
    }

    private void initTable(MetropolisesTable mt){
        JTable table = new JTable(mt);
        JScrollPane sp = new JScrollPane(table);
        add(sp);
    }

}
