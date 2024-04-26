import { useEffect } from "react"
import findAllBooks from "../api/BookApi"

const Book = () => {
  useEffect(() => {
    findAllBooks.then((value) => console.log(value))
  }, [])

  return (
    <div>
      <h1>Welcome to my app</h1>
    </div>
  )
}
export default Book
