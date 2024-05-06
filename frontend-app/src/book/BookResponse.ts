export interface BookResponse {
  id: string
  title: string
  author: Author
  review: Review
}

interface Author {
  firstName: string
  secondName: string
}

interface Review {
  comment: string
  rating: number
}
