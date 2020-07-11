package com.jayfella.importer.properties.component;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringComponent extends ReflectedSdkComponent<String> {

    private JTextField valueTextField;
    private JLabel propertyNameLabel;
    private JPanel contentPanel;

    public StringComponent() {
        super(null, null, null);
    }

    public StringComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);

        try {
            setValue((String) getter.invoke(parent));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);

        if (!isBinded()) {

            SwingUtilities.invokeLater(() -> {
                this.valueTextField.setText(value);
                bind();
            });
        }
    }

    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void bind() {
        super.bind();
        valueTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void set() {

                String newValue = valueTextField.getText();
                setValue(newValue);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                set();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                set();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                set();
            }
        });
    }

    @Override
    public void setPropertyName(String propertyName) {
        super.setPropertyName(propertyName);
        propertyNameLabel.setText("String: " + propertyName);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        propertyNameLabel = new JLabel();
        propertyNameLabel.setText("String");
        contentPanel.add(propertyNameLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        valueTextField = new JTextField();
        contentPanel.add(valueTextField, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        contentPanel.add(separator1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
