/* eslint-disable no-console */
export default function ({ $auth, $toast }) {
  $auth.onError((error, name, endpoint) => {
    console.error(name, error)
    $toast.clear()
    if (error.response && error.response.status === 401) {
      $toast.error('Login credentials invalid!')
    } else {
      const toast = $toast.error('Oups.. Something went wrong')
      toast.text('Please try again later').goAway(1000)
    }
  })
}
