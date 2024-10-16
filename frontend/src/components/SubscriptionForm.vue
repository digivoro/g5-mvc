<script setup>
import { useLocalityStore } from "@/stores/locality";
import { useSubscriptionStore } from "@/stores/subscription";
import { ref } from "vue";

const subscriptionStore = useSubscriptionStore();
const localityStore = useLocalityStore();

const name = ref("");
const email = ref("");
const locality = ref("");

async function onSubmit() {
  await subscriptionStore.createSubscription({
    nombre: name.value,
    email: email.value,
    localidad: locality.value,
  });
}
</script>

<template>
  <div class="card bg-secondary text-secondary-content shadow-xl">
    <div class="card-body">
      <form @submit.prevent="onSubmit" class="space-y-4">
        <div>
          <label for="name" class="block mb-2">Nombre:</label>
          <input
            type="text"
            id="name"
            v-model="name"
            placeholder="Nombre"
            class="input input-bordered input-secondary w-full text-white"
            required
          />
        </div>
        <div>
          <label for="name" class="block mb-2">Email:</label>
          <input
            type="text"
            id="email"
            v-model="email"
            placeholder="Email"
            class="input input-bordered input-secondary w-full text-white"
            required
          />
        </div>
        <div>
          <label for="locality" class="block mb-2">Seleccione localidad:</label>
          <select
            class="select select-bordered select-secondary w-full text-white"
          >
            <option disabled selected>- Seleccione localidad -</option>

            <option
              v-for="locality in localityStore.localities"
              :key="locality.id"
              class="text-white"
            >
              {{ locality.nombre }}
            </option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">
          Registrar suscripción
        </button>
      </form>
    </div>
  </div>
</template>
