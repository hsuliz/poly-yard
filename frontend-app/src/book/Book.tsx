import { useEffect, useState } from "react"
import { TailSpin } from "react-loader-spinner"
import findAllBooks from "../api/BookApi"
import { BookResponse } from "./BookResponse"
import { Link } from "react-router-dom"

const Book = () => {
  const [books, setBooks] = useState<Array<BookResponse>>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    findAllBooks
      .then((res) => {
        setBooks(res.data)
        setLoading(false)
      })
      .catch((err) => {
        setError(err.message)
        setLoading(false)
      })
  }, [])

  const getDateFromId = (id: string) => {
    const timestamp = parseInt(id.substring(0, 8), 16) * 1000
    return new Date(timestamp).toLocaleDateString()
  }

  if (loading)
    return (
      <div className="flex justify-center">
        <TailSpin color="#00BFFF" height={50} width={50} />
      </div>
    )
  if (error) return <div>Error: {error}</div>

  return (
    <ol className="mx-auto max-w-fit space-y-6 list-item">
      {books.map((book) => (
        <li key={book.id} className="bg-sky-950 rounded-lg p-6">
          <div className="">
            <Link
              to={`/book/${book.id}`}
              state={book}
              className="font-semibold text-white hover:text-blue-600 transition duration-300 ease-in-out"
            >
              {book.title}
            </Link>
            <p className="text-xs leading-5 text-gray-500">
              by {book.author.firstName} {book.author.secondName}
            </p>
            <div className="mt-2">
              <p className="text-xs leading-5">
                <span className="text-lg text-yellow-600">
                  {book.review.rating} ⭐
                </span>{" "}
                <span className="text-xs text-gray-500">
                  {getDateFromId(book.id)}
                </span>
              </p>
            </div>
            <div className="mt-2">
              <p className="text-sm leading-6 text-gray-400">
                {book.review.comment}
              </p>
            </div>
          </div>
        </li>
      ))}
    </ol>
  )
}

export default Book
