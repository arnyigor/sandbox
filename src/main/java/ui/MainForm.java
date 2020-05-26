package ui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import presentation.MainFormPresenter;

public class MainForm extends JDialog {
    private final MainFormPresenter mainFormPresenter;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonChoose;
    private JButton buttonCancel;
    private JButton buttonAuth;

    public MainForm() {
        mainFormPresenter = new MainFormPresenter();
        initUI();

        buttonOK.addActionListener(e -> onOK());
        buttonChoose.addActionListener(e -> onFileChoose());
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
        buttonChoose = new JButton("Choose file");
        buttonAuth = new JButton("Auth");
        contentPane.add(buttonOK);
        contentPane.add(buttonCancel);
        contentPane.add(buttonChoose);
        contentPane.add(buttonAuth);
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

    private void onFileChoose() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(contentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String absolutePath = chooser.getSelectedFile().getAbsolutePath();
            mainFormPresenter.convertToStringFile(absolutePath);
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
