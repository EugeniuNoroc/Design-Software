package org.example;

public class Main {
    public static void main(String[] args) {
        // --- Основной pipeline на контексте OrderContext ---
        Pipeline<OrderContext> pipeline = new Pipeline<OrderContext>()
                .addStep(new ValidateStep())
                .addStep(DiscountStep.INSTANCE)          // Singleton
                .addStep(new PaymentStep())
                .addStep(new ShippingStep())
                .addStep(new MailAdapterStep());          // Адаптер

        // Декоратор: оборачиваем все PaymentStep логированием.
        pipeline.wrapAll(PaymentStep.class, step -> new LoggingDecorator<>(step));

        System.out.println("=== Введение конвейера (интроспекция) ===");
        System.out.println(pipeline.describe());

        System.out.println("=== Успешный заказ ===");
        OrderContext good = new OrderContext("Anna", 200);
        pipeline.execute(good, ctx -> ctx.isDone);
        System.out.println(good.log);

        System.out.println("=== Заказ с ошибкой (Responsibility Chain обрывает цепочку) ===");
        OrderContext bad = new OrderContext("Boris", 0);
        pipeline.execute(bad, ctx -> ctx.isDone);
        System.out.println(bad.log);

        // --- Второй контекст, чтобы показать generics ---
        demoSecondContext();
    }

    // Демонстрация того, что Pipeline работает с ЛЮБЫМ типом контекста.
    static void demoSecondContext() {
        System.out.println("=== Второй контекст (String) ===");
        Pipeline<StringBuilder> textPipe = new Pipeline<StringBuilder>()
                .addStep(new IPipelineStep<StringBuilder>() {
                    public void execute(StringBuilder c) { c.append("hello "); }
                    public void describe(StringBuilder sb) { sb.append("AppendHello"); }
                })
                .addStep(new IPipelineStep<StringBuilder>() {
                    public void execute(StringBuilder c) { c.append("world"); }
                    public void describe(StringBuilder sb) { sb.append("AppendWorld"); }
                });

        StringBuilder text = new StringBuilder();
        textPipe.execute(text, c -> false); // здесь цепочку не обрываем
        System.out.println("Result: " + text);
    }
}