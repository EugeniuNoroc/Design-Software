package org.example;

// Декоратор (Wrapper): оборачивает другой шаг и добавляет код до/после, не меняя сам оборачиваемый шаг.
public class LoggingDecorator<TContext> implements IPipelineStep<TContext> {
    private final IPipelineStep<TContext> inner; // обёрнутый шаг

    public LoggingDecorator(IPipelineStep<TContext> inner) {
        this.inner = inner;
    }

    public void execute(TContext context) {
        System.out.println(">> before step");
        inner.execute(context);   // выполняем настоящий шаг
        System.out.println("<< after step");
    }

    public void describe(StringBuilder sb) {
        sb.append("Logged[");
        inner.describe(sb); // делегируем описание внутреннему шагу
        sb.append("]");
    }
}
