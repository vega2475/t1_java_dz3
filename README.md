## Инструкция по аутентификации

### 1. Регистрация пользователя

Отправьте POST-запрос на эндпоинт:

POST http://localhost:8000/api/v1/user/register

#### Тело запроса:
```json
{
  "login": "your_login",
  "password": "your_password"
}
```

### 2. Аутентификация

Для получения JWT токена отправьте POST-запрос на эндпоинт:

POST http://localhost:8000/api/v1/user/login

#### Тело запроса:
```json
{
  "login": "your_login",
  "password": "your_password"
}
```

В ответе вы получите JWT токен.

### 3. Доступ к защищённым ресурсам

Для доступа к защищённым ресурсам, добавьте полученный JWT токен в заголовок Authorization каждого запроса:

Authorization: Bearer <your_jwt_token>
