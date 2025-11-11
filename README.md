# Информационные системы

## Лабораторная работа № 1

Для запуска тестов нужно задать значение переменной окружения `SPRING_PROFILES_ACTIVE` равное `test` - в этом профиле используется БД поднимаемая `testcontainers`

Для запуска в обычном режиме значение переменной окружения `SPRING_PROFILES_ACTIVE` равное `dev`

Файл `.env` должен находится в директории `resources`

Локальный файл .env

```
## Spring profile settings
SPRING_PROFILES_ACTIVE=dev

## Is service settings
IS_SERVICE_PORT=8080
IS_SERVICE_BD_USERNAME=postgres
IS_SERVICE_BD_PASSWORD=admin
IS_SERVICE_BD_URL=jdbc:postgresql://localhost:5432/is_service
```

Проброс порта для helios:

`ssh -L 8080:localhost:335159 s335159@se.ifmo.ru -p 2222`

Ссылка на фронтенд - https://github.com/stoneshik/first-lab-is-frontend

Писалось все по TDD, можно глянуть в гите (*≧ω≦*)
