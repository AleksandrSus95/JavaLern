import org.junit.jupiter.api.Test;

public class ExampleUSingString {
    @Test
    public void exampleUsingStrings(){
        /*
        Приколы со строками
        Строку можно создать 2мя способами
        1. String someString1 = "some string" - это литерал
        2. String someString2 = new String("some string") - это объект
        Разница между new String("crate some string) и String nameVar = "create some string"
        Если строка создана при помощи конструктора то они хранится в куче как объект,
        а если строка создана как литерал то она помещается так же в кучу но в спец место под
        названием String pool (пул строк)
        Объект String можно интернировать и поместить в пул.
        Интернирование(помещение в пул) работает так:
        При создание литерала например String someString = "this literal"
        JVM обращается в пул строк и ищет там такое же значение, если оно там есть то Java
        вернет просто ссылку на соответствующий адрес с таким же значением.
        Если в пуле нету такого значения то переменная будет интернирована и возращена ссылка
        на участок памяти и присвоена переменной.
        Например:
        */
        String someStr1 = "some string";
        String someStr2 = "some string";
        System.out.println("Сравниваем ссылки someStr1 == someStr2 результат " + (someStr1 == someStr2));
        /*
        переменная str1 и str2 ссылаются на один и тот же участок памяти, при сравнении их ссылок ==
        получим значение true
        == - сравнивает ссылки на объекты а не их значения
        При конкатинации строковых литералов
         */
        String strFull = "some string";
        String strConcat = "some" + " string";
        System.out.println("Сравниваем ссылки strFull == strConcat результат " + (strFull == strConcat));
        /*
        Тут будет дело вот в чем
        strFull - помещает в пул строк занчение some string
        так же в поле строк у нас помещаются значения some и значение string
        при выполнении конкатенации (+) мы получаем новую строку, джава смотрит в пул строк видит там
        такое же значение и возвращает нам ссылку на него а не создает новый объект

        Еще один итересный пример:
         */
        String strChapter1 = "some";
        String strFullConcat = strChapter1 + " string";
        String strFullStringEx2 = "some string";
        System.out.println("Сравниваем ссылки strFullConcat == strFullStringEx2 результат " + (strFullConcat == strFullStringEx2));
        /*
        Результат будет false потому что:
        в переменной strFullConcat мы складываем переменную с литералом и это будет происходить
        во время рантайма приложения, а строковыйй пул набирается во время компиляции приоложения
        (во все проектк во време компиляции собираются строковые литералы и кладутся в пул)
        Попробуем интерировать их во время рантайма и посмотрим что их ссылки станут равны.
         */
        strFullConcat = strFullConcat.intern();
        System.out.println("Сравниваем ссылки strFullConcat == strFullStringEx2 результат " + (strFullConcat == strFullStringEx2));
        /*
        Тут уже будет значение труе так как мы во время рантайма интернировали переменную strFullConcat
         */
        String str1 = new String("this random string");
        String str2 = str1;//Приравниваем str2 ссылку на str1
        System.out.println("Сравнение ссылок на эти объекты = " + (str1 == str2));//Результат true
        str2 = "this random string";//Тут будет уже другая ссылка
        System.out.println("Сравнение ссылок на эти объекты = " + (str1 == str2));//Результат false
        //Но если мы будем сравнивать значения методом equals то они будут эквивалентны
        System.out.println("Сравнение значения Объекта = " + str1.equals(str2));
    }
}
