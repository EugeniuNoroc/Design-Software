package org.example;

public class ShippingStep implements IPipelineStep<OrderContext> {
    public void execute(OrderContext context) {
        context.shipped = true;
        context.addLog("Shipped to " + context.customer);
    }
    public void describe(StringBuilder sb) {
        sb.append("ShippingStep");
    }
}