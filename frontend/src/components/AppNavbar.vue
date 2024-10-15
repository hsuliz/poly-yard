<script setup lang="ts">
import { useKeycloak } from '@josempgon/vue-keycloak'

const { isAuthenticated, username, keycloak } = useKeycloak()

const login = () => {
  if (keycloak.value) {
    keycloak.value.login()
  }
}
</script>

<template>
  <nav class="bg-gray-800 p-4">
    <div class="container mx-auto flex justify-between items-center">
      <ul class="flex space-x-4">
        <li>
          <RouterLink to="/" class="text-white font-bold text-balance">Poly-Yard</RouterLink>
        </li>
        <li>
          <RouterLink to="/books" class="text-gray-300 hover:text-white">Books</RouterLink>
        </li>
      </ul>
      <div>
        <span v-if="isAuthenticated" class="text-gray-300 hover:text-white">{{ username }}</span>
        <span v-else @click="login" class="text-gray-300 hover:text-white">Log In</span>
      </div>
    </div>
  </nav>
</template>
