import type Book from "@/types/Book"
import type { ResourceRequest } from "@/api/request/ResourceRequest"

export interface ReviewRequest {
  type: string
  resource: Book | ResourceRequest
  rating: number
  comment: string | null
}

