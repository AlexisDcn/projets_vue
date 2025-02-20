import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import EnregistrerParticipation from "@/components/EnregistrerParticipation.vue";

const router = createRouter({
  history: createWebHistory(), // Utilisez createWebHistory sans base
  routes: [
    {
      path: '/',
      name: 'EnregistrerParticipation',
      component: EnregistrerParticipation,
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },

  ],
})

export default router
