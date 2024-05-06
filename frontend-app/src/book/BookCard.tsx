import React, { useEffect, useState } from "react"
import { Link, useParams } from "react-router-dom"
import { BookResponse } from "./BookResponse"
import { deleteBookById, getBookById } from "../api/BookApi"
import threeDots from "../img/3-dots-icon-29.png"
import { TailSpin } from "react-loader-spinner"

interface BookDetailsProps {
  initialBook?: BookResponse
}

const BookCard: React.FC<BookDetailsProps> = ({ initialBook }) => {
  const { bookId } = useParams()
  const [book, setBook] = useState(initialBook)
  const [isHovered, setIsHovered] = useState(false)

  const getDateFromId = (id: string) => {
    const timestamp = parseInt(id.substring(0, 8), 16) * 1000
    return new Date(timestamp).toLocaleDateString()
  }

  useEffect(() => {
    if (!initialBook) {
      getBookById(bookId!)
        .then((response) => {
          setBook(response.data)
        })
        .catch((error) => {
          console.error("Error fetching book:", error)
        })
    }
  }, [bookId, initialBook])

  if (!book)
    return (
      <div className="flex justify-center">
        <TailSpin color="#00BFFF" height={50} width={50} />
      </div>
    )

  return (
    <div className="mx-auto max-w-lg bg-sky-950 rounded-lg p-6">
      <div className="flex items-center justify-between">
        <Link
          to={`/book/${book.id}`}
          className="font-semibold text-white hover:text-blue-600 transition duration-300 ease-in-out"
        >
          {book.title}
        </Link>
        <div
          className="flex justify-around h-10"
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
        >
          {isHovered ? (
            <div className="grid grid-rows-3 grid-flow-col gap-6">
              <button
                className="font-semibold text-green-900 hover:text-green-500"
                onClick={() => deleteBookById(book.id)}
              >
                Update
              </button>
              <button
                className="font-semibold text-red-900 hover:text-red-500"
                onClick={() => deleteBookById(book.id)}
              >
                Delete
              </button>
            </div>
          ) : (
            <img src={threeDots} alt="threeDots" className="size-8" />
          )}
        </div>
      </div>
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
        <p className="text-sm leading-6 text-gray-400">{book.review.comment}</p>
      </div>
    </div>
  )
}

export default BookCard
