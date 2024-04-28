import axios from "axios"

const BOOK_API_URL: string = `${process.env.REACT_APP_BOOK_API}`

const findAllBooks = async () => axios.get(BOOK_API_URL)

export default findAllBooks()
