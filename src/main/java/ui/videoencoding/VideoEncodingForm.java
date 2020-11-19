/*
 * Created by JFormDesigner on Thu Nov 19 00:07:43 MSK 2020
 */

package ui.videoencoding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import presentation.videoencoding.VideoEncodingPresenter;
import presentation.videoencoding.VideoEncodingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Arny
 */
public class VideoEncodingForm extends JFrame implements VideoEncodingView {
    private final VideoEncodingPresenter presenter = new VideoEncodingPresenter(this);

    public VideoEncodingForm() {
        initComponents();
        pack();
        setVisible(true);
        presenter.onInitUI();
    }

    @Override
    public void setUIEnabled(boolean enabled) {
        button1.setEnabled(enabled);
        button2.setEnabled(enabled);
        button2.setEnabled(enabled);
    }

    private void button1ActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            presenter.ffmpegPath(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void button2ActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            presenter.filesPath(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    @Override
    public void setButton3Title(@NotNull String title) {
        button3.setText(title);
    }

    @Override
    public void showResult(@NotNull String result) {
        JOptionPane.showMessageDialog(this, result);
    }

    @Override
    public void showError(@Nullable String message) {
        JOptionPane.showMessageDialog(this, message, "Result", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setLabel1Text(@NotNull String text) {
        label1.setText(text);
    }

    @Override
    public void setLabel2Text(@NotNull String text) {
        label2.setText(text);
    }

    private void button3ActionPerformed(ActionEvent e) {
        presenter.merge();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        setTitle("VideoEncoding");
        Container contentPane = getContentPane();

        //---- button1 ----
        button1.setText("FFpreg Path");
        button1.addActionListener(e -> button1ActionPerformed(e));

        //---- button2 ----
        button2.setText("Files path");
        button2.addActionListener(e -> button2ActionPerformed(e));

        //---- button3 ----
        button3.setText("Merge");
        button3.addActionListener(e -> button3ActionPerformed(e));

        //---- label1 ----
        label1.setText("ffmpeg Path");

        //---- label2 ----
        label2.setText("Files");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label1, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(button1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(button2))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(button3)
                                    .addGap(4, 4, 4)))
                            .addGap(0, 243, Short.MAX_VALUE))
                        .addComponent(label2, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button1)
                        .addComponent(button2))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button3)
                    .addContainerGap(9, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label1;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
