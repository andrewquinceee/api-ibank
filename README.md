# Домашнее задание: API Тестирование (Test Mode)

[![Java CI with Gradle](https://github.com/andrewquinceee/api-ibank/actions/workflows/gradle.yml/badge.svg)](https://github.com/andrewquinceee/api-ibank/actions/workflows/gradle.yml)

Проект содержит API-тесты для интернет-банка с использованием:
- REST Assured для отправки HTTP-запросов
- Gson для сериализации Java-объектов в JSON
- JavaFaker для генерации тестовых данных
- Lombok для сокращения boilerplate-кода

Тесты покрывают сценарии: регистрация активного/заблокированного пользователя, перезапись данных, а также граничные случаи (пустой логин/пароль).
