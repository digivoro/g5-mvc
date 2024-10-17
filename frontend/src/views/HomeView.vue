<script setup>
import PageHeader from "@/components/PageHeader.vue";
import QuakeCard from "@/components/QuakeCard.vue";
import SubscriptionFrom from "@/components/SubscriptionForm.vue";
import { useQuakeStore } from "@/stores/quake";

const store = useQuakeStore();
store.getQuakes();
</script>

<template>
  <main>
    <PageHeader>Últimos sismos</PageHeader>

    <div v-if="store.recentQuakes" class="grid grid-cols-3 gap-4">
      <QuakeCard
        v-for="quake in store.recentQuakes.sort((a, b) => a.id - b.id)"
        :key="quake.id"
        :quakeData="quake"
      />
</div>
    <div v-else>LOADING</div>

    <section class="mt-8">
      <PageHeader>Nueva suscripción</PageHeader>
      <SubscriptionFrom />
    </section>
  </main>
</template>
