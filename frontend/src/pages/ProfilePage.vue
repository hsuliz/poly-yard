<script setup lang="ts">
import { useKeycloak } from "@josempgon/vue-keycloak"
import { computed } from "vue"

const { username, keycloak } = useKeycloak()
const currentUsername = computed(() => username.value)

const logout = async () => {
  try {
    await keycloak.value?.logout({
      redirectUri: window.location.origin
    })
    console.info("Logged out successfully")
  } catch (error) {
    console.error("Error during logout:", error)
  }
}
</script>

<template>
  <h1 class="text-2xl font-bold text-white">Hello {{ currentUsername }}</h1>
  <button
    class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded mb-4"
    @click="logout"
  >
    Logout
  </button>
</template>
