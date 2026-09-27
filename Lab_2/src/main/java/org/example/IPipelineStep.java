package org.example;

// Generic TContext позволяет задавать тип контекста извне.
public interface IPipelineStep<TContext> {

    // Полиморфизм: pipeline вызывает execute, не зная конкретный класс шага.
    void execute(TContext context);

    // Интроспекция: шаг сам описывает себя, дописывая в StringBuilder.
    void describe(StringBuilder sb);
}