<script setup lang="ts">
import type Book from "@/types/Book"

defineProps<{
  book: Book
}>()
</script>

<template>
  <div class="p-4">
    <div class="flex items-center">
      <img :src="book.image" alt="Book Cover" class="w-16 h-24 rounded-md mr-4" />
      <div class="flex-1">
        <h3 class="text-lg font-semibold">{{ book.title }} by {{ book.author }}</h3>
      </div>
    </div>
  </div>
</template>
