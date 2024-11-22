<script setup lang="ts">
import { ref, onMounted } from "vue"
import axios from "axios"
import type Review from "@/types/Review"
import BookCard from "@/components/cards/BookCard.vue"

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
  <div>
    <div class="grid grid-cols-1 gap-6 p-2">
      <div
        v-for="review in reviews"
        :key="review.id"
        class="bg-gray-800 rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
      >
        <BookCard :book="review.resource" :review="review" />
      </div>
    </div>
  </div>
</template>
