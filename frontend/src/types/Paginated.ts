import type { Review } from "@/types/Review"

export interface PaginatedResponse {
  content: Review[]
  pageable: Pageable
  totalPages: number
  totalElements: number
  size: number
  number: number
  sort: Sort
  first: boolean
  last: boolean
  numberOfElements: number
  empty: boolean
}

interface Pageable {
  offset: number
  pageNumber: number
  pageSize: number
  paged: boolean
  unpaged: boolean
  sort: Sort
}

interface Sort {
  empty: boolean
  sorted: boolean
  unsorted: boolean
}
