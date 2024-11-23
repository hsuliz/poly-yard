<script lang="ts" setup>
import { computed, onMounted, ref } from "vue"
import axios from "axios"
import BookCard from "@/components/cards/BookCard.vue"
import type Book from "@/types/Book"
import { useKeycloak } from "@josempgon/vue-keycloak"

const isbn = ref<string>("")
const rating = ref<number | null>(null)
const comment = ref<string>("")
const book = ref<Book | null>(null)
const errorMessage = ref<string>("")
const { token } = useKeycloak()

const options = computed(() => [
  { text: 1, value: 1 },
  { text: 2, value: 2 },
  { text: 3, value: 3 },
  { text: 4, value: 4 },
  { text: 5, value: 5 }
])

onMounted(() => {
  console.info()
})

// Check if book exists
const checkBook = async () => {
  try {
    const response = await axios.get(`/api/books/${isbn.value}`)
    console.log("Book found:", response.data)
    book.value = response.data
    console.info(token.value)
  } catch (error: any) {
    if (error.response && error.response.status === 404) {
      console.log("Book not found")
      errorMessage.value = `Book with isbn: ${isbn.value} not found`
    } else {
      console.error("Error checking book:", error)
    }
  }
}

const submitReview = async () => {
  try {
    await axios.post(
      "/api/me/reviews",
      {
        bookIsbn: isbn.value,
        rating: rating.value,
        comment: comment.value
      },
      {
        headers: { Authorization: `Bearer ${token.value}` }
      }
    )
    alert("Review submitted successfully!")
    // Reset form
    isbn.value = ""
    rating.value = null
    comment.value = ""
    book.value = null
  } catch (error) {
    console.error("Error submitting review:", error)
    alert("Failed to submit review.")
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
        id="isbn"
        v-model="isbn"
        class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        placeholder="Enter ISBN"
        type="text"
      />
      <button
        class="mt-2 w-full bg-blue-500 hover:bg-blue-600 py-2 rounded-md transition"
        @click="checkBook"
      >
        Check Book
      </button>
      <p v-if="errorMessage" class="text-red-500 mt-2">{{ errorMessage }}</p>
    </div>
    <div v-else>
      <BookCard :book="book" />
      <div class="space-y-4 p-4">
        <label class="block font-semibold" for="rating">Rating (1-5):</label>

        <select
          v-model.number="rating"
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option disabled value="">Please select one</option>
          <option v-for="option in options" :key="option.value" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <label class="block font-semibold" for="comment">Comment:</label>
        <textarea
          id="comment"
          v-model="comment"
          class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="(Optional)"
          rows="4"
        ></textarea>
        <button
          class="mt-2 w-full bg-green-500 hover:bg-green-600 text-white py-2 rounded-md transition"
          @click="submitReview"
        >
          Submit Review
        </button>
      </div>
    </div>
  </div>
</template>
