import java.awt.Color;
import java.awt.Graphics;

public class Car {
    private String brand;
    private String color;
    private float fuelLevel;
    private float fuelCapacity;
    private float consumptionRate;

    public Car(String brand, String color, float fuelCapacity, float consumptionRate) {
        this.brand = brand;
        this.color = color;
        this.fuelCapacity = fuelCapacity;
        this.consumptionRate = consumptionRate;
        this.fuelLevel = 0;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public float getConsumptionRate() {
        return consumptionRate;
    }

    public void addFuel(float amount) {
        fuelLevel = Math.min(fuelLevel + amount, fuelCapacity);
    }

    public void drive(float distance) {
        float fuelUsed = distance / consumptionRate;
        fuelLevel = Math.max(fuelLevel - fuelUsed, 0);
    }

    public void animateFuelChange(Graphics g, int x, int y, int width, int height) {
        int fuelHeight = (int) ((fuelLevel / fuelCapacity) * height);
        g.setColor(Color.GRAY);
        g.drawRect(x, y, width, height); // Tank outline
        g.setColor(Color.BLACK);
        g.fillRect(x, y + height - fuelHeight, width, fuelHeight); // Fuel level
    }

    public String getCarInfo() {
        return "<html>Brand: " + brand + "<br/>" +
                "Color: " + color + "<br/>" +
                "Fuel Level: " + fuelLevel + "L / " + fuelCapacity + "L<br/>" +
                "Consumption Rate: " + consumptionRate + " km/L</html>";
    }
}
