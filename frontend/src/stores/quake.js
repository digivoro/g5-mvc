import { computed, ref } from "vue";
import { defineStore } from "pinia";

export const useQuakeStore = defineStore("quakeStore", () => {
  const quakes = ref([]);
  const isLoading = ref(false);

  const recentQuakes = computed(() => {
    const sortedQuakes = quakes.value.sort(
      (a, b) => dateToNumber(a.fecha) - dateToNumber(b.fecha)
    );
    return sortedQuakes.slice(Math.max(sortedQuakes.length - 3, 0)).reverse();
  });

  async function getQuakes() {
    try {
      isLoading.value = true;
      const BASE_URL =
        import.meta.env.VITE_API_URL || "http://localhost:8080/api";
      const URL = BASE_URL + "/sismos";
      const res = await fetch(URL);
      const data = await res.json();
      quakes.value = [...data];
    } catch (error) {
      console.error(error);
    } finally {
      isLoading.value = false;
    }
  }

  return { quakes, isLoading, recentQuakes, getQuakes };
});

function dateToNumber(stringDate) {
  const date = stringDate.slice(0, 10);
  return +date.replace(new RegExp("-", "g"), "");
}
