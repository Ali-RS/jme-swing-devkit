package com.jayfella.importer.properties.component;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

public class BooleanComponent extends ReflectedSdkComponent<Boolean> {

    private JCheckBox checkBox;
    private JPanel contentPanel;

    public BooleanComponent() {
        super(null, null, null);
    }

    public BooleanComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
    }

    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);

        if (!isBinded()) {
            boolean newVal = (boolean) value;

            SwingUtilities.invokeLater(() -> {
                this.checkBox.setSelected(newVal);
                bind();
            });
        }

    }

    @Override
    public void bind() {
        super.bind();

        checkBox.addActionListener(e -> {
            JCheckBox checkbox = (JCheckBox) e.getSource();
            Boolean newValue = checkbox.isSelected();
            setValue(newValue);
        });
    }

    @Override
    public void setPropertyName(String propertyName) {
        super.setPropertyName(propertyName);
        checkBox.setText(propertyName);
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
        contentPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        checkBox = new JCheckBox();
        checkBox.setText("Enabled");
        contentPanel.add(checkBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
