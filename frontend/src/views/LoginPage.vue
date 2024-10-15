<script lang="ts" setup>
import { computed } from 'vue';
import { useKeycloak } from '@josempgon/vue-keycloak';

const { hasRoles, isAuthenticated, token, keycloak } = useKeycloak();

const hasAccess = computed(() => hasRoles(['RoleName']));

const login = () => {
  if (keycloak.value) {
    keycloak.value.login();
  }
};

</script>

<template>
  <h1 class="text-2xl font-bold">Welcome to the Home Page</h1>
  <button @click="login" class="btn btn-primary">Login</button>
  <h2>
    Access: {{ hasAccess }}
    Authenticated: {{ isAuthenticated }}
    Token: {{ token }}
  </h2>
</template>
