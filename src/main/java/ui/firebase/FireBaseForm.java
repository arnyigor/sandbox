/*
 * Created by JFormDesigner on Sat Feb 20 11:30:36 MSK 2021
 */

package ui.firebase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import presentation.firestore.FirebaseFirestorePresenter;
import presentation.firestore.FirebaseFormView;

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
        cbxCollection.removeAllItems();
        list.forEach(s -> cbxCollection.addItem(s));
    }

    @Override
    public void setDocs(@NotNull List<String> docs) {
        chBoxDoc.removeAllItems();
        docs.forEach(s -> chBoxDoc.addItem(s));
    }

    @Override
    public void setKeys(@NotNull List<String> keys) {
        chBoxKey.removeAllItems();
        keys.forEach(s -> chBoxKey.addItem(s));
    }

    @Override
    public void setKeyValue(@Nullable String keyValue) {
        if (keyValue != null) {
            edtValue.setText(keyValue);
        }
    }

    private void btnLoadActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            presenter.loadCollectionData(item.toString(), chboxIds.isSelected());
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
        chBoxKey.setEnabled(!loading);
        chBoxDoc.setEnabled(!loading);
        btnLoadDoc.setEnabled(!loading);
    }

    private void btnSendActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            String edtKeyText = edtKey.getText();
            String edtValueText = edtValue.getText();
            String document = edtDocument.getText();
            if (edtKeyText.isEmpty()) {
                Object selectedKey = chBoxKey.getSelectedItem();
                if (selectedKey != null) {
                    edtKeyText = selectedKey.toString();
                }
            }
            if (document.isEmpty()) {
                Object selectedDoc = chBoxDoc.getSelectedItem();
                if (selectedDoc != null) {
                    document = selectedDoc.toString();
                }
            }
            int dialogResult = JOptionPane.showConfirmDialog(null, String.format("Отправить данные ключ:%s->значение:%s для документа %s?", edtKeyText, edtValueText, document));
            if (dialogResult == JOptionPane.YES_OPTION) {
                presenter.sendData(
                        item.toString(),
                        document,
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
            presenter.duplicateData(item.toString(), chboxIds.isSelected(), edtDocument.getText());
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

    private void btnLoadDocActionPerformed(ActionEvent e) {
        Object item = cbxCollection.getSelectedItem();
        if (item != null) {
            Object selectedDoc = chBoxDoc.getSelectedItem();
            if (selectedDoc != null) {
                presenter.loadDocumentData(item.toString(), selectedDoc.toString());
            }
        }
    }

    private void chBoxKeyItemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object item = e.getItem();
            presenter.onKeyChanged(item.toString());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblFilePath = new JLabel();
        btnLoad = new JButton();
        edtKey = new JTextField();
        btnSend = new JButton();
        btnSaveSettings = new JButton();
        scrollPane2 = new JScrollPane();
        textPane1 = new JTextPane();
        btnDuplicate = new JButton();
        cbxCollection = new JComboBox();
        chboxIds = new JCheckBox();
        btnRemove = new JButton();
        btnRemoveField = new JButton();
        chBoxKey = new JComboBox();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        edtValue = new JTextPane();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        chBoxDoc = new JComboBox();
        edtDocument = new JTextField();
        btnLoadDoc = new JButton();

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

        //---- btnSend ----
        btnSend.setText("Send");
        btnSend.addActionListener(e -> btnSendActionPerformed(e));

        //---- btnSaveSettings ----
        btnSaveSettings.setText("Save");
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

        //---- chBoxKey ----
        chBoxKey.addItemListener(e -> chBoxKeyItemStateChanged(e));

        //---- label1 ----
        label1.setText("Collections:");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(edtValue);
        }

        //---- label2 ----
        label2.setText("Document");

        //---- label3 ----
        label3.setText("Key:");

        //---- label4 ----
        label4.setText("Value:");

        //---- btnLoadDoc ----
        btnLoadDoc.setText("LoadDoc");
        btnLoadDoc.addActionListener(e -> btnLoadDocActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(btnSaveSettings, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblFilePath, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(scrollPane2, GroupLayout.Alignment.LEADING)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGroup(contentPaneLayout.createParallelGroup()
                                                .addGroup(contentPaneLayout.createSequentialGroup()
                                                    .addGap(0, 0, Short.MAX_VALUE)
                                                    .addComponent(chboxIds)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(edtDocument, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                                        .addComponent(btnLoad)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(label1)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cbxCollection, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(contentPaneLayout.createSequentialGroup()
                                                        .addComponent(btnLoadDoc)
                                                        .addGap(44, 44, 44)
                                                        .addComponent(label2)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(chBoxDoc))))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(label3)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(chBoxKey, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(contentPaneLayout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(edtKey)))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(label4))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnDuplicate)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnRemoveField)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)))
                            .addGap(229, 229, 229))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSaveSettings)
                        .addComponent(lblFilePath, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnLoad)
                                .addComponent(label1)
                                .addComponent(cbxCollection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3)
                                .addComponent(chBoxKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(chBoxDoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnLoadDoc))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(chboxIds)
                                        .addComponent(edtDocument, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addComponent(edtKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSend)
                                .addComponent(btnDuplicate)
                                .addComponent(btnRemove)
                                .addComponent(btnRemoveField)))
                        .addComponent(scrollPane1))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(3, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblFilePath;
    private JButton btnLoad;
    private JTextField edtKey;
    private JButton btnSend;
    private JButton btnSaveSettings;
    private JScrollPane scrollPane2;
    private JTextPane textPane1;
    private JButton btnDuplicate;
    private JComboBox cbxCollection;
    private JCheckBox chboxIds;
    private JButton btnRemove;
    private JButton btnRemoveField;
    private JComboBox chBoxKey;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextPane edtValue;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JComboBox chBoxDoc;
    private JTextField edtDocument;
    private JButton btnLoadDoc;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
