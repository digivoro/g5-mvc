import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: '/',
			name: 'home',
			component: () => import('../views/HomeView.vue'),
		},
		{
			path: '/suscripciones',
			alias: '/subscriptions',
			name: 'subscriptions',
			component: () => import('../views/SubscriptionsView.vue'),
		},
	],
});

export default router;
