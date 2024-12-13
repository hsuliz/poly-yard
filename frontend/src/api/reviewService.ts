import { apiClient } from "./axiosConfig"
import type { ReviewRequest } from "@/api/request/ReviewRequest"
import type { Review } from "@/types/Review"
import axios, { AxiosError } from "axios"

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

const getReviewsByUser = async (username: string): Promise<Review[]> => {
  try {
    const response = await axios.get(`api/reviews`, {
      params: {
        username: username
      }
    })
    console.info(response)
    return response.data.content
  } catch (error: any) {
    if (error.response && error.response.status === 404) {
      console.log("Reviews not found")
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

const postReview = async (review: ReviewRequest): Promise<void> => {
  try {
    await apiClient.post("/api/me/reviews", review)
  } catch (error: unknown) {
    if (error instanceof AxiosError) {
      // Log Axios-specific error details
      console.error("HTTP Status:", error.response?.status)
      console.error("Response Data:", error.response?.data.message)
      console.error("Headers:", error.response?.headers)
    } else {
      // Handle generic error
      console.error("Unexpected error:", error)
    }
  }
}

const deleteReviewById = async (reviewId: number): Promise<any> => {
  await apiClient.delete(`/api/me/reviews/${reviewId}`)
}

export { getReviews, getReviewsByBook, getReviewsByUser, postReview, deleteReviewById }
