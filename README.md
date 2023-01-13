# Public

Несколько рабочих проектов.

1. Консольные скачки . Мультипоточность

2. Чат. Реализовал серверную и клиентскую часть. Также создал бот с календарной инфой (календарь, время...). Так же простая графическая оболочка на базе        swing. Реализована многопоточность на базе потокобезопасных элементов

3. Калькулятор. Десктопный простой калькулятор. Создавал в Java, собирал в Maven. оболочку создавал через JFrame. Заморочка и сложность оказалась в              последовательности операции при нажатии клавиши. То есть калькулятор должен правильно понять, что сейчас пойдет новое число, это следующая цифра этого        числа    или ввод операции. Тут и я похоже не все правильно сделал, надо будет еще поколдовать
   
4. Библиотека. Просто практика для себя. Технологии: Spring, MVC, JPA, REST, Thymeleaf, H2, Lombok. 
   Отображение наличия книг в библиотеке, с возможностью корректировки, в том числе через Rest. Хранение в базе H2.

5. Проект интернет-магазина Шаурмы. Технологии: Spring MVC, WEB, JPA, Rest, Thymeleaf, HTML, Lombok.
   Данные о заказах и ингредиентах хранятся в H2DataBase.
   Обработка GET, POST запросов.
   Добавлен REST контроллер для работы с ингредиентами и заказами

6. Проект приложения калькулятора расстояния между городами по координатам. Возможность расчета результата расстояния по прямой и по сфере.
   Реализован Rest контроллер, имеющий 3 конечные точки: 1. ВЫдать список всех городов в базе; 2. рассчитать расстояния между определенными городами; 3.            Загрузить и распарсить XML файл с данными городов.
   Хранение данных организовано в БД. 
   В планах прикрутить интерфейс.
7. Телеграмбот. Пока основные (start, stop, help, stat) комманды, будут доболняться со временем. Технологии:
   Spring boot
   Docker (Dovker-compose)
   БД: Postgresql в отдельном контейнере, JPA
   Flyway - миграция БД
   Созданы Баш скрипты для запуска и остановки контейнеров на удаленном сервере
   Mockito тесты 
   Maven
   
