<script setup>
import { useSubscriptionStore } from "@/stores/subscription";

const store = useSubscriptionStore();
store.getSubscriptions();
</script>

<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">Suscripciones</h1>

    <div class="overflow-x-auto">
      <table class="table table-zebra">
        <thead>
          <tr>
            <th></th>
            <th>Email</th>
            <th>Nombre</th>
            <th>Localidad</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="store.isLoading">
            <tr>
              <th></th>
              <td>CARGANDO DATOS...</td>
            </tr>
          </template>
          <template v-else-if="store.subscriptions">
            <tr
              v-for="subscription in store.subscriptions"
              :key="subscription.id"
            >
              <th>{{ subscription.id }}</th>
              <td>{{ subscription.email }}</td>
              <td>{{ subscription.nombre }}</td>
              <td>{{ subscription.localidad }}</td>
              <td>
                <input
                  type="checkbox"
                  class="toggle toggle-success bg-success hover:cursor-default"
                  :checked="subscription.activo === true"
                  @click.prevent=""
                />
              </td>
              <td>
                <div class="flex gap-2">
                  <div class="tooltip" data-tip="Editar">
                    <button class="btn btn-square btn-xs btn-primary">
                      ✏️
                    </button>
                  </div>
                  <div class="tooltip" data-tip="Eliminar">
                    <button class="btn btn-square btn-xs btn-error">🗑️</button>
                  </div>
                </div>
              </td>
            </tr>
          </template>
        </tbody>
      </table>
    </div>
  </div>
</template>
