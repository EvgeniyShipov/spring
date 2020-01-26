package ru.otus.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private Author author;
    private Jenre jenre;
    private Book book;

    @ChangeSet(order = "000", id = "dropDB", author = "shipovev", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "addAuthor", author = "shipovev", runAlways = true)
    public void insertAuthor(MongoTemplate template) {
        author = new Author()
                .setId("1")
                .setName("John")
                .setSurname("Steinbeck")
                .setPatronymic("Ernst");
        template.save(author);
    }

    @ChangeSet(order = "002", id = "addbook", author = "shipovev", runAlways = true)
    public void insertBook(MongoTemplate template) {
        book = new Book()
                .setId("1")
                .setTitle("The Winter of Our Discontent")
                .setAuthor(author)
                .setJenre(jenre);
        template.save(book);
    }

    @ChangeSet(order = "003", id = "addComment", author = "shipovev", runAlways = true)
    public void insertComment(MongoTemplate template) {
        Comment comment = new Comment()
                .setId("1")
                .setMessage("Just a message")
                .setBook(book);
        template.save(comment);
    }

    @ChangeSet(order = "004", id = "addComedy", author = "shipovev", runAlways = true)
    public void insertComedy(MongoTemplate template) {
        jenre = new Jenre()
                .setId("1")
                .setType("Comedy");
        template.save(jenre);
    }

    @ChangeSet(order = "005", id = "addTragedy", author = "shipovev", runAlways = true)
    public void insertTragedy(MongoTemplate template) {
        Jenre jenre = new Jenre()
                .setId("2")
                .setType("Tragedy");
        template.save(jenre);
    }

    @ChangeSet(order = "006", id = "addDrama", author = "shipovev", runAlways = true)
    public void insertDrama(MongoTemplate template) {
        Jenre jenre = new Jenre()
                .setId("3")
                .setType("Drama");
        template.save(jenre);
    }

    @ChangeSet(order = "007", id = "addHorror", author = "shipovev", runAlways = true)
    public void insertHorror(MongoTemplate template) {
        Jenre jenre = new Jenre()
                .setId("4")
                .setType("Horror");
        template.save(jenre);
    }
}