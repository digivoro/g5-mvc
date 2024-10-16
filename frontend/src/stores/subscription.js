import { defineStore } from "pinia";
import { ref } from "vue";

export const useSubscriptionStore = defineStore("subscriptionStore", () => {
  const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";
  const URL = BASE_URL + "/suscripciones";

  const subscriptions = ref([]);
  const currentSubscription = ref({});
  const isLoading = ref(false);

  async function getSubscriptions() {
    try {
      isLoading.value = true;
      const res = await fetch(URL, {
        method: "GET",
      });
      const data = await res.json();
      subscriptions.value = [...data];
    } catch (error) {
      console.error(error);
    } finally {
      isLoading.value = false;
    }
  }

  async function getSubscription(id) {
    try {
      isLoading.value = true;
      const res = await fetch(URL + "/" + id, {
        method: "GET",
      });
      const data = await res.json();
      currentSubscription.value = data;
    } catch (error) {
      console.error(error);
    } finally {
      isLoading.value = false;
    }
  }

  async function updateSubscription(id, subscriptionData) {
    console.log("updating");
    console.log(subscriptionData);
    try {
      isLoading.value = true;
      const res = await fetch(`${URL}/${id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(subscriptionData),
      });
      const data = await res.json();

      if (!data.id) {
        throw new Error("ocurrió un error al actualizar la suscripción");
      }
      console.log("updated");
      return true;
    } catch (error) {
      console.error(error);
    } finally {
      isLoading.value = false;
    }
  }

  return {
    subscriptions,
    currentSubscription,
    isLoading,
    getSubscription,
    getSubscriptions,
    updateSubscription,
  };
});
