package ru.leti.lab1;

public class StandardDelivery implements DeliveryMethod {
    private static final long COST = 150;
    private static final int DELIVERY_HOURS = 1;

    @Override
    public String getName() {
        return "Стандартная доставка пиццы";
    }

    @Override
    public long calculateCost(long itemsSubtotal) {
        return COST;
    }

    @Override
    public int estimateDeliveryHours() {
        return DELIVERY_HOURS;
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}