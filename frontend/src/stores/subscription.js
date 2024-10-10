import { defineStore } from "pinia";
import { ref } from "vue";

export const useSubscriptionStore = defineStore("subscriptionStore", () => {
  const subscriptions = ref([]);
  const isLoading = ref(false);

  async function getSubscriptions() {
    try {
      isLoading.value = true;
      const BASE_URL =
        import.meta.env.VITE_API_URL || "http://localhost:8080/api";
      const URL = BASE_URL + "/suscripciones";
      const res = await fetch(URL);
      const data = await res.json();
      subscriptions.value = [...data];
    } catch (error) {
      console.error(error);
    } finally {
      isLoading.value = false;
    }
  }

  return {
    subscriptions,
    isLoading,
    getSubscriptions,
  };
});
