import type { Review } from "@/types/Review"

export interface Page {
  content: Review[]
  empty: boolean
  first: boolean
  last: boolean
  number: number
  numberOfElements: number
  pageable: Pageable
  size: number
  sort: Sort
  totalElements: number
  totalPages: number
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
