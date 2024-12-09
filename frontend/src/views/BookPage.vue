<script setup lang="ts">
import { onMounted, ref } from "vue"
import type { Book } from "@/types/Book"
import BookCard from "@/components/cards/BookCard.vue"
import { getReviewsByBook } from "@/api/reviewService"
import type { Review } from "@/types/Review"
import ReviewsList from "@/components/ReviewsList.vue"
const { isbn } = defineProps<{
  isbn?: string
}>()

const book = ref<Book | null>(null)
const reviews = ref<Review[] | null>(null)

const fetchReviews = async () => {
  if (!isbn) return
  reviews.value = await getReviewsByBook(isbn)
  book.value = reviews.value[0].resource
}

onMounted(() => {
  fetchReviews()
})
</script>

<template>
  <div>
    <div v-if="book">
      <BookCard :book="book" />
      <ReviewsList :reviews="reviews" />
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>
