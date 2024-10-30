<template>
  <h1 class="text-2xl font-bold">Welcome to the Home Page</h1>

  <router-link to="/add-review" v-slot="{ addreview }">
    <button
      class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
      @click="addreview"
      role="link"
    >
      Add Review
    </button>
  </router-link>
</template>
