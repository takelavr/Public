package rogovig.Library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogovig.Library.data.BookRepository;
import rogovig.Library.model.Book;

import java.util.Optional;

@RestController
@RequestMapping(value = {"/api"}, produces = "application/json")
public class HomeControllerRest {

    private BookRepository repo;

    @Autowired
    public HomeControllerRest(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Book>getAllBooks() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Optional<Book>bookMayBe = repo.findById(id);
        if (bookMayBe.isPresent()) {
           return new ResponseEntity<>(bookMayBe.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public Book postBook(@RequestBody Book book) {
        return repo.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable("id") Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e){}
    }

    @PutMapping("/{id}")
    public Book putBook(@RequestBody Book book) {
        return repo.save(book);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public Book patchBook(@PathVariable("id") Long id, @RequestBody Book bookPath) {
        Book bookRefresh = repo.findById(id).get();
        if (bookPath.getAuthor() != null) {
            bookRefresh.setAuthor(bookPath.getAuthor());
        }
        if (bookRefresh.getName() != null) {
            bookRefresh.setName(bookPath.getName());
        }
        return repo.save(bookRefresh);
    }



}
