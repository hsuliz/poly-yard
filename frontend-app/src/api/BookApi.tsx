import axios from "axios"

const BOOK_API_URL: string = `${process.env.REACT_APP_BOOK_API}`

const findAllBooks = async () => axios.get(BOOK_API_URL)

const deleteBookById = (id: string) => {
  return axios.delete(`${BOOK_API_URL}/${id}`)
}

export { findAllBooks, deleteBookById }
