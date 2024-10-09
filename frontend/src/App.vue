<script>
import Sidenav from './components/Sidenav.vue';

export default {
  name: "App",
  components: {
    Sidenav
  }
};
</script>

<template>
  <div id="app">
    <Sidenav />
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<style>
#app {
  display: flex;
  margin: 0;
  padding: 0;
}
.content {
  margin-left: 250px; /* Same width as the sidenav */
  padding: 20px;
  flex-grow: 1;
}
</style>
