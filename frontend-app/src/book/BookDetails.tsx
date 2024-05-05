import React from "react"
import { useLocation } from "react-router-dom"
import { BookResponse } from "./BookResponse"

interface BookDetailsProps {
  book: BookResponse
}

const BookCard: React.FC<BookDetailsProps> = ({ book }) => {
  return (
    <div className="w-full max-w-2xl p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-semibold text-gray-800 mb-4">
        {book.title}
      </h2>
      <div className="flex items-center mb-4">
        <div className="text-gray-600">By</div>
        <div className="ml-2 text-gray-800">
          <span>
            {book.author.firstName} {book.author.secondName}
          </span>
        </div>
      </div>
      <div className="flex items-center mb-4">
        <div className="text-gray-600">Review:</div>
        <div className="ml-2 text-gray-800">
          <span>{book.review.rating}</span>
          <span className="text-yellow-500 ml-2">⭐</span>
        </div>
      </div>
      <div className="text-gray-600">
        <p>{book.review.comment}</p>
      </div>
    </div>
  )
}

const BookDetails = () => {
  const location = useLocation()
  const book: BookResponse = location.state
  return <BookCard book={book} />
}

export default BookDetails
