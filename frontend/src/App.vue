<script>
import Sidenav from "./components/Sidenav.vue";

export default {
  name: "App",
  components: {
    Sidenav,
  },
};
</script>

<template>
  <div id="app">
    <Sidenav>
      <template #content>
        <div class="px-8 py-6">
          <router-view />
        </div>
      </template>
    </Sidenav>
  </div>
</template>

<style>
#app {
  display: flex;
  margin: 0;
  padding: 0;
}
</style>
