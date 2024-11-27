import { apiClient } from "./axiosConfig"
import type { ReviewRequest } from "@/api/request/ReviewRequest"

export const submitReview = async (review: ReviewRequest): Promise<void> => {
  await apiClient.post("/api/me/reviews", review)
}
