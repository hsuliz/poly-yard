import { ref } from "vue"
import type { Page } from "@/types/Paginated"

export function pagination(
  fetchPageFn: (page: number, size: number) => Promise<Page>,
  initialPageSize = 10
) {
  const items = ref<Page>()
  const currentPage = ref(0)
  const pageSize = ref(initialPageSize)

  const fetchItems = async (page: number) => {
    items.value = await fetchPageFn(page, pageSize.value)
  }

  const nextPage = () => {
    if (currentPage.value < (items.value?.totalPages || 1) - 1) {
      currentPage.value += 1
      fetchItems(currentPage.value).then()
    }
  }

  const previousPage = () => {
    if (currentPage.value > 0) {
      currentPage.value -= 1
      fetchItems(currentPage.value).then()
    }
  }

  return {
    items,
    currentPage,
    pageSize,
    fetchItems,
    nextPage,
    previousPage
  }
}
