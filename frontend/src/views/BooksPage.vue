<script setup lang="ts">
import { ref, onMounted } from "vue"
import axios from "axios"
import type Review from "@/types/Review"

const reviews = ref<Review[]>([])

const fetchReviews = async () => {
  try {
    const response = await axios.get("http://localhost:8002/reviews")
    reviews.value = response.data
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
        :key="review.book.isbn"
        class="bg-gray-800 rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
      >
        <div class="p-4">
          <div class="flex items-center">
            <img :src="review.book.image" alt="Book Cover" class="w-16 h-24 rounded-md mr-4" />
            <div class="flex-1">
              <h3 class="text-lg font-semibold">
                {{ review.book.title }} by {{ review.book.author }}
              </h3>
              <p class="text-sm mt-1">Reviewed by: {{ review.user.name }}</p>
              <p class="text-sm mt-1">
                Rating:
                <span class="text-yellow-500 font-bold">{{ review.rating }}</span>
              </p>
              <p class="text-sm mt-2">{{ review.comment }}</p>
              <p class="text-xs text-gray-400 mt-4">
                Reviewed {{ new Date(review.createdAt).toLocaleDateString() }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
