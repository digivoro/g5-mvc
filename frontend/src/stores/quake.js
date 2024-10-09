import {} from "vue";
import { defineStore } from "pinia";

export const useQuakeStore = defineStore("quakeStore", {
  state: () => ({
    quakes: [],
    isLoading: false,
  }),

  actions: {
    async getQuakes() {
      try {
        this.isLoading = true;
        const URL =
          import.meta.env.VITE_API_URL + "/sismos" ||
          "http://localhost:8080/api/sismos";
        const res = await fetch(URL);
        const data = await res.json();
        console.log(data);
        this.quakes = [...data];
      } catch (error) {
        console.error(error);
      } finally {
        this.isLoading = false;
      }
    },
  },
});

// return [
//   {
//     id: 1,
//     localidad: "Panguipulli",
//     fecha: "2024-08-30T00:00:00",
//     profundidad: 112.0,
//     magnitud: 3.0,
//   },
//   {
//     id: 2,
//     localidad: "Pomaire",
//     fecha: "2024-08-30T00:00:00",
//     profundidad: 112.0,
//     magnitud: 4.0,
//   },
//   {
//     id: 3,
//     localidad: "Pichilemu",
//     fecha: "2024-08-30T00:00:00",
//     profundidad: 112.0,
//     magnitud: 2.7,
//   },
// ];
