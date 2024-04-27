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

  const getDateFromId = (id: string): Date => {
    const timestamp = parseInt(id.substring(0, 8), 16) * 1000
    return new Date(timestamp)
  }

  return (
    <div className="bg-gray-300 border-green-600 border-b p-4 m-4 rounded">
      <p>Books</p>
      <div role="list" className="divide-y divide-gray-100">
        {books.map((book) => (
          <li key={book.id} className="flex justify-between gap-x-6 py-5">
            <div className="flex min-w-0 gap-x-4">
              <div className="min-w-0 flex-auto">
                <p className="text-sm font-semibold leading-6 text-gray-900">
                  {book.title}
                </p>
                <p className="mt-1 truncate text-xs leading-5 text-gray-500">
                  {book.author.firstName} {book.author.secondName}{" "}
                </p>
              </div>
              <div className="min-w-0 flex-auto rounded">
                <p className="mt-1 truncate text-xs leading-5">
                  {getDateFromId(book.id).toLocaleDateString()}
                </p>
                <p className="text-sm leading-6">Stars: {book.review.rating}</p>
              </div>
            </div>
            <div className="shrink-0 sm:flex sm:flex-col sm:items-end">
              <p className="text-sm leading-6 text-gray-900">
                Comment: {book.review.comment}
              </p>
            </div>
          </li>
        ))}
      </div>
    </div>
  )
}

export default Book
