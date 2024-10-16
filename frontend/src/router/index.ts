import { createRouter, createWebHistory } from "vue-router"
import HomeView from "@/views/HomePage.vue"
import MainLayout from "@/components/MainLayout.vue"
import BooksPage from "@/views/BooksPage.vue"
import ProfilePage from "@/views/ProfilePage.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: MainLayout,
      children: [
        { path: "", name: "home", component: HomeView },
        { path: "/books", name: "books", component: BooksPage },
        { path: "/profile", name: "profile", component: ProfilePage }
      ]
    }
  ]
})

export default router
