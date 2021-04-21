package ui.mainform;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import presentation.mainform.MainFormPresenter;
import presentation.mainform.MainFormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
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
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        setVisible(true);
        mainPresenter.loadData();
    }

    private void choosePathActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainPresenter.savePath(
                    absolutePath,
                    txtCookie.getText(),
                    txtPath.getText(),
                    txtFrom.getText(),
                    txtTo.getText());
            label1.setText(absolutePath);
        }
    }

    @Override
    public void setUIEnabled(boolean enabled) {
        btnDownload.setEnabled(enabled);
        btnChoosePath.setEnabled(enabled);
        btnFFmpegPath.setEnabled(enabled);
    }

    private void btnFfmPegActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainPresenter.setFfmpegPath(absolutePath);
            mainPresenter.setFfmpegForWin(chbxWin.isSelected());
        }
    }

    private void btnDownloadActionListener(ActionEvent e) {
        mainPresenter.processFiles(txtFileName.getText(), integer -> {
            progressBar1.setValue(integer);
            return Unit.INSTANCE;
        }, (result) -> {
            JOptionPane.showMessageDialog(this, result);
            label1.setText("Files download success");
            return Unit.INSTANCE;
        }, s -> {
            JOptionPane.showMessageDialog(this, s, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            label1.setText("Files download success");
            return Unit.INSTANCE;
        });
    }

    private void btnSaveActionPerformed(ActionEvent e) {
        mainPresenter.saveData();
    }

    @Override
    public void showFirestoreFilePath(@NotNull String path) {

    }

    @Override
    public void setData(@Nullable String absolutePath,
                        @Nullable String cookie,
                        @NotNull String start,
                        @NotNull String end,
                        @Nullable String url) {
        txtCookie.setText(cookie);
        txtFrom.setText(start);
        txtTo.setText(end);
        txtPath.setText(url);
        txtFileName.setText(absolutePath);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        btnDownload = new JButton();
        txtPath = new JTextField();
        btnChoosePath = new JButton();
        progressBar1 = new JProgressBar();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        txtCookie = new JTextField();
        label4 = new JLabel();
        txtFrom = new JTextField();
        txtTo = new JTextField();
        label5 = new JLabel();
        btnFFmpegPath = new JButton();
        txtFileName = new JTextField();
        label6 = new JLabel();
        chbxWin = new JCheckBox();
        btnSave = new JButton();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();

        //---- btnDownload ----
        btnDownload.setText("Download");
        btnDownload.addActionListener(e -> btnDownloadActionListener(e));

        //---- btnChoosePath ----
        btnChoosePath.setText("ChoosePath");
        btnChoosePath.addActionListener(e -> choosePathActionPerformed(e));

        //---- label1 ----
        label1.setText("text");

        //---- label2 ----
        label2.setText("Cookie");

        //---- label3 ----
        label3.setText("Path");

        //---- label4 ----
        label4.setText("From");

        //---- label5 ----
        label5.setText("To");

        //---- btnFFmpegPath ----
        btnFFmpegPath.setText("ffmpegPath");
        btnFFmpegPath.addActionListener(e -> btnFfmPegActionPerformed(e));

        //---- label6 ----
        label6.setText("FileName");

        //---- chbxWin ----
        chbxWin.setText("Windows");

        //---- btnSave ----
        btnSave.setText("Save");
        btnSave.addActionListener(e -> btnSaveActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCookie, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPath, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFileName, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnDownload)))
                            .addGap(0, 16, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(progressBar1, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                                .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtFrom, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                        .addComponent(txtTo, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btnFFmpegPath, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnChoosePath, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
                                    .addGap(34, 34, 34)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btnSave)
                                        .addComponent(chbxWin))
                                    .addGap(0, 36, Short.MAX_VALUE)))
                            .addContainerGap())))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label3))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCookie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label2))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(txtFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFFmpegPath)
                        .addComponent(chbxWin))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label5)
                        .addComponent(btnChoosePath)
                        .addComponent(btnSave))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label6)
                        .addComponent(txtFileName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDownload))
                    .addContainerGap(22, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton btnDownload;
    private JTextField txtPath;
    private JButton btnChoosePath;
    private JProgressBar progressBar1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField txtCookie;
    private JLabel label4;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JLabel label5;
    private JButton btnFFmpegPath;
    private JTextField txtFileName;
    private JLabel label6;
    private JCheckBox chbxWin;
    private JButton btnSave;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
