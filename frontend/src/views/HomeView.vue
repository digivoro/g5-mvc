<script setup>
import QuakeCard from "@/components/QuakeCard.vue";
import SubscriptionFrom from "@/components/SubscriptionFrom.vue";
import { useQuakeStore } from "@/stores/quake";

const store = useQuakeStore();
store.getQuakes();
</script>

<template>
  <main>
    <h2 class="text-xl">Últimos sismos</h2>

    <div v-if="store.recentQuakes" class="grid grid-cols-3 gap-4">
      <QuakeCard
        v-for="quake in store.recentQuakes"
        :key="quake.id"
        :quakeData="quake"
      />
    </div>
    <div v-else>LOADING</div>

    <section class="mt-8">
      <h2 class="text-xl">Nueva suscripción</h2>
      <SubscriptionFrom />
    </section>
  </main>
</template>
