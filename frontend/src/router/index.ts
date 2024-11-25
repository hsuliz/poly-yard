import { createRouter, createWebHistory } from "vue-router"
import HomeView from "@/views/ReviewsPage.vue"
import MainLayout from "@/components/MainLayout.vue"
import ReviewsPage from "@/views/HomePage.vue"
import ProfilePage from "@/views/ProfilePage.vue"
import AddReview from "@/views/AddReview.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: MainLayout,
      children: [
        { path: "", name: "home", component: HomeView },
        { path: "/reviews", name: "books", component: ReviewsPage },
        { path: "/profile", name: "profile", component: ProfilePage },
        { path: "/add-review", name: "add-review", component: AddReview }
      ]
    }
  ]
})

export default router
