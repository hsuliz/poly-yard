import { apiClient } from "./axiosConfig"
import type Book from "@/types/Book"

const checkBook = async (isbn: string): Promise<Book | null> => {
  try {
    const response = await apiClient.get(`/api/books/${isbn}`)
    console.log("Book found:", response.data)
    return response.data
  } catch (error: any) {
    if (error.response && error.response.status === 404) {
      console.log("Book not found")
      return null
    }
    console.error("Error checking book:", error)
    throw error
  }
}

export { checkBook }
