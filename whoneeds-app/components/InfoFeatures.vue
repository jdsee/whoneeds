<template>
  <base-info-card title="Benefits">
    <a
      v-for="benefits in benefits"
      :key="benefits"
      :text="benefits"
      class="mb-8 grey--text body-1 d-block text-none"
      href="#"
      v-html="`&rsaquo; ${benefits}`"
    />
  </base-info-card>
</template>

<script>
  export default {
    name: 'SectionFeaturesAlt',

    data: () => ({
      benefits: [
        'Fast distribution',
        'Immediate help',
        'No bureaucracy',
        'Fair distribution of small quantities',
        'Open source :)',
      ],
    }),
  }
</script>
