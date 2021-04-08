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
import java.util.List;

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

    @Override
    public void setCollections(@NotNull List<String> list) {
        list.forEach(s -> cbxCollection.addItem(s));
    }

    private void btnLoadActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            String docName = edtDocument.getText();
            if (!docName.isEmpty()) {
                presenter.loadDocumentData(item.toString(), docName);
            } else {
                presenter.loadCollectionData(item.toString(), chboxIds.isSelected());
            }
        }
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
        cbxCollection.setEnabled(!loading);
        edtDocument.setEnabled(!loading);
        edtKey.setEnabled(!loading);
        btnSaveSettings.setEnabled(!loading);
        edtValue.setEnabled(!loading);
        textPane1.setEnabled(!loading);
        btnRemove.setEnabled(!loading);
        btnRemoveField.setEnabled(!loading);
        chboxIds.setEnabled(!loading);
    }

    private void btnSendActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            String edtKeyText = edtKey.getText();
            String edtValueText = edtValue.getText();
            int dialogResult = JOptionPane.showConfirmDialog(null, String.format("Отправить данные %s->%s?", edtKeyText, edtValueText));
            if (dialogResult == JOptionPane.YES_OPTION) {
                presenter.sendData(
                        item.toString(),
                        edtDocument.getText(),
                        edtKeyText,
                        edtValueText
                );
            }
        }
    }

    private void btnSaveSettingsActionPerformed(ActionEvent e) {
        presenter.savePathSettings();
    }

    private void btnDuplicateActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            presenter.duplicateData(item.toString(), chboxIds.isSelected());
        }
    }

    private void btnRemoveActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            String collection = item.toString();
            String documentText = edtDocument.getText();
            int dialogResult = JOptionPane.showConfirmDialog(null, String.format("Удалить документ %s?", documentText));
            if (dialogResult == JOptionPane.YES_OPTION) {
                presenter.removeDocument(collection, documentText, chboxIds.isSelected());
            }
        }
    }

    private void btnRemoveFieldActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            String collection = item.toString();
            String documentText = edtDocument.getText();
            String key = edtKey.getText();
            int dialogResult = JOptionPane.showConfirmDialog(null, String.format("Удалить поле %s документа в id=%s?", key, documentText));
            if (dialogResult == JOptionPane.YES_OPTION) {
                presenter.removeField(collection, documentText, key);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblFilePath = new JLabel();
        btnLoad = new JButton();
        edtKey = new JTextField();
        edtValue = new JTextField();
        edtDocument = new JTextField();
        btnSend = new JButton();
        btnSaveSettings = new JButton();
        scrollPane2 = new JScrollPane();
        textPane1 = new JTextPane();
        btnDuplicate = new JButton();
        cbxCollection = new JComboBox();
        chboxIds = new JCheckBox();
        btnRemove = new JButton();
        btnRemoveField = new JButton();

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

        //---- btnLoad ----
        btnLoad.setText("Load");
        btnLoad.addActionListener(e -> btnLoadActionPerformed(e));

        //---- edtKey ----
        edtKey.setToolTipText("\u041a\u043b\u044e\u0447");

        //---- edtValue ----
        edtValue.setToolTipText("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435");

        //---- edtDocument ----
        edtDocument.setToolTipText("\u042d\u043b\u0435\u043c\u0435\u043d\u0442 \u043a\u043e\u043b\u043b\u0435\u043a\u0446\u0438\u0438");

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

        //---- chboxIds ----
        chboxIds.setText("\u0422\u043e\u043b\u044c\u043a\u043e id \u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u0430");

        //---- btnRemove ----
        btnRemove.setText("Remove Document");
        btnRemove.addActionListener(e -> btnRemoveActionPerformed(e));

        //---- btnRemoveField ----
        btnRemoveField.setText("Remove field");
        btnRemoveField.addActionListener(e -> btnRemoveFieldActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSaveSettings)
                                                .addContainerGap(39, Short.MAX_VALUE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addComponent(btnLoad)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(cbxCollection))
                                                                        .addComponent(edtKey, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addComponent(edtDocument, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(edtValue, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(chboxIds)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDuplicate)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnRemoveField))
                                                        .addComponent(scrollPane2))
                                                .addGap(0, 6, Short.MAX_VALUE))))
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
                                        .addComponent(cbxCollection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(edtDocument, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(edtKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(edtValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addComponent(chboxIds)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSend)
                                        .addComponent(btnDuplicate)
                                        .addComponent(btnRemove)
                                        .addComponent(btnRemoveField))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblFilePath;
    private JButton btnLoad;
    private JTextField edtKey;
    private JTextField edtValue;
    private JTextField edtDocument;
    private JButton btnSend;
    private JButton btnSaveSettings;
    private JScrollPane scrollPane2;
    private JTextPane textPane1;
    private JButton btnDuplicate;
    private JComboBox cbxCollection;
    private JCheckBox chboxIds;
    private JButton btnRemove;
    private JButton btnRemoveField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
