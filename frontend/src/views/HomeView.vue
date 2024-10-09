<script setup>
import QuakeCard from "@/components/QuakeCard.vue";

const recentQuakes = [
  {
    id: 1,
    localidad: "Panguipulli",
    fecha: "2024-08-30T00:00:00",
    profundidad: 112.0,
    magnitud: 3.0,
  },
  {
    id: 2,
    localidad: "Pomaire",
    fecha: "2024-08-30T00:00:00",
    profundidad: 112.0,
    magnitud: 4.0,
  },
  {
    id: 3,
    localidad: "Pichilemu",
    fecha: "2024-08-30T00:00:00",
    profundidad: 112.0,
    magnitud: 2.7,
  },
];
</script>

<template>
  <main>
    <h2 class="text-xl">Últimos sismos</h2>

    <div class="grid grid-cols-3 gap-4">
      <QuakeCard
        v-for="quake in recentQuakes"
        :key="quake.id"
        :quakeData="quake"
      />
    </div>
  </main>
</template>
