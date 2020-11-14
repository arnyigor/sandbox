import presentation.MainPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/*
 * Created by JFormDesigner on Fri Nov 29 22:20:46 MSK 2019
 */



/**
 * @author unknown
 */
public class MainForm extends JFrame {
    private final MainPresenter mainPresenter = new MainPresenter();
    public MainForm() {
        initComponents();
        setVisible(true);
    }

    private void button1ActionPerformed(ActionEvent e) {
        mainPresenter.downloadFiles();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        button1 = new JButton();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();

        //---- button1 ----
        button1.setText("DownloadFiles");
        button1.addActionListener(e -> button1ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(77, 77, 77)
                    .addComponent(button1)
                    .addContainerGap(95, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(button1)
                    .addContainerGap(100, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
