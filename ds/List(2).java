package ds;
import java.util.Iterator;
import exceptions.*
;
public interface List<E> extends Iterable<E> {
    void insert(int pos, E data) throws WrongIndexException;
    void delete(int pos) throws WrongIndexException;
    E get(int pos) throws WrongIndexException;
    int search(E data);
    Iterator<E> iterator();
    int size();
   }
   