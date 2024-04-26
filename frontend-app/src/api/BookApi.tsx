import axios from "axios"

const findAllBooks = async () => axios.get("http://localhost:8080/api/book")

export default findAllBooks()
