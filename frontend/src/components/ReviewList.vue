<script setup lang="ts">
import type { Review } from "@/types/Review"
import BookCard from "@/components/cards/BookCard.vue"

defineProps<{
  reviews: Review[]
}>()
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
