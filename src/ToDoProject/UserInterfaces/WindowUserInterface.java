package ToDoProject.UserInterfaces;

import ToDoProject.Abstractions.IUserInterface;
import javax.swing.*;
import java.util.ArrayList;

public class WindowUserInterface implements IUserInterface {
    private boolean showListEnds = false;
    private ArrayList<String> bufList = new ArrayList<>();
    private JPanel field = new JPanel();
    private MainWindow mainWindow = new MainWindow();
    private String userInput;
    private boolean userInputEnds;

    private class MainWindow extends JFrame {
        public MainWindow() {
            setTitle("TODO");
            setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE));
            setBounds(300, 300, 900, 400);
            add(field);
            setVisible(true);
        }
    }

    @Override
    public String askInput (String message) {
        field.removeAll();
        showListEnds = true;
        this.show(message);
        JTextField textField = new JTextField(72);
        textField.setHorizontalAlignment(JTextField.CENTER);
        field.add(textField);
        field.repaint();
        mainWindow.setVisible(true);
        textField.addActionListener(e -> {
            userInput = textField.getText();
            if (userInput.equals("EXIT")) System.exit(0);
            userInputEnds = true;
            textField.setText("");
        });
        while (!userInputEnds) {try { Thread.sleep(250);} catch (InterruptedException ignored) {}}
        userInputEnds = false;
        return userInput;
    }

    @Override
    public void show (String message) {
        bufList.add(message);
        if (showListEnds) {
            DefaultListModel<String> dlm = new DefaultListModel<>();
            for (int i = bufList.size()-1; i >=0 ; i--) { dlm.add(0, bufList.get(i)); }
            JList<String> jList = new JList<>(dlm);
            jList.setFixedCellWidth(790);
            field.add(new JScrollPane(jList));
            bufList.clear();
            showListEnds=false;
        }
    }
}
