package org.example;

// "Чужой" класс с несовместимым интерфейсом (как будто внешняя библиотека).
public class LegacyMailSender {
    public void sendMail(String to) {
        System.out.println("[legacy] mail sent to " + to);
    }
}