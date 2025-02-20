<template>
  <div class="enregistrer-participation">
    <h2>Enregistrer une participation</h2>
    <form @submit.prevent="enregistrerParticipation">
      <!-- Sélection de la personne -->
      <div class="form-group">
        <label for="personne">Personne :</label>
        <select id="personne" v-model="selectedPersonne" required>
          <option v-for="personne in personnes" :key="personne.matricule" :value="personne.matricule">
            {{ personne.prenom }} {{ personne.nom }} - ({{ personne.poste }})
          </option>
        </select>
      </div>

      <!-- Sélection du projet -->
      <div class="form-group">
        <label for="projet">Projet :</label>
        <select id="projet" v-model="selectedProjet" required>
          <option v-for="projet in projets" :key="projet.id" :value="projet.id">
            {{ projet.nom }} ({{ projet.debut }})
          </option>
        </select>
      </div>

      <!-- Spécification du rôle -->
      <div class="form-group">
        <label for="role">Rôle :</label>
        <input type="text" id="role" v-model="role" required />
      </div>

      <!-- Spécification du pourcentage de participation -->
      <div class="form-group">
        <label for="pourcentage">Pourcentage :</label>
        <input type="range" id="pourcentage" v-model="pourcentage" min="0" max="100" step="1" required />
        <span>{{ pourcentage }}%</span>
      </div>

      <!-- Bouton d'enregistrement -->
      <button type="submit">Enregistrer</button>
    </form>

    <!-- Affichage des erreurs -->
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const personnes = ref([]);
const projets = ref([]);
const selectedPersonne = ref(null);
const selectedProjet = ref(null);
const role = ref("");
const pourcentage = ref(10); // Valeur par défaut
const errorMessage = ref("");

// Chargement des données au montage du composant
onMounted(async () => {
  try {
    // Récupérer les personnes depuis l'API
    const responsePersonnes = await axios.get("/api/personnes");
    console.log("Personnes récupérées :", responsePersonnes.data);
    personnes.value = responsePersonnes.data._embedded ? responsePersonnes.data._embedded.personnes : [];

    // Récupérer les projets depuis l'API
    const responseProjets = await axios.get("/api/projets");
    console.log("Projets récupérés :", responseProjets.data);
    projets.value = responseProjets.data._embedded ? responseProjets.data._embedded.projets : [];
  } catch (error) {
    console.error("Erreur lors de la récupération des données :", error);
  }
});

// Fonction pour enregistrer une participation
const enregistrerParticipation = async () => {
  try {
    await axios.post(
      "http://localhost:8989/api/gestion/participation",
      {},
      {
        params: {
          matricule: selectedPersonne.value,
          codeProjet: selectedProjet.value,
          role: role.value,
          pourcentage: pourcentage.value / 100, // Divisez par 100 pour obtenir une valeur entre 0.0 et 1.0
        },
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );
    alert("Participation enregistrée avec succès !");
  } catch (error) {
    if (error.response) {
      errorMessage.value = error.response.data.message
    } else {
      errorMessage.value = 'Erreur lors de l’enregistrement.'
    }
  }
};
</script>

<style scoped>
.enregistrer-participation {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

select, input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>
