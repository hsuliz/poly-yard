<script setup lang="ts">
import { onMounted, ref } from "vue"
import type { Book } from "@/types/Book"
import BookCard from "@/components/cards/BookCard.vue"
import { getReviews } from "@/api/reviewService"
import { getBookByIsbn } from "@/api/bookService"

const { isbn } = defineProps<{
  isbn?: string
}>()

const book = ref<Book | null>(null)

const fetchBook = async () => {
  if (!isbn) return
    book.value = await getBookByIsbn(isbn)
}

onMounted(() => {
  fetchBook()
})
</script>

<template>
  <div>
    <div v-if="book">
      <BookCard :book="book" />
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>
