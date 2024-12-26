<script setup lang="ts">
import type { Book } from "@/types/Book"
import type { Review } from "@/types/Review"
import { deleteReviewById } from "@/api/reviewService"

const { review } = defineProps<{
  book: Book
  review?: Review
  isUser?: boolean
}>()

const handleDeleteReview = async () => {
  try {
    await deleteReviewById(review!.id)
    alert("Deleted!")
    location.reload()
  } catch (error: any) {
    alert("Some error")
  }
}
</script>

<template>
  <div class="grid grid-cols-1 gap-6 p-2">
    <div
      class="bg-gray-800 rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300 group"
    >
      <div class="p-4">
        <div class="flex items-center">
          <img :src="book.image" alt="Book Cover" class="w-16 h-24 rounded-md mr-4" />
          <div class="flex-1">
            <h3 class="text-lg font-semibold">
              <router-link
                :to="`/books/${book.isbn}`"
                class="text-lg font-semibold text-blue-500 hover:underline"
              >
                {{ book.title }}
              </router-link>
              by {{ book.author }}
            </h3>
            <template v-if="review">
              <p class="text-sm mt-1">
                Reviewed by:
                <router-link
                  :to="`/users/${review.username}`"
                  class="text-blue-500 hover:underline"
                >
                  {{ review.username }}
                </router-link>
              </p>
              <p class="text-sm mt-1">
                Rating:
                <span class="text-yellow-500 font-bold">{{ review.rating }}</span>
              </p>
              <p class="text-sm mt-2">{{ review.comment }}</p>
              <p class="text-xs text-gray-400 mt-4">
                Reviewed {{ new Date(review.createdAt).toLocaleDateString() }}
              </p>
              <button
                v-if="isUser"
                class="bg-red-600 text-white text-xs px-2 py-1 rounded hover:bg-red-700 focus:outline-none opacity-0 group-hover:opacity-100 transition-opacity duration-300"
                @click="handleDeleteReview"
              >
                Delete
              </button>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
