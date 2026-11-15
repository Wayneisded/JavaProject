
import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Random;

public class Main extends JFrame {

    private int balance = 100;
    private final Random random = new Random();

    private final String[] symbols = {
        "🍒", "🍉", "🍋", "🔔", "⭐"
    };

    private final Color BG = new Color(20, 12, 40);
    private final Color GOLD = new Color(255, 190, 45);
    private final Color PURPLE = new Color(80, 35, 130);
    private final Color SLOT_BG = new Color(255, 248, 220);

    private JLabel balanceLabel;
    private JLabel[] slotLabels;
    private JLabel resultLabel;
    private JTextField betField;
    private JButton spinButton;

    public Main() {

        setTitle("Slot Machine");
        setSize(520, 570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

   
        JLabel titleLabel = new JLabel("🎰 JAVA SLOTS 🎰");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(GOLD);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(titleLabel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(BG);
        centerPanel.setLayout(
            new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        
        balanceLabel = new JLabel("BALANCE: $100");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(balanceLabel);
        centerPanel.add(Box.createVerticalStrut(25));

      
        JPanel slotPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        slotPanel.setBackground(BG);

        slotPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD, 4),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
            )
        );

        slotLabels = new JLabel[3];

        for (int i = 0; i < 3; i++) {

            slotLabels[i] = new JLabel("❔", SwingConstants.CENTER);
            slotLabels[i].setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 42)
            );
            slotLabels[i].setOpaque(true);
            slotLabels[i].setBackground(SLOT_BG);
            slotLabels[i].setForeground(PURPLE);
            slotLabels[i].setBorder(
                BorderFactory.createLineBorder(GOLD, 3)
            );
            slotLabels[i].setPreferredSize(
                new Dimension(110, 95)
            );

            slotPanel.add(slotLabels[i]);
        }

        centerPanel.add(slotPanel);
        centerPanel.add(Box.createVerticalStrut(25));

        // Bet label
        JLabel betLabel = new JLabel("ENTER YOUR BET AMOUNT");
        betLabel.setFont(new Font("Arial", Font.BOLD, 16));
        betLabel.setForeground(GOLD);
        betLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(betLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        
        betField = new JTextField("10");
        betField.setFont(new Font("Arial", Font.BOLD, 20));
        betField.setHorizontalAlignment(JTextField.CENTER);
        betField.setMaximumSize(new Dimension(180, 40));
        betField.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(betField);
        centerPanel.add(Box.createVerticalStrut(20));

       
        spinButton = new JButton("🎰 SPIN");
        spinButton.setFont(new Font("Arial", Font.BOLD, 22));
        spinButton.setBackground(GOLD);
        spinButton.setForeground(Color.BLACK);
        spinButton.setFocusPainted(false);
        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        spinButton.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(8, 30, 8, 30)
            )
        );

        spinButton.addActionListener(e -> spin());

        centerPanel.add(spinButton);
        centerPanel.add(Box.createVerticalStrut(20));

       
        resultLabel = new JLabel(
            "Place your bet and spin!",
            SwingConstants.CENTER
        );

        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 15));
        newGameButton.setBackground(PURPLE);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFocusPainted(false);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        newGameButton.addActionListener(e -> resetGame());

        centerPanel.add(newGameButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        getContentPane().setBackground(BG);

        setVisible(true);
    }

   
    private void playSound() {

        new Thread(() -> {

            try {

                File soundFile = new File("slotSound.wav");

                AudioInputStream audio =
                    AudioSystem.getAudioInputStream(soundFile);

                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();

            } catch (Exception e) {

                System.out.println(
                    "Sound error: " + e.getMessage()
                );
            }

        }).start();
    }


    private void spin() {

        int bet;

        try {

            bet = Integer.parseInt(
                betField.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Enter a valid whole number!",
                "Invalid Bet",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (bet <= 0) {

            JOptionPane.showMessageDialog(
                this,
                "Bet must be greater than 0!"
            );

            return;
        }

        if (bet > balance) {

            JOptionPane.showMessageDialog(
                this,
                "INSUFFICIENT FUNDS!"
            );

            return;
        }

        
        balance -= bet;
        balanceLabel.setText("BALANCE: $" + balance);

        // Disable controls
        spinButton.setEnabled(false);
        betField.setEnabled(false);

        
        playSound();

       
        final int[] count = {0};

        Timer timer = new Timer(100, null);

        timer.addActionListener(e -> {

            count[0]++;

            
            for (int i = 0; i < 3; i++) {

                slotLabels[i].setText(
                    symbols[random.nextInt(symbols.length)]
                );
            }

           
            if (count[0] >= 30) {

                timer.stop();

                
                String[] row = new String[3];

                for (int i = 0; i < 3; i++) {

                    row[i] = symbols[
                        random.nextInt(symbols.length)
                    ];

                    slotLabels[i].setText(row[i]);
                }

                
                int payout = getPayout(row, bet);

                if (payout > 0) {

                    balance += payout;

                    resultLabel.setText(
                        "🎉 YOU WON $" + payout + "!"
                    );

                    resultLabel.setForeground(
                        new Color(50, 255, 100)
                    );

                } else {

                    resultLabel.setText(
                        "😢 YOU LOST THIS ROUND!"
                    );

                    resultLabel.setForeground(
                        new Color(255, 80, 80)
                    );
                }

                balanceLabel.setText(
                    "BALANCE: $" + balance
                );

                
                spinButton.setEnabled(balance > 0);
                betField.setEnabled(true);

                if (balance == 0) {

                    JOptionPane.showMessageDialog(
                        this,
                        "GAME OVER! Final balance: $0",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        timer.start();
    }

   
    private int getPayout(String[] row, int bet) {

        
        if (row[0].equals(row[1]) &&
            row[1].equals(row[2])) {

            switch (row[0]) {

                case "🍒": return bet * 3;
                case "🍉": return bet * 4;
                case "🍋": return bet * 5;
                case "🔔": return bet * 10;
                case "⭐": return bet * 20;
            }
        }

        if (row[0].equals(row[1])) {

            switch (row[0]) {

                case "🍒": return bet * 2;
                case "🍉": return bet * 3;
                case "🍋": return bet * 4;
                case "🔔": return bet * 5;
                case "⭐": return bet * 10;
            }
        }

       
        if (row[1].equals(row[2])) {

            switch (row[1]) {

                case "🍒": return bet * 2;
                case "🍉": return bet * 3;
                case "🍋": return bet * 4;
                case "🔔": return bet * 5;
                case "⭐": return bet * 10;
            }
        }

        return 0;
    }

    
    private void resetGame() {

        balance = 100;

        balanceLabel.setText("BALANCE: $100");

        resultLabel.setText(
            "Place your bet and spin!"
        );

        resultLabel.setForeground(Color.WHITE);

        betField.setText("10");
        betField.setEnabled(true);

        for (JLabel slot : slotLabels) {

            slot.setText("❔");
        }

        spinButton.setEnabled(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
            () -> new Main()
        );
    }
}
