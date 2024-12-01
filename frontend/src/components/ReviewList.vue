<script setup lang="ts">
import type { Review } from "@/types/Review"
import BookCard from "@/components/cards/BookCard.vue"

defineProps<{
  reviews: Review[]
}>()
</script>

<template>
  <div class="grid grid-cols-1 gap-6 p-2">
    <div v-for="review in reviews" :key="review.id">
      <BookCard :book="review.resource" :review="review" />
    </div>
  </div>
</template>
