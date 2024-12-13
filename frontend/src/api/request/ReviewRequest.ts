import type { ResourceRequest } from "@/api/request/ResourceRequest"

export interface ReviewRequest {
  type: string
  resource: ResourceRequest
  rating: number
  comment: string | null
}
