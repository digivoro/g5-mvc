<template>
    <div class="sidenav">
      <ul>
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/about">About</router-link></li>
        <li><router-link to="/contact">Contact</router-link></li>
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
    background-color: #333;
    color: white;
    position: fixed;
    padding: 20px;
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
  