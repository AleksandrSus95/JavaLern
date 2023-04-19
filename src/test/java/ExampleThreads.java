import jdk.jfr.Description;
import org.examples.threadClass.TalkThread;
import org.examples.threadClass.WalkThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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

    @Test
    @DisplayName("Пример работы механизма Fork/Join")
    public void exampleForkJoin(){
        class SumRecursiveTask extends RecursiveTask<Long> {
            private List<Long> longList;
            private int begin;
            private int end;
            public static final long THRESHOLD = 10_000;
            private SumRecursiveTask(List<Long> longList, int begin, int end){
                this.longList = longList;
                this.begin = begin;
                this.end = end;
            }

            public SumRecursiveTask(List<Long> longList){
                this(longList, 0, longList.size());
            }

            @Override
            protected Long compute() {
                int length = end - begin;
                long result = 0;
                if(length <= THRESHOLD){
                    for (int i = begin; i < end; i++){
                        result += longList.get(i);
                    }
                }else {
                    int middle = begin + length / 2;
                    SumRecursiveTask taskLeft = new SumRecursiveTask(longList, begin, middle);
                    taskLeft.fork();// Асинхронный запуск
                    SumRecursiveTask taskRight = new SumRecursiveTask(longList, middle, end);
                    taskRight.fork();
                    Long leftSum = taskLeft.join();
                    Long rightSum = taskRight.join();
                    result = leftSum + rightSum;
                }
                return result;
            }
        }
        int end = 1_000_000;
        List<Long> numbers = LongStream.range(0, end)
                .boxed()
                .toList();
        ForkJoinTask<Long> task = new SumRecursiveTask(numbers);
        long result = new ForkJoinPool().invoke(task);
        System.out.println(result);
    }

    @Test
    @DisplayName("Параллелизм")
    public void exampleParallelism(){
        long result;
        result = LongStream.range(0, 1_000_000_000)
                .boxed()
                .parallel()
                .map(x -> x / 7)
                .peek(v -> System.out.println(Thread.currentThread().getName()))//будет выводить имена потоков
                .reduce((x,y) -> x + (int)(3*Math.sin(y)))
                .get();
        System.out.println(result);
    }

    @Test
    @DisplayName("Параллелизм")
    @Description("Обертка неявного параллелизма в ForkJoinPool")
    public void exampleParallelismInForkJoinPool(){
        long sec = System.currentTimeMillis();
        Callable<Integer> task = () -> IntStream.range(0, 1_000_000_000)
                .boxed()
                .parallel()
                .map(x -> x /3)
                .peek(th -> System.out.println(Thread.currentThread().getName()))
                .reduce((x,y) -> x + (int)(3*Math.sin(y)))
                .get();
        ForkJoinPool pool = new ForkJoinPool(8); //8 processors
        try {
            int result = pool.submit(task).get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println((System.currentTimeMillis() - sec) / 1000);
    }
}
