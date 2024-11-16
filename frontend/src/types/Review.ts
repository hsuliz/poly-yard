import type Resource from "@/types/Resource"

export default interface Review {
  id: number
  username: string
  type: string
  resource: Resource
  rating: number
  comment: string | null
  createdAt: string
}
