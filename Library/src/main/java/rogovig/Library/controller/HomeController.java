package rogovig.Library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rogovig.Library.model.Book;
import rogovig.Library.data.BookRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    private BookRepository bookRepository;

    @Autowired
    public HomeController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("newbook", new Book());
        return "index";
    }

    @PostMapping
    public String createBook(Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/{id}/show")
    public String showById(@PathVariable("id") Long id, Model model) {
        if (bookRepository.findById(id).isPresent()) {
            model.addAttribute("book", bookRepository.findById(id).get());
            return "show";
        }
        model.addAttribute("book", new Book());
        return "show";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editFormById(@PathVariable("id") Long id, Model model) {
        if (bookRepository.findById(id).isPresent()) {
            model.addAttribute("book", bookRepository.findById(id).get());
            return "edit";
        }
        model.addAttribute("book", new Book());
        return "edit";
    }

    @PostMapping("/{id}")
    public String editBook(@PathVariable("id") Long id, Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }


}
