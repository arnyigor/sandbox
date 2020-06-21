package ui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import presentation.MainFormPresenter;

public class MainForm extends JDialog {
    private final MainFormPresenter mainFormPresenter;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCalc;
    private JButton btnFileToStr;
    private JButton bntStrtoFile;
    private JButton buttonCancel;
    private JButton buttonAuth;
    private JTextField edtField;
    private JLabel labelResult;

    public MainForm() {
        mainFormPresenter = new MainFormPresenter();
        initUI();

        buttonOK.addActionListener(e -> onOK());
        buttonCalc.addActionListener(e -> onCalc());
        btnFileToStr.addActionListener(e -> onFileToString());
        bntStrtoFile.addActionListener(e -> onStrToFile());
        buttonAuth.addActionListener(e -> onAuth());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initUI() {
        contentPane = new JPanel();
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        btnFileToStr = new JButton("File to Str");
        bntStrtoFile = new JButton("Str to file");
        buttonAuth = new JButton("Auth");
        buttonCalc = new JButton("Calc");
        edtField = new JTextField("10;37.7;54.4;3.0");
        labelResult = new JLabel();
        contentPane.add(buttonOK);
        contentPane.add(buttonCancel);
        contentPane.add(btnFileToStr);
        contentPane.add(bntStrtoFile);
        contentPane.add(buttonAuth);
        contentPane.add(buttonCalc);
        contentPane.add(edtField);
        contentPane.add(labelResult);
        setContentPane(contentPane);
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2
        );
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    private void onOK() {
        mainFormPresenter.onOkClicked();
    }

    private void onCalc() {
        labelResult.setText(mainFormPresenter.calcSun(edtField.getText()));
    }

    private void onFileToString() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainFormPresenter.convertFileToString(absolutePath);
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
        mainFormPresenter.auth();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
