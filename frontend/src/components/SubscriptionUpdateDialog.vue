<script setup>
import { ref, watch } from "vue";
import { useSubscriptionStore } from "@/stores/subscription";

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  isLoading: {
    type: Boolean,
    required: true,
  },
  subscriptionId: {
    type: null,
    required: true,
    validator: (v) => typeof v === "number" || v === null,
  },
});

const emit = defineEmits(["close", "update"]);

const subscriptionStore = useSubscriptionStore();
const isLoading = ref(true);
const form = ref({
  email: "",
  nombre: "",
  localidad: "",
  activo: null,
});

const subscription = ref(null);

watch(
  () => props.isOpen,
  async (newValue) => {
    if (newValue) {
      isLoading.value = true;
      await subscriptionStore.getSubscription(props.subscriptionId);
      subscription.value = subscriptionStore.currentSubscription;
      isLoading.value = false;
    }
  }
);

function closeModal() {
  emit("close");
  clearForm();
}
function clearForm() {
  form.value = { email: "", nombre: "", localidad: "", activo: null };
}
async function onSubmit() {
  emit("update", form.value);
  emit("close");
}
</script>

<template>
  <dialog
    v-if="isOpen"
    id="update_subscription_dialog"
    class="modal modal-open"
  >
    <div class="modal-box">
      <button
        class="btn btn-sm btn-circle btn-ghost text-white absolute right-2 top-2"
        @click="closeModal"
      >
        ✕
      </button>
      <h3 class="text-lg font-bold mb-4">Editando suscripción</h3>

      <div v-if="isLoading">Cargando...</div>
      <form v-else @submit.prevent="onSubmit" class="flex flex-col gap-2">
        <label class="input input-bordered flex items-center gap-2">
          <span class="w-24">Email</span>
          <input
            v-model="form.email"
            type="text"
            class="grow"
            :placeholder="subscriptionStore.currentSubscription.email"
          />
        </label>

        <label class="input input-bordered flex items-center gap-2">
          <span class="w-24">Nombre</span>
          <input
            v-model="form.nombre"
            type="text"
            class="grow"
            :placeholder="subscriptionStore.currentSubscription.nombre"
          />
        </label>

        <label class="input input-bordered flex items-center gap-2">
          <span class="w-24">Localidad</span>
          <input
            v-model="form.localidad"
            type="text"
            class="grow"
            :placeholder="subscriptionStore.currentSubscription.localidad"
          />
        </label>

        <label class="input input-bordered flex items-center gap-2">
          <span class="w-24">Activo</span>
          <input
            v-model="form.activo"
            type="checkbox"
            class="toggle toggle-success bg-success hover:cursor-default"
            :checked="subscriptionStore.currentSubscription.activo"
          />
        </label>

        <button type="submit" class="btn btn-primary">
          <template v-if="isLoading"> Actualizando... </template>
          <template v-else>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              height="24px"
              viewBox="0 -960 960 960"
              width="24px"
            >
              <path
                d="M840-680v480q0 33-23.5 56.5T760-120H200q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h480l160 160Zm-80 34L646-760H200v560h560v-446ZM480-240q50 0 85-35t35-85q0-50-35-85t-85-35q-50 0-85 35t-35 85q0 50 35 85t85 35ZM240-560h360v-160H240v160Zm-40-86v446-560 114Z"
              />
            </svg>
            Actualizar suscripción
          </template>
        </button>
      </form>
    </div>
  </dialog>
</template>
