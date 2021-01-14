import WebFontLoader from 'webfontloader'

export default () => {
  WebFontLoader.load({
    google: {
      families: [
        'PT+Sans:400,700&display=swap',
        'Work+Sans:100,300,400,500,700,900&display=swap',
      ],
    },
  })
}