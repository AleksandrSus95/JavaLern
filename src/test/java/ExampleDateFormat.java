import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ExampleDateFormat {
    @Test
    public void simpleOut() {
        System.out.println("simple out test");

    }
    @Test
    @DisplayName("Вывод даты во всез форматах")
    public void dataAllFormats(){
        Date date = new Date();
        Locale[] locales = DateFormat.getAvailableLocales();
//        for(Locale loc: locales){
//            DateFormat format = DateFormat.getDateInstance(DateFormat.FULL, loc);
//            System.out.println(loc.toString() + "----> " + format.format(date));
//        }
        Arrays.stream(locales).forEach(loc -> {
            DateFormat format = DateFormat.getDateInstance(DateFormat.FULL, loc);
            System.out.println(loc.toString() + "----> " + format.format(date));
        });
    }

    @Test
    @DisplayName("API Date/Time in Java 8")
    public void usingInstanceApi(){
        Instant instant = Instant.now();
        System.out.println(instant);
        instant = Instant.parse("2020-03-17T14:27:32.728500600Z");
        System.out.println(instant);
        System.out.println("Epoch second " + instant.getEpochSecond());
        System.out.println("Epoch millis " + instant.toEpochMilli());
        System.out.println("Parse millis " + Instant.ofEpochMilli(instant.toEpochMilli()));
        long millis = System.currentTimeMillis();
        System.out.println(millis);
        instant = Instant.ofEpochMilli(millis);
        System.out.println("Instant parse millis " + instant);
        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(localDate);

        //Example Clock
        Clock clock = Clock.systemDefaultZone();
        System.out.println("Clock Instance " + clock.instant());
        System.out.println("Clock millis " + clock.millis());

        //Local Date time now
        System.out.println("LocalDate.now() = " + LocalDate.now());
        System.out.println("LocalDate.now() = " + LocalTime.now());
        System.out.println("LocalDate.now() = " + LocalDateTime.now());

        //Set Local Date Time
        LocalDate localDate1 = LocalDate.of(2077, Month.JANUARY, 11);
        LocalTime localTime = LocalTime.of(22, 33, 59);
        LocalDateTime localDateTime = LocalDateTime.of(localDate1, localTime);
        LocalDateTime localDateTime1 = LocalDateTime.of(2023, 12, 27, 23, 59, 59);
        System.out.println("Local date " + localDate1);
        System.out.println("Local time " + localTime);
        System.out.println("Local Date/Time " + localDateTime);
        System.out.println("Local Date/Time of" + localDateTime1);

        //Compare Date Time
        boolean after = LocalDate.now().isAfter(LocalDate.now().minusDays(1));
        boolean before = LocalDate.now().isBefore(LocalDate.now().plusDays(1));
        System.out.println(after + " " +before);

        //Example parse with Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MM yyyy");
        String date = "30 03 2025";
        LocalDate localDateNow = LocalDate.parse(date, formatter);
        System.out.println(localDateNow);
    }

    @Test
    @DisplayName("Example Using Period and Duration")
    public void exampleDurationAndPeriod(){
        LocalDate before = LocalDate.of(2023, 03, 1);
        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
        Period period = Period.between(before, nowDate);
        System.out.println(period);
        System.out.println(period.getDays());

        //Example Using Duration
        Duration duration = Duration.between(LocalDateTime.of(2020, Month.JULY, 02, 8,0,0), LocalDateTime.now());
        System.out.println("Duration hours " + duration.toHours());
        System.out.println("Duration min " + duration.toMinutes());
        System.out.println("Duration mills " + duration.toMillis());
        System.out.println("Duration day " + duration.toDays());
    }

}
