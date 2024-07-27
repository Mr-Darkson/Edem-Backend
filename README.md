# WEB-Сервис: «EDEM»

## Введение

Edem - это монолитное веб приложение, разработанное для безопасного обмена информации.  С ним вы можете не переживать о сохранности вашей информации. Проект реализован на Java, в качестве фундамента мы используем популярный фреймворк Spring. 
Основные его части в проекте:

- Spring MVC - это оригинальный веб-фреймворк, построенный на Servlet API и включенный в Spring Framework с самого начала
- Spring DATA - это модуль Spring Framework, который упрощает разработку приложений, использующих доступ к базам данных. Он предоставляет удобные абстракции для работы с различными типами баз данных и интегрируется с другими модулями Spring
- Spring SECURITY - это мощный и гибкий фреймворк для обеспечения безопасности в приложениях на платформе Java

## Основные функции:

- Авторизация/регистрация
- Кастомизация профиля
- Отправка, чтение, удаление сообщений
- Просмотр входящих, отправленных, корзины
- Отправка, скачивание файлов

## Установка
Для начала нужно выбрать место под проект. Открываем консоль:
```sh
git clone https://github.com/Mr-Darkson/Edem-Backend.git
```
Далее нужно ввести всего одну команду
```sh
docker compose up
```


