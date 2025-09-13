// ========================= src/view/ChessGUI.java =========================
package view;

import controller.Game;
import ai.NeuralNetworkAI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import model.board.Position;
import model.pieces.Pawn;
import model.pieces.Piece;

public class ChessGUI extends JFrame {
    // Botão para mudar tema
    private JButton themeButton;
    private static final long serialVersionUID = 1L; // evita warning de serialização

    // --- Config de cores/styles ---
        // Temas de tabuleiro
        private static Color LIGHT_SQ = new Color(255, 255, 255); // Branco puro
        private static Color DARK_SQ  = new Color(144, 238, 144); // Verde claro (padrão)
        private static int boardTheme = 2; // 1=branco/azul, 2=padrão, 3=marrom, 4=preto/branco, 5=preto/vermelho
    // Método para alterar o tema do tabuleiro
    public void setBoardTheme(int theme) {
        boardTheme = theme;
        switch (theme) {
            case 1: // branco e azul
                LIGHT_SQ = Color.WHITE;
                DARK_SQ = new Color(30, 144, 255); // azul
                break;
            case 2: // padrão
                LIGHT_SQ = new Color(255, 255, 255); // branco
                DARK_SQ = new Color(144, 238, 144); // verde claro
                break;
            case 3: // marrom claro e escuro
                LIGHT_SQ = new Color(222, 184, 135); // marrom claro
                DARK_SQ = new Color(139, 69, 19);   // marrom escuro
                break;
            case 4: // preto e branco
                LIGHT_SQ = Color.WHITE;
                DARK_SQ = Color.BLACK;
                break;
            case 5: // preto e vermelho
                LIGHT_SQ = Color.BLACK;
                DARK_SQ = Color.RED;
                break;
            default:
                LIGHT_SQ = new Color(255, 255, 255);
                DARK_SQ = new Color(144, 238, 144);
        }
        repaintBoardColors();
    }

    // Atualiza as cores das casas do tabuleiro
    private void repaintBoardColors() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (squares[r][c] != null) {
                    boolean light = ((r + c) % 2 == 0);
                    squares[r][c].setBackground(light ? LIGHT_SQ : DARK_SQ);
                }
            }
        }
        if (boardPanel != null) {
            boardPanel.repaint();
        }
    }
    private static final Color HILITE_SELECTED = new Color(50, 120, 220);
    private static final Color HILITE_LEGAL    = new Color(20, 140, 60);
    private static final Color HILITE_LASTMOVE = new Color(220, 170, 30);

    private static final Border BORDER_SELECTED = new MatteBorder(3,3,3,3, HILITE_SELECTED);
    private static final Border BORDER_LEGAL    = new MatteBorder(3,3,3,3, HILITE_LEGAL);
    private static final Border BORDER_LASTMOVE = new MatteBorder(3,3,3,3, HILITE_LASTMOVE);

    private final Game game;

    private final JPanel boardPanel;
    private final JButton[][] squares = new JButton[8][8];

    private final JLabel status;
    private final DefaultListModel<String> historyModel;
    private final JList<String> historyList;
    private final JScrollPane historyScroll;
    private JLabel histLabel;
    private JLabel turnLabel;
    private JPanel rightPanelRef;
    private JPanel topPanelRef;
    private JPanel topStackRef;
    private JPanel actionButtonsRef;
    private JPanel themeRowRef;
    private JPanel exitRowRef;
    private JPanel bottomPanelRef;

    // UI Theme (Light/Dark)
    private boolean darkTheme = false;
    private Color uiBg;
    private Color uiFg;
    private Color panelBg;
    private Color controlBg;
    private Color controlFg;
    private Color listBg;
    private Color listFg;
    private Color listHoverBg;
    private Color listSelBg;
    private int historyHoverIndex = -1;

    // Botões da sidebar (para estilização por tema)
    private JButton appThemeButton;
    private JButton btnNewGame;
    private JButton btnNewVsPC;
    private JButton btnExit;

    // Menu / controles
    private JCheckBoxMenuItem pcAsBlack;
    private JSpinner depthSpinner;

    // Seleção atual e movimentos legais
    private Position selected = null;
    private List<Position> legalForSelected = new ArrayList<>();

    // Realce do último lance
    private Position lastFrom = null, lastTo = null;

    // IA
    private boolean aiThinking = false;
    private final Random rnd = new Random();
    private NeuralNetworkAI neuralAI;
    private int currentDifficulty = NeuralNetworkAI.EASY;

    public ChessGUI() {
        super("ChessGame");

        // Look&Feel Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}
    // Exemplo: setar tema inicial (marrom/branco)
    setBoardTheme(3);

        this.game = new Game();
        this.neuralAI = new NeuralNetworkAI(currentDifficulty);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // Inicializa controles internos (sem menu)
        pcAsBlack = new JCheckBoxMenuItem();
        pcAsBlack.setSelected(false);
        depthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));

        // Painel do tabuleiro (8x8)
        boardPanel = new JPanel(new GridLayout(8, 8, 0, 0));
        boardPanel.setBackground(Color.DARK_GRAY);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        // Botão de tema
        themeButton = new JButton("Mudar tabuleiro");
        themeButton.setFocusable(false);
        themeButton.addActionListener(e -> showThemeMenu());

        // Cria botões das casas
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                final int rr = r;
                final int cc = c;
                JButton b = new JButton();
                b.setMargin(new Insets(0, 0, 0, 0));
                b.setFocusPainted(false);
                b.setOpaque(true);
                b.setBorderPainted(true);
                b.setContentAreaFilled(true);
                b.setFont(b.getFont().deriveFont(Font.BOLD, 24f)); // fallback com Unicode
                b.addActionListener(e -> handleClick(new Position(rr, cc)));
                squares[r][c] = b;
                boardPanel.add(b);
            }
        }

        // Barra inferior de status
        status = new JLabel("Vez: Brancas");
        status.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        // Histórico (JList com hover)
        historyModel = new DefaultListModel<>();
        historyList = new JList<>(historyModel);
        historyList.setVisibleRowCount(14);
        historyList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyList.setFocusable(false);
        historyList.setCellRenderer(new ListCellRenderer<>() {
            private final DefaultListCellRenderer delegate = new DefaultListCellRenderer();
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = delegate.getListCellRendererComponent(list, value, index, isSelected, false);
                if (c instanceof JComponent comp) {
                    boolean isHover = index == historyHoverIndex;
                    Color bg = isSelected ? listSelBg : (isHover ? listHoverBg : listBg);
                    Color fg = listFg;
                    comp.setBackground(bg);
                    comp.setForeground(fg);
                    if (comp instanceof JLabel lbl) {
                        lbl.setOpaque(true);
                    }
                }
                return c;
            }
        });
        historyList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int idx = historyList.locationToIndex(e.getPoint());
                if (idx != historyHoverIndex) {
                    historyHoverIndex = idx;
                    historyList.repaint();
                }
            }
        });
        historyList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (historyHoverIndex != -1) {
                    historyHoverIndex = -1;
                    historyList.repaint();
                }
            }
        });
        historyScroll = new JScrollPane(historyList);

        // Layout principal: tabuleiro à esquerda, histórico à direita
        JPanel rightPanel = new JPanel(new BorderLayout(6, 6));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
        rightPanelRef = rightPanel;
        histLabel = new JLabel("Histórico de lances:");
        histLabel.setBorder(BorderFactory.createEmptyBorder(0,0,4,0));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanelRef = topPanel;
        JPanel actionButtons = new JPanel(new GridLayout(0, 1, 0, 8));
        actionButtonsRef = actionButtons;
        btnNewGame = new JButton("Novo jogo");
        btnNewGame.setFocusable(false);
        btnNewGame.setPreferredSize(new Dimension(260, 40));
        btnNewGame.addActionListener(e -> { pcAsBlack.setSelected(false); doNewGame(); });
        btnNewVsPC = new JButton("Novo jogo com o computador");
        btnNewVsPC.setFocusable(false);
        btnNewVsPC.setPreferredSize(new Dimension(260, 40));
        btnNewVsPC.addActionListener(e -> startNewGameVsComputer());
        actionButtons.add(btnNewGame);
        actionButtons.add(btnNewVsPC);

        JPanel topStack = new JPanel(new GridLayout(0, 1, 0, 6));
        topStackRef = topStack;
        topStack.add(actionButtons);
        turnLabel = new JLabel();
        turnLabel.setBorder(BorderFactory.createEmptyBorder(6,0,0,0));
        topStack.add(turnLabel);
        topStack.add(histLabel);

        topPanel.add(topStack, BorderLayout.NORTH);
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(historyScroll, BorderLayout.CENTER);

        // Bottom area: exit button row, then theme row (Mudar tema + Mudar tabuleiro)
        JPanel bottomPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        bottomPanelRef = bottomPanel;
        JPanel exitRow = new JPanel(new BorderLayout());
        exitRowRef = exitRow;
        btnExit = new JButton("Sair");
        btnExit.setFocusable(false);
        btnExit.setFont(btnExit.getFont().deriveFont(Font.BOLD, 15f));
        btnExit.setPreferredSize(new Dimension(260, 36));
        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        exitRow.add(btnExit, BorderLayout.CENTER);

        JPanel themeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        themeRowRef = themeRow;
        appThemeButton = new JButton("Mudar tema");
        appThemeButton.setFocusable(false);
        appThemeButton.setPreferredSize(new Dimension(150, 32));
        appThemeButton.addActionListener(e -> { darkTheme = !darkTheme; applyUiTheme(); refresh(); });
        themeRow.add(appThemeButton);
        themeButton.setPreferredSize(new Dimension(150, 32));
        themeRow.add(themeButton);

        bottomPanel.add(exitRow);
        bottomPanel.add(themeRow);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(boardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Atualiza ícones conforme a janela/painel muda de tamanho
        boardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refresh(); // recarrega ícones ajustando o tamanho
            }
        });

        setMinimumSize(new Dimension(920, 680));
        setLocationRelativeTo(null);

        // Atalhos: Ctrl+N, Ctrl+Q
        setupAccelerators();

        applyUiTheme();
        setVisible(true);
        refresh();
        maybeTriggerAI(); // caso o PC jogue primeiro
    }

    // Exibe menu de seleção de tema
    private void showThemeMenu() {
        String[] options = {
            "Branco e Azul",
            "Padrão (Verde)",
            "Marrom Claro/Escuro",
            "Preto e Branco",
            "Preto e Vermelho"
        };
        int choice = JOptionPane.showOptionDialog(
            this,
            "Escolha o tema do tabuleiro:",
            "Temas do Tabuleiro",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[boardTheme-1]
        );
        if (choice >= 0) {
            setBoardTheme(choice+1);
        }
    }

    // ----------------- Menus e controles (removido o menu superior) -----------------

    private void setupAccelerators() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "newGame");
        getRootPane().getActionMap().put("newGame", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { doNewGame(); }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "quit");
        getRootPane().getActionMap().put("quit", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(ChessGUI.this, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    private void doNewGame() {
        selected = null;
        legalForSelected.clear();
        lastFrom = lastTo = null;
        aiThinking = false;
        game.newGame();
        // Reinicializa a IA com a dificuldade atual
        neuralAI = new NeuralNetworkAI(currentDifficulty);
        refresh();
        maybeTriggerAI();
    }

    private void startNewGameVsComputer() {
        String[] options = {
            "Muito Fácil (Nível 2)", 
            "Fácil (Nível 4)", 
            "Médio (Nível 7)", 
            "Difícil (Nível 10)"
        };
        int choice = JOptionPane.showOptionDialog(
                this,
                "Escolha a dificuldade da IA:",
                "Novo jogo vs Computador",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );
        if (choice < 0) return;
        
        int difficulty = switch (choice) {
            case 0 -> NeuralNetworkAI.VERY_EASY;
            case 1 -> NeuralNetworkAI.EASY;
            case 2 -> NeuralNetworkAI.MEDIUM;
            default -> NeuralNetworkAI.HARD;
        };
        
        currentDifficulty = difficulty;
        neuralAI = new NeuralNetworkAI(difficulty);
        depthSpinner.setValue(difficulty);
        pcAsBlack.setSelected(true);
        doNewGame();
    }

    // ----------------- Interação de tabuleiro -----------------

    private void handleClick(Position clicked) {
        if (game.isGameOver() || aiThinking) return;

        // Se for vez do PC (pretas) e modo PC ativado, ignore cliques
        if (pcAsBlack.isSelected() && !game.whiteToMove()) return;

        Piece p = game.board().get(clicked);

        if (selected == null) {
            // Nada selecionado ainda: só seleciona se for peça da vez
            if (p != null && p.isWhite() == game.whiteToMove()) {
                selected = clicked;
                legalForSelected = game.legalMovesFrom(selected);
            }
        } else {
            // Já havia uma seleção
            List<Position> legals = game.legalMovesFrom(selected); // recalc por segurança
            if (legals.contains(clicked)) {
                Character promo = null;
                Piece moving = game.board().get(selected);
                if (moving instanceof Pawn && game.isPromotion(selected, clicked)) {
                    promo = askPromotion();
                }
                lastFrom = selected;
                lastTo   = clicked;

                game.move(selected, clicked, promo);

                selected = null;
                legalForSelected.clear();

                refresh();
                maybeAnnounceEnd();
                maybeTriggerAI();
                return;
            } else if (p != null && p.isWhite() == game.whiteToMove()) {
                // Troca a seleção para outra peça da vez
                selected = clicked;
                legalForSelected = game.legalMovesFrom(selected);
            } else {
                // Clique inválido: limpa seleção
                selected = null;
                legalForSelected.clear();
            }
        }
        refresh();
    }

    private Character askPromotion() {
        String[] opts = {"Rainha", "Torre", "Bispo", "Cavalo"};
        int ch = JOptionPane.showOptionDialog(
                this,
                "Escolha a peça para promoção:",
                "Promoção",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opts,
                opts[0]
        );
        return switch (ch) {
            case 1 -> 'R';
            case 2 -> 'B';
            case 3 -> 'N';
            default -> 'Q';
        };
    }

    // ----------------- IA (não bloqueante) -----------------

    private void maybeTriggerAI() {
        if (game.isGameOver()) return;
        if (!pcAsBlack.isSelected()) return;
        if (game.whiteToMove()) return; // PC joga de pretas

        aiThinking = true;
        status.setText("Vez: Pretas — IA Neural pensando... (Nível " + currentDifficulty + ")");

        new SwingWorker<Void, Void>() {
            NeuralNetworkAI.Move aiMove;
            @Override
            protected Void doInBackground() {
                try {
                    // Usa a IA neural para encontrar o melhor movimento
                    aiMove = neuralAI.findBestMove(game, false); // pretas
                } catch (Exception e) {
                    System.err.println("Erro na IA neural: " + e.getMessage());
                    e.printStackTrace();
                    aiMove = null;
                }
                return null;
            }

            @Override
            protected void done() {
                try { get(); } catch (Exception ignored) {}

                if (aiMove != null && !game.isGameOver() && !game.whiteToMove()) {
                    lastFrom = aiMove.from;
                    lastTo   = aiMove.to;
                    Character promo = null;
                    Piece moving = game.board().get(aiMove.from);
                    if (moving instanceof Pawn && game.isPromotion(aiMove.from, aiMove.to)) {
                        promo = 'Q';
                    }
                    game.move(aiMove.from, aiMove.to, promo);
                }
                aiThinking = false;
                refresh();
                maybeAnnounceEnd();
            }
        }.execute();
    }


    // ----------------- Atualização de UI -----------------

    private void refresh() {
        // 0) Atualiza cores de UI dependentes de tema
        status.setForeground(uiFg);
        status.setBackground(panelBg);
        historyList.setBackground(listBg);
        historyList.setForeground(listFg);
        historyScroll.getViewport().setBackground(listBg);

        // 1) Cores base e limpa bordas
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                boolean light = (r + c) % 2 == 0;
                Color base = light ? LIGHT_SQ : DARK_SQ;
                JButton b = squares[r][c];
                b.setBackground(base);
                b.setBorder(null);
                b.setToolTipText(null);
            }
        }

        // 2) Realce último lance
        if (lastFrom != null) squares[lastFrom.getRow()][lastFrom.getColumn()].setBorder(BORDER_LASTMOVE);
        if (lastTo   != null) squares[lastTo.getRow()][lastTo.getColumn()].setBorder(BORDER_LASTMOVE);

        // 3) Realce seleção (pode aplicar borda já)
        if (selected != null) {
            squares[selected.getRow()][selected.getColumn()].setBorder(BORDER_SELECTED);
        }

        // 4) Ícones das peças (ou Unicode como fallback)
        int iconSize = computeSquareIconSize();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = game.board().get(new Position(r, c));
                JButton b = squares[r][c];

                if (p == null) {
                    b.setIcon(null);
                    b.setText("");
                    continue;
                }

                char sym = p.getSymbol().charAt(0);
                ImageIcon icon = ImageUtil.getPieceIcon(p.isWhite(), sym, iconSize);
                if (icon != null) {
                    b.setIcon(icon);
                    b.setText("");
                } else {
                    b.setIcon(null);
                    b.setText(toUnicode(p.getSymbol(), p.isWhite()));
                }
            }
        }

        // 4b) Sobrepor indicações de jogada legal (pontos/capturas) APÓS ícones
        if (selected != null) {
            int dotFontSize = Math.max(12, iconSize / 2);
            for (Position d : legalForSelected) {
                JButton target = squares[d.getRow()][d.getColumn()];
                Piece targetPiece = game.board().get(d);
                if (targetPiece == null) {
                    target.setIcon(null);
                    target.setText("\u25CF");
                    target.setForeground(Color.BLACK);
                    target.setFont(target.getFont().deriveFont(Font.BOLD, (float) dotFontSize));
                } else {
                    target.setBorder(BORDER_LEGAL);
                }
            }
        }

        // 5) Histórico + Turno
        String side = game.whiteToMove() ? "Brancas" : "Pretas";
        String chk = game.inCheck(game.whiteToMove()) ? " — Xeque!" : "";
        if (aiThinking) chk = " — PC pensando...";
        if (turnLabel != null) {
            turnLabel.setText("Turno: " + side + chk);
            turnLabel.setForeground(darkTheme ? Color.WHITE : uiFg);
        }
        var hist = game.history();
        historyModel.clear();
        for (int i = 0; i < hist.size(); i += 2) {
            int moveNo = (i / 2) + 1;
            String white = hist.get(i);
            String black = (i + 1 < hist.size()) ? hist.get(i + 1) : "";
            String line = String.format("%2d. %-8s %-8s", moveNo, white, black);
            historyModel.addElement(line);
        }
        if (!historyModel.isEmpty()) {
            int last = historyModel.size() - 1;
            historyList.ensureIndexIsVisible(last);
        }
    }

    private void maybeAnnounceEnd() {
        if (!game.isGameOver()) return;
        String msg;
        if (game.inCheck(game.whiteToMove())) {
            msg = "Xeque-mate! " + (game.whiteToMove() ? "Brancas" : "Pretas") + " estão em mate.";
        } else {
            msg = "Empate por afogamento (stalemate).";
        }
        JOptionPane.showMessageDialog(this, msg, "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
    }

    private String toUnicode(String sym, boolean white) {
        return switch (sym) {
            case "K" -> white ? "\u2654" : "\u265A";
            case "Q" -> white ? "\u2655" : "\u265B";
            case "R" -> white ? "\u2656" : "\u265C";
            case "B" -> white ? "\u2657" : "\u265D";
            case "N" -> white ? "\u2658" : "\u265E";
            case "P" -> white ? "\u2659" : "\u265F";
            default -> "";
        };
    }

    private int computeSquareIconSize() {
        JButton b = squares[0][0];
        int w = Math.max(1, b.getWidth());
        int h = Math.max(1, b.getHeight());
        int side = Math.min(w, h);
        if (side <= 1) return 64;
        return Math.max(24, side - 8);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }

    private void applyUiTheme() {
        if (darkTheme) {
            uiBg = new Color(16, 16, 18);
            uiFg = new Color(235, 235, 235); // textos claros para alto contraste
            panelBg = new Color(0, 0, 0); // preto/cinza bem escuro para sidebar
            controlBg = new Color(50, 50, 54);
            controlFg = new Color(245, 245, 245); // botões com texto claro
            listBg = new Color(24, 24, 28);
            listFg = new Color(235, 235, 235);
            listHoverBg = new Color(36, 36, 40);
            listSelBg = new Color(60, 66, 80);
        } else {
            uiBg = new Color(245, 245, 245);
            uiFg = new Color(25, 25, 25);
            panelBg = new Color(250, 250, 250);
            controlBg = new Color(240, 240, 240);
            controlFg = uiFg;
            listBg = Color.WHITE;
            listFg = uiFg;
            listHoverBg = new Color(235, 242, 255);
            listSelBg = new Color(204, 232, 255);
        }

        getContentPane().setBackground(uiBg);
        boardPanel.setBackground(panelBg);
        for (Component comp : boardPanel.getComponents()) {
            if (comp instanceof JButton b) {
                b.setForeground(controlFg);
            }
        }
        // Right panel colors
        // Since rightPanel is local in constructor, set via parent traversal
        historyScroll.setBorder(BorderFactory.createLineBorder(darkTheme ? new Color(60, 60, 60) : new Color(220, 220, 220)));
        historyScroll.getViewport().setBackground(listBg);
        if (histLabel != null) {
            histLabel.setForeground(darkTheme ? uiFg : uiFg);
        }
        status.setForeground(darkTheme ? uiFg : uiFg);
        if (turnLabel != null) {
            turnLabel.setForeground(darkTheme ? uiFg : uiFg);
        }
        themeButton.setForeground(darkTheme ? controlFg : controlFg);
        if (rightPanelRef != null) {
            rightPanelRef.setBackground(panelBg);
        }
        if (topPanelRef != null) { topPanelRef.setBackground(panelBg); }
        if (topStackRef != null) { topStackRef.setBackground(panelBg); }
        if (actionButtonsRef != null) { actionButtonsRef.setBackground(panelBg); }
        if (themeRowRef != null) { themeRowRef.setBackground(panelBg); }
        if (exitRowRef != null) { exitRowRef.setBackground(panelBg); }
        if (bottomPanelRef != null) { bottomPanelRef.setBackground(panelBg); }
        JMenuBar mb = getJMenuBar();
        if (mb != null) {
            mb.setBackground(controlBg);
            mb.setForeground(darkTheme ? Color.BLACK : controlFg);
            for (MenuElement el : mb.getSubElements()) {
                if (el.getComponent() instanceof JComponent jc) {
                    jc.setBackground(controlBg);
                    jc.setForeground(darkTheme ? Color.BLACK : controlFg);
                }
            }
        }
        if (themeButton != null) {
            themeButton.setBackground(controlBg);
            themeButton.setForeground(darkTheme ? Color.WHITE : controlFg);
        }
        // Apply theme to sidebar buttons
        if (appThemeButton != null) { appThemeButton.setBackground(controlBg); appThemeButton.setForeground(controlFg); }
        if (btnNewGame != null)     { btnNewGame.setBackground(controlBg);     btnNewGame.setForeground(controlFg); }
        if (btnNewVsPC != null)     { btnNewVsPC.setBackground(controlBg);     btnNewVsPC.setForeground(controlFg); }
        if (btnExit != null)        { btnExit.setBackground(controlBg);        btnExit.setForeground(controlFg); }
    }
}
