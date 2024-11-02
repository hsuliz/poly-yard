<template>
  <h1 class="text-2xl font-bold">Welcome to the Home Page</h1>

  <router-link to="/add-review" v-slot="{ navigate }">
    <button
      class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
      @click="navigate"
      role="link"
    >
      Add Review
    </button>
    <div>
      Your reviews
      <div>

      </div>
    </div>
  </router-link>
</template>
