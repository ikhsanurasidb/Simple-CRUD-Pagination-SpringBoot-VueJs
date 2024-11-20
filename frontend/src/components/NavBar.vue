<script setup lang="ts">
import { RouterLink, useRoute } from 'vue-router';

const isActiveLink = (routePath: string) => {
  const route = useRoute();
  return route.path === routePath;
}

</script>
<template>
  <nav class="p-4 bg-blue-500">
    <ul class="flex justify-center space-x-4" style="list-style-type: none;">
      <li>
        <RouterLink to="/students"
          :class="[isActiveLink('/students') ? 'bg-blue-900' : 'hover:bg-gray-900 hover:text-white', 'text-white', 'px-3', 'py-2', 'rounded-md']">
          Students
        </RouterLink>
      </li>
      <li>
        <RouterLink to="/schools"
          :class="[isActiveLink('/schools') ? 'bg-blue-900' : 'hover:bg-gray-900 hover:text-white', 'text-white', 'px-3', 'py-2', 'rounded-md']">
          Schools
        </RouterLink>
      </li>
    </ul>
  </nav>
</template>
