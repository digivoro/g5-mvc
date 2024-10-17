<script setup>
defineProps({
  quakeData: Object,
});
</script>

<template>
  <div class="card text-primary-content shadow-xl">
    <div class="card-body">
      <h2 class="card-title">Sismo {{ quakeData.id }}</h2>
      <div class="tooltip text-left" data-tip="Localidad">
        <p>🗺️ {{ quakeData.localidad }}</p>
      </div>
      <div class="tooltip text-left" data-tip="Magnitud">
        <p>📈 {{ quakeData.magnitud }}</p>
      </div>
      <div class="tooltip text-left" data-tip="Fecha">
        <p>📅 {{ quakeData.fecha }}</p>
      </div>
      <div class="tooltip text-left" data-tip="Profundidad">
        <p>⏬ {{ quakeData.profundidad }} [m]</p>
      </div>
    </div>
  </div>
</template>

<style>

.card{
  background-color: #9e92c7;
}

</style>