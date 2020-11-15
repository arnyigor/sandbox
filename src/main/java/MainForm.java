import presentation.MainFormPresenter;
import presentation.MainFormView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/*
 * Created by JFormDesigner on Fri Nov 29 22:20:46 MSK 2019
 */


/**
 * @author unknown
 */
public class MainForm extends JFrame  implements MainFormView {
    private final MainFormPresenter mainPresenter = new MainFormPresenter(this);

    public MainForm() {
        initComponents();
        setVisible(true);
    }

    private void button1ActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainPresenter.downloadFiles(absolutePath);
        }

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
                                .addContainerGap(105, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(button1)
                                .addContainerGap(155, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
