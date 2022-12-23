package rogovig.Library.data;

import org.springframework.data.repository.CrudRepository;
import rogovig.Library.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
