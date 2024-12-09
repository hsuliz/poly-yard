<script setup lang="ts">
import type { Review } from "@/types/Review"
import BookCard from "@/components/cards/BookCard.vue"

defineProps<{
  reviews: Review[]
}>()
</script>

<template>
  <div class="grid grid-cols-1">
    <div v-for="review in reviews" :key="review.id">
      <BookCard :book="review.resource" :review="review" />
    </div>
  </div>
</template>
