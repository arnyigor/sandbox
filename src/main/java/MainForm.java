import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import kotlin.Unit;
import presentation.MainFormPresenter;
import presentation.MainFormView;
/*
 * Created by JFormDesigner on Fri Nov 29 22:20:46 MSK 2019
 */


/**
 * @author unknown
 */
public class MainForm extends JFrame implements MainFormView {
    private final MainFormPresenter mainPresenter = new MainFormPresenter(this);

    public MainForm() {
        initComponents();
        setVisible(true);
    }

    private void button2ActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainPresenter.savePath(absolutePath, textField2.getText(), textField1.getText(), textField3.getText(), textField4.getText());
            label1.setText(absolutePath);
        }
    }

    private void button1ActionPerformed(ActionEvent e) {
        mainPresenter.downloadFiles(integer -> {
            progressBar1.setValue(integer);
            return Unit.INSTANCE;
        }, () -> {
            label1.setText("Files download success");
            return Unit.INSTANCE;
        });
    }

    private void button3ActionPerformed(ActionEvent e) {
        mainPresenter.filesList();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        button1 = new JButton();
        textField1 = new JTextField();
        button2 = new JButton();
        progressBar1 = new JProgressBar();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        textField2 = new JTextField();
        label4 = new JLabel();
        textField3 = new JTextField();
        textField4 = new JTextField();
        label5 = new JLabel();
        button3 = new JButton();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();

        //---- button1 ----
        button1.setText("Download");
        button1.addActionListener(e -> button1ActionPerformed(e));

        //---- button2 ----
        button2.setText("ChoosePath");
        button2.addActionListener(e -> button2ActionPerformed(e));

        //---- label1 ----
        label1.setText("text");

        //---- label2 ----
        label2.setText("Cookie");

        //---- label3 ----
        label3.setText("Path");

        //---- label4 ----
        label4.setText("From");

        //---- label5 ----
        label5.setText("to");

        //---- button3 ----
        button3.setText("Files list");
        button3.addActionListener(e -> button3ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(button2, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                    .addGap(54, 54, 54)
                                    .addComponent(button3, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                        .addGap(78, 78, 78))))
                            .addGap(0, 8, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(progressBar1, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                        .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
                    .addContainerGap())
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(176, 176, 176)
                    .addComponent(button1)
                    .addContainerGap(194, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label2))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(label3))
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(label4))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(7, 7, 7)
                                    .addComponent(label5))
                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button2)
                        .addComponent(button3))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(button1)
                    .addContainerGap(31, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    private JTextField textField1;
    private JButton button2;
    private JProgressBar progressBar1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField2;
    private JLabel label4;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel label5;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
