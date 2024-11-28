import { createRouter, createWebHistory } from "vue-router"
import HomeView from "@/views/ReviewsPage.vue"
import MainLayout from "@/components/MainLayout.vue"
import ReviewsPage from "@/views/HomePage.vue"
import ProfilePage from "@/views/ProfilePage.vue"
import AddReview from "@/views/AddReviewPage.vue"
import BookDetails from "@/views/BookPage.vue"

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
        { path: "/add-review", name: "add-review", component: AddReview },
        {
          path: "/books/:isbn",
          name: "BookDetails",
          component: BookDetails,
          props: true
        }
      ]
    }
  ]
})

export default router
