import { defineStore } from "pinia";
import { ref } from "vue";

export const useSubscriptionStore = defineStore("subscriptionStore", () => {
  const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";
  const URL = BASE_URL + "/suscripciones";

  const subscriptions = ref([]);
  const currentSubscription = ref({});
  const isLoading = ref(false);

  async function createSubscription(subscriptionData) {
    try {
      isLoading.value = true;
      const res = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(subscriptionData),
      });
      const data = await res.json();
      if (!data) {
        throw new Error("ocurrió un error al crear la suscripción");
      }
      return {
        ok: true,
      };
    } catch (error) {
      console.error(error);
      return {
        ok: false,
        message: error.message,
      };
    } finally {
      isLoading.value = false;
    }
  }

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
      return {
        ok: true,
      };
    } catch (error) {
      console.error(error);
      return {
        ok: false,
        message: error.message,
      };
    } finally {
      isLoading.value = false;
    }
  }

  async function deleteSubscription(id) {
    try {
      isLoading.value = true;
      const res = await fetch(`${URL}/${id}`, {
        method: "DELETE",
      });
      const data = await res.json();
      if (!data) {
        throw new Error("ocurrió un error al eliminar la suscripción");
      }
      return {
        ok: true,
      };
    } catch (error) {
      console.error(error);
      return {
        ok: false,
        message: error.message,
      };
    } finally {
      isLoading.value = false;
    }
  }

  return {
    subscriptions,
    currentSubscription,
    isLoading,
    createSubscription,
    getSubscription,
    getSubscriptions,
    updateSubscription,
    deleteSubscription,
  };
});
