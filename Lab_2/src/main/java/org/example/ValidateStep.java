package org.example;

public class ValidateStep implements IPipelineStep<OrderContext> {
    public void execute(OrderContext context) {
        if (context.amount <= 0) {
            context.addLog("Validation FAILED: amount <= 0");
            context.isDone = true;
            return;
        }
        context.addLog("Validation OK");
    }
    public void describe(StringBuilder sb) {
        sb.append("ValidateStep (checks amount)");
    }
}