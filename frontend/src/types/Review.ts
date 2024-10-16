import type Book from "@/types/Book"
import type User from "@/types/User"

export default interface Review {
  rating: number
  comment: string
  createdAt: string
  book: Book
  user: User
}
