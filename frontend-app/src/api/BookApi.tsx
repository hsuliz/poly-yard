import axios from "axios"

const BOOK_API_URL: string = `${process.env.REACT_APP_BOOK_API}`

const findAllBooks = async () => axios.get(BOOK_API_URL)

const getBookById = async (id: string) => axios.get(`${BOOK_API_URL}/${id}`)

const deleteBookById = (id: string) => axios.delete(`${BOOK_API_URL}/${id}`)

export { findAllBooks, deleteBookById, getBookById }
