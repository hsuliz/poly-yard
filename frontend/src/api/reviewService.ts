import { apiClient } from "./axiosConfig"
import type { ReviewRequest } from "@/api/request/ReviewRequest"
import type { Review } from "@/types/Review"

const getReviews = async (): Promise<Review[]> => {
  try {
    const response = await apiClient.get("/api/reviews")
    console.log("Reviews found:", response.data.content)
    return response.data.content
  } catch (error: any) {
    if (error.response && error.response.status === 404) {
      console.log("Book not found")
    }
    console.error("Error checking Reviews:", error)
    throw error
  }
}

const getReviewsByBook = async (isbn: string): Promise<Review[]> => {
  try {
    const response = await apiClient.get("/api/reviews", {
      params: {
        "resource-type": "isbn",
        "resource-value": isbn
      }
    })
    console.log("Reviews found:", response.data.content)
    return response.data.content
  } catch (error: any) {
    if (error.response && error.response.status === 404) {
      console.log("Book not found")
    }
    console.error("Error checking Reviews:", error)
    throw error
  }
}

const submitReview = async (review: ReviewRequest): Promise<void> => {
  await apiClient.post("/api/me/reviews", review)
}

export { getReviews, getReviewsByBook, submitReview }
