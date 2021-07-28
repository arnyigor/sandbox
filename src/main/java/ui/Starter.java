/*
 * Created by JFormDesigner on Thu Nov 19 00:15:35 MSK 2020
 */

package ui;

import ui.firebase.FireBaseForm;
import ui.mainform.MainForm;
import ui.mainframe.MainFrame;
import ui.videoencoding.VideoEncodingForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Arny
 */
public class Starter extends JFrame {
    private String[] args;

    public Starter(String[] args) {
        this.args = args;
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2
        );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setVisible(true);
    }

    private void menuItem1ActionPerformed(ActionEvent e) {
        new MainFrame(args);
        dispose();
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        new MainForm();
        dispose();
    }

    private void menuItem3ActionPerformed(ActionEvent e) {
        new VideoEncodingForm();
        dispose();
    }

    private void menuItem4ActionPerformed(ActionEvent e) {
        new FireBaseForm();
//        openFireabaseComposeWindow();
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Open");

                //---- menuItem1 ----
                menuItem1.setText("MainFrame");
                menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("MainForm");
                menuItem2.addActionListener(e -> menuItem2ActionPerformed(e));
                menu1.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText("VideoEncoding");
                menuItem3.addActionListener(e -> menuItem3ActionPerformed(e));
                menu1.add(menuItem3);

                //---- menuItem4 ----
                menuItem4.setText("FirebaseFirestore");
                menuItem4.addActionListener(e -> menuItem4ActionPerformed(e));
                menu1.add(menuItem4);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGap(0, 188, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGap(0, 109, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
