package model;
import model.User;
import model.UserRole;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthService{
    private Map<String, User> userStorage;
    private static final Path USER_FILE = Paths.get("users.txt");
    private User currentUser;

    public AuthService(){
        this.userStorage = new HashMap<>();
        loadUsersFromFile();
        // Если пользователей нет, добавляем одного админа по умолчанию
        if (userStorage.isEmpty()) {
            User admin = new User("admin", "123", UserRole.ADMIN);
            userStorage.put(admin.getLogin(), admin);
            saveUsersToFile();
        }
    }

    private void loadUsersFromFile(){
        if(!Files.exists(USER_FILE)){
            System.out.println("Файл пользователей не найден, создаем новый...");
            return;
        }
        try {
            List<String> lines = Files.readAllLines(USER_FILE);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    User user = new User(
                            parts[0].trim(),                          // login
                            parts[1].trim(),                          // password
                            UserRole.valueOf(parts[2].trim())         // role
                    );
                    userStorage.put(user.getLogin(), user);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла пользователей: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Неверный формат роли в файле: " + e.getMessage());
        }
    }

    /* 2. Сохраняет всех пользователей из userStorage в файл */
    private void saveUsersToFile() {
        try {
            List<String> userLines = userStorage.values().stream()
                    .map(User::toFileString)
                    .collect(Collectors.toList());

            Files.write(USER_FILE, userLines);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла пользователей: " + e.getMessage());
        }
    }

    /* 3. Логика регистрации пользователя (по умолчанию CLIENT) */
    public boolean register(String login, String password) {
        if (userStorage.containsKey(login)) {
            return false; // Пользователь с таким логином уже существует
        }

        User newUser = new User(login, password, UserRole.CLIENT);
        userStorage.put(login, newUser);
        saveUsersToFile(); // Сохраняем нового пользователя в файл
        return true;
    }

    /* 4. Проверяет учетные данные и устанавливает текущего пользователя */
    public User login(String login, String password) {
        User user = userStorage.get(login);

        if (user != null && user.getPassword().equals(password)) {
            this.currentUser = user; // Успешный вход: устанавливаем текущего пользователя
            return user;
        }
        this.currentUser = null;
        return null;
    }

    /* 5. Проверка на вошедшего пользователя */
    public User getCurrentUser() {
        return currentUser;
    }

    /* Дополнительно: Выход из системы */
    public void logout() {
        this.currentUser = null;
    }
}