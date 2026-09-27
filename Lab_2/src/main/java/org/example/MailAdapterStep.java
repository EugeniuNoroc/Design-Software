package org.example;

// Адаптер: приспосабливает LegacyMailSender под наш интерфейс IPipelineStep.
public class MailAdapterStep implements IPipelineStep<OrderContext> {
    private final LegacyMailSender sender = new LegacyMailSender();

    public void execute(OrderContext context) {
        sender.sendMail(context.customer); // переводим наш вызов в вызов чужого метода
        context.addLog("Confirmation mail sent");
    }
    public void describe(StringBuilder sb) {
        sb.append("MailAdapterStep (wraps LegacyMailSender)");
    }
}