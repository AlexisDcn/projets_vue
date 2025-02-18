<template>
  <div class="enregistrer-participation">
    <EnregistrerParticipation />
  </div>
</template>

<script setup>
import EnregistrerParticipation from "@/components/EnregistrerParticipation.vue";
</script>

<style scoped>
@media (min-width: 1024px) {
  .enregistrer-participation {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
