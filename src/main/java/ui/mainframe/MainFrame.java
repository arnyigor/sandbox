package ui.mainframe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import presentation.mainform.MainFormPresenter;
import presentation.mainform.MainFormView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JDialog implements MainFormView {
    private String[] args;
    private final MainFormPresenter mainFormPresenter;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCalc;
    private JButton buttonCalcGeoTime;
    private JButton btnFile;
    private JButton btnFileToStr;
    private JButton bntStrtoFile;
    private JButton buttonCancel;
    private JButton buttonAuth;
    private JButton btnReadXls;
    private JTextField edtField;
    private JLabel labelResult;

    public MainFrame(String[] args) {
        this.args = args;
        mainFormPresenter = new MainFormPresenter(this);
        initUI();

        buttonOK.addActionListener(e -> onOK());
        buttonCalc.addActionListener(e -> onCalc());
        btnFile.addActionListener(e -> openFile());
        buttonCalcGeoTime.addActionListener(e -> onCalcGeoTime());
        btnFileToStr.addActionListener(e -> onFileToString());
        btnReadXls.addActionListener(e -> onReadXls());
        bntStrtoFile.addActionListener(e -> onStrToFile());
        buttonAuth.addActionListener(e -> onAuth());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pack();
        setVisible(true);
    }

    @Override
    public void setData(@Nullable String absolutePath, @Nullable String cookie, @NotNull String start, @NotNull String end, @Nullable String url) {

    }

    @Override
    public void showFirestoreFilePath(@NotNull String path) {
        labelResult.setText(path);
    }

    private void initUI() {
        contentPane = new JPanel();
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        btnFile = new JButton("File");
        btnFileToStr = new JButton("File to Str");
        bntStrtoFile = new JButton("Str to file");
        buttonAuth = new JButton("Auth");
        btnReadXls = new JButton("ReadXls");
        buttonCalc = new JButton("Calc");
        buttonCalcGeoTime = new JButton("Calc Time");
        edtField = new JTextField("10;37.7;54.4;3.0");
        labelResult = new JLabel();
        contentPane.add(buttonOK);
        contentPane.add(buttonCancel);
        contentPane.add(btnFile);
        contentPane.add(btnFileToStr);
        contentPane.add(bntStrtoFile);
        contentPane.add(buttonAuth);
        contentPane.add(btnReadXls);
        contentPane.add(buttonCalc);
        contentPane.add(buttonCalcGeoTime);
        contentPane.add(edtField);
        contentPane.add(labelResult);
        setContentPane(contentPane);
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2
        );
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    private void onOK() {
        mainFormPresenter.onOkClicked();
    }

    private void onCalc() {
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainFormPresenter.openFile(absolutePath);
        }
    }

    private void onCalcGeoTime() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    private void onFileToString() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainFormPresenter.convertFileToString(absolutePath);
        }
    }

    private void onReadXls() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            mainFormPresenter.readHtml(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void onStrToFile() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String result = JOptionPane.showInputDialog(this,
                    "Введите имя файла с расширением", "file.zip");
            mainFormPresenter.convertStringToFile(chooser.getSelectedFile().getAbsolutePath(), result);
        }
    }

    private void onAuth() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            mainFormPresenter.initFirestore(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void onCancel() {
        dispose();
    }

    @Override
    public void setUIEnabled(boolean enabled) {

    }
}
