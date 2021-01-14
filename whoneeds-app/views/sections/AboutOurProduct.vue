<template>
  <base-section id="about-our-idea">
    <base-section-heading
      color="grey lighten-2"
      icon="mdi-vuetify"
      title="About our idea"
    />

    <v-container>
      <v-row>
        <v-col
          v-for="card in cards"
          :key="card.title"
          cols="12"
          md="4"
        >
          <base-info-card v-bind="card" />
        </v-col>
      </v-row>
    </v-container>
  </base-section>
</template>

<script>
  export default {
    name: 'SectionAboutOurIdea',

    data: () => ({
      cards: [
        {
          title: 'Distribute relief supplies better.',
          subtitle: 'Basic intention',
          text: 'Similique sunt in culpa qui officia deserunt mollitia animi, id est laborut dolorum fuga.harum quidem rerum facilis estexpedita distinctio.',
        },
        {
          title: 'Make small quantity dispensing meaningful',
          subtitle: 'Impact',
          text: 'Similique sunt in culpa qui officia deserunt mollitia animi, id est laborut dolorum fuga.harum quidem rerum facilis estexpedita distinctio.',
        },
        {
          title: 'Register now',
          subtitle: 'How can you help?',
          text: 'Similique sunt in culpa qui officia deserunt mollitia animi, id est laborut dolorum fuga.harum quidem rerum facilis estexpedita distinctio.',
        },
      ],
    }),
  }
</script>
