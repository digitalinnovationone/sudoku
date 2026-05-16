package br.com.dio.ui.custom.panel;

import javax.swing.JPanel;
import java.awt.Dimension;

public class MainPanel extends JPanel {

    public MainPanel(final Dimension dimension){
        this.setSize(dimension);
        this.setPreferredSize(dimension);
    }

}
