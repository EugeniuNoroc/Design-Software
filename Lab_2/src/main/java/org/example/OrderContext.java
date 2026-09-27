package org.example;

// Контекст - объект, который проходит через все шаги конвейера
// и накапливает результаты их работы.
public class OrderContext {
    public String customer;
    public double amount;      // сумма заказа
    public boolean paid;       // оплачен ли
    public boolean shipped;    // отправлен ли
    public boolean isDone;     // флаг для Responsibility Chain: если true - дальше не идём
    public String log = "";    // куда шаги пишут, что они сделали

    public OrderContext(String customer, double amount) {
        this.customer = customer;
        this.amount = amount;
    }

    public void addLog(String message) {
        log += message + "\n";
    }
}