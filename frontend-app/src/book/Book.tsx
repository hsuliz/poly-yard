import React, { useEffect, useState } from "react"
import { TailSpin } from "react-loader-spinner"

import threeDots from "../img/3-dots-icon-29.png"

import { BookResponse } from "./BookResponse"
import { findAllBooks } from "../api/BookApi"

const Book = () => {
  const [books, setBooks] = useState<Array<BookResponse>>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [isHovered, setIsHovered] = useState(false)

  useEffect(() => {
    findAllBooks()
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
        <li key={book.id} className="bg-sky-950 rounded-lg p-6 w-full max-w-sm">
          <div className="flex items-center justify-between">
            <p className="font-semibold text-white">{book.title}</p>
            <div
              className="flex justify-around h-10"
              onMouseEnter={() => setIsHovered(true)}
              onMouseLeave={() => setIsHovered(false)}
            >
              {isHovered ? (
                <div className="grid grid-rows-3 grid-flow-col gap-6">
                  <button>Update</button>
                  <button>Delete</button>
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
            <p className="text-sm leading-6 text-gray-400">
              {book.review.comment}
            </p>
          </div>
        </li>
      ))}
    </ol>
  )
}

export default Book
