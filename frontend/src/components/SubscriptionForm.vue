<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

// Variables reactivas
const name = ref("");
const email = ref("");
const locality = ref(""); // Aquí guardaremos el ID de la localidad seleccionada
const localidades = ref([]); // Lista de localidades obtenida desde el backend
const successMessage = ref(""); // Mensaje de éxito
const errorMessage = ref(""); // Mensaje de error

// Función para cargar las localidades desde el backend
const fetchLocalidades = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/localidades/all");
    localidades.value = response.data; // Asignar las localidades obtenidas
  } catch (error) {
    console.error("Error al obtener las localidades:", error);
  }
};

// Cargar las localidades al montar el componente
onMounted(() => {
  fetchLocalidades();
});

// Función para limpiar el formulario y los mensajes
const resetForm = () => {
  name.value = "";
  email.value = "";
  locality.value = "";
  successMessage.value = "";
  errorMessage.value = "";
};

// Función para manejar el envío del formulario
const submitForm = async () => {
  try {
    const suscripcion = {
      nombre: name.value,
      email: email.value,
      localidadId: locality.value // Enviamos el ID de la localidad
    };

    // Hacer el POST request al backend para crear la suscripción
    const response = await axios.post("http://localhost:8080/api/suscripciones/create", suscripcion);
    
    // Limpiar el formulario antes de mostrar el mensaje
    resetForm();

    // Mostrar mensaje de éxito si la suscripción fue creada
    successMessage.value = "Suscripción creada exitosamente!";
    errorMessage.value = ""; // Limpiar cualquier mensaje de error anterior

    // Hacer desaparecer el mensaje de éxito después de 3 segundos
    setTimeout(() => {
      successMessage.value = "";
    }, 3000);
    
  } catch (error) {
    // Limpiar el formulario antes de mostrar el mensaje
    resetForm();
    
    // Mostrar mensaje de error si ocurrió un problema
    if (error.response && error.response.data) {
      errorMessage.value = error.response.data; // Mostrar el mensaje de error del servidor
    } else {
      errorMessage.value = "Ocurrió un error al crear la suscripción.";
    }
    successMessage.value = ""; // Limpiar cualquier mensaje de éxito anterior

    // Hacer desaparecer el mensaje de error después de 3 segundos
    setTimeout(() => {
      errorMessage.value = "";
    }, 3000);
  }
};

</script>

<template>
  <div class="card bg-secondary text-secondary-content shadow-xl">
    <div class="card-body">
      <form @submit.prevent="submitForm" class="space-y-4">
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
          <label for="email" class="block mb-2">Email:</label>
          <input
            type="email"
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
            v-model="locality"
            class="select select-bordered select-secondary w-full text-white"
            required
          >
            <option disabled value="">- Seleccione localidad -</option>
            <!-- Iterar sobre las localidades obtenidas y mostrarlas en el select -->
            <option v-for="localidad in localidades" :key="localidad.id" :value="localidad.id">
              {{ localidad.nombre }}
            </option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">
          Registrar suscripción
        </button>
      </form>

      <!-- Mostrar mensaje de éxito o error -->
      <div v-if="successMessage" class="alert alert-success mt-4">
        {{ successMessage }}
      </div>
      <div v-if="errorMessage" class="alert alert-error mt-4">
        {{ errorMessage }}
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Estilos para los mensajes de alerta */
.alert {
  padding: 1rem;
  border-radius: 5px;
  margin-top: 10px;
}

.alert-success {
  background-color: #4caf50;
  color: white;
}

.alert-error {
  background-color: #f44336;
  color: white;
}
</style>
