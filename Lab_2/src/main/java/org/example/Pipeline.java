package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// Pipeline<TContext> - обобщённый по типу контекста.
public class Pipeline<TContext> {
    private final List<IPipelineStep<TContext>> steps = new ArrayList<>();

    public Pipeline<TContext> addStep(IPipelineStep<TContext> step) {
        steps.add(step);
        return this; // возвращаем себя, чтобы можно было цеплять .addStep().addStep()
    }

    // Выполнение конвейера. Responsibility Chain: если контекст говорит "done" - стоп.
    public void execute(TContext context, java.util.function.Predicate<TContext> isDone) {
        for (IPipelineStep<TContext> step : steps) {
            if (isDone.test(context)) {
                break; // какой-то шаг решил завершить цепочку
            }
            step.execute(context);
        }
    }

    // Интросперкция всего конвейера: собираем описания всех шагов.
    public String describe() {
        StringBuilder sb = new StringBuilder("Pipeline steps:\n");
        int i = 1;
        for (IPipelineStep<TContext> step : steps) {
            sb.append(i++).append(". ");
            step.describe(sb);
            sb.append("\n");
        }
        return sb.toString();
    }

    // ---- Data-Oriented: манипулируем списком шагов как данными ----

    // 1) Заменить первый шаг заданного типа на новый.
    public void replaceFirst(Class<?> typeToReplace, IPipelineStep<TContext> newStep) {
        for (int i = 0; i < steps.size(); i++) {
            if (typeToReplace.isInstance(steps.get(i))) {
                steps.set(i, newStep);
                return;
            }
        }
    }

    // 2) Обернуть все шаги заданного типа функцией-обёрткой (декоратор).
    public void wrapAll(Class<?> typeToWrap, Function<IPipelineStep<TContext>, IPipelineStep<TContext>> wrapper) {
        for (int i = 0; i < steps.size(); i++) {
            if (typeToWrap.isInstance(steps.get(i))) {
                steps.set(i, wrapper.apply(steps.get(i)));
            }
        }
    }

    // 3) Переместить первый шаг заданного типа на нужную позицию.
    public void moveTo(Class<?> typeToMove, int index) {
        for (int i = 0; i < steps.size(); i++) {
            if (typeToMove.isInstance(steps.get(i))) {
                IPipelineStep<TContext> step = steps.remove(i);
                steps.add(index, step);
                return;
            }
        }
    }
}