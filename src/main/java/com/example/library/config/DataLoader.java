package com.example.library.config;
import com.example.library.entity.*;
import com.example.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
**Аннотации Spring Framework:**
- `@Component` - помечает класс как компонент Spring (универсальная аннотация)
- `@Autowired` - автоматическое внедрение зависимостей
- `CommandLineRunner` - интерфейс для выполнения кода при запуске приложения
@Component
public class DataLoader implements CommandLineRunner {
@Autowired
private AuthorRepository authorRepository;
@Autowired
private CategoryRepository categoryRepository;
@Autowired
private BookRepository bookRepository;
@Autowired
private UserRepository userRepository;
@Override
public void run(String... args) throws Exception {
// Создание категорий
Category fiction = new Category("Художественная литература", "Романы, повести,
рассказы");
Category science = new Category("Научная литература", "Учебники, научные труды");
Category history = new Category("История", "Исторические книги");
categoryRepository.save(fiction);
categoryRepository.save(science);
categoryRepository.save(history);
// Создание авторов
Author tolstoy = new Author("Лев", "Толстой");
tolstoy.setBirthDate(LocalDate.of(1828, 9, 9));
tolstoy.setBiography("Русский писатель и мыслитель");

Полезные материалы

Документация
Spring Data JPA Reference
Author pushkin = new Author("Александр", "Пушкин");
pushkin.setBirthDate(LocalDate.of(1799, 6, 6));
pushkin.setBiography("Русский поэт, драматург и прозаик");
authorRepository.save(tolstoy);
authorRepository.save(pushkin);
// Создание книг
Book warAndPeace = new Book("Война и мир", "978-5-17-123456-7",
LocalDate.of(1869, 1, 1), 5);

warAndPeace.setAuthor(tolstoy);
warAndPeace.setCategory(fiction);
Book eugeneOnegin = new Book("Евгений Онегин", "978-5-17-123457-4",

LocalDate.of(1833, 1, 1), 3);

eugeneOnegin.setAuthor(pushkin);
eugeneOnegin.setCategory(fiction);
bookRepository.save(warAndPeace);
bookRepository.save(eugeneOnegin);
// Создание пользователей
User admin = new User();
admin.setEmail("admin@library.com");
admin.setPassword("admin123");
admin.setFirstName("Администратор");
admin.setLastName("Системы");
admin.setCreatedAt(LocalDateTime.now());
User librarian = new User();
librarian.setEmail("librarian@library.com");
librarian.setPassword("librarian123");
librarian.setFirstName("Библиотекарь");
librarian.setLastName("Главный");
librarian.setCreatedAt(LocalDateTime.now());
userRepository.save(admin);
userRepository.save(librarian);
System.out.println("Тестовые данные загружены успешно!");
}
}