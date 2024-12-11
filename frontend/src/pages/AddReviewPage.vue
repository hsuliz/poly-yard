<script lang="ts" setup>
import { computed, ref } from "vue"
import { postReview } from "@/api/reviewService"
import { getBookByIsbn } from "@/api/bookService"
import type { Book } from "@/types/Book"
import BookCard from "@/components/cards/BookCard.vue"

const isbn = ref<string>("")
const rating = ref<number | null>(null)
const comment = ref<string>("")
const book = ref<Book | null>(null)
const errorMessage = ref<string>("")
const loading = ref(false)

const options = computed(() => [1, 2, 3, 4, 5].map((value) => ({ text: value, value })))

const handleGetBook = async () => {
  if (!isbn.value.trim()) {
    errorMessage.value = "ISBN is required."
    return
  }

  try {
    loading.value = true
    book.value = await getBookByIsbn(isbn.value)
    errorMessage.value = ""
  } catch (error: any) {
    if (error.response?.status === 404) {
      errorMessage.value = `Book with ISBN: ${isbn.value} not found.`
    } else {
      errorMessage.value = "An error occurred while checking the book."
    }
  } finally {
    loading.value = false
  }
}

const handleSubmitReview = async () => {
  if (!rating.value || !isbn.value.trim()) {
    errorMessage.value = "Please provide a rating and an ISBN."
    return
  }

  try {
    loading.value = true
    await postReview({
      type: "BOOK",
      resource: { type: "ISBN", value: isbn.value },
      rating: rating.value,
      comment: comment.value
    })
    alert("Review submitted successfully!")
    isbn.value = ""
    rating.value = null
    comment.value = ""
    book.value = null
  } catch (error) {
    errorMessage.value = "Failed to submit review. Please try again."
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div
    class="bg-gray-800 rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
  >
    <div v-if="!book" class="space-y-4 p-4">
      <h2 class="text-2xl font-bold mb-4">Add a Review</h2>
      <input
        v-model="isbn"
        placeholder="Enter ISBN"
        class="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
      />
      <button
        :disabled="loading"
        class="mt-2 w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-md"
        @click="handleGetBook"
      >
        {{ loading ? "Checking..." : "Check Book" }}
      </button>
      <p v-if="errorMessage" class="text-red-500 mt-2">{{ errorMessage }}</p>
    </div>

    <div v-else>
      <BookCard :book="book" />
      <div class="space-y-4 p-4">
        <label class="block font-semibold">Rating (1-5):</label>
        <select
          v-model.number="rating"
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
        >
          <option disabled value="">Select a rating</option>
          <option v-for="option in options" :key="option.value" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <label class="block font-semibold">Comment:</label>
        <textarea
          v-model="comment"
          placeholder="(Optional)"
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
        ></textarea>

        <button
          :disabled="loading"
          class="mt-2 w-full bg-green-500 hover:bg-green-600 text-white py-2 rounded-md"
          @click="handleSubmitReview"
        >
          {{ loading ? "Submitting..." : "Submit Review" }}
        </button>
        <p v-if="errorMessage" class="text-red-500 mt-2">{{ errorMessage }}</p>
      </div>
    </div>
  </div>
</template>
