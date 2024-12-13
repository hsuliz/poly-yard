import type { Book } from "@/types/Book"

export interface Review {
  id: number
  username: string
  type: string
  resource: Book
  rating: number
  comment: string | null
  createdAt: string
}
