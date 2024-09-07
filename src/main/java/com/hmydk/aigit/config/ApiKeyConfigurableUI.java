package com.hmydk.aigit.config;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class ApiKeyConfigurableUI {

    private JPanel mainPanel;
    private JBTextField apiKeyField;
    private ComboBox<String> modelComboBox;
    private ComboBox<String> languageComboBox;
    private JBTable customPromptsTable;
    private DefaultTableModel customPromptsTableModel;

    public ApiKeyConfigurableUI() {
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        apiKeyField = new JBTextField();
        modelComboBox = new ComboBox<>(new String[] { "Gemini" });
        languageComboBox = new ComboBox<>(new String[] { "English", "Chinese", "German", "French" });
        customPromptsTableModel = new DefaultTableModel(new String[] { "Description", "Prompt" }, 0);
        customPromptsTable = new JBTable(customPromptsTableModel);
    }

    private void layoutComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        mainPanel.add(new JBLabel("模型:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        mainPanel.add(modelComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        mainPanel.add(new JBLabel("API 密钥:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        mainPanel.add(apiKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        mainPanel.add(new JBLabel("语言:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        mainPanel.add(languageComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3; // 将表格上移一行
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel customPromptsPanel = ToolbarDecorator.createDecorator(customPromptsTable)
                .setAddAction(button -> {
                    // 添加自定义提示的逻辑
                    JPanel panel = new JPanel(new GridBagLayout());
                    GridBagConstraints dialogGbc = new GridBagConstraints();
                    dialogGbc.insets = new Insets(10, 10, 10, 10);
                    dialogGbc.fill = GridBagConstraints.HORIZONTAL;
                    dialogGbc.anchor = GridBagConstraints.WEST;

                    JTextField descriptionField = new JTextField(30); // 增加宽度
                    JTextArea contentArea = new JTextArea(10, 40); // 增加行数和宽度
                    contentArea.setLineWrap(true);
                    contentArea.setWrapStyleWord(true);
                    JButton validateButton = new JButton("验证提示");

                    dialogGbc.gridx = 0;
                    dialogGbc.gridy = 0;
                    dialogGbc.weightx = 0.0;
                    panel.add(new JLabel("提示描述:"), dialogGbc);

                    dialogGbc.gridx = 1;
                    dialogGbc.gridy = 0;
                    dialogGbc.weightx = 1.0;
                    panel.add(descriptionField, dialogGbc);

                    dialogGbc.gridx = 0;
                    dialogGbc.gridy = 1;
                    dialogGbc.weightx = 0.0;
                    dialogGbc.anchor = GridBagConstraints.NORTHWEST;
                    panel.add(new JLabel("提示内容:"), dialogGbc);

                    dialogGbc.gridx = 1;
                    dialogGbc.gridy = 1;
                    dialogGbc.weightx = 1.0;
                    dialogGbc.weighty = 1.0;
                    dialogGbc.fill = GridBagConstraints.BOTH;
                    panel.add(new JScrollPane(contentArea), dialogGbc);

                    dialogGbc.gridx = 1;
                    dialogGbc.gridy = 2;
                    dialogGbc.weightx = 0.0;
                    dialogGbc.weighty = 0.0;
                    dialogGbc.fill = GridBagConstraints.NONE;
                    dialogGbc.anchor = GridBagConstraints.EAST;
                    panel.add(validateButton, dialogGbc);

                    validateButton.addActionListener(e -> {
                        // 在此处添加验证提示的逻辑
                        JOptionPane.showMessageDialog(panel, "提示验证功能待实现",
                                "验证结果", JOptionPane.INFORMATION_MESSAGE);
                    });

                    // 创建一个更大的对话框
                    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
                            JOptionPane.OK_CANCEL_OPTION);
                    JDialog dialog = optionPane.createDialog(mainPanel, "添加自定义提示");
                    dialog.setSize(600, 400); // 设置对话框大小
                    dialog.setVisible(true);

                    int result = (Integer) optionPane.getValue();
                    if (result == JOptionPane.OK_OPTION) {
                        // 将新提示添加到表格中
                        String description = descriptionField.getText().trim();
                        String content = contentArea.getText().trim();
                        if (!description.isEmpty() && !content.isEmpty()) {
                            customPromptsTableModel.addRow(new Object[] {
                                    description,
                                    content
                            });
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "描述和内容不能为空",
                                    "输入错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                })
                .setRemoveAction(button -> {
                    // 移除选中的自定义提示
                    int selectedRow = customPromptsTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String removedDescription = (String) customPromptsTableModel.getValueAt(selectedRow, 0);
                        customPromptsTableModel.removeRow(selectedRow);
                    }
                })
                .createPanel();
        mainPanel.add(customPromptsPanel, gbc);

        // 添加表格选择监听器
        customPromptsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = customPromptsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedPrompt = (String) customPromptsTableModel.getValueAt(selectedRow, 1);
                    // 这里可以处理选中的提示，例如更新到其他地方或执行其他操作
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JBTextField getApiKeyField() {
        return apiKeyField;
    }

    public ComboBox<String> getModelComboBox() {
        return modelComboBox;
    }

    public ComboBox<String> getLanguageComboBox() {
        return languageComboBox;
    }

    public JBTable getCustomPromptsTable() {
        return customPromptsTable;
    }

    public DefaultTableModel getCustomPromptsTableModel() {
        return customPromptsTableModel;
    }

    public String getSelectedPrompt() {
        int selectedRow = customPromptsTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) customPromptsTableModel.getValueAt(selectedRow, 1);
        }
        return null;
    }
}
