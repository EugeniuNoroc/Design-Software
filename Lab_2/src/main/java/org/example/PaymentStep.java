package org.example;

public class PaymentStep implements IPipelineStep<OrderContext> {
    public void execute(OrderContext context) {
        context.paid = true;
        context.addLog("Paid " + context.amount);
    }
    public void describe(StringBuilder sb) {
        sb.append("PaymentStep");
    }
}