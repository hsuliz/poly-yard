<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import axios from 'axios'

interface Book {
  isbn: string
  title: string
  author: string
  publishedDate: number
  pages: number
  image: string
}

interface User {
  id: number
  name: string
}

interface Review {
  userId: number
  bookId: number
  rating: number
  comment: string
  createdAt: string
  book: Book
  user: User
}

export default defineComponent({
  setup() {
    const reviews = ref<Review[]>([])

    const fetchReviews = async () => {
      try {
        const response = await axios.get('http://localhost:8002/reviews')
        reviews.value = response.data
      } catch (error) {
        console.error('Error fetching reviews:', error)
      }
    }

    onMounted(() => {
      fetchReviews()
    })

    return {
      reviews
    }
  }
})
</script>

<template>
  <div>
    <h2>Reviews</h2>
    <div v-for="review in reviews" :key="review.bookId">
      <h3>{{ review.book.title }} by {{ review.book.author }}</h3>
      <p>Reviewed by: {{ review.user.name }}</p>
      <p>Rating: {{ review.rating }}</p>
      <p>Comment: {{ review.comment }}</p>
      <p>Published: {{ new Date(review.createdAt).toLocaleDateString() }}</p>
    </div>
  </div>
</template>


<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
