const myMixin = {
    methods: {
      failmeg(message) {
        this.$notify.error({
          title: '错误',
          message
        });
      },
      winmeg(message) {
        this.$notify({
          title: '成功',
          message,
          type: 'success'
        });
      }
    },
  }
  export default myMixin;