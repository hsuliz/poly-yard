<script lang="ts" setup>
import Navbar from "./AppNavbar.vue"
</script>

<template>
  <div>
    <Navbar />
    <main class="container mx-auto p-4">
      <router-view />
    </main>
  </div>
</template>
