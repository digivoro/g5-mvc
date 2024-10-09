<script setup>
const subscriptions = [
  {
    id: 1,
    email: "felipe@richter.cl",
    nombre: "Felipe Richter",
    localidad: "Pomaire",
    activa: false,
  },
  {
    id: 2,
    email: "felipe@richter.cl",
    nombre: "Felipe Richter",
    localidad: "Pomaire",
    activa: false,
  },
  {
    id: 3,
    email: "felipe@richter.cl",
    nombre: "Felipe Richter",
    localidad: "Pomaire",
    activa: false,
  },
  {
    id: 4,
    email: "felipe@richter.cl",
    nombre: "Felipe Richter",
    localidad: "Pomaire",
    activa: false,
  },
];
</script>

<template>
  <div>
    <h1 class="text-xl">Suscripciones</h1>

    <div class="overflow-x-auto">
      <table class="table table-zebra">
        <thead>
          <tr>
            <th></th>
            <th>Email</th>
            <th>Nombre</th>
            <th>Localidad</th>
            <th>Acciones</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="subscription in subscriptions" :key="subscription.id">
            <th>{{ subscription.id }}</th>
            <td>{{ subscription.email }}</td>
            <td>{{ subscription.nombre }}</td>
            <td>{{ subscription.localidad }}</td>
            <td>
              <div class="flex gap-2">
                <div class="tooltip" data-tip="Editar">
                  <button class="btn btn-square btn-xs btn-primary">✏️</button>
                </div>
                <div class="tooltip" data-tip="Eliminar">
                  <button class="btn btn-square btn-xs btn-error">🗑️</button>
                </div>
              </div>
            </td>
            <td>
              <input
                type="checkbox"
                class="toggle toggle-success"
                checked="checked"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
