import { apiClient } from "./axiosConfig"
import type { Book } from "@/types/Book"

const getBookByIsbn = async (bookIsbn: string): Promise<Book | null> => {
  try {
    const response = await apiClient.get(`/api/books/${bookIsbn}`)
    console.log("Book found:", response.data)
    return response.data
  } catch (error: any) {
    if (error.response && error.response.status === 404) {
      console.log("Book not found")
    }
    console.error("Error checking book:", error)
    throw error
  }
}

export { getBookByIsbn }
