<template>
    <div class="card bg-primary-100 shadow-xl input-bordered">
    <div class="card-body">
        <form @submit.prevent="submitForm" class="space-y-4">
            <div>
                <label for="name" class="block mb-2">Nombre:</label>
                <input type="text" id="name" v-model="name" placeholder="Nombre" class="input input-bordered w-full" required/>
            </div>
            <div>
                <label for="name" class="block mb-2">Email:</label>
                <input type="text" id="email" v-model="email" placeholder="Email" class="input input-bordered w-full" required/>
            </div>
            <div>
                <label for="locality" class="block mb-2">Seleccione localidad:</label>
                <select class="select select-bordered w-full">
                    <option disabled selected>- Seleccione localidad -</option>
                    <option>Han Solo</option>
                    <option>Greedo</option>
                </select>
            </div>
            <button type="submit" class="btn btn-neutral">Registrar suscripción</button>
        </form>
    </div>
  </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const name = ref('');
  const email = ref('');
  const locality = ref('');
  
  const submitForm = () => {
    console.log('Nombre:', name.value);
    console.log('Email:', email.value);
    console.log('Localidad:', locality.value);
    // Puedes manejar el envío del formulario aquí
  };
  </script>
  
  <style scoped>
  /* Puedes agregar estilos personalizados aquí */
  </style>
  