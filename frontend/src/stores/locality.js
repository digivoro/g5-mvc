import { defineStore } from "pinia";
import { ref } from "vue";

export const useLocalityStore = defineStore("localityStore", () => {
  const localities = ref([]);
  const isLoading = ref(false);

  const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";
  const URL = BASE_URL + "/localidades";

  async function getLocalities() {
    try {
      isLoading.value = true;
      const res = await fetch(URL, {
        method: "GET",
      });
      const data = await res.json();
      localities.value = [...data];
    } catch (error) {
      console.error(error);
      return {
        ok: false,
        message: error.message || error,
      };
    } finally {
      isLoading.value = false;
    }
  }

  return {
    localities,
    getLocalities,
  };
});
