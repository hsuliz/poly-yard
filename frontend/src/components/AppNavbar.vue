<script setup lang="ts">
import { useKeycloak } from "@josempgon/vue-keycloak"
import { computed } from "vue"

const { isAuthenticated, username, keycloak } = useKeycloak()

const login = () => {
  if (keycloak.value) {
    keycloak.value.login()
  }
}
const truncatedUsername = computed(() => {
  return username.value && username.value.length > 8
    ? username.value.substring(0, 8) + "..."
    : username.value
})
</script>

<template>
  <nav class="bg-gray-800 p-4">
    <div class="container mx-auto flex justify-between items-center">
      <ul class="flex space-x-4">
        <li>
          <router-link to="/" class="text-white font-bold text-balance">Poly-Yard</router-link>
        </li>
        <li>
          <router-link to="/reviews" class="text-gray-300 hover:text-white">Reviews</router-link>
        </li>
      </ul>
      <div>
        <router-link
          v-if="isAuthenticated"
          to="/profile"
          class="text-gray-300 hover:text-white truncate"
          >{{ truncatedUsername }}
        </router-link>
        <span v-else @click="login" class="text-gray-300 hover:text-green-800">Log In</span>
      </div>
    </div>
  </nav>
</template>
