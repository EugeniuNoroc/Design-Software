package org.example;

public class DiscountStep implements IPipelineStep<OrderContext> {
    public static final DiscountStep INSTANCE = new DiscountStep();
    private DiscountStep() {}

    public void execute(OrderContext context) {
        if (context.amount > 100) {
            context.amount *= 0.9;
            context.addLog("Discount applied, new amount = " + context.amount);
        } else {
            context.addLog("No discount");
        }
    }
    public void describe(StringBuilder sb) {
        sb.append("DiscountStep (10% over 100)");
    }
}