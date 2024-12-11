<script setup lang="ts">
import { onMounted, ref } from "vue"
import type { Review } from "@/types/Review"
import ReviewList from "@/components/ReviewList.vue"
import { getReviews } from "@/api/reviewService"

const reviews = ref<Review[]>([])

const fetchReviews = async () => {
  reviews.value = await getReviews()
}

onMounted(() => {
  fetchReviews()
})
</script>

<template>
  <ReviewList :reviews="reviews" />
</template>
