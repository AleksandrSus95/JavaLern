import jdk.jfr.Description;
import org.examples.threadClass.TalkThread;
import org.examples.threadClass.WalkThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExampleThreads {
    @Test
    @DisplayName("Пример запуска потоков")
    public void exampleRunThread() {
        WalkThread walkThread = new WalkThread();
        Thread talkThread = new Thread(new TalkThread());
        walkThread.setName("This Thread Walk");
        talkThread.setName("This Thread Talk");
        walkThread.start();// Расширили классом Thread запустили поток
        talkThread.start();// Имплиментировали интерфейс Runnable создали поток с этим классом и запустили
        while (walkThread.getState() != Thread.State.TERMINATED
                && talkThread.getState() != Thread.State.TERMINATED){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Walk Thread in State " + walkThread.getState().name());
            System.out.println("Talk Thread in State " + talkThread.getState().name());
        }
    }

    @Test
    @DisplayName("Интерфейс Callable")
    @Description("Пусть стоит задача посчитать сумму значений элементов целовичленного списка," +
        "решить эту задачу можно просто с помощью интерфейса Stream, но если таких элементов " +
        "сотни тысяч, то решать такую задачу лучше с помощью потоков")
    public void exampleUsingCallable(){
        // Пример с небольщим набором данных
        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9,10);
        int sum1 = list.stream().mapToInt(x -> x).sum();
        int sum2 = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum1 + " equals " + sum2);
        class ActionCallable implements Callable<Integer> {
            private List<Integer> integers;
            public ActionCallable(List<Integer> integers){
                this.integers = integers;
            }
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int number: integers){
                    sum += number;
                }
                return sum;
            }
        }

        List<Integer> bigList = IntStream.range(0, 10000).boxed().toList();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new ActionCallable(bigList));
        executorService.shutdown(); // Отсановка сервиса но не потока
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
