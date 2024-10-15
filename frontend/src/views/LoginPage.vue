<script lang="ts" setup>
import { useKeycloak } from '@josempgon/vue-keycloak'

const { username, keycloak } = useKeycloak()


</script>

<template>
  <h1 class="text-2xl font-bold">Welcome to the Home Page</h1>
  <h2>Username: {{ username }}</h2>
</template>
