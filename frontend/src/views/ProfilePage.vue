<script setup lang="ts">
import { useKeycloak } from "@josempgon/vue-keycloak"
import { computed } from "vue"

const { username } = useKeycloak()
const usernameD = computed(() => username.value)
</script>

<template>
  <h1 class="text-2xl font-bold text-white">Hello {{ usernameD }}</h1>
</template>
