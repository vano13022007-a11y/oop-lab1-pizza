package ru.leti.lab1;

public class ExpressDelivery implements DeliveryMethod {
    private static final long COST = 400;
    private static final int DELIVERY_HOURS = 30;

    @Override
    public String getName() {
        return "Экспресс доставка";
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