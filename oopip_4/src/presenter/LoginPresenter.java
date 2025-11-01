package presenter;

import model.AuthService;
import model.User;
import view.LoginController;

public class LoginPresenter {
    private AuthService authService = new AuthService();
    private LoginController view;

    public LoginPresenter(LoginController view) {
        this.view = view;
    }

    public void handleLogin(String login, String password) {
        User user = authService.login(login, password);

        if (user != null) {
            // Вход успешен: передаем роль пользователя для открытия нужного окна
            view.showMainStore(user.getRole());
        } else {
            view.showError("Неверный логин или пароль.");
        }
    }

    public void handleRegistration(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            view.showError("Логин и пароль не могут быть пустыми.");
            return;
        }

        if (authService.register(login, password)) {
            view.showSuccess("Регистрация успешна! Выполните вход.");
        } else {
            view.showError("Ошибка регистрации. Пользователь с таким логином уже существует.");
        }
    }
}