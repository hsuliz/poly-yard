<script setup lang="ts"></script>
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
      <RouterLink to="/login" class="text-gray-300 hover:text-white">Log In</RouterLink>
    </div>
  </nav>
</template>
