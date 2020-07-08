package com.jayfella.importer.properties.component.control;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jayfella.importer.properties.component.SdkComponent;
import com.jayfella.importer.service.JmeEngineService;
import com.jayfella.importer.service.ServiceManager;
import com.jme3.anim.AnimClip;
import com.jme3.anim.AnimComposer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

public class AnimComposerComponent implements SdkComponent {

    private final Timer timer;

    private JList<AnimClip> animationsList;
    private JButton playButton;
    private JButton stopButton;
    private JSlider timeSlider;
    private JPanel contentPanel;
    private JSlider speedSlider;

    private final AnimComposer animComposer;
    private final DefaultBoundedRangeModel animTimelineModel = new DefaultBoundedRangeModel(0, 1, 0, 1000);
    private AnimClip animClip;
    private com.jme3.anim.tween.action.Action action;

    public AnimComposerComponent(AnimComposer animComposer) {

        this.animComposer = animComposer;

        final JmeEngineService engineService = ServiceManager.getService(JmeEngineService.class);

        timeSlider.setModel(animTimelineModel);

        engineService.enqueue(() -> {

            Collection<AnimClip> animClips = animComposer.getAnimClips();

            // fill our list
            SwingUtilities.invokeLater(() -> {

                DefaultListModel<AnimClip> listModel = new DefaultListModel<>();
                for (AnimClip clip : animClips) {
                    listModel.addElement(clip);
                }

                animationsList.setModel(listModel);

            });


        });

        animationsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                final AnimClip selectedClip = animationsList.getSelectedValue();

                if (selectedClip != null) {

                    engineService.enqueue(() -> {
                        animClip = selectedClip;

                        // int max = (int) (animClip.getLength() * 1000);
                        final double maxTime = animClip.getLength();

                        SwingUtilities.invokeLater(() -> {

                            int max = (int) (maxTime * 1000);
                            animTimelineModel.setMaximum(max);

                        });
                    });
                }
            }
        });

        speedSlider.setModel(new DefaultBoundedRangeModel(1000, 250, 0, 2000));
        speedSlider.addChangeListener(e -> {

            final float animSpeed = speedSlider.getValue() / 1000f;

            engineService.enqueue(() -> {
                action.setSpeed(animSpeed);
            });


        });

        timeSlider.addChangeListener(e -> {

            final float animTime = timeSlider.getValue() / 1000f;

            engineService.enqueue(() -> {
                animComposer.setTime(AnimComposer.DEFAULT_LAYER, animTime);
            });

        });

        playButton.addActionListener(e -> {

            final float animSpeed = speedSlider.getValue() / 1000f;

            engineService.enqueue(() -> {
                action = animComposer.setCurrentAction(animClip.getName());
                action.setSpeed(animSpeed);
            });

        });

        stopButton.addActionListener(e -> {

            engineService.enqueue(() -> {
                action.setSpeed(0);
            });

        });

        // create a timer that queries the animation channel time so we can update the time slider position.
        timer = new Timer(10, e -> {

            engineService.enqueue(() -> {

                if (animClip != null) {

                    final double time = animComposer.getTime(AnimComposer.DEFAULT_LAYER);

                    SwingUtilities.invokeLater(() -> {
                        timeSlider.setValue((int) (time * 1000));
                    });

                }

            });

        });

        timer.setRepeats(true);
        timer.start();

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
        contentPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        timeSlider = new JSlider();
        contentPanel.add(timeSlider, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        speedSlider = new JSlider();
        contentPanel.add(speedSlider, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Time");
        contentPanel.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Speed");
        contentPanel.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        playButton = new JButton();
        playButton.setText("Play");
        panel1.add(playButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        panel1.add(stopButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        animationsList = new JList();
        scrollPane1.setViewportView(animationsList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void cleanup() {
        timer.stop();
    }
}
