package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class StudentPredictionUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea logArea;

    // Данные для работы
    private ControlGroups[] controlGroup;
    private ExpGroups[] group1Sem, group2Sem, group3Sem, group4Sem, group5Sem, group6Sem, group7Sem, group8Sem;

    // Матрицы переходов
    private TransitionMatrix matrix1to2, matrix2to3, matrix3to4, matrix4to5, matrix5to6, matrix6to7, matrix7to8;

    // Результаты прогнозов
    private double[][] finalTable;
    private double[] vector1, vector2, vector3, vector4, vector5, vector6, vector7;

    // Метки для отображения загруженных файлов
    private JLabel controlGroupLabel, group1SemLabel, group2SemLabel, group3SemLabel, group4SemLabel, group5SemLabel, group6SemLabel, group7SemLabel, group8SemLabel;

    // Кнопки состояния
    private JButton calculateBtn;
    private JProgressBar progressBar;

    public StudentPredictionUI() {
        setTitle("Система прогнозирования успеваемости студентов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 900);
        setLocationRelativeTo(null);

        initComponents();
        setupMenuBar();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Вкладка загрузки данных
        tabbedPane.addTab("Загрузка данных", createLoadPanel());

        // Вкладка матриц переходов
        tabbedPane.addTab("Матрицы переходов", createMatricesPanel());

        // Вкладка прогнозов
        tabbedPane.addTab("Прогнозы", createPredictionPanel());

        // Вкладка результатов
        tabbedPane.addTab("Итоговая таблица", createResultsPanel());

        // Вкладка информации о студентах
        tabbedPane.addTab("Данные студентов", createStudentsDataPanel());

        // Вкладка логов
        JPanel logPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logPanel.add(logScroll, BorderLayout.CENTER);

        JButton clearLogBtn = new JButton("Очистить логи");
        clearLogBtn.addActionListener(e -> logArea.setText(""));
        logPanel.add(clearLogBtn, BorderLayout.SOUTH);

        tabbedPane.addTab("Логи", logPanel);

        add(tabbedPane);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu viewMenu = new JMenu("Вид");
        JMenuItem refreshItem = new JMenuItem("Обновить все");
        refreshItem.addActionListener(e -> refreshAllDisplays());
        viewMenu.add(refreshItem);

        JMenu helpMenu = new JMenu("Помощь");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> showAboutDialog());
        JMenuItem instructionsItem = new JMenuItem("Инструкция");
        instructionsItem.addActionListener(e -> showInstructions());
        helpMenu.add(aboutItem);
        helpMenu.add(instructionsItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private JPanel createLoadPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Левая панель - файлы экспериментальных групп
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Экспериментальные группы",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // 1 семестр
        leftPanel.add(new JLabel("1 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn1Sem = createFileSelectButton("Выбрать файл", "1Sem", this::load1SemFile);
        leftPanel.add(btn1Sem, gbc);
        gbc.gridx = 2;
        group1SemLabel = new JLabel("Файл не выбран");
        group1SemLabel.setForeground(Color.RED);
        leftPanel.add(group1SemLabel, gbc);

        // 2 семестр
        gbc.gridx = 0;
        gbc.gridy = 1;
        leftPanel.add(new JLabel("2 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn2Sem = createFileSelectButton("Выбрать файл", "2Sem", this::load2SemFile);
        leftPanel.add(btn2Sem, gbc);
        gbc.gridx = 2;
        group2SemLabel = new JLabel("Файл не выбран");
        group2SemLabel.setForeground(Color.RED);
        leftPanel.add(group2SemLabel, gbc);

        // 3 семестр
        gbc.gridx = 0;
        gbc.gridy = 2;
        leftPanel.add(new JLabel("3 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn3Sem = createFileSelectButton("Выбрать файл", "3Sem", this::load3SemFile);
        leftPanel.add(btn3Sem, gbc);
        gbc.gridx = 2;
        group3SemLabel = new JLabel("Файл не выбран");
        group3SemLabel.setForeground(Color.RED);
        leftPanel.add(group3SemLabel, gbc);

        // 4 семестр
        gbc.gridx = 0;
        gbc.gridy = 3;
        leftPanel.add(new JLabel("4 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn4Sem = createFileSelectButton("Выбрать файл", "4Sem", this::load4SemFile);
        leftPanel.add(btn4Sem, gbc);
        gbc.gridx = 2;
        group4SemLabel = new JLabel("Файл не выбран");
        group4SemLabel.setForeground(Color.RED);
        leftPanel.add(group4SemLabel, gbc);

        // 5 семестр
        gbc.gridx = 0;
        gbc.gridy = 4;
        leftPanel.add(new JLabel("5 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn5Sem = createFileSelectButton("Выбрать файл", "5Sem", this::load5SemFile);
        leftPanel.add(btn5Sem, gbc);
        gbc.gridx = 2;
        group5SemLabel = new JLabel("Файл не выбран");
        group5SemLabel.setForeground(Color.RED);
        leftPanel.add(group5SemLabel, gbc);

        // 6 семестр
        gbc.gridx = 0;
        gbc.gridy = 5;
        leftPanel.add(new JLabel("6 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn6Sem = createFileSelectButton("Выбрать файл", "6Sem", this::load6SemFile);
        leftPanel.add(btn6Sem, gbc);
        gbc.gridx = 2;
        group6SemLabel = new JLabel("Файл не выбран");
        group6SemLabel.setForeground(Color.RED);
        leftPanel.add(group6SemLabel, gbc);

        // 7 семестр
        gbc.gridx = 0;
        gbc.gridy = 6;
        leftPanel.add(new JLabel("7 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn7Sem = createFileSelectButton("Выбрать файл", "7Sem", this::load7SemFile);
        leftPanel.add(btn7Sem, gbc);
        gbc.gridx = 2;
        group7SemLabel = new JLabel("Файл не выбран");
        group7SemLabel.setForeground(Color.RED);
        leftPanel.add(group7SemLabel, gbc);

        // 8 семестр
        gbc.gridx = 0;
        gbc.gridy = 7;
        leftPanel.add(new JLabel("8 семестр:"), gbc);
        gbc.gridx = 1;
        JButton btn8Sem = createFileSelectButton("Выбрать файл", "8Sem", this::load8SemFile);
        leftPanel.add(btn8Sem, gbc);
        gbc.gridx = 2;
        group8SemLabel = new JLabel("Файл не выбран");
        group8SemLabel.setForeground(Color.RED);
        leftPanel.add(group8SemLabel, gbc);

        // Правая панель - контрольная группа
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Контрольная группа",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;

        rightPanel.add(new JLabel("Контрольная группа:"), gbcRight);
        gbcRight.gridx = 1;
        JButton btnControl = createFileSelectButton("Выбрать файл", "Control", this::loadControlFile);
        rightPanel.add(btnControl, gbcRight);
        gbcRight.gridx = 2;
        controlGroupLabel = new JLabel("Файл не выбран");
        controlGroupLabel.setForeground(Color.RED);
        rightPanel.add(controlGroupLabel, gbcRight);

        // Панель с кнопками действий
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton loadAllBtn = new JButton("Загрузить все файлы (из последней директории)");
        loadAllBtn.setFont(new Font("Arial", Font.BOLD, 12));
        loadAllBtn.setBackground(new Color(76, 175, 80));
        loadAllBtn.addActionListener(e -> loadAllFiles());

        calculateBtn = new JButton("Рассчитать матрицы переходов и прогнозы");
        calculateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        calculateBtn.setBackground(new Color(33, 150, 243));
        calculateBtn.setEnabled(false);
        calculateBtn.addActionListener(e -> calculateTransitionMatrices());

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        actionPanel.add(loadAllBtn);
        actionPanel.add(calculateBtn);
        actionPanel.add(progressBar);

        // Основная компоновка
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createFileSelectButton(String text, String fileType, Runnable loadAction) {
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выберите JSON файл для " + fileType);
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

            // Запоминаем последнюю использованную директорию
            if (lastDirectory != null) {
                fileChooser.setCurrentDirectory(new File(lastDirectory));
            }

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                lastDirectory = selectedFile.getParent();
                loadAction.run();
            }
        });
        return button;
    }

    private String lastDirectory = null;

    private void loadControlFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл контрольной группы");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "controlGroup");
        }
    }

    private void load1SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 1 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "1Sem");
        }
    }

    private void load2SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 2 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "2Sem");
        }
    }

    private void load3SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 3 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "3Sem");
        }
    }

    private void load4SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 4 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "4Sem");
        }
    }

    private void load5SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 5 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "5Sem");
        }
    }

    private void load6SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 6 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "6Sem");
        }
    }

    private void load7SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 7 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "7Sem");
        }
    }

    private void load8SemFile() {
        JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setDialogTitle("Выберите JSON файл для 8 семестра");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastDirectory = file.getParent();
            loadFile(file.getAbsolutePath(), "8Sem");
        }
    }

    private void loadFile(String filePath, String fileType) {
        logMessage("Загрузка файла: " + filePath);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                progressBar.setVisible(true);
                progressBar.setIndeterminate(true);

                try {
                    switch (fileType) {
                        case "controlGroup":
                            controlGroup = ControlGroups.parseControlGroup(filePath);
                            controlGroupLabel.setText(new File(filePath).getName());
                            controlGroupLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + controlGroup.length + " записей контрольной группы");
                            break;
                        case "1Sem":
                            group1Sem = ExpGroups.parseExpGroup(filePath);
                            group1SemLabel.setText(new File(filePath).getName());
                            group1SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group1Sem.length + " записей 1 семестра");
                            break;
                        case "2Sem":
                            group2Sem = ExpGroups.parseExpGroup(filePath);
                            group2SemLabel.setText(new File(filePath).getName());
                            group2SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group2Sem.length + " записей 2 семестра");
                            break;
                        case "3Sem":
                            group3Sem = ExpGroups.parseExpGroup(filePath);
                            group3SemLabel.setText(new File(filePath).getName());
                            group3SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group3Sem.length + " записей 3 семестра");
                            break;
                        case "4Sem":
                            group4Sem = ExpGroups.parseExpGroup(filePath);
                            group4SemLabel.setText(new File(filePath).getName());
                            group4SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group4Sem.length + " записей 4 семестра");
                            break;
                        case "5Sem":
                            group5Sem = ExpGroups.parseExpGroup(filePath);
                            group5SemLabel.setText(new File(filePath).getName());
                            group5SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group5Sem.length + " записей 5 семестра");
                            break;
                        case "6Sem":
                            group6Sem = ExpGroups.parseExpGroup(filePath);
                            group6SemLabel.setText(new File(filePath).getName());
                            group6SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group6Sem.length + " записей 6 семестра");
                            break;
                        case "7Sem":
                            group7Sem = ExpGroups.parseExpGroup(filePath);
                            group7SemLabel.setText(new File(filePath).getName());
                            group7SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group7Sem.length + " записей 7 семестра");
                            break;
                        case "8Sem":
                            group8Sem = ExpGroups.parseExpGroup(filePath);
                            group8SemLabel.setText(new File(filePath).getName());
                            group8SemLabel.setForeground(Color.GREEN);
                            logMessage("Загружено " + group8Sem.length + " записей 8 семестра");
                            break;
                    }
                } catch (Exception e) {
                    logMessage("Ошибка при загрузке: " + e.getMessage());
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(StudentPredictionUI.this,
                                "Ошибка при загрузке файла: " + e.getMessage(),
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    });
                }
                return null;
            }

            @Override
            protected void done() {
                progressBar.setVisible(false);
                progressBar.setIndeterminate(false);
                checkAllFilesLoaded();
            }
        };
        worker.execute();
    }

    private void checkAllFilesLoaded() {
        boolean allLoaded = controlGroup != null && group1Sem != null && group2Sem != null &&
                group3Sem != null && group4Sem != null && group5Sem != null &&
                group6Sem != null && group7Sem != null && group8Sem != null;
        calculateBtn.setEnabled(allLoaded);

        if (allLoaded) {
            logMessage("✓ Все файлы загружены! Можно выполнять расчет.");
        }
    }

    private void loadAllFiles() {
        if (lastDirectory == null) {
            JOptionPane.showMessageDialog(this,
                    "Сначала выберите хотя бы один файл, чтобы определить директорию.",
                    "Информация", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Загрузить все файлы из директории:\n" + lastDirectory + "\n\n" +
                        "Ожидаются файлы:\n- controlGroup.json\n- 1Sem.json\n- 2Sem.json\n- 3Sem.json\n" +
                        "- 4Sem.json\n- 5Sem.json\n- 6Sem.json\n- 7Sem.json\n- 8Sem.json",
                "Подтверждение загрузки", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            loadFile(lastDirectory + File.separator + "controlGroup.json", "controlGroup");
            loadFile(lastDirectory + File.separator + "1Sem.json", "1Sem");
            loadFile(lastDirectory + File.separator + "2Sem.json", "2Sem");
            loadFile(lastDirectory + File.separator + "3Sem.json", "3Sem");
            loadFile(lastDirectory + File.separator + "4Sem.json", "4Sem");
            loadFile(lastDirectory + File.separator + "5Sem.json", "5Sem");
            loadFile(lastDirectory + File.separator + "6Sem.json", "6Sem");
            loadFile(lastDirectory + File.separator + "7Sem.json", "7Sem");
            loadFile(lastDirectory + File.separator + "8Sem.json", "8Sem");
        }
    }

    private void calculateTransitionMatrices() {
        if (!checkDataLoaded()) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста, сначала загрузите все файлы данных!",
                    "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                progressBar.setVisible(true);
                progressBar.setIndeterminate(true);
                logMessage("\n=== РАСЧЕТ МАТРИЦ ПЕРЕХОДОВ ===");

                matrix1to2 = new TransitionMatrix(group1Sem, group2Sem);
                matrix2to3 = new TransitionMatrix(group2Sem, group3Sem);
                matrix3to4 = new TransitionMatrix(group3Sem, group4Sem);
                matrix4to5 = new TransitionMatrix(group4Sem, group5Sem);
                matrix5to6 = new TransitionMatrix(group5Sem, group6Sem);
                matrix6to7 = new TransitionMatrix(group6Sem, group7Sem);
                matrix7to8 = new TransitionMatrix(group7Sem, group8Sem);

                logMessage("Матрицы переходов успешно рассчитаны!");

                calculatePredictions();
                return null;
            }

            @Override
            protected void done() {
                progressBar.setVisible(false);
                progressBar.setIndeterminate(false);
                refreshAllDisplays();

                JOptionPane.showMessageDialog(StudentPredictionUI.this,
                        "Матрицы переходов и прогнозы рассчитаны!\nПерейдите на вкладки для просмотра результатов.",
                        "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        worker.execute();
    }

    private boolean checkDataLoaded() {
        return controlGroup != null && group1Sem != null && group2Sem != null &&
                group3Sem != null && group4Sem != null && group5Sem != null &&
                group6Sem != null && group7Sem != null && group8Sem != null;
    }

    private void calculatePredictions() {
        int[] controlCounts = BaseStudentGroup.countConditions(controlGroup);

        vector1 = matrix1to2.getPredictionVector(
                matrix1to2.buildPredictionMatrix(controlCounts)
        );
        vector2 = matrix2to3.getPredictionVector(
                matrix2to3.buildPredictionMatrix(vector1)
        );
        vector3 = matrix3to4.getPredictionVector(
                matrix3to4.buildPredictionMatrix(vector2)
        );
        vector4 = matrix4to5.getPredictionVector(
                matrix4to5.buildPredictionMatrix(vector3)
        );
        vector5 = matrix5to6.getPredictionVector(
                matrix5to6.buildPredictionMatrix(vector4)
        );
        vector6 = matrix6to7.getPredictionVector(
                matrix6to7.buildPredictionMatrix(vector5)
        );
        vector7 = matrix7to8.getPredictionVector(
                matrix7to8.buildPredictionMatrix(vector6)
        );

        finalTable = new double[7][7];
        finalTable[0] = vector1;
        finalTable[1] = vector2;
        finalTable[2] = vector3;
        finalTable[3] = vector4;
        finalTable[4] = vector5;
        finalTable[5] = vector6;
        finalTable[6] = vector7;

        logMessage("\n=== ПРОГНОЗЫ РАССЧИТАНЫ ===");
        logMessage("2 семестр: " + Arrays.toString(vector1));
        logMessage("3 семестр: " + Arrays.toString(vector2));
        logMessage("4 семестр: " + Arrays.toString(vector3));
        logMessage("5 семестр: " + Arrays.toString(vector4));
        logMessage("6 семестр: " + Arrays.toString(vector5));
        logMessage("7 семестр: " + Arrays.toString(vector6));
        logMessage("8 семестр: " + Arrays.toString(vector7));
    }

    private JPanel createMatricesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> matrixSelector = new JComboBox<>(new String[]{
                "1→2 семестр", "2→3 семестр", "3→4 семестр",
                "4→5 семестр", "5→6 семестр", "6→7 семестр", "7→8 семестр"
        });

        JButton refreshBtn = new JButton("Обновить");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Выберите матрицу:"));
        topPanel.add(matrixSelector);
        topPanel.add(refreshBtn);

        JTextArea matrixDisplay = new JTextArea();
        matrixDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        matrixDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(matrixDisplay);

        refreshBtn.addActionListener(e -> {
            int index = matrixSelector.getSelectedIndex();
            String display = getMatrixDisplay(index);
            matrixDisplay.setText(display);
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String getMatrixDisplay(int index) {
        StringBuilder sb = new StringBuilder();
        TransitionMatrix matrix = null;
        String title = "";

        switch (index) {
            case 0: matrix = matrix1to2; title = "Матрица переходов 1→2 семестр"; break;
            case 1: matrix = matrix2to3; title = "Матрица переходов 2→3 семестр"; break;
            case 2: matrix = matrix3to4; title = "Матрица переходов 3→4 семестр"; break;
            case 3: matrix = matrix4to5; title = "Матрица переходов 4→5 семестр"; break;
            case 4: matrix = matrix5to6; title = "Матрица переходов 5→6 семестр"; break;
            case 5: matrix = matrix6to7; title = "Матрица переходов 6→7 семестр"; break;
            case 6: matrix = matrix7to8; title = "Матрица переходов 7→8 семестр"; break;
        }

        sb.append("=== ").append(title).append(" ===\n\n");

        if (matrix != null) {
            sb.append("КОЛИЧЕСТВЕННАЯ МАТРИЦА:\n");
            sb.append("      T1  T2  T3  T4  T5  T6  T7\n");
            String[] states = {"T1", "T2", "T3", "T4", "T5", "T6", "T7"};
            int[][] counts = matrix.getTransitionCounts();
            for (int i = 0; i < 7; i++) {
                sb.append(states[i]).append(" ");
                for (int j = 0; j < 7; j++) {
                    sb.append(String.format("%3d ", counts[i][j]));
                }
                sb.append("\n");
            }

            sb.append("\nМАТРИЦА ВЕРОЯТНОСТЕЙ:\n");
            double[][] probs = matrix.getTransitionProbabilities();
            for (double[] row : probs) {
                for (double val : row) {
                    sb.append(String.format("%6.4f ", val));
                }
                sb.append("\n");
            }
        } else {
            sb.append("Матрица не рассчитана. Пожалуйста, загрузите данные и рассчитайте матрицы.");
        }

        return sb.toString();
    }

    private JPanel createPredictionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> predictionSelector = new JComboBox<>(new String[]{
                "2 семестр", "3 семестр", "4 семестр",
                "5 семестр", "6 семестр", "7 семестр", "8 семестр"
        });

        JButton refreshBtn = new JButton("Показать прогноз");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Выберите семестр:"));
        topPanel.add(predictionSelector);
        topPanel.add(refreshBtn);

        JTextArea predictionDisplay = new JTextArea();
        predictionDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        predictionDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(predictionDisplay);

        refreshBtn.addActionListener(e -> {
            int index = predictionSelector.getSelectedIndex();
            String display = getPredictionDisplay(index);
            predictionDisplay.setText(display);
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String getPredictionDisplay(int index) {
        StringBuilder sb = new StringBuilder();
        double[] vector = null;
        String title = "";
        TransitionMatrix matrix = null;

        switch (index) {
            case 0:
                vector = vector1;
                title = "2 семестр";
                matrix = matrix1to2;
                break;
            case 1:
                vector = vector2;
                title = "3 семестр";
                matrix = matrix2to3;
                break;
            case 2:
                vector = vector3;
                title = "4 семестр";
                matrix = matrix3to4;
                break;
            case 3:
                vector = vector4;
                title = "5 семестр";
                matrix = matrix4to5;
                break;
            case 4:
                vector = vector5;
                title = "6 семестр";
                matrix = matrix5to6;
                break;
            case 5:
                vector = vector6;
                title = "7 семестр";
                matrix = matrix6to7;
                break;
            case 6:
                vector = vector7;
                title = "8 семестр";
                matrix = matrix7to8;
                break;
        }

        sb.append("=== ПРОГНОЗ ДЛЯ ").append(title).append(" ===\n\n");

        if (vector != null && matrix != null) {
            sb.append("Прогноз количества студентов по состояниям:\n\n");
            sb.append("┌─────────┬─────────────┐\n");
            sb.append("│ Состояние│ Количество  │\n");
            sb.append("├─────────┼─────────────┤\n");
            String[] states = {"T1", "T2", "T3", "T4", "T5", "T6", "T7"};
            double total = 0;
            for (int i = 0; i < 7; i++) {
                sb.append(String.format("│    %s    │   %8.2f   │\n", states[i], vector[i]));
                total += vector[i];
            }
            sb.append("├─────────┼─────────────┤\n");
            sb.append(String.format("│  Всего  │   %8.2f   │\n", total));
            sb.append("└─────────┴─────────────┘\n\n");

            sb.append("Прогнозная матрица:\n");
            sb.append("      T1    T2    T3    T4    T5    T6    T7\n");

            double[][] predMatrix = null;
            if (index == 0) predMatrix = matrix.buildPredictionMatrix(BaseStudentGroup.countConditions(controlGroup));
            else if (index == 1 && vector1 != null) predMatrix = matrix.buildPredictionMatrix(vector1);
            else if (index == 2 && vector2 != null) predMatrix = matrix.buildPredictionMatrix(vector2);
            else if (index == 3 && vector3 != null) predMatrix = matrix.buildPredictionMatrix(vector3);
            else if (index == 4 && vector4 != null) predMatrix = matrix.buildPredictionMatrix(vector4);
            else if (index == 5 && vector5 != null) predMatrix = matrix.buildPredictionMatrix(vector5);
            else if (index == 6 && vector6 != null) predMatrix = matrix.buildPredictionMatrix(vector6);

            if (predMatrix != null) {
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        sb.append(String.format("%6.2f ", predMatrix[i][j]));
                    }
                    sb.append("\n");
                }
            }
        } else {
            sb.append("Прогноз не рассчитан. Пожалуйста, загрузите данные и выполните расчет.");
        }

        return sb.toString();
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea resultsDisplay = new JTextArea();
        resultsDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultsDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsDisplay);

        JButton refreshBtn = new JButton("Обновить итоговую таблицу");
        refreshBtn.addActionListener(e -> {
            resultsDisplay.setText(getResultsDisplay());
        });

        panel.add(refreshBtn, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String getResultsDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ИТОГОВАЯ ТАБЛИЦА ПРОГНОЗОВ ===\n\n");
        sb.append("┌────────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐\n");
        sb.append("│  Семестр   │   T1    │   T2    │   T3    │   T4    │   T5    │   T6    │   T7    │\n");
        sb.append("├────────────┼─────────┼─────────┼─────────┼─────────┼─────────┼─────────┼─────────┤\n");

        String[] semesters = {"2", "3", "4", "5", "6", "7", "8"};
        double[][] vectors = {vector1, vector2, vector3, vector4, vector5, vector6, vector7};

        for (int i = 0; i < vectors.length && vectors[i] != null; i++) {
            sb.append(String.format("│   %s сем.   │", semesters[i]));
            for (int j = 0; j < 7; j++) {
                sb.append(String.format(" %7.2f │", vectors[i][j]));
            }
            sb.append("\n");
        }

        sb.append("└────────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘\n\n");

        // Добавляем графическое представление столбцов
        sb.append("\n=== ГРАФИЧЕСКОЕ ПРЕДСТАВЛЕНИЕ ===\n\n");

        for (int i = 0; i < vectors.length && vectors[i] != null; i++) {
            sb.append(semesters[i]).append(" семестр:\n");
            for (int j = 0; j < 7; j++) {
                int barLength = (int)(vectors[i][j] / 5);
                sb.append(String.format("  T%d: ", j+1));
                sb.append(String.format(" %.2f\n", vectors[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private JPanel createStudentsDataPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> dataSelector = new JComboBox<>(new String[]{
                "Контрольная группа", "1 семестр", "2 семестр", "3 семестр",
                "4 семестр", "5 семестр", "6 семестр", "7 семестр", "8 семестр"
        });

        JButton refreshBtn = new JButton("Показать данные");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Выберите группу:"));
        topPanel.add(dataSelector);
        topPanel.add(refreshBtn);

        JTextArea dataDisplay = new JTextArea();
        dataDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        dataDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(dataDisplay);

        refreshBtn.addActionListener(e -> {
            int index = dataSelector.getSelectedIndex();
            String display = getStudentsDataDisplay(index);
            dataDisplay.setText(display);
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String getStudentsDataDisplay(int index) {
        StringBuilder sb = new StringBuilder();

        switch (index) {
            case 0:
                if (controlGroup != null) {
                    sb.append("КОНТРОЛЬНАЯ ГРУППА\n");
                    sb.append("=================\n\n");
                    for (int i = 0; i < controlGroup.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, controlGroup[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                controlGroup[i].avgBall(), controlGroup[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 1:
                if (group1Sem != null) {
                    sb.append("1 СЕМЕСТР (2 экзамена)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group1Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group1Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group1Sem[i].avgBall(), group1Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 2:
                if (group2Sem != null) {
                    sb.append("2 СЕМЕСТР (4 экзамена)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group2Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group2Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group2Sem[i].avgBall(), group2Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 3:
                if (group3Sem != null) {
                    sb.append("3 СЕМЕСТР (5 экзаменов)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group3Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group3Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group3Sem[i].avgBall(), group3Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 4:
                if (group4Sem != null) {
                    sb.append("4 СЕМЕСТР (6 экзаменов)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group4Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group4Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group4Sem[i].avgBall(), group4Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 5:
                if (group5Sem != null) {
                    sb.append("5 СЕМЕСТР (5 экзаменов)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group5Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group5Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group5Sem[i].avgBall(), group5Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 6:
                if (group6Sem != null) {
                    sb.append("6 СЕМЕСТР (5 экзаменов)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group6Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group6Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group6Sem[i].avgBall(), group6Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 7:
                if (group7Sem != null) {
                    sb.append("7 СЕМЕСТР (5 экзаменов)\n");
                    sb.append("=====================\n\n");
                    for (int i = 0; i < group7Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group7Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group7Sem[i].avgBall(), group7Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
            case 8:
                if (group8Sem != null) {
                    sb.append("8 СЕМЕСТР (3 экзамена)\n");
                    sb.append("====================\n\n");
                    for (int i = 0; i < group8Sem.length; i++) {
                        sb.append(String.format("Студент %d: %s", i+1, group8Sem[i].toString()));
                        sb.append(String.format(" -> Средний балл: %.2f, Состояние: %s\n",
                                group8Sem[i].avgBall(), group8Sem[i].getCondition()));
                    }
                } else {
                    sb.append("Данные не загружены");
                }
                break;
        }

        return sb.toString();
    }

    private void refreshAllDisplays() {
        // Обновляем все вкладки при изменении данных
        if (tabbedPane.getTabCount() > 0) {
            for (int i = 1; i <= 4; i++) {
                Component comp = tabbedPane.getComponentAt(i);
                if (comp instanceof JPanel) {
                    comp.repaint();
                }
            }
        }
    }

    private void logMessage(String message) {
        if (logArea != null) {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
        System.out.println(message);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Система прогнозирования успеваемости студентов\n" +
                        "Версия 2.0\n\n" +
                        "Программа анализирует данные об успеваемости студентов\n" +
                        "и строит прогнозы на основе цепей Маркова.\n\n" +
                        "Особенности:\n" +
                        "- Поддержка выбора файлов через диалоговое окно\n" +
                        "- Автоматический расчет матриц переходов\n" +
                        "- Прогнозирование на 8 семестров\n" +
                        "- Визуализация результатов\n\n" +
                        "© 2026",
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInstructions() {
        JTextArea instructions = new JTextArea(
                "ИНСТРУКЦИЯ ПО ИСПОЛЬЗОВАНИЮ\n" +
                        "============================\n\n" +
                        "1. ЗАГРУЗКА ДАННЫХ:\n" +
                        "   - Нажмите кнопку 'Выбрать файл' для каждого семестра\n" +
                        "   - Или используйте 'Загрузить все файлы из последней директории'\n" +
                        "   - Поддерживаются только JSON файлы\n\n" +
                        "2. ФОРМАТ JSON ФАЙЛОВ:\n" +
                        "   - exam1: x, exam2: y, ... examN:j\n\n" +
                        "3. РАСЧЕТ:\n" +
                        "   - После загрузки всех файлов кнопка 'Рассчитать' станет активной\n" +
                        "   - Нажмите ее для построения матриц переходов и прогнозов\n\n" +
                        "4. ПРОСМОТР РЕЗУЛЬТАТОВ:\n" +
                        "   - 'Матрицы переходов' - количественные матрицы и вероятности\n" +
                        "   - 'Прогнозы' - прогнозы для каждого семестра\n" +
                        "   - 'Итоговая таблица' - сводная таблица всех прогнозов\n" +
                        "   - 'Данные студентов' - просмотр исходных данных\n\n" +
                        "5. СОСТОЯНИЯ (T1-T7):\n" +
                        "\tT1: 4.5-5.0, \n\tT2: 4.0-4.5, \n\tT3: 3.5-4.0, \n\tT4: 3.0-3.5,\n" +
                        "\tT5: 2.5-3.0, \n\tT6: 1.5-2.5, \n\tT7: <1.5"
        );
        instructions.setEditable(false);
        instructions.setFont(new Font("Monospaced", Font.PLAIN, 12));
        instructions.setRows(30);
        instructions.setColumns(80);

        JScrollPane scrollPane = new JScrollPane(instructions);
        scrollPane.setPreferredSize(new Dimension(800, 600));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Инструкция", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new StudentPredictionUI().setVisible(true);
        });
    }
}