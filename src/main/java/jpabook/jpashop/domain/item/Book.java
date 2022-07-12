package jpabook.jpashop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{

    private String author;
    private String isbn;

    //== 연관관계 편의 메서드 ==//
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
    //==생성 메서드==//
    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);

        book.setAuthor(author);
        book.setIsbn(isbn);

        return book;
    }
}
