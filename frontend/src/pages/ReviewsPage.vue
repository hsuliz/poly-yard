<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { useKeycloak } from "@josempgon/vue-keycloak"
import type { Review } from "@/types/Review"
import ReviewList from "@/components/ReviewList.vue"
import { getReviewsByUser } from "@/api/reviewService"

const { username, isAuthenticated } = useKeycloak()

const reviews = ref<Review[]>([])

const currentUsername = computed(() => username.value)

const fetchReviews = async () => {
  if (!currentUsername.value) return
  reviews.value = await getReviewsByUser(currentUsername.value)
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
  <h1 class="text-2xl font-bold">Welcome to the Reviews Page</h1>
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
        <ReviewList :reviews="reviews" :isUser="isAuthenticated" />
      </div>
    </div>
  </div>

  <div v-else>
    <p class="text-yellow-500">User not logged in. Please log in to see your reviews.</p>
  </div>
</template>
