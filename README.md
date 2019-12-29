Конфигурация:
В пути build/resources/main находится файл конфигурации сервера applications.properties.
Файл необходимо поменять под свою базу данных
spring.datasource.url=jdbc:postgresql://localhost:5432/leader_it_test - адрес базы данных
spring.datasource.username=postgres - имя пользователя
spring.datasource.password=12345 - пароль

Для запуска сервера необходимо запустить исполняемый .jar файл, находящийся в build\libs
Запуск производится консольной командой: "java -jar LeaderItTest-1.0.jar"
