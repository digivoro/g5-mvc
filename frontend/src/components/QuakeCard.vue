<script setup>
defineProps({
  quakeData: Object,
});
</script>

<template>
  <div class="card bg-primary-100 shadow-xl">
    <div class="card-body">
      <h2 class="card-title">Sismo {{ quakeData.id }}</h2>
      <p>🗺️ {{ quakeData.localidad }}</p>
      <p>📈 {{ quakeData.magnitud }}</p>
      <p>📅 {{ quakeData.fecha }}</p>
      <p>⏬ {{ quakeData.profundidad }} [m]</p>
    </div>
  </div>
</template>
