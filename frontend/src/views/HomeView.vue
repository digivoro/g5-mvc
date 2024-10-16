<script setup>
import PageHeader from "@/components/PageHeader.vue";
import QuakeCard from "@/components/QuakeCard.vue";
import SubscriptionFrom from "@/components/SubscriptionForm.vue";
import { useLocalityStore } from "@/stores/locality";
import { useQuakeStore } from "@/stores/quake";

const quakeStore = useQuakeStore();
const localityStore = useLocalityStore();

quakeStore.getQuakes();
localityStore.getLocalities();
</script>

<template>
  <main>
    <PageHeader>Últimos sismos</PageHeader>

    <div v-if="quakeStore.recentQuakes" class="grid grid-cols-3 gap-4">
      <QuakeCard
        v-for="quake in quakeStore.recentQuakes"
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
