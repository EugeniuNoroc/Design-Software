import org.example.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest {

    @Test
    void successfulOrderRunsAllSteps() {
        Pipeline<OrderContext> p = new Pipeline<OrderContext>()
                .addStep(new ValidateStep())
                .addStep(DiscountStep.INSTANCE)
                .addStep(new PaymentStep())
                .addStep(new ShippingStep());

        OrderContext ctx = new OrderContext("Test", 200);
        p.execute(ctx, c -> c.isDone);

        assertTrue(ctx.paid);
        assertTrue(ctx.shipped);
        assertEquals(180.0, ctx.amount); // 200 - 10%
    }

    @Test
    void chainStopsWhenValidationFails() {
        Pipeline<OrderContext> p = new Pipeline<OrderContext>()
                .addStep(new ValidateStep())
                .addStep(new PaymentStep());

        OrderContext ctx = new OrderContext("Test", 0);
        p.execute(ctx, c -> c.isDone);

        assertTrue(ctx.isDone);
        assertFalse(ctx.paid); // до оплаты не дошли
    }

    @Test
    void discountSingletonIsSameInstance() {
        assertSame(DiscountStep.INSTANCE, DiscountStep.INSTANCE);
    }

    @Test
    void replaceFirstReplacesStep() {
        Pipeline<OrderContext> p = new Pipeline<OrderContext>()
                .addStep(new PaymentStep());

        // заменим PaymentStep на пустышку, оплаты быть не должно
        p.replaceFirst(PaymentStep.class, new IPipelineStep<OrderContext>() {
            public void execute(OrderContext c) { /* ничего */ }
            public void describe(StringBuilder sb) { sb.append("Noop"); }
        });

        OrderContext ctx = new OrderContext("Test", 50);
        p.execute(ctx, c -> c.isDone);
        assertFalse(ctx.paid);
    }

    @Test
    void describeListsAllSteps() {
        Pipeline<OrderContext> p = new Pipeline<OrderContext>()
                .addStep(new ValidateStep())
                .addStep(new PaymentStep());

        String desc = p.describe();
        assertTrue(desc.contains("ValidateStep"));
        assertTrue(desc.contains("PaymentStep"));
    }
}