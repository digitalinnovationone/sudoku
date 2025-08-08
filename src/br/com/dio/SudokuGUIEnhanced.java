package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.service.BoardService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

/**
 * Interface Gráfica Melhorada para o Sudoku
 * Integra com os services da DIO mantendo visual moderno e intuitivo
 */
public class SudokuGUIEnhanced extends JFrame {
    
    private static final int BOARD_SIZE = 9;
    private static final Color FIXED_COLOR = new Color(230, 230, 230);
    private static final Color EDITABLE_COLOR = Color.WHITE;
    private static final Color ERROR_COLOR = new Color(255, 200, 200);
    private static final Color CORRECT_COLOR = new Color(200, 255, 200);
    private static final Color HOVER_COLOR = new Color(240, 248, 255);
    
    private BoardService boardService;
    private Board board;
    private JTextField[][] cells;
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JLabel titleLabel;
    
    public SudokuGUIEnhanced() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("🎯 Sudoku DIO Challenge - Enhanced GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Usar Look and Feel padrão
        
        // Criar header com título estilizado
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Criar painel de controles
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.WEST);
        
        // Criar tabuleiro centralizado
        boardPanel = createBoardPanel();
        add(boardPanel, BorderLayout.CENTER);
        
        // Criar painel de status
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Definir ícone da janela se disponível
        try {
            setIconImage(createSudokuIcon());
        } catch (Exception e) {
            // Ícone não disponível
        }
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        titleLabel = new JLabel("🎯 SUDOKU - DESAFIO DIO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel);
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        panel.setPreferredSize(new Dimension(180, 0));
        panel.setBackground(new Color(248, 249, 250));
        
        // Título do painel
        JLabel controlTitle = new JLabel("CONTROLES");
        controlTitle.setFont(new Font("Arial", Font.BOLD, 14));
        controlTitle.setForeground(new Color(70, 130, 180));
        controlTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Botões de controle
        JButton newGameButton = createStyledButton("🎮 Novo Jogo", new Color(70, 130, 180));
        JButton checkStatusButton = createStyledButton("✅ Verificar Status", new Color(60, 179, 113));
        JButton clearGameButton = createStyledButton("🧹 Limpar Jogo", new Color(255, 140, 0));
        JButton finishGameButton = createStyledButton("🏆 Finalizar", new Color(220, 20, 60));
        
        // Adicionar listeners
        newGameButton.addActionListener(e -> showNewGameDialog());
        checkStatusButton.addActionListener(e -> checkGameStatus());
        clearGameButton.addActionListener(e -> clearGame());
        finishGameButton.addActionListener(e -> finishGame());
        
        panel.add(controlTitle);
        panel.add(newGameButton);
        panel.add(checkStatusButton);
        panel.add(clearGameButton);
        panel.add(finishGameButton);
        panel.add(Box.createVerticalGlue()); // Espaçamento flexível
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedSoftBevelBorder(),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setPreferredSize(new Dimension(160, 40));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(33, 37, 41));
        
        cells = new JTextField[BOARD_SIZE][BOARD_SIZE];
        
        // Criar 9 subpainéis 3x3
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                JPanel subPanel = createSubPanel(boxRow, boxCol);
                panel.add(subPanel);
            }
        }
        
        return panel;
    }
    
    private JPanel createSubPanel(int boxRow, int boxCol) {
        JPanel subPanel = new JPanel(new GridLayout(3, 3, 2, 2));
        subPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(33, 37, 41), 3),
            BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        subPanel.setBackground(Color.GRAY);
        
        for (int cellRow = 0; cellRow < 3; cellRow++) {
            for (int cellCol = 0; cellCol < 3; cellCol++) {
                int globalRow = boxRow * 3 + cellRow;
                int globalCol = boxCol * 3 + cellCol;
                
                JTextField cell = createStyledCell(globalRow, globalCol);
                cells[globalRow][globalCol] = cell;
                subPanel.add(cell);
            }
        }
        
        return subPanel;
    }
    
    private JTextField createStyledCell(int row, int col) {
        JTextField cell = new JTextField();
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 20));
        cell.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        cell.setPreferredSize(new Dimension(50, 50));
        
        // Limitar entrada a um dígito
        cell.setDocument(new javax.swing.text.PlainDocument() {
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) 
                    throws javax.swing.text.BadLocationException {
                if (str == null) return;
                if ((getLength() + str.length()) <= 1 && str.matches("[1-9]")) {
                    super.insertString(offset, str, attr);
                }
            }
        });
        
        // Listeners para interação
        cell.addActionListener(e -> validateAndUpdateCell(row, col, cell));
        cell.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cell.selectAll();
            }
        });
        
        // Efeito hover
        cell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (cell.isEditable()) {
                    cell.setBackground(HOVER_COLOR);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateCellColor(cell, getCurrentSpace(row, col));
            }
        });
        
        return cell;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panel.setBackground(new Color(248, 249, 250));
        
        statusLabel = new JLabel("🎮 Clique em 'Novo Jogo' para começar sua jornada!");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(new Color(70, 130, 180));
        
        panel.add(statusLabel);
        return panel;
    }
    
    private void showNewGameDialog() {
        // Argumentos padrão do desafio DIO
        String defaultArgs = "0,0;4,false 1,0;7,false 2,0;9,true 3,0;5,false 4,0;8,true 5,0;6,true 6,0;2,true 7,0;3,false 8,0;1,false 0,1;1,false 1,1;3,true 2,1;5,false 3,1;4,false 4,1;7,true 5,1;2,false 6,1;8,false 7,1;9,true 8,1;6,true 0,2;2,false 1,2;6,true 2,2;8,false 3,2;9,false 4,2;1,true 5,2;3,false 6,2;7,false 7,2;4,false 8,2;5,true 0,3;5,true 1,3;1,false 2,3;3,true 3,3;7,false 4,3;6,false 5,3;4,false 6,3;9,false 7,3;8,true 8,3;2,false 0,4;8,false 1,4;9,true 2,4;7,false 3,4;1,true 4,4;2,true 5,4;5,true 6,4;3,false 7,4;6,true 8,4;4,false 0,5;6,false 1,5;4,true 2,5;2,false 3,5;3,false 4,5;9,false 5,5;8,false 6,5;1,true 7,5;5,false 8,5;7,true 0,6;7,true 1,6;5,false 2,6;4,false 3,6;2,false 4,6;3,true 5,6;9,false 6,6;6,false 7,6;1,true 8,6;8,false 0,7;9,true 1,7;8,true 2,7;1,false 3,7;6,false 4,7;4,true 5,7;7,false 6,7;5,false 7,7;2,true 8,7;3,false 0,8;3,false 1,8;2,false 2,8;6,true 3,8;8,true 4,8;5,true 5,8;1,false 6,8;4,true 7,8;7,false 8,8;9,false";
        
        JTextArea textArea = new JTextArea(defaultArgs, 5, 50);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        int result = JOptionPane.showConfirmDialog(this, scrollPane, 
            "Configuração do Jogo - Cole os argumentos ou use o padrão:", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String input = textArea.getText().trim();
            if (!input.isEmpty()) {
                startNewGame(input);
            }
        }
    }
    
    private void startNewGame(String argsString) {
        try {
            String[] args = argsString.split(" ");
            final Map<String, String> positions = Stream.of(args)
                    .collect(toMap(
                            k -> k.split(";")[0],
                            v -> v.split(";")[1]
                    ));
            
            boardService = new BoardService(positions);
            
            // Criar board diretamente para ter acesso aos métodos de modificação
            java.util.List<java.util.List<Space>> spaces = new java.util.ArrayList<>();
            for (int i = 0; i < 9; i++) {
                spaces.add(new java.util.ArrayList<>());
                for (int j = 0; j < 9; j++) {
                    var positionConfig = positions.get("%s,%s".formatted(i, j));
                    var expected = Integer.parseInt(positionConfig.split(",")[0]);
                    var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                    var currentSpace = new Space(expected, fixed);
                    spaces.get(i).add(currentSpace);
                }
            }
            board = new Board(spaces);
            
            updateBoardDisplay();
            statusLabel.setText("🎮 Jogo iniciado! Boa sorte na sua jornada!");
            statusLabel.setForeground(new Color(60, 179, 113));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Erro ao inicializar o jogo. Verifique o formato dos argumentos.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateBoardDisplay() {
        if (isNull(board)) return;
        
        var spaces = board.getSpaces();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Space space = spaces.get(i).get(j);
                JTextField cell = cells[i][j];
                
                if (space.isFixed()) {
                    cell.setText(String.valueOf(space.getActual()));
                    cell.setEditable(false);
                    cell.setBackground(FIXED_COLOR);
                    cell.setFont(new Font("Arial", Font.BOLD, 20));
                } else {
                    cell.setText(space.getActual() != null ? String.valueOf(space.getActual()) : "");
                    cell.setEditable(true);
                    cell.setFont(new Font("Arial", Font.PLAIN, 18));
                }
                
                updateCellColor(cell, space);
            }
        }
    }
    
    private void updateCellColor(JTextField cell, Space space) {
        if (space == null) {
            cell.setBackground(EDITABLE_COLOR);
            return;
        }
        
        if (space.isFixed()) {
            cell.setBackground(FIXED_COLOR);
        } else if (space.getActual() != null) {
            if (space.getActual().equals(space.getExpected())) {
                cell.setBackground(CORRECT_COLOR);
            } else {
                cell.setBackground(ERROR_COLOR);
            }
        } else {
            cell.setBackground(EDITABLE_COLOR);
        }
    }
    
    private Space getCurrentSpace(int row, int col) {
        if (board == null) return null;
        return board.getSpaces().get(row).get(col);
    }
    
    private void validateAndUpdateCell(int row, int col, JTextField cell) {
        if (isNull(board)) return;
        
        String text = cell.getText().trim();
        
        try {
            if (text.isEmpty()) {
                board.clearValue(row, col);
            } else {
                int value = Integer.parseInt(text);
                if (value >= 1 && value <= 9) {
                    if (!board.changeValue(row, col, value)) {
                        JOptionPane.showMessageDialog(this, 
                            "⚠️ Esta posição tem um valor fixo!", 
                            "Aviso", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    cell.setText("");
                    return;
                }
            }
        } catch (NumberFormatException e) {
            cell.setText("");
            return;
        }
        
        updateBoardDisplay();
    }
    
    private void checkGameStatus() {
        if (isNull(board)) {
            JOptionPane.showMessageDialog(this, 
                "🎮 Nenhum jogo foi iniciado!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String status = board.getStatus().getLabel();
        String errorStatus = board.hasErrors() ? 
            "❌ O jogo contém erros" : "✅ O jogo não contém erros";
        
        JOptionPane.showMessageDialog(this, 
            String.format("📊 Status do jogo: %s\n%s", status, errorStatus), 
            "Status do Jogo", 
            JOptionPane.INFORMATION_MESSAGE);
        
        statusLabel.setText(String.format("📊 Status: %s - %s", status, errorStatus));
        statusLabel.setForeground(board.hasErrors() ? 
            new Color(220, 20, 60) : new Color(60, 179, 113));
    }
    
    private void clearGame() {
        if (isNull(board)) {
            JOptionPane.showMessageDialog(this, 
                "🎮 Nenhum jogo foi iniciado!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "🧹 Tem certeza que deseja limpar seu jogo e perder todo seu progresso?",
            "Confirmar Limpeza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            board.reset();
            updateBoardDisplay();
            statusLabel.setText("🧹 Jogo limpo! Continue sua jornada.");
            statusLabel.setForeground(new Color(70, 130, 180));
        }
    }
    
    private void finishGame() {
        if (isNull(board)) {
            JOptionPane.showMessageDialog(this, 
                "🎮 Nenhum jogo foi iniciado!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (board.gameIsFinished()) {
            JOptionPane.showMessageDialog(this, 
                "🎉 PARABÉNS! Você concluiu o jogo com sucesso!\n" +
                "🏆 Desafio DIO completado com êxito!", 
                "🎉 Jogo Concluído! 🎉", 
                JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("🎉 Jogo concluído com sucesso! Desafio DIO completado! 🏆");
            statusLabel.setForeground(new Color(60, 179, 113));
        } else if (board.hasErrors()) {
            JOptionPane.showMessageDialog(this, 
                "❌ Seu jogo contém erros. Verifique o tabuleiro e ajuste-o.", 
                "Erros Encontrados", 
                JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "⏳ Você ainda precisa preencher alguns espaços!", 
                "Jogo Incompleto", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private Image createSudokuIcon() {
        // Criar um ícone simples em memória
        return new java.awt.image.BufferedImage(32, 32, java.awt.image.BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuGUIEnhanced().setVisible(true);
        });
    }
}