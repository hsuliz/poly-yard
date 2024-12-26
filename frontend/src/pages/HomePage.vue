<script setup lang="ts">
import { onMounted } from "vue"
import ReviewList from "@/components/ReviewList.vue"
import { getReviews } from "@/api/reviewService"
import { pagination } from "@/api/pagination"

const {
  items: reviews,
  currentPage,
  fetchItems,
  nextPage,
  previousPage
} = pagination(getReviews, 10)

onMounted(() => {
  fetchItems(currentPage.value)
})
</script>

<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">Welcome to the Home Page</h1>
    <ReviewList v-if="reviews?.content" :reviews="reviews.content" />

    <div v-if="reviews" class="flex items-center justify-between mt-4">
      <button
        @click="previousPage"
        :disabled="currentPage === 0"
        class="px-4 py-2 text-white bg-blue-500 hover:bg-blue-700 disabled:bg-gray-800 disabled:cursor-not-allowed"
      >
        Previous
      </button>
      <span class="text-gray-700"> Page {{ currentPage + 1 }} of {{ reviews.totalPages }} </span>
      <button
        @click="nextPage"
        :disabled="currentPage >= reviews.totalPages - 1"
        class="px-4 py-2 text-white bg-blue-500 hover:bg-blue-700 disabled:bg-gray-800 disabled:cursor-not-allowed"
      >
        Next
      </button>
    </div>
  </div>
</template>
