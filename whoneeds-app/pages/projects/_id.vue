<template>
  <div>
    <div v-if="!$fetchState.pending">
      <h2>{{ project.name }}</h2>
      <h4>Description</h4>
      <p>{{ project.description }}</p>
      <div class="category-chips">
        <v-chip
          v-for="category in project.categories"
          :key="category"
          :color="getChipColor()"
          small
        >
          {{ category }}
        </v-chip>
      </div>
      <h4>Address</h4>
      <p>Name: {{ project.address.name }}</p>
      <p>Street: {{ project.address.street }} {{ project.address.nr }}</p>
      <p>Zip: {{ project.address.zip }}</p>
      <p>City: {{ project.address.city }}</p>
      <p>State: {{ project.address.state }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    project: undefined,
    chipColors: ['green', 'blue', 'purple', 'red', 'orange', 'yellow'],
    chipColorCounter: 0
  }),
  async fetch () {
    const id = this.$route.params.id
    await this.$axios
      .get(`/projects/${id}`)
      .then((response) => {
        this.project = response.data
      })
      .catch(() => {
        this.$toast.error('The given project id is unknown.')
      })
  },
  methods: {
    getChipColor () {
      return this.chipColors[this.chipColorCounter++ % 6]
    }
  }
}
</script>
