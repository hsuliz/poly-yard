import React, { useEffect, useState } from "react"
import { TailSpin } from "react-loader-spinner"

import { BookResponse } from "./BookResponse"
import { findAllBooks } from "../api/BookApi"
import BookCard from "./BookCard"

const Book = () => {
  const [books, setBooks] = useState<Array<BookResponse>>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

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

  if (loading)
    return (
      <div className="flex justify-center">
        <TailSpin color="#00BFFF" height={50} width={50} />
      </div>
    )

  if (error) return <div>Error: {error}</div>

  return (
    <ol className="space-y-6 list-item">
      {books.map((book) => (
        <BookCard key={book.id} initialBook={book} />
      ))}
    </ol>
  )
}

export default Book
