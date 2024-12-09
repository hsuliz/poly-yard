<script setup lang="ts">
import type { Review } from "@/types/Review"

defineProps<{
  reviews: Review[] | null
}>()
</script>

<template>
  <div class="grid grid-cols-1 gap-1 p-2">
    <div v-for="review in reviews" :key="review.id">
      <div class="grid grid-cols-1 gap-6">
        <div
          class="bg-gray-800 rounded-lg p-2"
        >
          <p class="text-sm mt-1">Reviewed by: {{ review.username }}</p>
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
</template>
