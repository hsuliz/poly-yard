package dev.hsuliz.bookservice.book

data class BookResponse(
    val isbn: String,
    val title: String,
    val author: String,
    val publishedDate: Int,
    val numberOfPages: Int,
    val image: String,
) {
    constructor(
        book: Book
    ) : this(book.isbn, book.title, book.author, book.publishedDate, book.numberOfPages, book.image)
}
