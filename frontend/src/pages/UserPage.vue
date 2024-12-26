<script setup lang="ts">
import { onMounted } from "vue"
import ReviewList from "@/components/ReviewList.vue"
import { getReviewsByUserPage } from "@/api/reviewService"
import { pagination } from "@/api/pagination"

const { username } = defineProps<{
  username: string
}>()

const {
  items: reviews,
  currentPage,
  fetchItems,
  nextPage,
  previousPage
} = pagination((page, size) => getReviewsByUserPage(username, page, size), 10)

onMounted(() => {
  fetchItems(currentPage.value)
})
</script>

<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">{{ username }} reviews</h1>

    <ReviewList v-if="reviews?.content?.length" :reviews="reviews.content" />
    <div v-else class="text-gray-500">No reviews found</div>

    <div
      v-if="reviews?.content?.length && reviews.totalPages > 1"
      class="flex items-center justify-between mt-4"
    >
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
