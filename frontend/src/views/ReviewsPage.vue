<script setup lang="ts">
import { onMounted, ref } from "vue"
import axios from "axios"
import type { Review } from "@/types/Review"
import ReviewList from "@/components/ReviewList.vue"

const reviews = ref<Review[]>([])

const fetchReviews = async () => {
  try {
    const response = await axios.get("/api/reviews")
    reviews.value = response.data.content
    console.info(response)
  } catch (error) {
    console.error("Error fetching reviews:", error)
  }
}

onMounted(() => {
  fetchReviews()
})
</script>

<template>
  <ReviewList :reviews="reviews" />
</template>
