/*
 * Created by JFormDesigner on Sat Feb 20 11:30:36 MSK 2021
 */

package ui.firebase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import presentation.firebasefirestore.FirebaseFirestorePresenter;
import presentation.firebasefirestore.FirebaseFormView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FireBaseForm extends JFrame implements FirebaseFormView {
    private final FirebaseFirestorePresenter presenter;

    public FireBaseForm() {
        presenter = new FirebaseFirestorePresenter(this);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                presenter.setView(null);
                dispose();
            }
        });
        pack();
        setVisible(true);
        loadSettings();
    }

    private void loadSettings() {
        presenter.loadSettings();
    }

    private void label1MouseClicked(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            presenter.initFirestore(absolutePath);
        }
    }

    @Override
    public void setPathText(@NotNull String absolutePath) {
        lblFilePath.setText("File path:" + absolutePath);
    }

    private void btnLoadActionPerformed(ActionEvent e) {
        presenter.loadCollectionData(edtCollection.getText());
    }

    @Override
    public void showError(@Nullable String error) {
        JOptionPane.showMessageDialog(this, error, "Внимание!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSuccess(@NotNull String message) {
        JOptionPane.showMessageDialog(this, message, "Информация!", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setData(String data) {
        textPane1.setText(data);
    }

    @Override
    public void setLoading(boolean loading) {
        if (loading) {
            setTitle("Загрузка данных...подождите");
        } else {
            setTitle("Firebase FireStore");
        }
        btnLoad.setEnabled(!loading);
        btnSend.setEnabled(!loading);
        btnDuplicate.setEnabled(!loading);
        edtCollection.setEnabled(!loading);
        edtCollectionItem.setEnabled(!loading);
        edtKey.setEnabled(!loading);
        btnSaveSettings.setEnabled(!loading);
        edtValue.setEnabled(!loading);
        textPane1.setEnabled(!loading);
    }

    private void btnSendActionPerformed(ActionEvent e) {
        presenter.sendData(edtCollection.getText(),
                edtCollectionItem.getText(),
                edtKey.getText(),
                edtValue.getText()
        );
    }

    private void btnSaveSettingsActionPerformed(ActionEvent e) {
        presenter.savePathSettings();
    }

    private void btnDuplicateActionPerformed(ActionEvent e) {
        presenter.duplicateData();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblFilePath = new JLabel();
        edtCollection = new JTextField();
        btnLoad = new JButton();
        edtKey = new JTextField();
        edtValue = new JTextField();
        edtCollectionItem = new JTextField();
        btnSend = new JButton();
        btnSaveSettings = new JButton();
        scrollPane2 = new JScrollPane();
        textPane1 = new JTextPane();
        btnDuplicate = new JButton();

        //======== this ========
        setTitle("Firebase FireStore");
        setResizable(false);
        Container contentPane = getContentPane();

        //---- lblFilePath ----
        lblFilePath.setText("Choose path to access file");
        lblFilePath.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label1MouseClicked(e);
            }
        });

        //---- edtCollection ----
        edtCollection.setToolTipText("\u0418\u043c\u044f \u043a\u043e\u043b\u043b\u0435\u043a\u0446\u0438\u0438");

        //---- btnLoad ----
        btnLoad.setText("Load");
        btnLoad.addActionListener(e -> btnLoadActionPerformed(e));

        //---- edtKey ----
        edtKey.setToolTipText("\u041a\u043b\u044e\u0447");

        //---- edtValue ----
        edtValue.setToolTipText("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435");

        //---- edtCollectionItem ----
        edtCollectionItem.setToolTipText("\u042d\u043b\u0435\u043c\u0435\u043d\u0442 \u043a\u043e\u043b\u043b\u0435\u043a\u0446\u0438\u0438");

        //---- btnSend ----
        btnSend.setText("Send");
        btnSend.addActionListener(e -> btnSendActionPerformed(e));

        //---- btnSaveSettings ----
        btnSaveSettings.setText("Save path");
        btnSaveSettings.addActionListener(e -> btnSaveSettingsActionPerformed(e));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(textPane1);
        }

        //---- btnDuplicate ----
        btnDuplicate.setText("Duplicate");
        btnDuplicate.addActionListener(e -> btnDuplicateActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnSaveSettings))
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addComponent(btnLoad)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(edtCollection, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(edtKey, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(edtCollectionItem, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                                                        .addComponent(edtValue, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnSend)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDuplicate)))
                                                .addGap(0, 11, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSaveSettings))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLoad)
                                        .addComponent(edtCollection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(edtCollectionItem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSend)
                                        .addComponent(btnDuplicate))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(edtKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(edtValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblFilePath;
    private JTextField edtCollection;
    private JButton btnLoad;
    private JTextField edtKey;
    private JTextField edtValue;
    private JTextField edtCollectionItem;
    private JButton btnSend;
    private JButton btnSaveSettings;
    private JScrollPane scrollPane2;
    private JTextPane textPane1;
    private JButton btnDuplicate;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
