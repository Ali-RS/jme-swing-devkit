package com.jayfella.devkit.properties.component;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jayfella.devkit.service.JmeEngineService;
import com.jayfella.devkit.service.ServiceManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.util.MaterialDebugAppState;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class MaterialChooserComponent extends ReflectedSdkComponent<Material> {

    private static final Logger log = Logger.getLogger(MaterialChooserComponent.class.getName());

    private JComboBox<String> materialsComboBox;
    private JPanel contentPanel;
    private JButton reloadMaterialButton;

    public MaterialChooserComponent() {
        super(null, null, null);

    }

    public MaterialChooserComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);

        // get all available materials.
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        Reflections reflections = new Reflections(null, new ResourcesScanner());
        Set<String> j3mdResourceList = reflections.getResources(Pattern.compile(".*\\.j3md"));
        Set<String> j3mResourceList = reflections.getResources(Pattern.compile(".*\\.j3m"));

        for (String resource : j3mdResourceList) {
            model.addElement(resource);
        }

        for (String resource : j3mResourceList) {
            model.addElement(resource);
        }

        materialsComboBox.setModel(model);

        try {
            setValue((Material) getter.invoke(parent));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        reloadMaterialButton.addActionListener(e -> {

            if (getReflectedProperty() != null) {

                Material material = getReflectedProperty().getValue();

                if (material != null) {
                    JmeEngineService engineService = ServiceManager.getService(JmeEngineService.class);
                    engineService.enqueue(() -> engineService
                            .getStateManager()
                            .getState(MaterialDebugAppState.class)
                            .reloadMaterial(material));
                }

            }

        });

    }

    @Override
    public void setValue(Material value) {
        super.setValue(value);

        if (!isBinded()) {
            SwingUtilities.invokeLater(() -> {
                materialsComboBox.setSelectedItem(value.getMaterialDef().getAssetName());
                bind();
            });
        }
    }

    @Override
    public void bind() {
        super.bind();

        materialsComboBox.addActionListener(e -> {

            String selectedMaterial = (String) materialsComboBox.getSelectedItem();

            AssetManager assetManager = ServiceManager.getService(JmeEngineService.class).getAssetManager();
            Material material = null;

            if (selectedMaterial != null) {

                if (selectedMaterial.endsWith(".j3md")) {
                    material = new Material(assetManager, selectedMaterial);
                } else if (selectedMaterial.endsWith(".j3m")) {
                    material = assetManager.loadMaterial(selectedMaterial);
                }

            } else {
                log.warning("The specified material is NULL. This is probably not intended!");
            }

            setValue(material);
            selectionChanged(material);
        });

    }

    public void selectionChanged(Material material) {

    }


    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void cleanup() {

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
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Material");
        contentPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        materialsComboBox = new JComboBox();
        contentPanel.add(materialsComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reloadMaterialButton = new JButton();
        reloadMaterialButton.setText("Reload Material");
        contentPanel.add(reloadMaterialButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
