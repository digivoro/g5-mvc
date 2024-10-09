<template>
    <div class="sidenav">
        <h1 class="titulo bg-base-200">Alerta Sismos</h1>
        <ul class="menu menu-lg bg-base-200 w-full h-full p-0 [&_li>*]:rounded-none">
            <li>
                <router-link to="/" class="nav-link" active-class="active">Inicio</router-link>
            </li>
            <li>
                <router-link to="/suscripciones" class="nav-link" active-class="active">Suscripciones</router-link>
            </li>
        </ul>
    </div>
</template>
  
  <script>
  export default {
    name: "Sidenav"
  };
  </script>
  
  <style scoped>
  .sidenav {
    width: 250px;
    height: 100vh;
    position: fixed;
  }
  .titulo{
    text-align: center;
    padding-top: 20px;
    font-size: 35px;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    margin: 15px 0;
  }
  a {
    color: white;
    text-decoration: none;
  }
  </style>
  