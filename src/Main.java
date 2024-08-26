import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Car car = selectCar();

        JFrame frame = new JFrame("Fuel Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JPanel graphicsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.WHITE);
                g.setColor(Color.BLACK);
                g.drawRect(50, 50, 300, 200); // Border
                car.animateFuelChange(g, 50, 50, 300, 200);
            }
        };

        graphicsPanel.setPreferredSize(new Dimension(400, 300));
        graphicsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel carInfoLabel = new JLabel(car.getCarInfo());
        carInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        carInfoLabel.setForeground(Color.BLACK);

        JPanel infoPanel = new JPanel();
        infoPanel.add(carInfoLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(graphicsPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);

        String[] options = {"Add Fuel", "Drive", "Exit"};
        String choice;

        do {
            choice = (String) JOptionPane.showInputDialog(frame, "Choose an option", "Car Simulation",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case "Add Fuel":
                    if (car.getFuelLevel() == car.getFuelCapacity()) {
                        JOptionPane.showMessageDialog(frame, "Fuel tank is full!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String input = JOptionPane.showInputDialog(frame, "Enter amount of fuel to add (L):");
                        try {
                            float amount = Float.parseFloat(input);
                            animateFuelAddition(car, amount, graphicsPanel, carInfoLabel);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case "Drive":
                    if (car.getFuelLevel() == 0) {
                        JOptionPane.showMessageDialog(frame, "No fuel in the tank!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        float distance = Float.parseFloat(JOptionPane.showInputDialog(frame, "Enter distance to drive (km):"));
                        animateDriving(car, distance, graphicsPanel, carInfoLabel);
                    }
                    break;

                case "Exit":
                    break;

                default:
                    JOptionPane.showMessageDialog(frame, "Invalid option!");
                    break;
            }

        } while (!choice.equals("Exit"));
    }

    private static Car selectCar() {
        String[] carOptions = {"Toyota Conquest", "Ford Ranger", "Mitsubishi Triton"};
        int choice = JOptionPane.showOptionDialog(null, "Select a car:", "Car Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, carOptions, carOptions[0]);

        if (choice >= 0) {
            String color = selectColor();
            switch (carOptions[choice]) {
                case "Toyota Conquest":
                    return new Car("Toyota Conquest", color, 80, 12);
                case "Ford Ranger":
                    return new Car("Ford Ranger", color, 80, 10);
                case "Mitsubishi Triton":
                    return new Car("Mitsubishi Triton", color, 75, 11);
                default:
                    throw new IllegalArgumentException("Invalid car selection.");
            }
        } else {
            throw new IllegalArgumentException("No car selected.");
        }
    }

    private static String selectColor() {
        String[] colorOptions = {"Black", "Red", "White"};
        int choice = JOptionPane.showOptionDialog(null, "Select a Color", "Color Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, colorOptions, colorOptions[0]);

        if (choice >= 0) {
            return colorOptions[choice];
        } else {
            throw new IllegalArgumentException("No color selected.");
        }
    }

    private static void animateFuelAddition(Car car, float amount, JPanel panel, JLabel carInfoLabel) {
        float targetFuel = Math.min(car.getFuelLevel() + amount, car.getFuelCapacity());

        Timer timer = new Timer(25, e -> {
            if (car.getFuelLevel() < targetFuel) {
                car.addFuel(0.5f); // Faster animation
                carInfoLabel.setText(car.getCarInfo());
                panel.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    private static void animateDriving(Car car, float distance, JPanel panel, JLabel carInfoLabel) {
        float initialFuel = car.getFuelLevel();
        float targetFuel = Math.max(car.getFuelLevel() - (distance / car.getConsumptionRate()), 0);

        Timer timer = new Timer(25, e -> {
            if (car.getFuelLevel() > targetFuel) {
                car.drive(distance / 50); // Faster animation
                carInfoLabel.setText(car.getCarInfo());
                panel.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
}
