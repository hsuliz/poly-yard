import { useEffect, useState } from "react"
import findAllBooks from "../api/BookApi"
import { BookResponse } from "./BookResponse"

const Book = () => {
  const [books, setBooks] = useState<Array<BookResponse>>([])

  useEffect(() => {
    findAllBooks
      .then((res) => res.data)
      .then((data) => {
        setBooks(data)
      })
      .catch((err) => {
        console.log(err.message)
      })
  }, [])

  return (
    <div>
      <p>Books</p>
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Author</th>
            <th>Rating</th>
            <th>Comment</th>
          </tr>
        </thead>
        <tbody>
          {books.map((book, index) => (
            <tr key={book.id}>
              <td>{index + 1}</td>
              <td>{book.title}</td>
              <td>
                {book.author.firstName} {book.author.firstName}
              </td>
              <td>{book.review.rating}</td>
              <td>{book.review.comment}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default Book
