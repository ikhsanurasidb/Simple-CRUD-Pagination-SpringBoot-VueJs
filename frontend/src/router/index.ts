import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import StudentsView from '@/views/StudentsView.vue'
import SchoolsView from '@/views/SchoolsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/students',
      name: 'students',
      component: StudentsView,
    },
    {
      path: '/schools',
      name: 'schools',
      component: SchoolsView,
    },
  ],
})

export default router
