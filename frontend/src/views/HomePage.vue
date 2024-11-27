<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { useKeycloak } from "@josempgon/vue-keycloak"
import axios from "axios"
import type { Review } from "@/types/Review"
import ReviewList from "@/components/ReviewList.vue"

const { username, isAuthenticated } = useKeycloak()

const reviews = ref<Review[]>([])

const currentUsername = computed(() => username.value)

const fetchReviews = async () => {
  try {
    if (!currentUsername.value) return
    const response = await axios.get(`api/reviews/${currentUsername.value}`)
    reviews.value = response.data.content
    console.info(response)
  } catch (error) {
    console.error("Error fetching reviews:", error)
  }
}

watch(
  () => isAuthenticated.value,
  (isReady) => {
    if (isReady) fetchReviews()
  },
  { immediate: true }
)
</script>

<template>
  <h1 class="text-2xl font-bold">Welcome to the Home Page</h1>

  <div v-if="isAuthenticated">
    <router-link to="/add-review" v-slot="{ navigate }">
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        @click="navigate"
        role="link"
      >
        Add Review
      </button>
    </router-link>

    <div>
      <h2 class="text-xl font-semibold">Your reviews</h2>
      <div>
        <ReviewList :reviews="reviews" />
      </div>
    </div>
  </div>

  <div v-else>
    <p class="text-red-500">User not logged in. Please log in to see your reviews.</p>
  </div>
</template>
