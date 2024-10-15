<script lang="ts" setup>
import { useKeycloak } from '@josempgon/vue-keycloak'

const { username } = useKeycloak()

</script>

<template>
  <h1 class="text-2xl font-bold text-white">Hello {{ username }}</h1>
</template>
