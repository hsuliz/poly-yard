<script setup lang="ts">
import type { Book } from "@/types/Book"
import type { Review } from "@/types/Review"

defineProps<{
  book: Book
  review?: Review
}>()
</script>

<template>
  <div class="grid grid-cols-1 gap-6 p-2">
    <div
      class="bg-gray-800 rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
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
              <p class="text-sm mt-1">Reviewed by: {{ review.username }}</p>
              <p class="text-sm mt-1">
                Rating:
                <span class="text-yellow-500 font-bold">{{ review.rating }}</span>
              </p>
              <p class="text-sm mt-2">{{ review.comment }}</p>
              <p class="text-xs text-gray-400 mt-4">
                Reviewed {{ new Date(review.createdAt).toLocaleDateString() }}
              </p>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
