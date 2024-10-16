<script setup>
import TheLogo from "./TheLogo.vue";
</script>

<template>
  <div class="drawer md:drawer-open">
    <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content">
      <!-- Page content here -->
      <slot name="content" />
    </div>
    <div class="drawer-side">
      <label
        for="my-drawer-2"
        aria-label="close sidebar"
        class="drawer-overlay"
      ></label>

      <ul class="menu min-h-full w-72 p-6">
        <div class="w-full">
          <TheLogo />
        </div>
        <div class="divider"></div>
        <!-- Sidebar content here -->
        <li>
          <router-link to="/" class="" active-class="active">
            Inicio
          </router-link>
        </li>
        <li>
          <router-link to="/suscripciones" class="" active-class="active">
            Suscripciones
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>
